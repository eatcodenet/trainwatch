package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.hazelcast.HazelcastTiplocRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class PopulateTiplocs {

    private static final Logger log = LoggerFactory.getLogger(PopulateTiplocs.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String fileName = args[0];
        assertFileExists(fileName);
        HazelcastTiplocRepo repo = new HazelcastTiplocRepo();
        new TiplocRepositoryPopulator(repo).populateFromFile(fileName).whenCompleteAsync((value, err) -> {
            log.info("Done populating!");
        }).get();

        repo.shutdown();
    }

    private static void assertFileExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new RuntimeException("file does not exist: " + fileName);
        }
    }

}
