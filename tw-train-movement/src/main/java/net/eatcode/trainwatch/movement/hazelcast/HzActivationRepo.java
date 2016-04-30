package net.eatcode.trainwatch.movement.hazelcast;

import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzActivationRepo implements ActivationRepo {
    private final HazelcastInstance client;
    private final IMap<String, TrainActivation> map;

    public HzActivationRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMap("trainActivation");
    }

    @Override
    public Optional<TrainActivation> get(String trainId) {
        TrainActivation activation = map.get(trainId);
        if (activation == null) {
            return Optional.empty();
        }
        return Optional.of(activation);
    }

    @Override
    public void delete(TrainMovement tm) {
        map.remove(tm.trainId());
    }

    @Override
    public void put(TrainActivation activation) {
        map.put(activation.trainId(), activation);
    }

}
