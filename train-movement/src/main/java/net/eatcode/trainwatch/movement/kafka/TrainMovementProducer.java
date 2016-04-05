package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.GsonTrainMovementParser;
import net.eatcode.trainwatch.movement.TrainMovementCombinedMessage;
import net.eatcode.trainwatch.movement.TrainMovementStomp;

public class TrainMovementProducer extends CreateTopic {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String topicName = "trust-train-movements";
    private final KafkaProducer<String, byte[]> producer;
    private final GsonTrainMovementParser parser = new GsonTrainMovementParser();

    public TrainMovementProducer(String kafkaServers) {
        Properties props = new PropertiesBuilder().forProducer(kafkaServers).withByteArrayValueSerializer().build();
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessages(String nrUsername, String nrPassword) {
        TrainMovementStomp stomp = new TrainMovementStomp(nrUsername, nrPassword);
        stomp.subscribe(movements -> {
            parser.parseArray(movements).forEach((tm) -> sendMessage(tm));
        });
    }

    private void sendMessage(TrainMovementCombinedMessage m) {
        log.debug("{} {} {}", m.header.msg_type, m.body.train_id, m.body.train_service_code);
        producer.send(new ProducerRecord<>(topicName, m.body.train_service_code, KryoUtils.toByteArray(m)));
    }

    public static void main(String[] args) {

        String networkRailUsername = args[0];
        String networkRailPassword = args[1];
        System.out.println("username = " + networkRailUsername);
        new TrainMovementProducer("192.168.99.100:9092").produceMessages(networkRailUsername, networkRailPassword);
    }
}
