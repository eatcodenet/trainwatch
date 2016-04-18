package net.eatcode.trainwatch.movement;

import java.util.Optional;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;

public class LookupFromActivation implements ScheduleLookup {

    private final TrainActivationRepo activationRepo;
    private final DayScheduleRepo scheduleRepo;

    public LookupFromActivation(TrainActivationRepo activationRepo, DayScheduleRepo scheduleRepo) {
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
