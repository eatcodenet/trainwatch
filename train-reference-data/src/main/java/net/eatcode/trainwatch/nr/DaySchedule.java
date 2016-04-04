package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DaySchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmm");

    Location origin;
    Location destination;
    LocalTime departure;
    LocalTime arrival;
    String trainServiceCode;
    String headCode;
    DayOfWeek runDay;
    String atocCode;

    public String id() {
        return trainServiceCode + headCode + runDay.getValue() + departure.format(fmt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainServiceCode, headCode, runDay, departure);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(id(), ((DaySchedule) obj).id());
    }

}
