package net.eatcode.trainwatch.nr.dataimport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.eatcode.trainwatch.nr.TrustSchedule;

public class ScheduleFileParserTest {

    @Test
    public void parsesFile() throws Exception {
        List<TrustSchedule> result = new ArrayList<>();
        ScheduleFileParser parse = new ScheduleFileParser("src/test/resources/sampledata/10-valid-schedules.json");
        parse.parse(schedule -> {
            result.add(schedule);
            System.err.println(schedule.JsonScheduleV1.schedule_segment.schedule_location[0].location_type);
        }).whenCompleteAsync((value, err) -> {
            assertThat(result.size(), is(10));
            assertThat(result.get(0).JsonScheduleV1.schedule_segment.CIF_train_service_code, is("57610314"));
            assertThat(result.get(9).JsonScheduleV1.schedule_segment.CIF_train_service_code, is("21702001"));
        }).get();
    }

}
