package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.RunDays;
import org.junit.Test;

import java.time.DayOfWeek;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RunDaysTest {

    @Test
    public void asFlagsFrom() throws Exception {
        assertThat(RunDays.asFlagsFrom(DayOfWeek.MONDAY), is("1000000"));
        assertThat(RunDays.asFlagsFrom(DayOfWeek.TUESDAY), is("0100000"));
        assertThat(RunDays.asFlagsFrom(DayOfWeek.WEDNESDAY), is("0010000"));
        assertThat(RunDays.asFlagsFrom(DayOfWeek.THURSDAY), is("0001000"));
        assertThat(RunDays.asFlagsFrom(DayOfWeek.FRIDAY), is("0000100"));
        assertThat(RunDays.asFlagsFrom(DayOfWeek.SATURDAY), is("0000010"));
        assertThat(RunDays.asFlagsFrom(DayOfWeek.SUNDAY), is("0000001"));

    }
}
