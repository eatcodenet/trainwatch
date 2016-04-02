package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1;
import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1.Schedule_segment.Schedule_location;

public class TransformTrustSchedule {

    public DaySchedule toDaySchedule(TrustSchedule ts) {
        JsonScheduleV1 s = ts.JsonScheduleV1;
        Set<DayOfWeek> days = RunDays.from(s.schedule_days_runs);
        List<Schedule_location> locs = Arrays.asList(s.schedule_segment.schedule_location);
        String headcode = s.schedule_segment.signalling_id;
        String trainServiceCode = s.schedule_segment.CIF_train_service_code;
        String atoc = s.atoc_code;
        DaySchedule ds = new DaySchedule();
        ds.origin = null;
        ds.trainServiceCode = trainServiceCode;
        return ds;
    }
}
