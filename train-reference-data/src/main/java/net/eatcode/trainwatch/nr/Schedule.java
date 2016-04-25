package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    public String id;
    public LocalDate startDate;
    public LocalDate endDate;
    public Location origin;
    public LocalTime departure;
    public Location destination;
    public LocalTime arrival;
    public String trainServiceCode;
    public String atocCode;
    public String runDays;

    public boolean isRunning() {
        return endDate.isAfter(LocalDate.now());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainServiceCode, runDays);
    }

    @Override
    public boolean equals(Object obj) {
        Schedule other = (Schedule) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(trainServiceCode, other.trainServiceCode) &&
                Objects.equals(runDays, other.runDays);
    }

    @Override
    public String toString() {
        return "Schedule [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", origin=" + origin
                + ", departure=" + departure + ", destination=" + destination + ", arrival=" + arrival
                + ", trainServiceCode=" + trainServiceCode + ", atocCode=" + atocCode + "]";
    }

}
