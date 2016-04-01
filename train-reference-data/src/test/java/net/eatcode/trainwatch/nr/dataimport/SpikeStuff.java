package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastScheduleRepo;

public class SpikeStuff {

    public static void main(String[] args) {
        HazelcastScheduleRepo repo = new HazelcastScheduleRepo();
        Schedule schedule = new Schedule();
        String id = "aruba" + +System.currentTimeMillis();
        schedule.id = id;
        schedule.startDate = "mummy!";
        repo.put(schedule);
        System.out.println(id + " " + repo.get(id));
        repo.all().forEach((s) -> {
            System.out.println(s);
        });
        repo.shutdown();
    }
}
