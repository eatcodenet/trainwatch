package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Schedule;

public class TrainActivation implements Serializable {

	private static final long serialVersionUID = 1L;

	public final String trainId;
	public final Schedule schedule;
	public final String wttDeparture;
	private final LocalDateTime timestamp = LocalDateTime.now();

	public TrainActivation(String trainId, Schedule schedule, String wttDeparture) {
		this.trainId = trainId;
		this.schedule = schedule;
		this.wttDeparture = wttDeparture;
	}

	@Override
	public int hashCode() {
		return Objects.hash(trainId, schedule.id);
	}

	@Override
	public boolean equals(Object obj) {
		TrainActivation other = (TrainActivation) obj;
		return Objects.equals(trainId, other.trainId) && Objects.equals(schedule.id, other.schedule.id);
	}

	public LocalDateTime timestamp() {
		return this.timestamp;
	}

	@Override
	public String toString() {
		return "TrainActivation [trainId=" + trainId + ", schedule.id=" + schedule.id + "]";
	}

}
