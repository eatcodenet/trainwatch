package net.eatcode.trainwatch.movement.kafka;

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
import net.eatcode.trainwatch.movement.ScheduleLookup;
import net.eatcode.trainwatch.movement.SimpleTrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementCombinedMessage;
import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastDayScheduleRepo;

public class SimpleMovementStream {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ScheduleLookup scheduleLookup;

    public SimpleMovementStream(ScheduleLookup scheduleLookup) {
        this.scheduleLookup = scheduleLookup;
    }

    public void process() {
        Properties props = new PropertiesBuilder().forStream("192.168.99.100:9092").build();

        Deserializer<String> kDeserializer = new StringDeserializer();
        Deserializer<byte[]> vDeserializer = new ByteArrayDeserializer();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, "trust-train-movements");
        movements.mapValues(value -> {
            TrainMovementCombinedMessage msg = KryoUtils.fromByteArray(value, TrainMovementCombinedMessage.class);
            return createSimpleTrainMovement(msg);
        }).filter((key, value) -> {
            if (value != null) log.debug("{}", value);
            return (value == null);
        });

        new KafkaStreams(builder, props).start();
    }

    private SimpleTrainMovement createSimpleTrainMovement(TrainMovementCombinedMessage message) {
        DaySchedule ds = scheduleLookup.lookup(message);
        if (ds == null)
            return null;
        return new SimpleTrainMovement(message.body.train_id, location(ds.origin), time(ds.departure),
                location(ds.destination), time(ds.arrival), delay(message.body.timetable_variation), ds.estimated);
    }

    private String location(Location location) {
        return location.description + " (" + location.crs + ")";
    }

    private String time(LocalTime departure) {
        return departure.toString();
    }

    private String delay(String timetable_variation) {
        return timetable_variation + " mins";
    }

    public static void main(String[] args) {
        ScheduleLookup lookup = new FallbackScheduleLookup(new HazelcastTrainActivationRepo(),
                new HazelcastDayScheduleRepo());
        new SimpleMovementStream(lookup).process();
    }

}
