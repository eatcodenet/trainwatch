package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;

public class HazelcastDayScheduleRepo implements DayScheduleRepo {

    private final HazelcastInstance client;
    private final IMap<String, DaySchedule> idMap;
    private final IMap<String, DaySchedule> keyMap;

    public HazelcastDayScheduleRepo(String servers) {
        this.client = new HazelcastClientBuilder().buildInstance(servers);
        this.idMap = client.getMap("idDaySchedule");
        this.keyMap = client.getMap("keyDaySchedule");
    }

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
        return keyMap.get(key);
    }

    public void shutdown() {
        client.shutdown();
    }
}
