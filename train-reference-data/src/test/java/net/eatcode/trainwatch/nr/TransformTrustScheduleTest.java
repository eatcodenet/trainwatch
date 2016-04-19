package net.eatcode.trainwatch.nr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

import org.junit.Test;

import com.google.gson.Gson;

public class TransformTrustScheduleTest {

    @Test
    public void toDaySchedule() throws IOException {
        Schedule schedule = new TransformTrustSchedule(new StubGeoLocatiobRepo()).toSchedule(trustScheduleFromFile());
        assertThat(schedule.destination.stanox, is("a stanox"));
        assertThat(schedule.origin.stanox, is("a stanox"));
        assertThat(schedule.departure, is(LocalTime.parse("12:54")));
        assertThat(schedule.arrival, is(LocalTime.parse("15:20")));
        assertThat(schedule.trainServiceCode, is("57610314"));
        assertThat(schedule.atocCode, is("ZZ"));
        assertThat(schedule.runDays, is("0000011"));
    }

    private TrustSchedule trustScheduleFromFile() throws IOException {
        return new Gson().fromJson(new FileReader("src/test/resources/sampledata/1-valid-schedule.json"),
                TrustSchedule.class);
    }

    private static class StubGeoLocatiobRepo implements LocationRepo {

        Location location = new Location("a stanox", "desc", "a tiploc", "a crs", new LatLon("1", "2"));

        @Override
        public void put(Location location) {
        }

        @Override
        public Location getByStanox(String stanox) {
            return location;
        }

        @Override
        public Location getByTiploc(String tiploc) {
            return location;
        }
    }
}
