package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;

public class PopulateLocationsApp {

    private static final Logger log = LoggerFactory.getLogger(PopulateLocationsApp.class);

    public static void main(String[] args) throws Exception {

        String hazelcastServers = args[0];
        String crsFile = args[1];
        assertFileExists(crsFile);

        String tiplocFile = args[2];
        assertFileExists(tiplocFile);

        HzLocationRepo repo = new HzLocationRepo(hazelcastServers);

        new LocationPopulator(repo).populateFromFiles(crsFile, tiplocFile).whenCompleteAsync((value, err) -> {
            if (err == null) {
                log.info("Done populating!");
            }
            repo.shutdown();
        }).get();

    }

    private static void assertFileExists(String fileName) {
        if (!Files.exists(Paths.get(fileName))) {
            throw new RuntimeException("file does not exist: " + fileName);
        }
    }

}