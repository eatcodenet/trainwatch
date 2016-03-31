package net.eatcode.trainwatch.movement.kafka;

import net.eatcode.trainwatch.movement.TrainMovementStompSubscription;
import net.eatcode.trainwatch.movement.TrustTrainMovement;
import net.eatcode.trainwatch.movement.TrustTrainMovementListener;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class TrainMovementProducer {

    private final String topicName = "trust-train-movements";
    private final Properties props;
    private final KafkaProducer<String, byte[]> producer;

    public TrainMovementProducer(String boostrapServers) {
        this.props = new KafkaPropertiesBuilder().producerProperties(boostrapServers);
        this.producer = new KafkaProducer<>(props);
    }

    public void produceTrainMovementMessagesFromStomp(String username, String password) {
        TrainMovementStompSubscription stompSubscription = new TrainMovementStompSubscription(username, password);
        stompSubscription.subscribe(new TrustTrainMovementListener() {
            @Override
            public void onTrainMovement(TrustTrainMovement tm) {
                System.out.println(tm);
                byte[] data = KryoUtils.toByteArray(tm);
                producer.send(new ProducerRecord<>(topicName, tm.body.actual_timestamp, data));
            }
        });
    }

    public void close() {
        this.producer.close();
    }

    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];
        System.out.println("username = " + username);
        new TrainMovementProducer("192.168.99.100:9092")
                .produceTrainMovementMessagesFromStomp(username, password);
    }
}
