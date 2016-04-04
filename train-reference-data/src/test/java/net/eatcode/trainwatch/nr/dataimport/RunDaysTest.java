package net.eatcode.trainwatch.nr.dataimport;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.eatcode.trainwatch.nr.RunDays;

public class RunDaysTest {

    @Test
    public void toDaysOfWeek() throws Exception {
        assertThat(RunDays.from("0000000"), equalTo(list()));
        assertThat(RunDays.from("1111111"), equalTo(list(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)));
        assertThat(RunDays.from("1111110"), equalTo(list(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)));
        assertThat(RunDays.from("1111100"), equalTo(list(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)));
        assertThat(RunDays.from("1111000"), equalTo(list(MONDAY, TUESDAY, WEDNESDAY, THURSDAY)));
        assertThat(RunDays.from("1110000"), equalTo(list(MONDAY, TUESDAY, WEDNESDAY)));
        assertThat(RunDays.from("1100000"), equalTo(list(MONDAY, TUESDAY)));
        assertThat(RunDays.from("1000000"), equalTo(list(MONDAY)));

    }

    private List<DayOfWeek> list(DayOfWeek... d) {
        return Arrays.asList(d);
    }

}
