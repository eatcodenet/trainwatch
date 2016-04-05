package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class DaySchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    public String id;
    public Location origin;
    public Location destination;
    public LocalTime departure;
    public LocalTime arrival;
    public String trainServiceCode;
    String headCode;
    DayOfWeek runDay;
    String atocCode;
    public boolean estimate = false;

    public String key() {
        return trainServiceCode + headCode + runDay.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(key());
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
        return Objects.equals(key(), ((DaySchedule) obj).key());
    }

}
