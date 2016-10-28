package net.eatcode.trainwatch.movement.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.DeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;

public class HzDeparturesRepo implements DeparturesRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IMap<String, TrainDeparture> map;

    public HzDeparturesRepo(HazelcastInstance client) {
        this.map = client.getMap("trainDeparture");
    }

    @Override
    public void put(TrainDeparture td) {
        if (hasBothCrsCodes(td)) {
            log.debug("PUT: {}", td);
            map.set(td.trainId(), td);
        } else {
        	log.warn("Departure does not gave both crs codes: {}", td);
        }
    }

    @Override
    public void delete(String trainId) {
        TrainDeparture trainDeparture = map.get(trainId);
        if ((trainDeparture != null) && trainDeparture.hasDepartedAccordingToSchedule()) {
            //log.debug("DELETE {}", trainId);
            //map.delete(trainId);
        }
    }

    private boolean hasBothCrsCodes(TrainDeparture td) {
        return hasCrs(td.originCrs()) && hasCrs(td.destCrs());
    }

    private boolean hasCrs(String crs) {
        return (crs != null) && (crs.length() == 3);
    }

}
