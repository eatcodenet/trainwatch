package net.eatcode.trainwatch.nr.hazelcast;

import net.eatcode.trainwatch.nr.Schedule;

public class DayScheduleSerializer extends CommonSerializer<Schedule> {

    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    protected Class<Schedule> getClassToSerialize() {
        return Schedule.class;
    }
}
