package net.eatcode.trainwatch.nr;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class RunDays {
    private static final int daysInWeek = 7;

    public static Set<DayOfWeek> from(String runDays) {
        Set<DayOfWeek> result = new HashSet<>();
        int days = Integer.parseInt(runDays, 2);
        for (int i = daysInWeek; i >= 0; i--) {
            if (isRunning(days)) {
                result.add(DayOfWeek.of(i));
            }
            days >>= 1;
        }
        return result;
    }

    private static boolean isRunning(int days) {
        return (days & 1) == 1;
    }
}
