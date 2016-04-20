package net.eatcode.trainwatch.movement;

import java.util.List;
import java.util.stream.Collectors;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzTrainMovementRepo implements TrainMovementRepo {

    private final HazelcastInstance client;
    private final MultiMap<DelayWindow, TrainMovement> map;

    public HzTrainMovementRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMultiMap("trainMovement");
    }

    @Override
    public List<TrainMovement> getAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<TrainMovement> getByMaxDelay(DelayWindow delayWindow) {
        return map.get(delayWindow).stream().collect(Collectors.toList());
    }

    @Override
    public void put(TrainMovement tm) {
        map.put(DelayWindow.from(tm.delayInMins()), tm);
    }

    public void shutdown() {
        client.shutdown();
    }

}
