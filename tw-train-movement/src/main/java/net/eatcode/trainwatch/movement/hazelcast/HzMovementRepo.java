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
import net.eatcode.trainwatch.nr.hazelcast.KryoUtils;

public class HzMovementRepo implements MovementRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IMap<String, byte[]> map;

    public HzMovementRepo(HazelcastInstance client) {
        this.map = client.getMap("trainMovement");
    }

    @Override
    public void put(TrainMovement tm) {
        map.set(tm.trainId(), KryoUtils.toByteArray(tm));
    }

    @Override
    public void delete(TrainMovement tm) {
        map.delete(tm.trainId());
    }

    @Override
    public void evictOlderThan(int ttlHours) {
        log.info("evicting with timestamp older than {} hours", ttlHours);
        LocalDateTime cutoff = LocalDateTime.now().minusHours(ttlHours);
        List<TrainMovement> stale = map.values().stream()
                .map(data -> KryoUtils.fromByteArray(data, TrainMovement.class))
                .filter(tm -> tm.timestamp().isBefore(cutoff))
                .collect(Collectors.toList());
        stale.forEach(tm -> map.evict(tm.trainId()));
    }

}
