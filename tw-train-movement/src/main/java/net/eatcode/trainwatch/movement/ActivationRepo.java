package net.eatcode.trainwatch.movement;

import java.util.Optional;

public interface ActivationRepo {

    Optional<TrainActivation> get(String trainId);

    void put(TrainActivation activation);

    void delete(String trainId);

    void evictOlderThan(int ageInHours);
}
