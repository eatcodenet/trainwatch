package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1;
import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1.Schedule_segment.Schedule_location;

public class TransformTrustSchedule {

    private final GeoLocationRepo repo;

    public TransformTrustSchedule(GeoLocationRepo repo) {
        this.repo = repo;
    }

    public DaySchedule toDaySchedule(TrustSchedule ts) {
        JsonScheduleV1 s = ts.JsonScheduleV1;
        Set<DayOfWeek> days = RunDays.from(s.schedule_days_runs);
        List<Schedule_location> locs = Arrays.asList(s.schedule_segment.schedule_location);
        Schedule_location origin = getLocationByType(locs, "LO");
        String headcode = s.schedule_segment.signalling_id;
        String trainServiceCode = s.schedule_segment.CIF_train_service_code;
        String atoc = s.atoc_code;
        DaySchedule ds = new DaySchedule();
        ds.origin = getLocation(origin);
        ds.trainServiceCode = trainServiceCode;
        return ds;
    }

    private GeoLocation getLocation(Schedule_location origin) {
        return null;
    }

    private Schedule_location getLocationByType(List<Schedule_location> locs, String type) {
        return locs.stream().filter(sl -> sl.location_type.equals(type)).findFirst().get();
    }
}
