package net.eatcode.trainwatch.movement.hazelcast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzTrainMovementRepo implements TrainMovementRepo {
    private final HazelcastInstance client;
    private final MultiMap<DelayWindow, TrainMovement> map;

    public HzTrainMovementRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMultiMap("trainMovement");
    }

    @Override
    public List<TrainMovement> getByMaxDelay(DelayWindow delayWindow) {
        return map.get(delayWindow).stream().collect(Collectors.toList());
    }

    @Override
    public void put(TrainMovement tm) {
        removeExistingEntries(tm);
        if (stillTravellingToDest(tm)) {
            map.put(DelayWindow.from(tm.delayInMins()), tm);
        }
    }

    private boolean stillTravellingToDest(TrainMovement tm) {
        return !tm.hasArrivedAtDest();
    }

    private void removeExistingEntries(TrainMovement tm) {
        DelayWindow[] values = DelayWindow.values();
        for (int i = 0; i < values.length; i++) {
            map.remove(values[i], tm);
        }
    }

    @Override
    public void evictAll(int olderThanDays) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(olderThanDays);
        calculateStaleValues(cutoff).entrySet().forEach(e -> map.remove(e.getKey(), e.getValue()));
    }

    private MultiMap<DelayWindow, TrainMovement> calculateStaleValues(LocalDateTime cutoff) {
        MultiMap<DelayWindow, TrainMovement> stale = client.getMultiMap("stale");
        stale.clear();
        map.entrySet().forEach((e) -> {
            if (e.getValue().timestamp().isBefore(cutoff)) {
                stale.put(e.getKey(), e.getValue());
            }
        });
        return stale;
    }

    public void shutdown() {
        client.shutdown();
    }

}
