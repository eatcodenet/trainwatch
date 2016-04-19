package net.eatcode.trainwatch.nr.dataimport;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.TransformTrustSchedule;
import net.eatcode.trainwatch.nr.TrustSchedule;

public class ScheduleRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ScheduleRepo scheduleRepo;
    private final LocationRepo locationRepo;

    public ScheduleRepositoryPopulator(ScheduleRepo scheduleRepo, LocationRepo locationRepo) {
        this.scheduleRepo = scheduleRepo;
        this.locationRepo = locationRepo;
    }

    public CompletableFuture<Void> populateFromFile(String fileName) {
        log.info("Starting populating from: {}", fileName);
        log.info("This will take a while!");
        return new ScheduleFileParser(fileName).parse(ts -> generateScheduleAndFilter(ts));
    }

    private void generateScheduleAndFilter(TrustSchedule ts) {
        Schedule schedule = new TransformTrustSchedule(locationRepo).toSchedule(ts);
        if (schedule.isRunning()) {
            scheduleRepo.put(schedule);
        }
    }

}
