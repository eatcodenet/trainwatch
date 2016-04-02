package net.eatcode.trainwatch.nr;

public class DaySchedule {

    private DaySchedule(Builder builder) {
    }

    public static class Builder {

        public DaySchedule build() {
            return new DaySchedule(this);
        }

    }

}
