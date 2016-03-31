package net.eatcode.trains.model;

import java.time.DayOfWeek;
import java.util.Arrays;

public class RunDays {

    public static String asFlagsFrom(DayOfWeek d) {
        char[] days ="0000000".toCharArray();
        days[d.getValue() - 1] = '1';
        return new String(days);
    }
}
