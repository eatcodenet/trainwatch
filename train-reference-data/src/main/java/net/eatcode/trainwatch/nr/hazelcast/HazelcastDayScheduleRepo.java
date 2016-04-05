package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;

public class HazelcastDayScheduleRepo implements DayScheduleRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, DaySchedule> idMap = client.getMap("idDaySchedule");
    private final IMap<String, DaySchedule> keyMap = client.getMap("keyDaySchedule");

    @Override
    public void put(DaySchedule schedule) {
        idMap.put(schedule.id, schedule);
        keyMap.put(schedule.key(), schedule);
    }

    @Override
    public DaySchedule get(String key) {
        return idMap.get(key);
    }

    @Override
    public DaySchedule getByDaykey(String key) {
        return keyMap.get(key);
    }

    public void shutdown() {
        client.shutdown();
    }
}
