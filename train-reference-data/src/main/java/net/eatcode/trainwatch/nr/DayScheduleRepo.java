package net.eatcode.trainwatch.nr;

public interface DayScheduleRepo {

    void put(DaySchedule schedule);

    DaySchedule get(String id);

    DaySchedule getByDaykey(String key);

}
