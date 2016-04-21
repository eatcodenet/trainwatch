package net.eatcode.trainwatch.movement.kafka;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TrainActivation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String trainId;
    private final String scheduleId;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public TrainActivation(String trainId, String scheduleId) {
        this.trainId = trainId;
        this.scheduleId = scheduleId;
    }

    public String trainId() {
        return trainId;
    }

    public String scheduleId() {
        return scheduleId;
    }

    LocalDateTime timestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, scheduleId);
    }

    @Override
    public boolean equals(Object obj) {
        TrainActivation other = (TrainActivation) obj;
        return Objects.equals(trainId, other.trainId) && Objects.equals(scheduleId, other.scheduleId);
    }

    @Override
    public String toString() {
        return "TrainActivation [trainId=" + trainId + ", scheduleId=" + scheduleId + "]";
    }

}
