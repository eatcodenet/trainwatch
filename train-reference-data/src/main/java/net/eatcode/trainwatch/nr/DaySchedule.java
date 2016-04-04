package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class DaySchedule {

    GeoLocation origin;
    GeoLocation destination;
    LocalTime departure;
    LocalTime arrival;
    String trainServiceCode;
    String headCode;
    DayOfWeek runDay;
    String atocCode;

    public String id() {
        return trainServiceCode + headCode + runDay.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainServiceCode, headCode, runDay);
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
        DaySchedule other = (DaySchedule) obj;
        Objects.equals(id(), other.id());
        return true;
    }

}
