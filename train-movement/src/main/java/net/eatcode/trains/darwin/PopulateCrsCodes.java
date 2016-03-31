package net.eatcode.trains.darwin;

import net.eatcode.trains.model.hazelcast.HazelcastCrsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class PopulateCrsCodes {

    private static final Logger log = LoggerFactory.getLogger(PopulateCrsCodes.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String fileName = args[0];
        assertFileExists(fileName);
        HazelcastCrsRepo repo = new HazelcastCrsRepo();

        new CrsRepositoryPopulator(repo).populateFromFile(fileName);
        log.info("Done populating!");
        repo.shutdown();
    }

    private static void assertFileExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new RuntimeException("file does not exist: " + fileName);
        }
    }

}
