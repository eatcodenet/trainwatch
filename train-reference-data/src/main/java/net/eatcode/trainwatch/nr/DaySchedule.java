package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DaySchedule {

    GeoLocation origin;
    GeoLocation destination;
    LocalTime departure;
    LocalTime arrival;
    String trainServiceCode;
    String headCode;
    DayOfWeek runDay;
    String atocCode;

    String id() {
        return trainServiceCode + headCode + runDay.getValue();
    }
}
