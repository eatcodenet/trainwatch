package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

public class TrainDeparture implements Serializable, Comparable<TrainDeparture> {

    private static final long serialVersionUID = 1L;

    private final String trainId;
    private final Location origin;
    private final LocalTime departure;

    private final Location destination;
    private final LocalTime arrival;

    private final LocalDateTime wtt;

    //TODO: check whether expected departure is needed
    public TrainDeparture(String trainId, String expectedDeparture, Schedule schedule) {
        this.trainId = trainId;
        this.origin = schedule.origin;
        this.wtt = makeDateFrom(expectedDeparture);
        this.departure = wtt.toLocalTime();
        this.destination = schedule.destination;
        this.arrival = schedule.arrival;
    }

    public LocalDateTime scheduledDeparture() {
        return wtt;
    }

    public String trainId() {
        return trainId;
    }

    public String originCrs() {
        return origin.crs;
    }

    public String originName() {
        return origin.description;
    }

    public Location origin() {
        return origin;
    }

    public String destCrs() {
        return destination.crs;
    }

    public String destName() {
        return destination.description;
    }
    
    public LocalTime departure() {
        return departure;
    }

    private LocalDateTime makeDateFrom(String timestamp) {
        return LocalDateTime.ofEpochSecond(Long.parseLong(timestamp) / 1000, 0, ZoneOffset.UTC);
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

    @Override
    public int compareTo(TrainDeparture o) {
        return scheduledDeparture().compareTo(o.scheduledDeparture());
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
