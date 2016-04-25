package net.eatcode.trainwatch.movement;

import static net.eatcode.trainwatch.movement.Strings.titleCase;

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

    private final boolean hasArrived;

    public TrainMovement(String trainId, LocalDateTime timestamp, Location current, String delay, String terminated,
            Schedule schedule) {
        this.trainId = trainId;
        this.timestamp = timestamp;
        this.currentLocation = current;
        this.origin = schedule.origin;
        this.departure = schedule.departure;
        this.destination = schedule.destination;
        this.arrival = schedule.arrival;
        this.hasArrived = Boolean.valueOf(terminated);
        this.delay = parse(delay);
    }

    public LocalTime departure() {
        return departure;
    }

    public String trainId() {
        return trainId;
    }

    public String originCrs() {
        return origin == null ? "" : origin.crs;
    }

    public String destCrs() {
        return destination == null ? "" : destination.crs;
    }

    public LocalTime arrival() {
        return this.arrival;
    }

    public Location destination() {
        return this.destination;
    }

    public Location origin() {
        return this.origin;
    }

    public int delayInMins() {
        return this.delay;
    }

    public LocalDateTime timestamp() {
        return this.timestamp;
    }

    public boolean hasArrivedAtDest() {
        return hasArrived;
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
        if (delay == null)
            return 0;
        return Integer.parseInt(delay);
    }

    static class Formatted {

        public String format(TrainMovement tm) {
            String orig = tm.origin == null ? "N/A" : titleCase(tm.origin.description);
            String oCrs = tm.originCrs().equals("") ? "---" : tm.originCrs();
            String dCrs = tm.destCrs().equals("") ? "---" : tm.destCrs();
            String dest = tm.destination == null ? "N/A" : tm.destination.description;
            String curr = tm.currentLocation == null ? "N/A" : tm.currentLocation.description;
            return String.format("%1$s %2$-3s %3$-32s %4$-3s %5$-32s %6$s %7$2dm %8$-32s %9$s",
                    tm.departure, oCrs, orig, dCrs, dest, tm.arrival, tm.delay, curr, tm.timestamp);
        }
    }
}
