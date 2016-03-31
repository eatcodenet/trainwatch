package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.Tiploc;


import java.util.List;

public class HazelcastQuerySpike {

    public static void main(String[] args) throws InterruptedException {
        HazelcastInstance client = new ClientBuilder().build();
        IMap<String, Schedule> map1 = client.getMap("schedule");
        List<Schedule> schedules = (List<Schedule>) map1.values(new SqlPredicate("headcode = 0B00"));
        System.out.println("s map size: " + map1.size());
        System.out.println("s qry size: " + schedules.size());
        System.out.println("s s = " + schedules.get(0));

        IMap<String, Tiploc> map2 = client.getMap("tiploc");
        List<Tiploc> tiplocs = (List<Tiploc>) map2.values(new SqlPredicate("crsCode = MAN"));
        System.out.println("t map size: " + map2.size());
        System.out.println("t qry size: " + tiplocs.size());
        System.out.println("t = " + tiplocs.get(0));
        client.shutdown();
    }
}
