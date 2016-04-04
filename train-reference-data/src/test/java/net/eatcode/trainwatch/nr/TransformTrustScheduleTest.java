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

        DaySchedule ds = new TransformTrustSchedule(new StubRepo()).toDaySchedule(trustScheduleFromFile());
        assertThat(ds.trainServiceCode, is("57610314"));
        assertThat(ds.destination.stanox, is("a stanox"));
        assertThat(ds.origin.stanox, is("a stanox"));
        assertThat(ds.headCode, is("SI01"));
        assertThat(ds.departure, is(LocalTime.parse("12:54")));
    }

    private TrustSchedule trustScheduleFromFile() throws IOException {
        return new Gson().fromJson(new FileReader("src/test/resources/sampledata/1-valid-schedule.json"),
                TrustSchedule.class);
    }

    private static class StubRepo implements GeoLocationRepo {

        final GeoLocation location = new GeoLocation("a stanox", "desc", "a tiploc", "a crs", new LatLon("1", "2"));

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
