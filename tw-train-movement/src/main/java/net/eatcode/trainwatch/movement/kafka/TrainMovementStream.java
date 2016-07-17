package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.TrainMovement;

public class TrainMovementStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String kafkaServers;
    private final TrainMovementProcessor movementProcessor;

    public TrainMovementStream(String kafkaServers, TrainMovementProcessor processor) {
        this.kafkaServers = kafkaServers;
        this.movementProcessor = processor;
    }

    public void processMessages() {
        try {
            startStreaming();
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    private void startStreaming() {
        log.info("Kafka servers: {}", kafkaServers);
        Properties props = new PropertiesBuilder().forStream(kafkaServers, "trainMovements").build();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(Serdes.String(), Serdes.ByteArray(), Topic.trainMovement);
        movements
                .mapValues(value -> (TrainMovement) SerializationUtils.deserialize(value))
                .process(() -> movementProcessor);

        log.info("Starting train movement stream...");
        new KafkaStreams(builder, props).start();
    }

}
