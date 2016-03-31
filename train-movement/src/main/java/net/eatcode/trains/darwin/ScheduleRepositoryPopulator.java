package net.eatcode.trains.darwin;

import net.eatcode.trains.model.ScheduleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

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
