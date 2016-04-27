package net.eatcode.trainwatch.movement.hazelcast;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.movement.LiveDeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class HzLiveDeparturesRepo implements LiveDeparturesRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;
    private final MultiMap<String, TrainDeparture> map;

    public HzLiveDeparturesRepo(String bootStrapServers) {
        this.client = new HzClientBuilder().buildInstance(bootStrapServers);
        this.map = client.getMultiMap("trainDeparture");
    }

    @Override
    public void put(TrainDeparture td) {
        log.debug("PUT: {}", td);
        map.put(td.originCrs(), td);
    }

    @Override
    public List<String> getAvailableCrsCodes() {
        return new ArrayList<>(map.keySet());
    }

    @Override
    public List<TrainDeparture> getByCrs(String crs) {
        return new ArrayList<>(map.get(crs));
    }

}