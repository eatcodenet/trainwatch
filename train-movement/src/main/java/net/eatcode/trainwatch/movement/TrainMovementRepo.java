package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementRepo {

    List<TrainMovement> getByMaxDelay(DelayWindow d);

    List<TrainMovement> getAll();

    void put(TrainMovement tm);
}
