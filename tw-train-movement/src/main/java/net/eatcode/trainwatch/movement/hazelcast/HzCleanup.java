package net.eatcode.trainwatch.movement.hazelcast;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovementRepo;

public class HzCleanup {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final long interval = 60 * 3;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final TrainMovementRepo movementRepo;

    private final TrainActivationRepo activationRepo;

    private final int movementThreshold = 2;
    private final int activationThreshold = 12;

    public HzCleanup(TrainMovementRepo movementRepo, TrainActivationRepo activationRepo) {
        this.movementRepo = movementRepo;
        this.activationRepo = activationRepo;
    }

    public void start() {
        log.info("Starting movement cleanup thread");
        scheduler.scheduleAtFixedRate(() -> movementRepo.evictOlderThan(movementThreshold), interval, interval, SECONDS);
        scheduler.scheduleAtFixedRate(() -> activationRepo.evictOlderThan(activationThreshold), interval, interval, SECONDS);
    }

}
