package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

public class TrainMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String trainId;
    private final Location origin;
    private final LocalTime departure;

    private final Location destination;
    private final LocalTime arrival;

    private final Integer delay;
    private final Location currentLocation;
    private final LocalDateTime timestamp;

    private final String originCrs;
    private final String destinationCrs;

    public TrainMovement(String trainId, LocalDateTime timestamp, Location current, String delay, Schedule schedule) {
        this.trainId = trainId;
        this.timestamp = timestamp;
        this.currentLocation = current;
        this.origin = schedule.origin;
        this.originCrs = origin == null ? "" : origin.crs;
        this.departure = schedule.departure;
        this.destination = schedule.destination;
        this.destinationCrs = destination == null ? "" : destination.crs;
        this.arrival = schedule.arrival;
        this.delay = parse(delay);
    }

    public int delayInMins() {
        return this.delay;
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

    private Integer parse(String delay) {
        try {
            if (delay == null)
                return 0;
            return Integer.parseInt(delay);
        } catch (Exception e) {
        }
        return 0;
    }

    static class Formatted {
        public String format(TrainMovement tm) {
            String orig = tm.origin == null ? "N/A" : tm.origin.description;
            String oCrs = tm.originCrs.equals("") ? "---" : tm.originCrs;
            String dCrs = tm.destinationCrs.equals("") ? "---" : tm.destinationCrs;
            String dest = tm.destination == null ? "N/A" : tm.destination.description;
            String curr = tm.currentLocation == null ? "N/A" : tm.currentLocation.description;
            return String.format("%1$s %2$-3s %3$-40s %4$-3s %5$-40s %6$s %7$2dm delay location: %8$-40s %9$s",
                    tm.departure, oCrs, orig, dCrs, dest, tm.arrival, tm.delay, curr, tm.timestamp);
        }

    }

}
