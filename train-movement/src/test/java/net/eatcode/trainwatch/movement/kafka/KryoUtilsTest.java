package net.eatcode.trainwatch.movement.kafka;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.eatcode.trainwatch.movement.TrainMovementCombinedMessage;

public class KryoUtilsTest {

    private final TrainMovementCombinedMessage msg = new TrainMovementCombinedMessage(
            new TrainMovementCombinedMessage.Header(), new TrainMovementCombinedMessage.Body());

    @Test
    public void toByteArray() {
        msg.body.train_id = "BigTrain";
        byte[] buffer = KryoUtils.toByteArray(msg);
        TrainMovementCombinedMessage msg2 = deserialize(buffer);
        assertThat(msg2.body.train_id, is("BigTrain"));
    }

    @Test
    public void fromByteArray() {
        msg.body.train_id = "SmallTrain";
        Output output = serialize(msg);
        TrainMovementCombinedMessage msg2 = KryoUtils.fromByteArray(output.getBuffer(),
                TrainMovementCombinedMessage.class);
        System.out.println(msg2);
        assertThat(msg2.body.train_id, is("SmallTrain"));
    }

    private Output serialize(TrainMovementCombinedMessage msg) {
        Kryo kryo = KryoInstances.get();
        Output output = new Output(1024, 16384);
        kryo.writeObject(output, msg);
        output.flush();
        KryoInstances.release(kryo);
        return output;
    }

    private TrainMovementCombinedMessage deserialize(byte[] buffer) {
        Kryo kryo = KryoInstances.get();
        TrainMovementCombinedMessage msg = kryo.readObject(new Input(buffer), TrainMovementCombinedMessage.class);
        KryoInstances.release(kryo);
        return msg;
    }
}
