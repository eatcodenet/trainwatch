package net.eatcode.trainwatch.movement;

public enum DelayWindow {
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

}
