package net.eatcode.trainwatch.nr.dataimport;

import java.util.concurrent.CompletableFuture;

import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.TransformTrustSchedule;
import net.eatcode.trainwatch.nr.TrustSchedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ScheduleRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ScheduleRepo scheduleRepo;
    private final LocationRepo locationRepo;

    ScheduleRepositoryPopulator(ScheduleRepo scheduleRepo, LocationRepo locationRepo) {
        this.scheduleRepo = scheduleRepo;
        this.locationRepo = locationRepo;
    }

    CompletableFuture<Void> populateFromFile(String fileName) {
        log.info("Starting populating from: {}", fileName);
        log.info("This will take a while!");
        return new ScheduleFileParser(fileName).parse(ts -> generateScheduleAndFilter(ts));
    }

    private void generateScheduleAndFilter(TrustSchedule ts) {
        scheduleRepo.put(new TransformTrustSchedule(locationRepo).toSchedule(ts));
    }

}
