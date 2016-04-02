package net.eatcode.trainwatch.nr.hazelcast;

import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import net.eatcode.trainwatch.nr.TrustSchedule;

public class ScheduleSerializer implements StreamSerializer<TrustSchedule> {

    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    public void write(ObjectDataOutput objectDataOutput, TrustSchedule object) {
        Output output = new Output((OutputStream) objectDataOutput);
        Kryo kryo = KryoInstances.get();
        kryo.writeObject(output, object);
        output.flush(); // do not close!
        KryoInstances.release(kryo);
    }

    @Override
    public TrustSchedule read(ObjectDataInput objectDataInput) {
        Input input = new Input((InputStream) objectDataInput);
        Kryo kryo = KryoInstances.get();
        TrustSchedule result = kryo.readObject(input, TrustSchedule.class);
        KryoInstances.release(kryo);
        return result;
    }

    @Override
    public void destroy() {
        // empty
    }
}
