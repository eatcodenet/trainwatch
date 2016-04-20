package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trustMessages;

import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.HzScheduleLookup;
import net.eatcode.trainwatch.movement.HzTrainActivationRepo;
import net.eatcode.trainwatch.movement.HzTrainMovementRepo;
import net.eatcode.trainwatch.movement.ScheduleLookup;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementRepo;
import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ScheduleLookup scheduleLookup;
    private final String kafkaServers;
    private final TrainMovementRepo trainMovementRepo;
    private final LocationRepo locationRepo;

    public TrainMovementStream(String kafkaServers, ScheduleLookup scheduleLookup, TrainMovementRepo trainMovementRepo,
            LocationRepo locationRepo) {
        this.kafkaServers = kafkaServers;
        this.scheduleLookup = scheduleLookup;
        this.trainMovementRepo = trainMovementRepo;
        this.locationRepo = locationRepo;
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
        }).process(() -> new PutInRepoProcessor(this.trainMovementRepo));
        log.info("Starting stream...");
        new KafkaStreams(builder, props).start();
    }

    private TrainMovement createTrainMovement(TrustTrainMovementMessage msg) {
        if (!msg.header.msg_type.equals("0003"))
            return null;
        Optional<Schedule> schedule = scheduleLookup.lookup(msg);
        if (schedule.isPresent())
            log.info("schedule:" + schedule);
        return schedule.map(s -> {
            Location current = locationRepo.getByStanox(msg.body.loc_stanox);
            return new TrainMovement(msg.body.train_id, msg.body.actual_timestamp, current,
                    msg.body.timetable_variation, s);
        }).orElse(null);

    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String hazelcastServers = args[1];
        ScheduleLookup scheduleLookup = new HzScheduleLookup(new HzTrainActivationRepo(hazelcastServers),
                new HzScheduleRepo(hazelcastServers));
        TrainMovementRepo trainMovementRepo = new HzTrainMovementRepo(hazelcastServers);
        LocationRepo locationRepo = new HzLocationRepo(hazelcastServers);
        new TrainMovementStream(kafkaServers, scheduleLookup, trainMovementRepo, locationRepo).process();
    }

}
