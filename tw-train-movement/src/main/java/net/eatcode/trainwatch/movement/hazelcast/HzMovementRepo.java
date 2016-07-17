package net.eatcode.trainwatch.movement.hazelcast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.MovementRepo;
import net.eatcode.trainwatch.movement.TrainMovement;

public class HzMovementRepo implements MovementRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IMap<String, TrainMovement> map;

    public HzMovementRepo(HazelcastInstance client) {
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
        log.info("evicting with timestamp older than {} hours", ageInHours);
        LocalDateTime cutoff = LocalDateTime.now().minusHours(ageInHours);
        List<TrainMovement> stale = map.values().stream()
                .filter(tm -> tm.timestamp().isBefore(cutoff))
                .collect(Collectors.toList());
        stale.forEach(tm -> map.evict(tm.trainId()));
    }

}
