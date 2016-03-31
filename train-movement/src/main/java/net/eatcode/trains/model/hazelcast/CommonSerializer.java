package net.eatcode.trains.model.hazelcast;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;

/**
 * Mostly cribbed from http://blog.hazelcast.com/comparing-serialization-methods/
 */
public abstract class CommonSerializer<T> implements StreamSerializer<T> {

    Class<T> clazz = getType();

    private Class<T> getType() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public void write(ObjectDataOutput objectDataOutput, T object) throws IOException {
        Kryo kryo = KryoInstances.get();
        Output output = new Output((OutputStream) objectDataOutput);
        kryo.writeObject(output, object);
        output.flush();
        KryoInstances.release(kryo);
    }

    @Override
    public T read(ObjectDataInput objectDataInput) throws IOException {
        Input input = new Input((InputStream) objectDataInput);
        Kryo kryo = KryoInstances.get();
        T result = kryo.readObject(input, clazz);
        KryoInstances.release(kryo);
        return result;
    }

    @Override
    public void destroy() {
        // empty
    }
}
