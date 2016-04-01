package net.eatcode.trainwatch.movement;

@FunctionalInterface
public interface TrainMovementListener {
    void onTrainMovement(String jsonArray);
}
