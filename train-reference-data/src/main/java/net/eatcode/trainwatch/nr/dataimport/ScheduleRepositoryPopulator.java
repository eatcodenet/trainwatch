package net.eatcode.trainwatch.nr.dataimport;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.ScheduleRepo;

public class ScheduleRepositoryPopulator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ScheduleRepo repo;

    public ScheduleRepositoryPopulator(ScheduleRepo repo) {
        this.repo = repo;
    }

    public CompletableFuture<Void> populateFromFile(String fileName) {
        log.info("Starting populating repo from: {}", fileName);
        return new ScheduleFileParser(fileName).parse(schedule -> repo.put(schedule));
    }
}
