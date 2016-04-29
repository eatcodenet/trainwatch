package net.eatcode.trainwatch.search;

import java.util.List;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzTrainWatchSearch implements TrainWatchSearch {

    private final HazelcastInstance client;

    public HzTrainWatchSearch(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
    }

    @Override
    public List<Station> listStations() {
        return null;
    }

    @Override
    public List<TrainMovement> trainMovementsByDelay(DelayWindow d, int limit) {
        return null;
    }

}
