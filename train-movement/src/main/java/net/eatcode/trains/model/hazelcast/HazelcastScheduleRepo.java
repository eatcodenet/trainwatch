package net.eatcode.trains.model.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import net.eatcode.trains.model.Schedule;
import net.eatcode.trains.model.ScheduleRepo;

import java.util.ArrayList;
import java.util.List;

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
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("trainServiceCode").equal(trainServiceCode);
        return new ArrayList<>(map.values(predicate));
    }

    @Override
    public long getCount() {
        return map.size();
    }

    public void shutdown() {
        client.shutdown();
    }
}
