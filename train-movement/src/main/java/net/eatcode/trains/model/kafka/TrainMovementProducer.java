package net.eatcode.trains.model.kafka;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import net.eatcode.trainwatch.nr.Crs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class TrainMovementProducer {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Kryo kryo = new Kryo();
        kryo.register(Crs.class);

        Properties props = new KafkaPropertiesBuilder().producerProperties("hp-z400:9092");
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 2; i++) {
            String random = System.currentTimeMillis() + "";
            Crs crs = new Crs();
            crs.crs = "crs" + random;
            crs.name = "name" + random;
            crs.lat = "lat" + random;
            crs.lon = "lon" + random;
            Output output = new Output(1024, 2048);
            kryo.writeObject(output, crs);
            output.flush();
            output.close();
            Thread.sleep(10);
            producer.send(new ProducerRecord<>("crs1", "key" + random, output.getBuffer()));
        }
        producer.close();

    }
}
