package net.eatcode.trainwatch.nr.dataimport;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.RunDays;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.TrustSchedule;
import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1;
import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1.Schedule_segment.Schedule_location;

public class ScheduleRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ScheduleRepo repo;

    public ScheduleRepositoryPopulator(ScheduleRepo repo) {
        this.repo = repo;
    }

    public CompletableFuture<Void> populateFromFile(String fileName) {
        log.info("Starting populating repo from: {}", fileName);
        return new ScheduleFileParser(fileName).parse(ts -> generateDailySchedulesFromTrustSchedule(ts));
    }

    private void generateDailySchedulesFromTrustSchedule(TrustSchedule ts) {
        JsonScheduleV1 s = ts.JsonScheduleV1;
        Set<DayOfWeek> days = RunDays.from(s.schedule_days_runs);
        List<Schedule_location> locs = Arrays.asList(s.schedule_segment.schedule_location);
        String headcode = s.schedule_segment.signalling_id;
        String trainServiceCode = s.schedule_segment.CIF_train_service_code;
        String atoc = s.atoc_code;
    }

}
