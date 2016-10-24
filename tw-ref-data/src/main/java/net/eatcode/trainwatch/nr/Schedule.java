package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;
	public LocalDate startDate;
	public LocalDate endDate;
	public Location origin;
	public Location destination;
	public LocalTime departure;
	public LocalTime arrival;
	public String trainServiceCode;
	public String atocCode;
	public String runDays;
	public Boolean isPassenger = Boolean.TRUE;

	public boolean isRunning() {
		return endDate.isAfter(LocalDate.now().minusDays(1));
	}

	public boolean isPassenger() {
		return isPassenger;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, trainServiceCode, runDays);
	}

	@Override
	public boolean equals(Object obj) {
		Schedule other = (Schedule) obj;
		return Objects.equals(id, other.id) && Objects.equals(trainServiceCode, other.trainServiceCode)
				&& Objects.equals(runDays, other.runDays);
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", origin=" + origin
				+ ", departure=" + departure + ", destination=" + destination + ",	 arrival=" + arrival
				+ ", trainServiceCode=" + trainServiceCode + ", atocCode=" + atocCode + "]";
	}

}
