package net.eatcode.trainwatch.nr.dataimport;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.TransformTrustSchedule;
import net.eatcode.trainwatch.nr.TrustSchedule;

public class ScheduleRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DayScheduleRepo scheduleRepo;
    private final LocationRepo locationRepo;

    public ScheduleRepositoryPopulator(DayScheduleRepo scheduleRepo, LocationRepo locationRepo) {
        this.scheduleRepo = scheduleRepo;
        this.locationRepo = locationRepo;
    }

    public CompletableFuture<Void> populateFromFile(String fileName) {
        log.info("Starting populating from: {}", fileName);
        return new ScheduleFileParser(fileName).parse(ts -> generateDailySchedulesFromTrustSchedule(ts));
    }

    private void generateDailySchedulesFromTrustSchedule(TrustSchedule ts) {
        List<DaySchedule> daySchedules = new TransformTrustSchedule(locationRepo).toDaySchedules(ts);
        daySchedules.forEach(schedule -> {
            log.debug("{} {}", schedule.id, schedule.key());
            scheduleRepo.put(schedule);
        });
    }

}
