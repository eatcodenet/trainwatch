package net.eatcode.trainwatch.nr.hazelcast;

import net.eatcode.trainwatch.nr.DaySchedule;

public class DayScheduleSerializer extends CommonSerializer<DaySchedule> {

    @Override
    public int getTypeId() {
        return 2;
    }

    @Override
    protected Class<DaySchedule> getClassToSerialize() {
        return DaySchedule.class;
    }
}
