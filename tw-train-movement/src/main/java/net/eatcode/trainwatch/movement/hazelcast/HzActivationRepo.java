package net.eatcode.trainwatch.movement.hazelcast;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.KryoUtils;

public class HzActivationRepo implements ActivationRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IMap<String, byte[]> map;

    public HzActivationRepo(HazelcastInstance client) {
        this.map = client.getMap("trainActivation");
    }

    @Override
    public Optional<TrainActivation> get(String trainId) {
        byte[] data = map.get(trainId);
        if (data == null) {
            return Optional.empty();
        }
        return Optional.of(KryoUtils.fromByteArray(data, TrainActivation.class));
    }

    @Override
    public void delete(TrainMovement tm) {
        map.delete(tm.trainId());
    }

    @Override
    public void put(TrainActivation activation) {
        log.debug("PUT: {} {}", activation.trainId(), activation.scheduleId());
        map.set(activation.trainId(), KryoUtils.toByteArray(activation));
    }

}
