package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;
	public String trainServiceCode;
	public LocalDate startDate;
	public LocalDate endDate;
	public Location origin;
	public Location destination;
	public LocalTime departure;
	public LocalTime arrival;
	public String atocCode;
	public Boolean isPassenger = Boolean.TRUE;

	public boolean isRunning() {
		return endDate.isAfter(LocalDate.now().minusDays(1));
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniqueKey());
	}

	@Override
	public boolean equals(Object obj) {
		Schedule other = (Schedule) obj;
		return Objects.equals(uniqueKey(), other.uniqueKey());
	}

	@Override
	public String toString() {
		return "Schedule [id=" + id + ", trainServiceCode=" + trainServiceCode + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", origin=" + origin + ", destination=" + destination + ", departure="
				+ departure + ", arrival=" + arrival + ", atocCode=" + atocCode + ", isPassenger=" + isPassenger + "]";
	}

	public String uniqueKey() {
		return id + trainServiceCode + startDate + endDate;
	}

}
