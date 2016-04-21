package net.eatcode.trainwatch.movement.hazelcast;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.movement.kafka.TrainActivation;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzTrainActivationRepo implements TrainActivationRepo {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HazelcastInstance client;
    private final IMap<String, TrainActivation> map;

    public HzTrainActivationRepo(String bootStrapServers) {
        this.client = new HzClientBuilder().buildInstance(bootStrapServers);
        this.map = client.getMap("trainActivation");
    }

    @Override
    public Optional<String> getScheduleId(String trainId) {
        String value = map.get(trainId).scheduleId();
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public void put(TrainActivation activation) {
        log.debug("PUT: {} {}", activation.trainId(), activation);
        map.put(activation.scheduleId(), activation);
    }

}
