package net.eatcode.trainwatch.movement;

import java.util.Optional;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class LookupFromActivation implements ScheduleLookup {

    private final TrainActivationRepo activationRepo;
    private final ScheduleRepo scheduleRepo;

    public LookupFromActivation(TrainActivationRepo activationRepo, ScheduleRepo scheduleRepo) {
        this.activationRepo = activationRepo;
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public Optional<Schedule> lookup(TrustTrainMovementMessage message) {
        return activationRepo
                .getScheduleId(message.body.train_id)
                .map(value -> scheduleRepo.get(activationRepo.getScheduleId(message.body.train_id).get()));
    }

}
