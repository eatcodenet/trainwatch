package net.eatcode.trainwatch.movement.hazelcast;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import net.eatcode.trainwatch.movement.MovementRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HzCleanup {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final long interval = 60 * 2;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> task;

    private final MovementRepo movementRepo;

    public HzCleanup(MovementRepo movementRepo) {
        this.movementRepo = movementRepo;
    }

    public void start() {
        log.info("Starting cleanup thread");
        Runnable evict = new Runnable() {
            @Override
            public void run() {
                movementRepo.evictOlderThan(3);
            }
        };
        task = scheduler.scheduleAtFixedRate(evict, interval, interval, SECONDS);
    }

    public void stop() {
        task.cancel(true);
    }

}
