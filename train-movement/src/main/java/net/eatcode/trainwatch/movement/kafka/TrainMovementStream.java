package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trustMessages;

import java.time.LocalTime;
import java.util.Properties;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.FallbackScheduleLookup;
import net.eatcode.trainwatch.movement.HazelcastTrainActivationRepo;
import net.eatcode.trainwatch.movement.HazelcastTrainMovementRepo;
import net.eatcode.trainwatch.movement.ScheduleLookup;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementRepo;
import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;
import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastDayScheduleRepo;

public class TrainMovementStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ScheduleLookup scheduleLookup;
    private final String kafkaServers;
    private final TrainMovementRepo trainMovementRepo;

    public TrainMovementStream(String kafkaServers, ScheduleLookup scheduleLookup,
            TrainMovementRepo trainMovementRepo) {
        this.kafkaServers = kafkaServers;
        this.scheduleLookup = scheduleLookup;
        this.trainMovementRepo = trainMovementRepo;
    }

    public void process() {
        log.info("Kafka servers: {}", kafkaServers);
        Properties props = new PropertiesBuilder().forStream(kafkaServers).build();

        Deserializer<String> kDeserializer = new StringDeserializer();
        Deserializer<byte[]> vDeserializer = new ByteArrayDeserializer();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, trustMessages.topicName());
        movements.mapValues(value -> {
            return createTrainMovement(KryoUtils.fromByteArray(value, TrustTrainMovementMessage.class));
        }).filter((key, value) -> {
            return (value != null);
        }).process(() -> new PutInRepoProcessor(this.trainMovementRepo));

        new KafkaStreams(builder, props).start();
    }

    private TrainMovement createTrainMovement(TrustTrainMovementMessage message) {
        DaySchedule ds = scheduleLookup.lookup(message);
        if (ds == null)
            return null;
        return new TrainMovement(message.body.train_id, location(ds.origin), time(ds.departure),
                location(ds.destination), time(ds.arrival), delay(message.body.timetable_variation), ds.estimated);
    }

    private String location(Location location) {
        return location.description + " (" + location.crs + ")";
    }

    private String time(LocalTime departure) {
        return departure.toString();
    }

    private Integer delay(String timetable_variation) {
        return Integer.parseInt(timetable_variation);
    }

    public static void main(String[] args) {
        String kafkaServers = "docker.local:9092";
        String hazelcastServers = args[1];
        ScheduleLookup lookup = new FallbackScheduleLookup(new HazelcastTrainActivationRepo(hazelcastServers),
                new HazelcastDayScheduleRepo(hazelcastServers));
        TrainMovementRepo trainMovementRepo = new HazelcastTrainMovementRepo(hazelcastServers);
        new TrainMovementStream(kafkaServers, lookup, trainMovementRepo).process();
    }

}
