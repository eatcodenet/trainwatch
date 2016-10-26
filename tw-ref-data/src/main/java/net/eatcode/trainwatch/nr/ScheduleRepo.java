package net.eatcode.trainwatch.nr;

public interface ScheduleRepo {

    void put(Schedule schedule);

    Schedule getBy(String id, String trainServiceCode, String start, String end);
    

    Integer count();
}
