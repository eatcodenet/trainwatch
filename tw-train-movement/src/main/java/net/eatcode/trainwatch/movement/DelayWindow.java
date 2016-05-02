package net.eatcode.trainwatch.movement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum DelayWindow implements Comparator<DelayWindow> {

    max5mins(5), max15mins(15), max30mins(30), over30mins(9999);

    private int delay;

    private DelayWindow(int delay) {
        this.delay = delay;
    }

    public static DelayWindow from(int delayInMins) {

        if (delayInMins <= 5) {
            return max5mins;
        }

        if (delayInMins <= 15) {
            return max15mins;
        }

        if (delayInMins <= 30) {
            return max30mins;
        }

        return over30mins;
    }

    @Override
    public int compare(DelayWindow o1, DelayWindow o2) {
        if (o1.ordinal() == o2.ordinal()) {
            return 1;
        } else if (o1.ordinal() < o2.ordinal()) {
            return -1;
        } else {
            return 1;
        }
    }

    public static List<DelayWindow> sortedValues() {
        List<DelayWindow> values = Arrays.asList(DelayWindow.values());
        Collections.sort(values);
        return values;
    }

    public int delay() {
        return delay;
    }

}
