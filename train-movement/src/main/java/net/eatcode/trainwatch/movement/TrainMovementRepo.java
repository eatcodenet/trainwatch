package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementRepo {

    List<TrainMovement> getByMaxDelay(DelayWindow d);

    void put(TrainMovement tm);

    void evictAll(int olderThanDays);
}
