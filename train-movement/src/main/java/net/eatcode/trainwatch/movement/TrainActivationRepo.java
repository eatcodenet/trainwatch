package net.eatcode.trainwatch.movement;

import java.util.Optional;

import net.eatcode.trainwatch.movement.kafka.TrainActivation;

public interface TrainActivationRepo {

    Optional<String> getScheduleId(String trainServiceCode);

    void put(TrainActivation activation);
}
