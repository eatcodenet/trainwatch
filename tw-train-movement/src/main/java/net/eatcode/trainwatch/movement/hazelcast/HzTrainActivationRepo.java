package net.eatcode.trainwatch.movement.hazelcast;

import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzTrainActivationRepo implements TrainActivationRepo {
    private final HazelcastInstance client;
    private final IMap<String, TrainActivation> map;

    public HzTrainActivationRepo(String bootStrapServers) {
        this.client = new HzClientBuilder().buildInstance(bootStrapServers);
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
