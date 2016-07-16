package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trainMovement;

import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.MovementRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.hazelcast.HzActivationRepo;
import net.eatcode.trainwatch.movement.hazelcast.HzMovementRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class TrainMovementStream {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String kafkaServers;
    private final TrainMovementProcessor movementProcessor;

    public TrainMovementStream(String kafkaServers, TrainMovementProcessor processor) {
        this.kafkaServers = kafkaServers;
        this.movementProcessor = processor;
    }

    public void process() {
        try {
            log.info("Kafka servers: {}", kafkaServers);
            Properties props = new PropertiesBuilder().forStream(kafkaServers, "trainMovements").build();

            Serde<String> kSerde = Serdes.serdeFrom(new StringSerializer(), new StringDeserializer());
            Serde<byte[]> vSerde = Serdes.serdeFrom(new ByteArraySerializer(), new ByteArrayDeserializer());

            KStreamBuilder builder = new KStreamBuilder();
            KStream<String, byte[]> movements = builder.stream(kSerde, vSerde, trainMovement.topicName());
            movements
                    .mapValues(value -> (TrainMovement) SerializationUtils.deserialize(value))
                    .process(() -> movementProcessor);

            log.info("Starting train movement stream...");
            new KafkaStreams(builder, props).start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        HazelcastInstance hzClient = new HzClientBuilder().build("localhost");
        ActivationRepo activationRepo = new HzActivationRepo(hzClient);
        MovementRepo movementRepo = new HzMovementRepo(hzClient);
        new TrainMovementStream("localhost:9092", new TrainMovementProcessor(movementRepo, activationRepo)).process();
    }
}
