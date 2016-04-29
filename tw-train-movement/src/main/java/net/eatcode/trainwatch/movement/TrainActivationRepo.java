package net.eatcode.trainwatch.movement;

import java.util.Optional;

public interface TrainActivationRepo {

    Optional<TrainActivation> get(String trainId);

    void put(TrainActivation activation);

    void delete(TrainMovement tm);
}
