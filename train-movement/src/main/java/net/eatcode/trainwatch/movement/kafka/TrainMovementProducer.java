package net.eatcode.trainwatch.movement.kafka;

import com.esotericsoftware.kryo.Kryo;
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
    private final Kryo kryo = new Kryo();

    public TrainMovementProducer(String boostrapServers) {
        this.props = new KafkaPropertiesBuilder().producerProperties(boostrapServers);
        this.producer = new KafkaProducer<>(props);
        kryo.register(TrustTrainMovement.class);
    }

    public void subscribeToMovements(String username, String password) {
        TrainMovementStompSubscription stompSubscription = new TrainMovementStompSubscription(username, password);
        stompSubscription.subscribe(new TrustTrainMovementListener() {
            @Override
            public void onTrainMovement(TrustTrainMovement tm) {
                byte[] data = serializeToBytes(tm);
                producer.send(new ProducerRecord<>("crs1", "key" + random, output.getBuffer()));
            }
        });
    }

    private byte[] serializeToBytes(TrustTrainMovement tm) {
        kryo.
    }

    public void close() {
        this.producer.close();
    }
}
