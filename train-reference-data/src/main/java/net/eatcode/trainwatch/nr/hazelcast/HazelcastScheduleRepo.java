package net.eatcode.trainwatch.nr.hazelcast;

import java.util.ArrayList;
import java.util.List;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

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
    @SuppressWarnings("unchecked")
    public List<Schedule> getForServiceCode(String trainServiceCode) {
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate<String, Schedule> predicate = e.get("trainServiceCode").equal(trainServiceCode);
        return new ArrayList<>(map.values(predicate));
    }

    public void shutdown() {
        client.shutdown();
    }
}
