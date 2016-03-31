package net.eatcode.trainwatch.movement.kafka;

import net.eatcode.trainwatch.movement.TrustTrainMovement;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class TrainMovementConsumer {

    private final String topicName = "trust-train-movements";
    private final Properties props;
    private final KafkaConsumer<String, byte[]> consumer;

    public TrainMovementConsumer(String boostrapServers) {
        this.props = new KafkaPropertiesBuilder().consumerProperties(boostrapServers);
        this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribeToTrainMovementTopic() {
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("Waiting...");
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records) {
                TrustTrainMovement tm = KryoUtils.fromByteArray(record.value(), TrustTrainMovement.class);
                System.out.println(tm);
            }
        }
    }

    public static void main(String[] args) {
        new TrainMovementConsumer("192.168.99.100:9092").subscribeToTrainMovementTopic();

    }
}
