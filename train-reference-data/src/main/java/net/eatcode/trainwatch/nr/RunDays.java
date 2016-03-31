package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;

public class RunDays {

    public static String asFlagsFrom(DayOfWeek d) {
        char[] days ="0000000".toCharArray();
        days[d.getValue() - 1] = '1';
        return new String(days);
    }
}
