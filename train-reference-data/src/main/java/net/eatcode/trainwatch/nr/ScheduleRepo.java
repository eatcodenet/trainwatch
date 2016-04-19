package net.eatcode.trainwatch.nr;

public interface ScheduleRepo {

    void put(Schedule schedule);

    Schedule get(String id);

    Integer count();
}
