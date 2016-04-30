package net.eatcode.trainwatch.nr;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1;
import net.eatcode.trainwatch.nr.TrustSchedule.JsonScheduleV1.Schedule_segment.Schedule_location;

public class TransformTrustSchedule {

    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm");
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;

    private final LocationRepo repo;

    public TransformTrustSchedule(LocationRepo repo) {
        this.repo = repo;
    }

    public Schedule toSchedule(TrustSchedule ts) {

        JsonScheduleV1 s = ts.JsonScheduleV1;

        List<Schedule_location> locs = Arrays.asList(s.schedule_segment.schedule_location);
        String id = s.CIF_train_uid;
        LocalDate start = LocalDate.parse(s.schedule_start_date, dateFormat);
        LocalDate end = LocalDate.parse(s.schedule_end_date, dateFormat);
        Schedule_location origin = getLocationByType(locs, "LO");
        Schedule_location dest = getLocationByType(locs, "LT");
        String trainServiceCode = s.schedule_segment.CIF_train_service_code;
        String atoc = s.atoc_code;
        return makeDaySchedule(id, start, end, origin, dest, trainServiceCode, atoc, s.schedule_days_runs);
    }

    private Schedule makeDaySchedule(String id, LocalDate start, LocalDate end, Schedule_location origin,
            Schedule_location dest, String trainServiceCode, String atoc, String runDays) {
        Schedule s = new Schedule();
        s.id = id;
        s.startDate = start;
        s.endDate = end;
        s.origin = getLocation(origin);
        s.departure = LocalTime.parse(origin.public_departure.substring(0, 4), timeFormat);
        s.destination = getLocation(dest);
        s.arrival = LocalTime.parse(dest.public_arrival.substring(0, 4), timeFormat);
        s.runDays = runDays;
        s.trainServiceCode = trainServiceCode;
        s.atocCode = atoc;
        return s;
    }

    private Location getLocation(Schedule_location loc) {
        return repo.getByTiploc(loc.tiploc_code);
    }

    private Schedule_location getLocationByType(List<Schedule_location> locs, String type) {
        try {
            return locs.stream().filter(sl -> sl.location_type.equals(type)).findFirst().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
