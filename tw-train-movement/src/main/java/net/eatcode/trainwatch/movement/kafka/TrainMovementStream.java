package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trainMovement;

import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes.ByteArraySerde;
import org.apache.kafka.common.serialization.Serdes.StringSerde;
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

    public void process() {
        log.info("Kafka servers: {}", kafkaServers);
        Properties props = new PropertiesBuilder().forStream(kafkaServers, "trainMovements").build();

        Serde<String> kDeserializer = new StringSerde();
        Serde<byte[]> vDeserializer = new ByteArraySerde();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, trainMovement.topicName());
        movements
                .mapValues(value -> (TrainMovement) SerializationUtils.deserialize(value))
                .process(() -> movementProcessor);

        log.info("Starting train movement stream...");
        new KafkaStreams(builder, props).start();
    }

}
