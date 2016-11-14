package net.eatcode.trainwatch.movement.hazelcast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementRepo;

public class HzTrainMovementRepo implements TrainMovementRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IMap<String, TrainMovement> map;

    public HzTrainMovementRepo(HazelcastInstance client) {
        this.map = client.getMap("trainMovement");
    }

    @Override
    public void put(TrainMovement tm) {
        map.set(tm.trainId(), tm);
    }

    @Override
    public void delete(TrainMovement tm) {
        map.delete(tm.trainId());
    }

    @Override
    public void evictOlderThan(int ageInHours) {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(ageInHours);
        log.info("evicting with timestamp older than {}", cutoff);
        List<TrainMovement> stale = map.values().stream()
                .filter(tm -> tm.timestamp().isBefore(cutoff))
                .collect(Collectors.toList());
        stale.forEach(tm -> map.evict(tm.trainId()));
    }

}