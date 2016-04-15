package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.util.Objects;

public class TrainMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String origin;
    private final String departure;

    private final String destination;
    private final String arrival;
    private final String trainId;

    private final String delay;

    public TrainMovement(String id, String origin, String departure, String destination, String arrival, String delay,
            boolean isEstimated) {
        this.trainId = id;
        this.origin = origin.replaceAll("\\(\\)", "");
        this.departure = isEstimated ? " " : departure;
        this.destination = destination.replaceAll("\\(\\)", "");
        this.arrival = isEstimated ? " " : arrival;
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
        return Objects.equals(trainId, ((TrainMovement) obj).trainId);
    }

    static class Formatted {
        public String format(TrainMovement tm) {
            return String.format("%1$-5s %2$-40s %3$-5s %4$-40s %5$s delay", tm.departure, tm.origin, tm.arrival,
                    tm.destination, tm.delay);
        }
    }

}
