package net.eatcode.trainwatch.movement;

import static net.eatcode.trainwatch.movement.Strings.titleCase;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;

public class TrainDeparture implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String trainId;
    private final Location origin;
    private final LocalTime departure;

    private final Location destination;
    private final LocalTime arrival;

    private final String originCrs;
    private final String destinationCrs;

    public TrainDeparture(String trainId, Location origin, LocalTime departure, Location destination,
            LocalTime arrival) {
        this.trainId = trainId;
        this.origin = origin;
        this.originCrs = origin == null ? "" : origin.crs;
        this.departure = departure;
        this.destination = destination;
        this.destinationCrs = destination == null ? "" : destination.crs;
        this.arrival = arrival;
    }

    public LocalTime departure() {
        return departure;
    }

    public String trainId() {
        return trainId;
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
        return Objects.equals(trainId, ((TrainDeparture) obj).trainId);
    }

    static class Formatted {

        public String format(TrainDeparture tm) {
            String orig = tm.origin == null ? "N/A" : titleCase(tm.origin.description);
            String oCrs = tm.originCrs.equals("") ? "---" : tm.originCrs;
            String dCrs = tm.destinationCrs.equals("") ? "---" : tm.destinationCrs;
            String dest = tm.destination == null ? "N/A" : tm.destination.description;
            return String.format("%1$s %2$-3s %3$-32s %4$-3s %5$-32s %6$s",
                    tm.departure, oCrs, orig, dCrs, dest, tm.arrival);
        }
    }

    public String originCrs() {
        return originCrs;
    }

    public String destCrs() {
        return destinationCrs;
    }
}
