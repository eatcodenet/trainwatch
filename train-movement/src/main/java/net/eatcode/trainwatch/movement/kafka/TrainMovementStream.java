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

import net.eatcode.trainwatch.movement.HzTrainMovementRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementRepo;

public class TrainMovementStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String kafkaServers;
    private final TrainMovementRepo trainMovementRepo;

    public TrainMovementStream(String kafkaServers, TrainMovementRepo trainMovementRepo) {
        this.kafkaServers = kafkaServers;
        this.trainMovementRepo = trainMovementRepo;
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
                .process(() -> new TrainMovementProcessor(trainMovementRepo));

        log.info("Starting train movement stream...");
        new KafkaStreams(builder, props).start();
    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String hazelcastServers = args[1];
        TrainMovementRepo trainMovementRepo = new HzTrainMovementRepo(hazelcastServers);
        new TrainMovementStream(kafkaServers, trainMovementRepo).process();
    }

}
