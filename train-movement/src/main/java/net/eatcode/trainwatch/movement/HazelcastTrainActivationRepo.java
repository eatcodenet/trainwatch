package net.eatcode.trainwatch.movement;

import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.hazelcast.ClientBuilder;

public class HazelcastTrainActivationRepo implements TrainActivationRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

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
        map.put(trainId, scheduleId);
    }

}
