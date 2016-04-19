package net.eatcode.trainwatch.nr.dataimport;

import java.util.concurrent.atomic.AtomicInteger;

import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Schedule;

public class ScheduleCounter {

    private static final AtomicInteger count = new AtomicInteger(0);

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

        ScheduleRepo scheduleRepo = new ScheduleRepo() {

            @Override
            public void put(Schedule schedule) {
                count.incrementAndGet();
            }

            @Override
            public Schedule get(String id) {
                return null;
            }
        };
        new ScheduleRepositoryPopulator(scheduleRepo, locationRepo)
                .populateFromFile("/var/trainwatch/data/full-train-schedules").whenCompleteAsync((v, error) -> {
                    if (error == null) {
                        System.out.println("Done populating schedules!");
                        System.out.println("Count: " + count.get());
                    } else
                        error.printStackTrace();
                }).get();
    }
}
