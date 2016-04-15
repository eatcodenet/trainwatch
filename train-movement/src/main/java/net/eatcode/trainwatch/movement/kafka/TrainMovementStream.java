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
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;
import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastDayScheduleRepo;

public class TrainMovementStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ScheduleLookup scheduleLookup;
    private final String boostrapServers;

    public TrainMovementStream(String boostrapServers, ScheduleLookup scheduleLookup) {
        this.boostrapServers = boostrapServers;
        this.scheduleLookup = scheduleLookup;
    }

    public void process() {
        Properties props = new PropertiesBuilder().forStream(boostrapServers).build();

        Deserializer<String> kDeserializer = new StringDeserializer();
        Deserializer<byte[]> vDeserializer = new ByteArrayDeserializer();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, "trust-train-movements");
        movements.mapValues(value -> {
            TrustTrainMovementMessage msg = KryoUtils.fromByteArray(value, TrustTrainMovementMessage.class);
            return createTrainMovement(msg);
        }).filter((key, value) -> {
            if (value != null)
                log.debug("{}", value);
            return (value == null);
        });

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

    private String delay(String timetable_variation) {
        return timetable_variation + " mins";
    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String hazelcastServers = args[1];
        ScheduleLookup lookup = new FallbackScheduleLookup(new HazelcastTrainActivationRepo(hazelcastServers),
                new HazelcastDayScheduleRepo(hazelcastServers));
        new TrainMovementStream(kafkaServers, lookup).process();
    }

}
