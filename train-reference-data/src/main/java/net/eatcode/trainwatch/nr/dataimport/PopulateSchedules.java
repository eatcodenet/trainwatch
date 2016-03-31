package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastScheduleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PopulateSchedules {

    private static final Logger log = LoggerFactory.getLogger(PopulateSchedules.class);

    public static void main(String[] args) throws Exception {
        String fileName = args[0];
        assertFileExists(fileName);

        HazelcastScheduleRepo repo = new HazelcastScheduleRepo();
        new ScheduleRepositoryPopulator(repo).populateFromFile(fileName).whenCompleteAsync((v, e) -> {
            log.info("Done populating!");
        }).get();
        log.info("Schedule count: {}", repo.getCount());
        repo.shutdown();
    }

    private static void assertFileExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new RuntimeException("file does not exist: " + fileName);
        }
    }

}
