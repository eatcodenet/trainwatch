package net.eatcode.trainwatch.nr;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

public class TransformTrustScheduleTest {

    @Test
    public void toDaySchedule() throws IOException {
        TrustSchedule ts = trustScheduleFromFile();
        DaySchedule daySchedule = new TransformTrustSchedule().toDaySchedule(ts);
        assertThat(daySchedule.trainServiceCode, is("57610314"));
    }

    private TrustSchedule trustScheduleFromFile() throws IOException {
        return new Gson().fromJson(new FileReader("src/test/resources/sampledata/1-valid-schedule.json"),
                TrustSchedule.class);
    }

}
