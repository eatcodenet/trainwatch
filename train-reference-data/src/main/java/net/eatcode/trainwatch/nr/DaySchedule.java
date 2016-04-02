package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DaySchedule {

    GeoStanox origin;
    GeoStanox destination;
    LocalDateTime departure;
    LocalDateTime arrival;
    String trainServiceCode;
    String headCode;
    DayOfWeek runDay;
    String atocCode;

    String id() {
        return trainServiceCode + headCode + runDay.getValue();
    }
}
