package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.MovementRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HzMovementRepo implements MovementRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;
    private final IMap<String, TrainMovement> map;

    public HzMovementRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMap("trainMovement");
    }

    @Override
    public void put(TrainMovement tm) {
        map.put(tm.trainId(), tm);
    }

    @Override
    public void delete(TrainMovement tm) {
        map.delete(tm.trainId());
    }

    public void shutdown() {
        log.debug("shutdown");
        client.shutdown();
    }

}
