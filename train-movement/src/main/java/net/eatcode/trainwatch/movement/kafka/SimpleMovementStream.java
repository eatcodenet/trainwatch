package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;

import net.eatcode.trainwatch.movement.ScheduleLookup;
import net.eatcode.trainwatch.movement.SimpleTrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementCombinedMessage;

public class SimpleMovementStream {

    private final ScheduleLookup scheduleLookup;

    public SimpleMovementStream(ScheduleLookup scheduleLookup) {
        this.scheduleLookup = scheduleLookup;
    }

    public void process() {
        Properties props = new PropertiesBuilder().forStream("192.168.99.100:9092").build();

        Deserializer<String> kDeserializer = new StringDeserializer();
        Deserializer<byte[]> vDeserializer = new ByteArrayDeserializer();

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, byte[]> movements = builder.stream(kDeserializer, vDeserializer, "trust-train-movements");
        movements.mapValues(new ValueMapper<byte[], SimpleTrainMovement>() {

            @Override
            public SimpleTrainMovement apply(byte[] value) {
                TrainMovementCombinedMessage msg = KryoUtils.fromByteArray(value, TrainMovementCombinedMessage.class);
                SimpleTrainMovement simple = createSimpleTrainMovement(msg);
                System.out.println(simple);
                return simple;
            }



        });

        KafkaStreams streams = new KafkaStreams(builder, props);
        streams.start();
    }

    private SimpleTrainMovement createSimpleTrainMovement(TrainMovementCombinedMessage msg) {
        return new SimpleTrainMovement(msg.body.train_id, "", "", "", "");
    }

    public static void main(String[] args) {
        ScheduleLookup lookup = null;
        new SimpleMovementStream(lookup).process();
    }

}
