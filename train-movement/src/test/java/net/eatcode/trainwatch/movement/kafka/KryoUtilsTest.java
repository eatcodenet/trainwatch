package net.eatcode.trainwatch.movement.kafka;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.eatcode.trainwatch.movement.TrainMovementMessage;

public class KryoUtilsTest {

    private final TrainMovementMessage ttm = new TrainMovementMessage(
            new TrainMovementMessage.Header(), new TrainMovementMessage.Body());

    @Test
    public void toByteArray() {
        ttm.body.train_id = "BigTrain";
        byte[] buffer = KryoUtils.toByteArray(ttm);
        TrainMovementMessage ttm2 = deserialize(buffer);
        assertThat(ttm2.body.train_id, is("BigTrain"));
    }

    @Test
    public void fromByteArray() {
        ttm.body.train_id = "SmallTrain";
        Output output = serialize(ttm);
        TrainMovementMessage ttm2 = KryoUtils.fromByteArray(output.getBuffer(),
                TrainMovementMessage.class);
        System.out.println(ttm2);
        assertThat(ttm2.body.train_id, is("SmallTrain"));
    }

    private Output serialize(TrainMovementMessage ttm2) {
        Kryo kryo = KryoInstances.get();
        Output output = new Output(1024, 16384);
        kryo.writeObject(output, ttm);
        output.flush();
        KryoInstances.release(kryo);
        return output;
    }

    private TrainMovementMessage deserialize(byte[] buffer) {
        Kryo kryo = KryoInstances.get();
        TrainMovementMessage ttm2 = kryo.readObject(new Input(buffer),
                TrainMovementMessage.class);
        KryoInstances.release(kryo);
        return ttm2;
    }
}
