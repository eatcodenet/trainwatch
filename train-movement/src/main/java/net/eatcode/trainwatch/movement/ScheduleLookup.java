package net.eatcode.trainwatch.movement;

import net.eatcode.trainwatch.nr.DaySchedule;

public interface ScheduleLookup {

    public DaySchedule lookup(TrustTrainMovementMessage message);
}
