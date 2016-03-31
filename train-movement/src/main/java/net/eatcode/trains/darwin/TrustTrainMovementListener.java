package net.eatcode.trains.darwin;

@FunctionalInterface
public interface TrustTrainMovementListener {
    void onTrainMovement(TrustTrainMovement tm);
}
