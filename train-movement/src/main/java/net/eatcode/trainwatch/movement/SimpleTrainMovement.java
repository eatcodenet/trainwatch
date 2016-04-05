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

    private final String delay;

    public SimpleTrainMovement(String origin, String departure, String destination, String arrival, String trainId,
            String delay) {
        this.origin = origin;
        this.departure = departure;
        this.destination = destination;
        this.arrival = arrival;
        this.trainId = trainId;
        this.delay = delay;
    }

    @Override
    public String toString() {
        return new Formatted().format(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(trainId, ((SimpleTrainMovement) obj).trainId);
    }

    static class Formatted {
        public String format(SimpleTrainMovement stm) {
            return String.format("%1$-20s | %2$-20s ", stm.origin, stm.destination);
        }
    }

}
