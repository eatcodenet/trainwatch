package net.eatcode.trainwatch.movement;

import java.util.Optional;

import net.eatcode.trainwatch.nr.DaySchedule;

public interface ScheduleLookup {

    public Optional<DaySchedule> lookup(TrustTrainMovementMessage message);
}
