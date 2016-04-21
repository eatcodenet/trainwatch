package net.eatcode.trainwatch.movement.hazelcast;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzTrainActivationRepo implements TrainActivationRepo {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HazelcastInstance client;
    private final IMap<String, String> map;

    public HzTrainActivationRepo(String bootStrapServers) {
        this.client = new HzClientBuilder().buildInstance(bootStrapServers);
        this.client.getConfig().getMapConfig("trainActivation").setTimeToLiveSeconds(5);
        this.map = client.getMap("trainActivation");
    }

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
