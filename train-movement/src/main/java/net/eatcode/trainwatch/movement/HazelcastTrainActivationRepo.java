package net.eatcode.trainwatch.movement;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.hazelcast.HazelcastClientBuilder;

public class HazelcastTrainActivationRepo implements TrainActivationRepo {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HazelcastInstance client = new HazelcastClientBuilder().buildInstance();

    private final IMap<String, String> map = client.getMap("trainActivation");

    @Override
    public Optional<String> getScheduleId(String trainId) {
        String value = map.get(trainId);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public void putScheduleId(String trainId, String scheduleId) {
        log.debug("PUT: {} {}", trainId, scheduleId);
        map.put(trainId, scheduleId);
    }

}
