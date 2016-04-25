package net.eatcode.trainwatch.movement;

import java.util.Optional;

public interface TrainActivationRepo {

    Optional<String> getByIdAndServiceCode(String trainId, String trainServiceCode);

    void put(TrainActivation activation);
}
