package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

public class TrainDeparture implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String trainId;
    private final Location origin;
    private final LocalTime departure;

    private final Location destination;
    private final LocalTime arrival;

    private final LocalDateTime expectedDeparture;

    public TrainDeparture(String trainId, String expectedDeparture, Schedule schedule) {
        this.trainId = trainId;
        this.origin = schedule.origin;
        this.departure = schedule.departure;
        this.destination = schedule.destination;
        this.arrival = schedule.arrival;
        this.expectedDeparture = makeDateFrom(expectedDeparture);
    }

    public LocalTime scheduledDeparture() {
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

    public LocalTime expectedDepartureTime() {
        return expectedDeparture.toLocalTime();
    }

    public long delayInMins() {
        return Duration.between(departure.atDate(LocalDate.now()), expectedDeparture).toMinutes();
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

    static class Formatted {

        public String format(TrainDeparture t) {
            String orig = t.origin == null ? "N/A" : t.origin.description;
            String oCrs = t.originCrs().equals("") ? "---" : t.originCrs();
            String dCrs = t.destCrs().equals("") ? "---" : t.destCrs();
            String dest = t.destination == null ? "N/A" : t.destination.description;
            Long mins = t.delayInMins();
            System.out.println(t.expectedDeparture);
            return String.format("%1$s (%7$dm) %2$-3s %3$-32s %4$s %5$-3s %6$-32s",
                    t.expectedDepartureTime(), oCrs, orig, t.arrival, dCrs, dest, mins);
        }
    }

}
