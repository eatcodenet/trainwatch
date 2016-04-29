package net.eatcode.trainwatch.nr;

public interface ScheduleRepo {

    void put(Schedule schedule);

    Schedule getByIdAndServiceCode(String id, String trainServiceCode);

    Integer count();
}
