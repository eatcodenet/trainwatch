package net.eatcode.trainwatch.movement;

import java.io.Serializable;

public class Stats implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer numberOfTrainsMonitored;

    private final Integer highestDelay;

    public Stats(Integer numberOfTrainsMonitored, Integer highestDelay) {
        this.numberOfTrainsMonitored = numberOfTrainsMonitored;
        this.highestDelay = highestDelay;
    }

    public Integer getNumberOfTrainsMonitored() {
        return numberOfTrainsMonitored;
    }

    public Integer getHighestDelay() {
        return highestDelay;
    }
}
