package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HazelcastScheduleRepo implements ScheduleRepo {

    private final HazelcastInstance client;
    private final IMap<String, Schedule> map;

    public HazelcastScheduleRepo(String servers) {
        this.client = new HazelcastClientBuilder().buildInstance(servers);
        this.map = client.getMap("schedule");
    }

    @Override
    public void put(Schedule schedule) {
        map.put(schedule.id, schedule);
    }

    @Override
    public Schedule get(String id) {
        return map.get(id);
    }

    public void shutdown() {
        client.shutdown();
    }
}
