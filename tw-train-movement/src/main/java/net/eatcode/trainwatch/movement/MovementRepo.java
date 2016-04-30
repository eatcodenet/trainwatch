package net.eatcode.trainwatch.movement;

import java.util.List;

public interface MovementRepo {

    List<TrainMovement> getByMaxDelay(DelayWindow d);

    void put(TrainMovement tm);

    void evictAll(int olderThanDays);

    void delete(TrainMovement tm);
}
