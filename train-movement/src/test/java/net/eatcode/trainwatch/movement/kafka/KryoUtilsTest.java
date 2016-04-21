package net.eatcode.trainwatch.movement.kafka;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.eatcode.trainwatch.movement.trust.TrustMovementMessage;

public class KryoUtilsTest {

    private final TrustMovementMessage msg = new TrustMovementMessage(
            new TrustMovementMessage.Header(), new TrustMovementMessage.Body());

    @Test
    public void toByteArray() {
        msg.body.train_id = "BigTrain";
        byte[] buffer = KryoUtils.toByteArray(msg);
        TrustMovementMessage msg2 = deserialize(buffer);
        assertThat(msg2.body.train_id, is("BigTrain"));
    }

    @Test
    public void fromByteArray() {
        msg.body.train_id = "SmallTrain";
        Output output = serialize(msg);
        TrustMovementMessage msg2 = KryoUtils.fromByteArray(output.getBuffer(),
                TrustMovementMessage.class);
        assertThat(msg2.body.train_id, is("SmallTrain"));
    }

    private Output serialize(TrustMovementMessage msg) {
        Kryo kryo = KryoInstances.get(msg.getClass());
        Output output = new Output(1024, 16384);
        kryo.writeObject(output, msg);
        output.flush();
        KryoInstances.release(kryo);
        return output;
    }

    private TrustMovementMessage deserialize(byte[] buffer) {
        Kryo kryo = KryoInstances.get(TrustMovementMessage.class);
        TrustMovementMessage msg = kryo.readObject(new Input(buffer), TrustMovementMessage.class);
        KryoInstances.release(kryo);
        return msg;
    }
}
