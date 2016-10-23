package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class PopulateSchedulesApp {

	private static final Logger log = LoggerFactory.getLogger(PopulateSchedulesApp.class);

	public static void main(String[] args) throws Exception {
		checkUsage(args);
		String hazelcastServers = args[0];
		String scheduleFileName = args[1];
		assertFileExists(scheduleFileName);

		HazelcastInstance client = new HzClientBuilder().build(hazelcastServers);
		HzScheduleRepo scheduleRepo = new HzScheduleRepo(client);
		StopWatch sw = new StopWatch();
		sw.start();
		new ScheduleRepositoryPopulator(scheduleRepo).populateFromFile(scheduleFileName)
				.whenCompleteAsync((v, error) -> {
					if (error == null) {
						log.info("Done populating schedules!");
					} else {
						log.error("Error populating schedules!", error);
					}

					sw.stop();
					log.info("Took {}s", sw.getTime() / 1000);
					client.shutdown();
				}).get();
	}

	private static void checkUsage(String[] args) {
		if (args.length != 2) {
			System.out.println("USAGE: PopulateSchedulesApp hazelcastServers schedule-file");
			System.exit(1);
		}
	}

	private static void assertFileExists(String fileName) {
		if (!Files.exists(Paths.get(fileName))) {
			throw new RuntimeException("file does not exist: " + fileName);
		}
	}

}
