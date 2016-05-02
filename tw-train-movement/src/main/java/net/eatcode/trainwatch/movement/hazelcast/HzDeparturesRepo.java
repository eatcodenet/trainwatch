package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.DeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class HzDeparturesRepo implements DeparturesRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;

    private final IMap<String, TrainDeparture> map;

    public HzDeparturesRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMap("trainDeparture");
    }

    @Override
    public void put(TrainDeparture td) {
        if (hasBothCrsCodes(td)) {
            log.info("PUT: {}", td);
            map.set(td.trainId(), td);
        }
    }

    private boolean hasBothCrsCodes(TrainDeparture td) {
        return hasCrs(td.originCrs()) && hasCrs(td.destCrs());
    }

    private boolean hasCrs(String crs) {
        return crs != null && crs.length() == 3;
    }

}
