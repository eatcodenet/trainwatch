package net.eatcode.trainwatch.nr.dataimport;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

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
        log.info("This may take a while!");
        return new ScheduleFileParser(fileName).parse(ts -> generateDailySchedulesFromTrustSchedule(ts));
    }

    private void generateDailySchedulesFromTrustSchedule(TrustSchedule ts) {
        List<DaySchedule> daySchedules = new TransformTrustSchedule(locationRepo).toDaySchedules(ts);
        final long total = daySchedules.size();
        final AtomicInteger count = new AtomicInteger(0);
        daySchedules.forEach(schedule -> {
            scheduleRepo.put(schedule);
            progress(total, count);
        });
    }

    private void progress(final long total, final AtomicInteger count) {
        if (count.incrementAndGet() % 1000 == 0) {
          log.debug("{}/{}", count.get(), total);
        }
    }

}
