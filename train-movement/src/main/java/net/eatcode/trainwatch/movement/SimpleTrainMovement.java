package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.util.Objects;

public class SimpleTrainMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String origin;
    private final String departure;

    private final String destination;
    private final String arrival;
    private final String trainId;

    public SimpleTrainMovement(String origin, String departure, String destination, String arrival, String trainId) {
        this.origin = origin;
        this.departure = departure;
        this.destination = destination;
        this.arrival = arrival;
        this.trainId = trainId;
    }

    @Override
    public String toString() {
        return "SimpleTrainMovement [origin=" + origin + ", departure=" + departure + ", destination=" + destination
                + ", arrival=" + arrival + ", trainId=" + trainId + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(trainId, ((SimpleTrainMovement) obj).trainId);
    }

}
