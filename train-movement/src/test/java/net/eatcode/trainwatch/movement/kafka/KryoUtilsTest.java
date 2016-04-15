package net.eatcode.trainwatch.movement.kafka;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;

public class KryoUtilsTest {

    private final TrustTrainMovementMessage msg = new TrustTrainMovementMessage(
            new TrustTrainMovementMessage.Header(), new TrustTrainMovementMessage.Body());

    @Test
    public void toByteArray() {
        msg.body.train_id = "BigTrain";
        byte[] buffer = KryoUtils.toByteArray(msg);
        TrustTrainMovementMessage msg2 = deserialize(buffer);
        assertThat(msg2.body.train_id, is("BigTrain"));
    }

    @Test
    public void fromByteArray() {
        msg.body.train_id = "SmallTrain";
        Output output = serialize(msg);
        TrustTrainMovementMessage msg2 = KryoUtils.fromByteArray(output.getBuffer(),
                TrustTrainMovementMessage.class);
        assertThat(msg2.body.train_id, is("SmallTrain"));
    }

    private Output serialize(TrustTrainMovementMessage msg) {
        Kryo kryo = KryoInstances.get(msg.getClass());
        Output output = new Output(1024, 16384);
        kryo.writeObject(output, msg);
        output.flush();
        KryoInstances.release(kryo);
        return output;
    }

    private TrustTrainMovementMessage deserialize(byte[] buffer) {
        Kryo kryo = KryoInstances.get(TrustTrainMovementMessage.class);
        TrustTrainMovementMessage msg = kryo.readObject(new Input(buffer), TrustTrainMovementMessage.class);
        KryoInstances.release(kryo);
        return msg;
    }
}
