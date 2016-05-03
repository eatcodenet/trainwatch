package net.eatcode.trainwatch.movement;

public interface MovementRepo {

    void put(TrainMovement tm);

    void delete(TrainMovement tm);

    void evictOlderThan(int ttlHours);
}
