package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.hazelcast.HazelcastLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastDayScheduleRepo;

public class PopulateSchedules {

    private static final Logger log = LoggerFactory.getLogger(PopulateSchedules.class);

    public static void main(String[] args) throws Exception {
        String fileName = args[0];
        assertFileExists(fileName);

        HazelcastDayScheduleRepo scheduleRepo = new HazelcastDayScheduleRepo();
        HazelcastLocationRepo locationRepo = new HazelcastLocationRepo();
        new ScheduleRepositoryPopulator(scheduleRepo, locationRepo).populateFromFile(fileName).whenCompleteAsync((v, error) -> {
            if (error == null)
            log.info("Done populating!");

            scheduleRepo.shutdown();
        }).get();
    }

    private static void assertFileExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new RuntimeException("file does not exist: " + fileName);
        }
    }

}
