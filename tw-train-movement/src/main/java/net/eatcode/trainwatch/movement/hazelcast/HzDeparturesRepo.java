package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.DeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class HzDeparturesRepo implements DeparturesRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;
    private final MultiMap<String, TrainDeparture> map;

    public HzDeparturesRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMultiMap("trainDeparture");
    }

    @Override
    public void put(TrainDeparture td) {
        if (hasCrs(td)) {
            log.info("PUT: {}", td);
            map.put(td.originCrs(), td);
        }
    }

    private boolean hasCrs(TrainDeparture td) {
        return td.originCrs() != null && td.originCrs().length() == 3;
    }

}
