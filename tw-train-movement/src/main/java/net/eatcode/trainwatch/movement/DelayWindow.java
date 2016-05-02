package net.eatcode.trainwatch.movement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum DelayWindow implements Comparator<DelayWindow> {

    max5mins, max15mins, max30mins, over30mins;

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
        System.out.println(values);
        return values;
    }

}
