package net.eatcode.trainwatch.movement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum DelayWindow implements Comparator<DelayWindow> {

    upTo5mins, from5to15mins, from15to30mins, over30mins;

    public static DelayWindow from(int delayInMins) {

        if (delayInMins <= 5) {
            return upTo5mins;
        }

        if (delayInMins <= 15) {
            return from5to15mins;
        }

        if (delayInMins <= 30) {
            return from15to30mins;
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

}
