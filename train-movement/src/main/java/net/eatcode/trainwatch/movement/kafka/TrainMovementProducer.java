package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import net.eatcode.trainwatch.movement.GsonTrainMovementParser;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementStomp;

public class TrainMovementProducer extends CreateTopic {

    private final String topicName = "trust-train-movements";
    private final KafkaProducer<String, String> producer;
    private final GsonTrainMovementParser parser = new GsonTrainMovementParser();

    public TrainMovementProducer(String kafkaServers) {
        Properties props = new PropertiesBuilder().forProducer(kafkaServers).build();
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessages(String nrUsername, String nrPassword) {
        TrainMovementStomp stomp = new TrainMovementStomp(nrUsername, nrPassword);
        stomp.subscribe(movements -> {
            parser.parseArray(movements).forEach((tm) -> sendMessage(tm));
        });
    }

    private Future<RecordMetadata> sendMessage(TrainMovement tm) {
        return producer.send(new ProducerRecord<>(topicName, tm.body.train_service_code, tm.toString()));
    }

    public static void main(String[] args) {

        String networkRailUsername = args[0];
        String networkRailPassword = args[1];

        // new CreateTopic().createTopic(topicName);
        System.out.println("username = " + networkRailUsername);
        new TrainMovementProducer("192.168.99.100:9092").produceMessages(networkRailUsername, networkRailPassword);
    }
}
