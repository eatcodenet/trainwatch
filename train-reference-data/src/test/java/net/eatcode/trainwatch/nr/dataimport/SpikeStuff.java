package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.TrustSchedule;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastDayScheduleRepo;

public class SpikeStuff {

    public static void main(String[] args) {
        HazelcastDayScheduleRepo repo = new HazelcastDayScheduleRepo();
        TrustSchedule schedule = new TrustSchedule();
        String id = "aruba" + +System.currentTimeMillis();

    }
}
