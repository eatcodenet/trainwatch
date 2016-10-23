package net.eatcode.trainwatch.nr.dataimport;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

class ScheduleRepositoryPopulator {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final Gson gson = new GsonBuilder().create();

	private final ScheduleRepo scheduleRepo;

	ScheduleRepositoryPopulator(ScheduleRepo scheduleRepo) {
		this.scheduleRepo = scheduleRepo;
	}

	CompletableFuture<Void> populateFromFile(String scheduleFile) {
		log.info("Starting populating from: {}", scheduleFile);
		log.info("This will take a while!");
		return populateAsync(scheduleFile);
	}

	private CompletableFuture<Void> populateAsync(String scheduleFile) {
		return CompletableFuture.runAsync(() -> {
			try (Stream<String> lines = Files.lines(Paths.get(scheduleFile))) {
				lines.map(this::toSchedule).forEach(this::addToRepo);
			} catch (Exception e) {
				log.error("location file parse error", e);
			}
		}).whenComplete((r, e) -> {
			if (e != null) {
				log.error("{}", e);
			}
		});
	}

	private Schedule toSchedule(String json) {
		System.err.println(json);
		return gson.fromJson(json, Schedule.class);

	}

	private void addToRepo(Schedule schedule) {
		System.err.println(schedule);
		// scheduleRepo.put(schedule);
	}
}
