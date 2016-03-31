package net.eatcode.trains.model.kafka;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import net.eatcode.trainwatch.nr.Crs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class TrainMovementConsumer {

    public static void main(String[] args) throws InterruptedException {
        Kryo kryo = new Kryo();
        kryo.register(Crs.class);

        Properties props = new KafkaPropertiesBuilder().consumerProperties("hp-z400:9092");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("crs1"));
        System.out.println("Waiting...");
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(100);
            for (ConsumerRecord<String, byte[]> record : records) {
                Input input = new Input(record.value(), 0, record.value().length);
                Crs crs = kryo.readObject(input, Crs.class);
                System.out.println(crs);
            }
        }
    }
}
