package net.eatcode.trainwatch.nr.hazelcast;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoUtils {

    private static final int _1K = 1024;
    private static final int _16K = 16384;

    public static <T> byte[] toByteArray(T object) {
        Kryo kryo = KryoInstances.get(object.getClass());
        try (Output output = new Output(_1K, _16K)) {
            kryo.writeObject(output, object);
            return output.getBuffer();
        } finally {
            KryoInstances.release(kryo);
        }
    }

    public static <T> T fromByteArray(byte[] data, Class<T> clazz) {
        Kryo kryo = KryoInstances.get(clazz);
        try (Input input = new Input(data)) {
            return kryo.readObject(input, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            KryoInstances.release(kryo);
        }

    }

}
