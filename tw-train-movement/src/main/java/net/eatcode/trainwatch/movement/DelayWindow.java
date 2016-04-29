package net.eatcode.trainwatch.movement;

public enum DelayWindow {
    upTo1Min, upTo5Mins, upTo10Mins, upTo15mins, over15mins;

    public static DelayWindow from(int delayInMins) {

        if (delayInMins <= 1)
            return upTo1Min;

        if (delayInMins <= 5)
            return upTo5Mins;

        if (delayInMins <= 10)
            return upTo10Mins;

        if (delayInMins <= 15)
            return upTo15mins;

        return over15mins;
    }

}
