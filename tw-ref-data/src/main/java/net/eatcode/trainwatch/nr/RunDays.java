package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class RunDays {

    private static final int daysInWeek = 7;

    public static List<DayOfWeek> from(String runDays) {
        List<DayOfWeek> result = new ArrayList<>();
        int days = Integer.parseInt(runDays, 2);
        for (int i = 0; i < daysInWeek; i++) {
            if (isRunning(days)) {
                result.add(0, DayOfWeek.of(daysInWeek - i));
            }
            days >>= 1;
        }
        return result;
    }

    private static boolean isRunning(int days) {
        return (days & 1) == 1;
    }
}
