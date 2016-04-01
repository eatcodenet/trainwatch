package net.eatcode.trainwatch.movement.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import net.eatcode.trainwatch.movement.TrainMovement;

public class TrainMovementConsumer {

    private final String topicName = "trust-train-movements2";
    private final Properties props;
    private final KafkaConsumer<String, byte[]> consumer;

    public TrainMovementConsumer(String kafkaServers) {
        this.props = new PropertiesBuilder().forConsumer(kafkaServers).build();
        this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribeToTrainMovementTopic() {
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("Waiting...");
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records) {
                TrainMovement tm = KryoUtils.fromByteArray(record.value(), TrainMovement.class);
                System.out.println(tm);
            }
        }
    }

    public static void main(String[] args) {
        new TrainMovementConsumer("192.168.99.100:9092").subscribeToTrainMovementTopic();

    }
}
