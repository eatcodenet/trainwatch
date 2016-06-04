package net.eatcode.trainwatch.nr;

import java.util.Date;
import java.util.TimeZone;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

public class DateShizzle {

    private final TimeZone tzLondon = TimeZone.getTimeZone("Europe/London");

    @Test
    public void test() {
        Date epoch = LocalDateTime.parse("1970-01-01T01:55").toDate(tzLondon);
        LocalTime t1 = org.joda.time.LocalTime.fromDateFields(epoch);
        System.out.println(t1);

        Date d1 = LocalDateTime.parse("2016-10-29T19:00").toDate();
        System.out.println(d1);

        Date d2 = LocalDateTime.fromDateFields(d1)
                .withHourOfDay(t1.getHourOfDay())
                .withMinuteOfHour(t1.getMinuteOfHour())
                .plusDays(1)
                .toDate(tzLondon);
        System.out.println(d2);

        int delta = d1.getTimezoneOffset() - d2.getTimezoneOffset();
        System.out.println(delta);

        Date d3 = LocalDateTime.fromDateFields(d2).plusMinutes(delta).toDate();


        System.out.println(d3);
    }

}
