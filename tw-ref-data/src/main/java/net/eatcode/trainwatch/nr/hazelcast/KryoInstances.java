package net.eatcode.trainwatch.nr.hazelcast;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

class KryoInstances {

    private static KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
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
