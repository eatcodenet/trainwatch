package net.eatcode.trainwatch.nr.dataimport;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.RunDays;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.TrustSchedule;

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
        Set<DayOfWeek> days = RunDays.from(ts.runDays);
        String orig = ts.origin;
        String dest = ts.destination;
        String arrival = ts.publicArrival;
        String depart = ts.publicDeparture;
        String headcode = ts.headcode;
        String trainServiceCode = ts.trainServiceCode;
        String atoc = ts.atocCode;
    }

}
