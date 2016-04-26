package net.eatcode.trainwatch.movement;

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

    public TrainDeparture(String trainId, Location origin, LocalTime departure, Location destination,
            LocalTime arrival) {
        this.trainId = trainId;
        this.origin = origin;
        this.departure = departure;
        this.destination = destination;
        this.arrival = arrival;
    }

    public LocalTime departure() {
        return departure;
    }

    public String trainId() {
        return trainId;
    }

    public String originCrs() {
        return origin.crs;
    }

    public String destCrs() {
        return destination.crs;
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

        public String format(TrainDeparture t) {
            String orig = t.origin == null ? "N/A" : t.origin.description;
            String oCrs = t.originCrs().equals("") ? "---" : t.originCrs();
            String dCrs = t.destCrs().equals("") ? "---" : t.destCrs();
            String dest = t.destination == null ? "N/A" : t.destination.description;
            return String.format("%1$s %2$-3s %3$-32s %4$s %5$-3s %6$-32s",
                    t.departure, oCrs, orig, t.arrival, dCrs, dest);
        }
    }

}
