package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TrainActivation implements Serializable {

	private static final long serialVersionUID = 1L;

	public final String trainId;
	public final String scheduleId;
	public final String serviceCode;
	public final String startDate;
	public final String endDate;

	private final LocalDateTime timestamp = LocalDateTime.now();

	public TrainActivation(String trainId, String serviceCode, String scheduleId, String start, String end) {
		this.trainId = trainId;
		this.serviceCode = serviceCode;
		this.scheduleId = scheduleId;
		this.startDate = start;
		this.endDate = end;
	}

	@Override
	public int hashCode() {
		return Objects.hash(trainId, serviceCode, scheduleId);
	}

	@Override
	public boolean equals(Object obj) {
		TrainActivation other = (TrainActivation) obj;
		return Objects.equals(trainId, other.trainId) && Objects.equals(serviceCode, other.serviceCode)
				&& Objects.equals(scheduleId, other.scheduleId);
	}

	public LocalDateTime timestamp() {
		return this.timestamp;
	}

	@Override
	public String toString() {
		return "TrainActivation [trainId=" + trainId + ", scheduleId=" + scheduleId + ", serviceCode=" + serviceCode
				+ "]";
	}

}
