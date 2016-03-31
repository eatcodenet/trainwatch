package net.eatcode.trainwatch.nr.hazelcast;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import net.eatcode.trainwatch.nr.GeoStanox;
import org.objenesis.strategy.StdInstantiatorStrategy;

class KryoInstances {

    private static KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.register(GeoStanox.class);
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
