package net.eatcode.trainwatch.nr.hazelcast;

import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

/**
 * Mostly cribbed from
 * http://blog.hazelcast.com/comparing-serialization-methods/
 */
public abstract class CommonSerializer<T> implements StreamSerializer<T> {

    protected abstract Class<T> getClassToSerialize();

    @Override
    public void write(ObjectDataOutput objectDataOutput, T object) {
        Output output = new Output((OutputStream) objectDataOutput);
        Kryo kryo = KryoInstances.get();
        kryo.writeObject(output, object);
        output.flush(); // do not close!
        KryoInstances.release(kryo);
    }

    @Override
    public T read(ObjectDataInput objectDataInput) {
        Input input = new Input((InputStream) objectDataInput);
        Kryo kryo = KryoInstances.get();
        T result = kryo.readObject(input, getClassToSerialize());
        KryoInstances.release(kryo);
        return result;
    }

    @Override
    public void destroy() {
        // empty
    }
}
