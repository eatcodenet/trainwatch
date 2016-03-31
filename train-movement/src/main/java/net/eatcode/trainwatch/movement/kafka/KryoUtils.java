package net.eatcode.trainwatch.movement.kafka;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;

class KryoUtils {

    static <T> byte[] toByteArray(T object) {
        Kryo kryo = KryoInstances.get();
        Output output = new Output();
        kryo.writeObject(output, object);
        output.flush();
        KryoInstances.release(kryo);
        return output.getBuffer();
    }

    static <T> T fromByteArray(byte[] data, Class<T> clazz) {
        Input input = new Input(new ByteArrayInputStream(data));
        Kryo kryo = KryoInstances.get();
        T result = kryo.readObject(input, clazz);
        KryoInstances.release(kryo);
        return result;
    }

}
