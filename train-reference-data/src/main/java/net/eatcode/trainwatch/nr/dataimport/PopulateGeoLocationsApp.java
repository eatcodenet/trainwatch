package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.hazelcast.HazelcastGeoLocationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PopulateGeoLocationsApp {

    private static final Logger log = LoggerFactory.getLogger(PopulateGeoLocationsApp.class);

    public static void main(String[] args) throws Exception {
        String crsFile = args[0];
        assertFileExists(crsFile);

        String tiplocFile = args[1];
        assertFileExists(tiplocFile);

        HazelcastGeoLocationRepo repo = new HazelcastGeoLocationRepo();

        new GeoLocationPopulator(repo).populateFromFiles(tiplocFile, crsFile).whenCompleteAsync((value, err) -> {
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
