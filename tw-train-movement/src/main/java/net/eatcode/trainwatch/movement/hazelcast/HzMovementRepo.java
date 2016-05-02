package net.eatcode.trainwatch.movement.hazelcast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.MovementRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class HzMovementRepo implements MovementRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;
    private final MultiMap<DelayWindow, TrainMovement> map;

    public HzMovementRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMultiMap("trainMovement");
    }

    @Override
    public List<TrainMovement> getByMaxDelay(DelayWindow delayWindow) {
        return map.get(delayWindow).stream().collect(Collectors.toList());
    }

    @Override
    public void put(TrainMovement tm) {
        if (map.containsValue(tm)) {
            delete(tm);
        }
        map.put(DelayWindow.from(tm.delayInMins()), tm);
    }

    @Override
    public void delete(TrainMovement tm) {
        DelayWindow[] keys = DelayWindow.values();
        for (int i = 0; i < keys.length; i++) {
            map.remove(keys[i], tm);
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
