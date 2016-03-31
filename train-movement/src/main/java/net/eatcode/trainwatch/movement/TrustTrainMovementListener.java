package net.eatcode.trainwatch.movement;

@FunctionalInterface
public interface TrustTrainMovementListener {
    void onTrainMovement(TrustTrainMovement tm);
}
