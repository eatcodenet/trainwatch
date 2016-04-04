package net.eatcode.trainwatch.nr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

public class TransformTrustScheduleTest {

    @Test
    public void toDaySchedule() throws IOException {

        List<DaySchedule> schedules = new TransformTrustSchedule(new StubGeoLocatiobRepo())
                .toDaySchedules(trustScheduleFromFile());
        assertThat(schedules.size(), is(5));
        DaySchedule ds = schedules.get(0);
        assertThat(ds.destination.stanox, is("a stanox"));
        assertThat(ds.origin.stanox, is("a stanox"));
        assertThat(ds.departure, is(LocalTime.parse("12:54")));
        assertThat(ds.arrival, is(LocalTime.parse("15:20")));
        assertThat(ds.trainServiceCode, is("57610314"));
        assertThat(ds.headCode, is("SI01"));
        assertThat(ds.atocCode, is("ZZ"));
        assertThat(ds.runDay, is(DayOfWeek.MONDAY));
        assertThat(ds.id(), is("57610314SI011"));
    }

    private TrustSchedule trustScheduleFromFile() throws IOException {
        return new Gson().fromJson(new FileReader("src/test/resources/sampledata/1-valid-schedule.json"),
                TrustSchedule.class);
    }

    private static class StubGeoLocatiobRepo implements GeoLocationRepo {

        GeoLocation location = new GeoLocation("a stanox", "desc", "a tiploc", "a crs", new LatLon("1", "2"));

        @Override
        public void put(GeoLocation location) {
        }

        @Override
        public GeoLocation getByStanox(String stanox) {
            return location;
        }

        @Override
        public GeoLocation getByTiploc(String tiploc) {
            return location;
        }
    }
}
