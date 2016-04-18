package net.eatcode.trainwatch.movement;

import java.util.Optional;

import net.eatcode.trainwatch.nr.Schedule;

public interface ScheduleLookup {

    public Optional<Schedule> lookup(TrustTrainMovementMessage message);
}
