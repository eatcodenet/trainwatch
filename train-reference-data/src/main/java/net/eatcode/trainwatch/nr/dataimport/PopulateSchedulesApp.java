package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.hazelcast.HazelcastScheduleRepo;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastLocationRepo;

public class PopulateSchedulesApp {

    private static final Logger log = LoggerFactory.getLogger(PopulateSchedulesApp.class);

    public static void main(String[] args) throws Exception {
        checkUsage(args);
        String hazelcastServers = args[0];
        String fileName = args[1];
        assertFileExists(fileName);

        HazelcastScheduleRepo scheduleRepo = new HazelcastScheduleRepo(hazelcastServers);
        HazelcastLocationRepo locationRepo = new HazelcastLocationRepo(hazelcastServers);

        new ScheduleRepositoryPopulator(scheduleRepo, locationRepo).populateFromFile(fileName)
                .whenCompleteAsync((v, error) -> {
                    if (error == null)
                        log.info("Done populating schedules!");
                    else
                        error.printStackTrace();

                    scheduleRepo.shutdown();
                    locationRepo.shutdown();
                }).get();
    }

    private static void checkUsage(String[] args) {
        if (args.length != 2) {
            System.out.println("USAGE: PopulateSchedulesApp hazelcastServers full-schedule-file");
            System.exit(1);
        }
    }

    private static void assertFileExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new RuntimeException("file does not exist: " + fileName);
        }
    }

}
