package net.eatcode.trainwatch.nr;

public interface ScheduleRepo {

    void put(DaySchedule schedule);

    DaySchedule get(String id);

}
