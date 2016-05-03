package net.eatcode.trainwatch.movement.hazelcast;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.MovementRepo;

public class HzCleanup {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final long interval = 60;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> task;

    private final MovementRepo movementRepo;

    public HzCleanup(MovementRepo movementRepo) {
        this.movementRepo = movementRepo;
    }

    public void start() {
        log.info("Starting cleanup thread");
        Runnable beeper = new Runnable() {
            @Override
            public void run() {
                movementRepo.evictOlderThan(2);
            }
        };
        task = scheduler.scheduleAtFixedRate(beeper, interval, interval, SECONDS);
    }

    public void stop() {
        task.cancel(true);
    }

}
