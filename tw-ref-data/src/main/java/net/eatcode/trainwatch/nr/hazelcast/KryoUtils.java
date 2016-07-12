package net.eatcode.trainwatch.nr.hazelcast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoUtils {

    private static final int _2K = 2048;
    private static final int _32K = 32768;

    public static <T> byte[] toByteArray(T object) {
        Kryo kryo = KryoInstances.get(object.getClass());
        kryo.register(LocalDate.class);
        kryo.register(LocalTime.class);
        kryo.register(LocalDateTime.class);
        try (Output output = new Output(_2K, _32K)) {
            kryo.writeObject(output, object);
            return output.getBuffer();
        } finally {
            KryoInstances.release(kryo);
        }
    }

    public static <T> T fromByteArray(byte[] data, Class<T> clazz) {
        Kryo kryo = KryoInstances.get(clazz);
        kryo.register(LocalDate.class);
        kryo.register(LocalTime.class);
        kryo.register(LocalDateTime.class);
        try (Input input = new Input(data)) {
            return kryo.readObject(input, clazz);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            KryoInstances.release(kryo);
        }
       
    }

}
