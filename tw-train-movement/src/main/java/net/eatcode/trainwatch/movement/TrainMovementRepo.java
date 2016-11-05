package net.eatcode.trainwatch.movement;

public interface TrainMovementRepo {

    void put(TrainMovement tm);

    void delete(TrainMovement tm);

    void evictOlderThan(int ageInHours);
}
