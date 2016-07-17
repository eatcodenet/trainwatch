package net.eatcode.trainwatch.movement.hazelcast;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.MovementRepo;

public class HzCleanup {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final long interval = 60 * 3;
    private final int ageInHours = 2;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final MovementRepo movementRepo;

    public HzCleanup(MovementRepo movementRepo) {
        this.movementRepo = movementRepo;
    }

    public void start() {
        log.info("Starting movement cleanup thread");
        scheduler.scheduleAtFixedRate(() -> movementRepo.evictOlderThan(ageInHours), interval, interval, SECONDS);
    }

}
