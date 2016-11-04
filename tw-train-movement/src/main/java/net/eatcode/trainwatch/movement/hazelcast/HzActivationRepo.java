package net.eatcode.trainwatch.movement.hazelcast;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.TrainActivation;

public class HzActivationRepo implements ActivationRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IMap<String, TrainActivation> map;

    public HzActivationRepo(HazelcastInstance client) {
        this.map = client.getMap("trainActivation");
    }

    @Override
    public Optional<TrainActivation> get(String trainId) {
        return Optional.ofNullable(map.get(trainId));
    }

    @Override
    public void delete(String trainId) {
        map.delete(trainId);
    }

    @Override
    public void put(TrainActivation t) {
        log.debug("PUT: {} {}", t.trainId, t.schedule.id);
        map.set(t.trainId, t);
    }

    @Override
    public void evictOlderThan(int ageInHours) {
        LocalDateTime ageThreshold = LocalDateTime.now().minusHours(ageInHours);
        LocalTime departedThreshold = LocalTime.now().minusMinutes(1);
        List<TrainActivation> stale = map.values().stream()
                .filter(ta -> ta.timestamp().isBefore(ageThreshold)
                        || ta.schedule.departure.isBefore(departedThreshold))
                .collect(Collectors.toList());
        int count = stale.size();
        stale.forEach(ta -> map.evict(ta.trainId));
        log.info("evicted {} activations older than {} hours. New size is {}", count, ageInHours, map.size());

    }
}
