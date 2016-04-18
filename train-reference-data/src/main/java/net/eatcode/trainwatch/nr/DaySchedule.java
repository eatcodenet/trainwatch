package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class DaySchedule implements Serializable {

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
    public List<DayOfWeek> runDays;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(id, ((DaySchedule) obj).id);
    }

}
