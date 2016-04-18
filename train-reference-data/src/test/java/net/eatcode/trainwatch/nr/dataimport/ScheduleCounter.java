package net.eatcode.trainwatch.nr.dataimport;

import java.util.concurrent.atomic.AtomicInteger;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;

public class ScheduleCounter {

    public static void main(String[] args) throws Exception {
        LocationRepo locationRepo = new LocationRepo() {

            @Override
            public void put(Location location) {
            }

            @Override
            public Location getByTiploc(String tiploc) {
                return null;
            }

            @Override
            public Location getByStanox(String stanox) {
                return null;
            }
        };

        DayScheduleRepo scheduleRepo = new DayScheduleRepo() {
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public void put(Schedule schedule) {
                count.incrementAndGet();
                // System.out.println(count);
            }

            @Override
            public Schedule get(String id) {
                return null;
            }
        };
        new ScheduleRepositoryPopulator(scheduleRepo, locationRepo)
                .populateFromFile("/var/trainwatch/data/full-train-schedules").whenCompleteAsync((v, error) -> {
                    if (error == null)
                        System.out.println("Done populating schedules!");
                    else
                        error.printStackTrace();
                }).get();
    }
}
