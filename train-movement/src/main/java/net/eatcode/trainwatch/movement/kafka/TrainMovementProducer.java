package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import net.eatcode.trainwatch.movement.TrainMovementStomp;
import net.eatcode.trainwatch.movement.TrainMovementListener;

public class TrainMovementProducer extends CreateTopic {

    private static final String topicName = "trust-train-movements2";
    private final Properties props;
    private final KafkaProducer<String, String> producer;

    public TrainMovementProducer(String boostrapServers) {
        this.props = new KafkaPropertiesBuilder().producerProperties(boostrapServers);
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessages(String nrUsername, String nrPassword) {
        TrainMovementStomp stomp = new TrainMovementStomp(nrUsername, nrPassword);
        stomp.subscribe(new TrainMovementListener() {
            @Override
            public void onTrainMovement(String movements) {
                String key = null;
                producer.send(new ProducerRecord<>(topicName, key, movements));
            }
        });
    }

    public void close() {
        this.producer.close();
    }

    public static void main(String[] args) {

        String networkRailUsername = args[0];
        String networkRailPassword= args[1];

        // new CreateTopic().createTopic(topicName);

        System.out.println("username = " + networkRailUsername);
        new TrainMovementProducer("192.168.99.100:9092").produceMessages(networkRailUsername, networkRailPassword);
    }
}
