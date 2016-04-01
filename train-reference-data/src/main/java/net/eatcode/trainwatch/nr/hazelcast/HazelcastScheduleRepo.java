package net.eatcode.trainwatch.nr.hazelcast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HazelcastScheduleRepo implements ScheduleRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, Schedule> map = client.getMap("schedule");

    @Override
    public void put(Schedule schedule) {
        map.put(schedule.id, schedule);
    }

    @Override
    public Schedule get(String id) {
        return map.get(id);
    }

    @Override
    public List<Schedule> getForServiceCode(String trainServiceCode) {
        return new ArrayList<>(map.values(new SqlPredicate( "active AND age < 30" )));
    }

    public void shutdown() {
        client.shutdown();
    }

    public Stream<Schedule> all() {
        return map.values().stream();
    }
}
