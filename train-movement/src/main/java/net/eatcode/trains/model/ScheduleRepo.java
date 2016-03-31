package net.eatcode.trains.model;

import java.util.List;

public interface ScheduleRepo {

    void put(Schedule schedule);

    Schedule get(String id);

    List<Schedule> getForServiceCode(String trainServiceCode);

    long getCount();
}
