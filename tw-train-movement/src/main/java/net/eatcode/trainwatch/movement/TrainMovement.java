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

    private final boolean hasArrived;

    private final boolean isPassenger;

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
        this.isPassenger = schedule.isPassenger();
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

    public boolean isPassenger() {
        return isPassenger;
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
        if (delay == null) {
            return 0;
        }
        return Integer.parseInt(delay);
    }

    static class Formatted {

        public String format(TrainMovement t) {
            String orig = t.origin == null ? "N/A" : t.origin.description;
            String oCrs = t.originCrs().equals("") ? "---" : t.originCrs();
            String dCrs = t.destCrs().equals("") ? "---" : t.destCrs();
            String dest = t.destination == null ? "N/A" : t.destination.description;
            String arrv = t.hasArrived + "";
            return String.format("%1$s %2$-3s %3$-32s %4$s %5$-3s %6$-32s %7$2dm %8$-5s %9$s",
                    t.departure, oCrs, orig, t.arrival, dCrs, dest, t.delay, arrv, t.timestamp);
        }
    }

    public Location currentLocation() {
        return currentLocation;
    }
}
