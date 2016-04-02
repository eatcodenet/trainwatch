package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HazelcastScheduleRepo implements ScheduleRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, DaySchedule> map = client.getMap("schedule");

    @Override
    public void put(DaySchedule schedule) {
        map.put(schedule.toString(), schedule);
    }

    @Override
    public DaySchedule get(String id) {
        return map.get(id);
    }


    public void shutdown() {
        client.shutdown();
    }
}
