package net.eatcode.trainwatch.movement.hazelcast;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.MovementRepo;

public class HzCleanup {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final long interval = 60 * 5;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final MovementRepo movementRepo;

    private final ActivationRepo activationRepo;

    private final int hours12 = 12;
    private final int hours36 = 24;

    public HzCleanup(MovementRepo movementRepo, ActivationRepo activationRepo) {
        this.movementRepo = movementRepo;
        this.activationRepo = activationRepo;
    }

    public void start() {
        log.info("Starting movement cleanup thread");
        scheduler.scheduleAtFixedRate(() -> movementRepo.evictOlderThan(hours12), interval, interval, SECONDS);
        scheduler.scheduleAtFixedRate(() -> activationRepo.evictOlderThan(hours36), interval, interval, SECONDS);
    }

}
