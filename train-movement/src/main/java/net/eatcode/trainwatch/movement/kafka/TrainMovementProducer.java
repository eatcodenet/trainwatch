package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import net.eatcode.trainwatch.movement.GsonTrainMovementParser;
import net.eatcode.trainwatch.movement.HzTrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovementStomp;
import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;

public class TrainMovementProducer {

    private final KafkaProducer<String, byte[]> producer;
    private final GsonTrainMovementParser parser = new GsonTrainMovementParser();
    private final TrainActivationRepo activationRepo;

    public TrainMovementProducer(String kafkaServers, TrainActivationRepo activationRepo) {
        this.activationRepo = activationRepo;
        Properties props = new PropertiesBuilder().forProducer(kafkaServers).withByteArrayValueSerializer().build();
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessages(String nrUsername, String nrPassword) {
        TrainMovementStomp stomp = new TrainMovementStomp(nrUsername, nrPassword);
        stomp.subscribe(movements -> {
            parser.parseArray(movements).forEach((tm) -> sendMessage(tm));
        });
    }

    private void sendMessage(TrustTrainMovementMessage m) {
        if (m.isActivation()) {
            activationRepo.putScheduleId(m.body.train_id, m.body.train_uid);
        } else {
            producer.send(new ProducerRecord<>(Topic.trustMessages.topicName(), m.body.train_id,
                    KryoUtils.toByteArray(m)));
        }
    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String zookeeperServers = args[1];
        String hazelcastServers = args[2];
        String networkRailUsername = args[3];
        String networkRailPassword = args[4];
        checkTopicExists(zookeeperServers);
        TrainActivationRepo repo = new HzTrainActivationRepo(hazelcastServers);
        new TrainMovementProducer(kafkaServers, repo).produceMessages(networkRailUsername, networkRailPassword);
    }

    private static void checkTopicExists(String zookeeperServers) {
        Topics topics = new Topics(zookeeperServers);
        if (!topics.topicExists(Topic.trustMessages)) {
            throw new RuntimeException("Topic does not exist: " + Topic.trustMessages);
        }

    }
}
