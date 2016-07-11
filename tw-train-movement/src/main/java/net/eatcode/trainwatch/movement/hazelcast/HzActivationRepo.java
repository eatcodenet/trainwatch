package net.eatcode.trainwatch.movement.hazelcast;

import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.KryoUtils;

public class HzActivationRepo implements ActivationRepo {

    private final IMap<String, byte[]> map;

    public HzActivationRepo(HazelcastInstance client) {
        this.map = client.getMap("trainActivation");
    }

    @Override
    public Optional<TrainActivation> get(String trainId) {
        TrainActivation activation = KryoUtils.fromByteArray(map.get(trainId), TrainActivation.class);
        if (activation == null) {
            return Optional.empty();
        }
        return Optional.of(activation);
    }

    @Override
    public void delete(TrainMovement tm) {
        map.delete(tm.trainId());
    }

    @Override
    public void put(TrainActivation activation) {
        map.set(activation.trainId(), KryoUtils.toByteArray(activation));
    }

}
