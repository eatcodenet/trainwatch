package net.eatcode.trainwatch.movement.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.TrainMovement;

public class TrainMovementSpikeApp {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String kafkaServers;

    public TrainMovementSpikeApp(String kafkaServers) {
        this.kafkaServers = kafkaServers;
    }

    public void process() {
        log.info("Kafka bootstrap servers: {}", kafkaServers);
        Properties properties = new PropertiesBuilder().forConsumer(kafkaServers).build();
        properties.put("group.id", "net.eatcode3");
        properties.put("auto.offset.reset", "earliest");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(Topic.trainMovement.topicName()));

        log.info("Starting train movement consumer...");
        for (int i = 0; i < 10; i++) {
            ConsumerRecords<String, byte[]> poll = consumer.poll(1000);
            for (ConsumerRecord<String, byte[]> rec : poll.records(Topic.trainMovement.topicName())) {
                TrainMovement tm = SerializationUtils.deserialize(rec.value());
                log.info("{}", tm);
            }
        }
        log.info("done");
        consumer.close();
    }

    public static void main(String[] args) {
        new TrainMovementSpikeApp("localhost:9092").process();
    }
}
