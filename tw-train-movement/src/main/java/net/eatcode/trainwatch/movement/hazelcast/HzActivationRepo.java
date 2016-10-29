package net.eatcode.trainwatch.movement.hazelcast;

import java.time.LocalDateTime;
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
		log.info("evicting older than {} hours", ageInHours);
		LocalDateTime cutoff = LocalDateTime.now().minusHours(ageInHours);
		List<TrainActivation> stale = map.values().stream().filter(ta -> ta.timestamp().isBefore(cutoff))
				.collect(Collectors.toList());
		stale.forEach(ta -> map.evict(ta.trainId));

	}
}
