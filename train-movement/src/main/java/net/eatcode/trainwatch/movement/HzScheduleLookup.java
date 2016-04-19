package net.eatcode.trainwatch.movement;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HzScheduleLookup implements ScheduleLookup {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TrainActivationRepo activationRepo;
    private final ScheduleRepo scheduleRepo;

    public HzScheduleLookup(TrainActivationRepo activationRepo, ScheduleRepo scheduleRepo) {
        this.activationRepo = activationRepo;
        this.scheduleRepo = scheduleRepo;
        log.info("schedule count: {}", scheduleRepo.count());
    }

    @Override
    public Optional<Schedule> lookup(TrustTrainMovementMessage message) {
        return activationRepo.getScheduleId(message.body.train_id)
                .map(value -> scheduleRepo.get(activationRepo.getScheduleId(message.body.train_id).get()));
    }

}
