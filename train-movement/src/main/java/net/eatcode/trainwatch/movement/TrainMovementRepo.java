package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementRepo {

    List<TrainMovement> getByMaxDelay(Delay d);

    List<TrainMovement> getAll();

    void put(TrainMovement tm);
}
