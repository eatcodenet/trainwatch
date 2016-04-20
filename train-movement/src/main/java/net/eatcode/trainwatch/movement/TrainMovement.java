package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

// TODO ADD CURRENT LOCATION AND TIMESTAMP
public class TrainMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String trainId;
    private final Location origin;
    private final LocalTime departure;

    private final Location destination;
    private final LocalTime arrival;

    private final Integer delay;
    private final Location currentLocation;
    private final String timestamp;

    public TrainMovement(String trainId, String timestamp, Location currentLocation, String delay, Schedule schedule) {
        this.trainId = trainId;
        this.timestamp = timestamp;
        this.currentLocation = currentLocation;
        this.origin = schedule.origin;
        this.departure = schedule.departure;
        this.destination = schedule.destination;
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
            return Integer.parseInt(delay);
        } catch (Exception e) {
        }
        return 0;
    }

    static class Formatted {
        public String format(TrainMovement tm) {
            return String.format("%1$-5s %2$-40s %3$-40s", tm.departure, format(tm.origin), format(tm.destination));
        }

        private String format(Location l) {
            try {
                return l.description + " (" + l.crs + ")";
            } catch (Exception e) {
            }
            return "N/A";
        }
    }

}
