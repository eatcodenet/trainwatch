package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trustMessages;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;

public class TrainMovementConsumer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Properties props;
    private final KafkaConsumer<String, byte[]> consumer;

    public TrainMovementConsumer(String kafkaServers) {
        this.props = new PropertiesBuilder().forConsumer(kafkaServers).withByteArrayValueDeserializer().build();
        this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribeToTrainMovementTopic() {
        consumer.subscribe(Arrays.asList(trustMessages.topicName()));
        log.debug("Waiting...");
        while (true) {
            consumer.poll(100).forEach(record -> consume(record));
        }
    }

    private void consume(ConsumerRecord<String, byte[]> r) {
        TrustTrainMovementMessage tm = KryoUtils.fromByteArray(r.value(),
        TrustTrainMovementMessage.class);
        log.info("{}", tm);
    }

    public static void main(String[] args) {
        new TrainMovementConsumer("trainwatch.eatcode.net:9092").subscribeToTrainMovementTopic();
    }
}
