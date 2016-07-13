package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HzScheduleRepo implements ScheduleRepo {

    private final IMap<String, byte[]> map;

    public HzScheduleRepo(HazelcastInstance client) {
        this.map = client.getMap("schedule");
    }

    @Override
    public void put(Schedule schedule) {
        map.set(schedule.id + schedule.trainServiceCode, KryoUtils.toByteArray(schedule));
    }

    @Override
    public Schedule getByIdAndServiceCode(String id, String serviceCode) {
        byte[] data = map.get(id + serviceCode);
        if (data == null) {
            return null;
        }
        return KryoUtils.fromByteArray(data, Schedule.class);
    }

    @Override
    public Integer count() {
        return map.size();
    }
}
