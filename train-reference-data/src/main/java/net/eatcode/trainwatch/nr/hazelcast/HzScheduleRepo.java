package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HzScheduleRepo implements ScheduleRepo {

    private final HazelcastInstance client;
    private final IMap<String, Schedule> map;

    public HzScheduleRepo(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.map = client.getMap("schedule");
    }

    @Override
    public void put(Schedule schedule) {
        map.put(schedule.id + schedule.trainServiceCode, schedule);
    }

    @Override
    public Schedule getByIdAndServiceCode(String id, String serviceCode) {
        return map.get(id + serviceCode);
    }

    @Override
    public Integer count() {
        return map.size();
    }

    public void shutdown() {
        client.shutdown();
    }
}
