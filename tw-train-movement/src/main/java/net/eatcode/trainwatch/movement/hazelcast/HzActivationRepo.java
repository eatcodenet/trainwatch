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
		TrainActivation value = map.get(trainId);
		if (value == null) {
			return Optional.empty();
		}
		return Optional.of(value);
	}

	@Override
	public void delete(String trainId) {
		map.delete(trainId);
	}

	@Override
	public void put(TrainActivation activation) {
		log.debug("PUT: {} {} {} {}", activation.trainId, activation.scheduleId, activation.startDate, activation.endDate);
		map.set(activation.trainId, activation);
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
