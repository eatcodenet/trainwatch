package net.eatcode.trains.model.hazelcast;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import net.eatcode.trains.model.Crs;

class KryoInstances {

    private static KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.register(Crs.class);
        return kryo;
    };

    private static KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    static Kryo get() {
        return pool.borrow();
    }

    static void release(Kryo instance) {
        pool.release(instance);
    }
}
