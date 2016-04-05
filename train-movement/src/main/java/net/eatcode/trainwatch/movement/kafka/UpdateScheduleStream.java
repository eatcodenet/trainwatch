package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;

import net.eatcode.trainwatch.movement.SimpleTrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementCombinedMessage;

public class UpdateScheduleStream {

    public void process() {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.JOB_ID_CONFIG, "simplifyMovements");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2181");
        streamsConfiguration.put(StreamsConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        streamsConfiguration.put(StreamsConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        streamsConfiguration.put(StreamsConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        streamsConfiguration.put(StreamsConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        Deserializer<String> kDeserializer = new StringDeserializer();
        Deserializer<byte[]> vDeserializer = new ByteArrayDeserializer();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, "trust-train-movements");
        movements.mapValues(new ValueMapper<byte[], SimpleTrainMovement>() {

            @Override
            public SimpleTrainMovement apply(byte[] value) {
                TrainMovementCombinedMessage msg = KryoUtils.fromByteArray(value, TrainMovementCombinedMessage.class);
                SimpleTrainMovement simple = addScheduleInfo(msg);
                System.out.println(simple);
                return simple;
            }

            private SimpleTrainMovement addScheduleInfo(TrainMovementCombinedMessage msg) {
                return new SimpleTrainMovement(msg.body.train_id, "", "", "", "");
            }

        });

        KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();
    }

    public static void main(String[] args) {
        new UpdateScheduleStream().process();
    }

}
