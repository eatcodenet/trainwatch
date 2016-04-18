package net.eatcode.trainwatch.nr;

public interface DayScheduleRepo {

    void put(Schedule schedule);

    Schedule get(String id);

}
