package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.TrustSchedule;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastScheduleRepo;

public class SpikeStuff {

    public static void main(String[] args) {
        HazelcastScheduleRepo repo = new HazelcastScheduleRepo();
        TrustSchedule schedule = new TrustSchedule();
        String id = "aruba" + +System.currentTimeMillis();

    }
}
