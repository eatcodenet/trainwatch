package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1;
import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1.Schedule_segment.Schedule_location;

public class TransformTrustSchedule {

    private final LocationRepo repo;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmm");

    public TransformTrustSchedule(LocationRepo repo) {
        this.repo = repo;
    }

    public List<DaySchedule> toDaySchedules(TrustSchedule ts) {
        JsonScheduleV1 s = ts.JsonScheduleV1;
        List<DayOfWeek> days = RunDays.from(s.schedule_days_runs);
        List<Schedule_location> locs = Arrays.asList(s.schedule_segment.schedule_location);
        Schedule_location origin = getLocationByType(locs, "LO");
        Schedule_location dest = getLocationByType(locs, "LT");
        String headcode = s.schedule_segment.signalling_id;
        String trainServiceCode = s.schedule_segment.CIF_train_service_code;
        String atoc = s.atoc_code;
        String id = s.CIF_train_uid;
        return days.stream().map(dow -> makeDaySchedule(id, dow, origin, dest, headcode, trainServiceCode, atoc))
                .collect(Collectors.toList());
    }

    private DaySchedule makeDaySchedule(String id, DayOfWeek dow, Schedule_location origin, Schedule_location dest,
            String headcode, String trainServiceCode, String atoc) {
        DaySchedule ds = new DaySchedule();
        ds.id = id;
        ds.runDay = dow;
        ds.origin = getLocation(origin);
        ds.departure = LocalTime.parse(origin.departure.substring(0, 4), fmt);
        ds.destination = getLocation(dest);
        ds.arrival = LocalTime.parse(dest.arrival.substring(0, 4), fmt);
        ds.trainServiceCode = trainServiceCode;
        ds.headCode = headcode;
        ds.atocCode = atoc;
        return ds;
    }

    private Location getLocation(Schedule_location loc) {
        return repo.getByTiploc(loc.tiploc_code);
    }

    private Schedule_location getLocationByType(List<Schedule_location> locs, String type) {
        return locs.stream().filter(sl -> sl.location_type.equals(type)).findFirst().get();
    }
}
