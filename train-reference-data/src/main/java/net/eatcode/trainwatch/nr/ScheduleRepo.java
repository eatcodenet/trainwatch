package net.eatcode.trainwatch.nr;

import java.util.List;

public interface ScheduleRepo {

    void put(TrustSchedule schedule);

    TrustSchedule get(String id);

    List<TrustSchedule> getForServiceCode(String trainServiceCode);

}
