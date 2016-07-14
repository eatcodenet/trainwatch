package net.eatcode.trainwatch.movement.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.trust.TrustMovementMessage;

public class TrainMovementConsumer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Properties props;
    private final KafkaConsumer<String, byte[]> consumer;

    public TrainMovementConsumer(String kafkaServers) {
        this.props = new PropertiesBuilder().forConsumer(kafkaServers).withByteArrayValueDeserializer().build();
        this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribeToTrainMovementTopic() {
        consumer.subscribe(Arrays.asList(Topic.trainMovement.topicName()));
        log.debug("Waiting...");
        while (true) {
            consumer.poll(200).forEach(record -> consume(record));
        }
    }

    private void consume(ConsumerRecord<String, byte[]> r) {
        TrustMovementMessage tm = SerializationUtils.deserialize(r.value());
        log.debug("{}", tm);
    }

    public static void main(String[] args) {
        new TrainMovementConsumer("trainwatch.eatcode.net:9092").subscribeToTrainMovementTopic();
    }
}
