package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;

public class PopulateLocationsApp {

	private static final Logger log = LoggerFactory.getLogger(PopulateLocationsApp.class);

	public static void main(String[] args) throws Exception {

		String hazelcastServers = args[0];
		String locationsFile = args[1];
		assertFileExists(locationsFile);

		HazelcastInstance client = new HzClientBuilder().build(hazelcastServers);
		HzLocationRepo repo = new HzLocationRepo(client);

		new LocationPopulator(repo).populateFromFiles(locationsFile).whenCompleteAsync((value, err) -> {
			if (err == null) {
				log.info("Done populating!");
			}

			System.err.println("Count: " + client.getMap("locationByStanox").size());
			client.shutdown();
		}).get();

	}

	private static void assertFileExists(String fileName) {
		if (!Files.exists(Paths.get(fileName))) {
			throw new RuntimeException("file does not exist: " + fileName);
		}
	}

}
