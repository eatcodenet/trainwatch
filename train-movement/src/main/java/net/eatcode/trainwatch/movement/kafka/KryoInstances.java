package net.eatcode.trainwatch.movement.kafka;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import net.eatcode.trainwatch.movement.TrainMovement;

class KryoInstances {

    private static KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        return kryo;
    };

    private static KryoPool pool = new KryoPool.Builder(factory)
            .softReferences().build();

    static Kryo get() {
        Kryo k = pool.borrow();
        //TODO: move T
        k.register(TrainMovement.class, 100);
        return k;
    }

    static void release(Kryo instance) {
        pool.release(instance);
    }
}
