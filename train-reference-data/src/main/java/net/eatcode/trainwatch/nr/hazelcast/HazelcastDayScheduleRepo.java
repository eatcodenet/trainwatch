package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;

public class HazelcastDayScheduleRepo implements DayScheduleRepo {

    private final HazelcastInstance client;
    private final IMap<String, DaySchedule> map;

    public HazelcastDayScheduleRepo(String servers) {
        this.client = new HazelcastClientBuilder().buildInstance(servers);
        this.map = client.getMap("schedule");
    }

    @Override
    public void put(DaySchedule schedule) {
        map.put(schedule.id, schedule);
    }

    @Override
    public DaySchedule get(String id) {
        return map.get(id);
    }

    public void shutdown() {
        client.shutdown();
    }
}
