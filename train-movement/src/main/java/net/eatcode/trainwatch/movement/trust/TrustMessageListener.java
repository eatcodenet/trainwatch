package net.eatcode.trainwatch.movement.trust;

@FunctionalInterface
public interface TrustMessageListener {
    void onTrainMovement(String jsonArray);
}
