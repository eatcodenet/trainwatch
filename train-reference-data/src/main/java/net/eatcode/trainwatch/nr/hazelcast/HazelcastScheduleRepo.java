package net.eatcode.trainwatch.nr.hazelcast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import net.eatcode.trainwatch.nr.TrustSchedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HazelcastScheduleRepo implements ScheduleRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, TrustSchedule> map = client.getMap("schedule");

    @Override
    public void put(TrustSchedule schedule) {
        map.put(schedule.id, schedule);
    }

    @Override
    public TrustSchedule get(String id) {
        return map.get(id);
    }

    @Override
    public List<TrustSchedule> getForServiceCode(String trainServiceCode) {
        return new ArrayList<>(map.values(new SqlPredicate( "id = " + "W60003" )));
    }

    public void shutdown() {
        client.shutdown();
    }

    public Stream<TrustSchedule> all() {
        return map.values().stream();
    }
}
