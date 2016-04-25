package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trainMovement;

import java.util.Properties;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.LiveDeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.hazelcast.HzLiveDeparturesRepo;

public class LiveDepartureStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String kafkaServers;
    private final LiveDeparturesRepo liveDeparturesRepo;

    public LiveDepartureStream(String kafkaServers, LiveDeparturesRepo liveDeparturesRepo) {
        this.kafkaServers = kafkaServers;
        this.liveDeparturesRepo = liveDeparturesRepo;
    }

    public void process() {
        log.info("Kafka servers: {}", kafkaServers);
        Properties props = new PropertiesBuilder().forStream(kafkaServers).build();

        Deserializer<String> kDeserializer = new StringDeserializer();
        Deserializer<byte[]> vDeserializer = new ByteArrayDeserializer();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, trainMovement.topicName());
        movements
                .mapValues(value -> KryoUtils.fromByteArray(value, TrainMovement.class))

                .mapValues(tm -> new TrainDeparture(tm.trainId(), tm.origin(), tm.departure(), tm.destination(),
                        tm.arrival()))
                .process(() -> new LiveDepartureProcessor(liveDeparturesRepo));

        log.info("Starting live departures stream...");
        KafkaStreams streams = new KafkaStreams(builder, props);
        streams.setUncaughtExceptionHandler((t, e) -> log.error(e.getMessage(), e));
        streams.start();
    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String hazelcastServers = args[1];
        new LiveDepartureStream(kafkaServers, new HzLiveDeparturesRepo(hazelcastServers)).process();

    }

}
