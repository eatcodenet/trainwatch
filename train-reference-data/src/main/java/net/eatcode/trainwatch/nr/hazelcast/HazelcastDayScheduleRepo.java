package net.eatcode.trainwatch.nr.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;

public class HazelcastDayScheduleRepo implements DayScheduleRepo {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final HazelcastInstance client = new ClientBuilder().build();
    private final IMap<String, DaySchedule> idMap = client.getMap("idDaySchedule");
    private final IMap<String, DaySchedule> keyMap = client.getMap("keyDaySchedule");

    @Override
    public void put(DaySchedule schedule) {
        idMap.put(schedule.id, schedule);
        keyMap.put(schedule.key(), schedule);
    }

    @Override
    public DaySchedule get(String id) {
        return idMap.get(id);
    }

    @Override
    public DaySchedule getByDaykey(String key) {
        log.debug("HAS {} {}", key, keyMap.containsKey(key));
        return keyMap.get(key);
    }

    public void shutdown() {
        client.shutdown();
    }
}
