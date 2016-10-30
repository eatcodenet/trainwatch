package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

public class TrainMovement implements Serializable, Comparable<TrainMovement> {

	private static final long serialVersionUID = 1L;

	private final String trainId;
	private final Location origin;
	private final LocalTime departure;

	private final Location destination;
	private final LocalTime arrival;

	private final Integer delay;
	private final Location currentLocation;
	private final LocalDateTime timestamp;

	private final boolean hasArrived;

	private final boolean isPassenger;

	public TrainMovement(String trainId, LocalDateTime timestamp, Location current, String delay, String terminated,
			Schedule schedule) {
		this.trainId = trainId;
		this.timestamp = timestamp;
		this.currentLocation = current;
		this.origin = schedule.origin;
		this.departure = schedule.departure;
		this.destination = schedule.destination;
		this.arrival = schedule.arrival;
		this.hasArrived = Boolean.valueOf(terminated);
		this.delay = Integer.parseInt(delay == null ? "0" : delay);
		this.isPassenger = schedule.isPassenger;
	}

	public LocalTime departure() {
		return departure;
	}

	public String trainId() {
		return trainId;
	}

	public String originCrs() {
		return safeCrs(origin);
	}

	private String safeCrs(Location loc) {
		if (loc == null) {
			return "---";
		}
		return loc.crs == null ? "---" : loc.crs;
	}

	public String destCrs() {
		return safeCrs(destination);
	}

	public LocalTime arrival() {
		return this.arrival;
	}

	public Location destination() {
		return this.destination;
	}

	public Location origin() {
		return this.origin;
	}

	public Integer delayInMins() {
		return this.delay;
	}

	public LocalDateTime timestamp() {
		return this.timestamp;
	}

	public boolean hasArrivedAtDest() {
		return hasArrived;
	}

	public boolean isPassenger() {
		return isPassenger;
	}

	@Override
	public String toString() {
		return new Formatted().format(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(trainId);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(trainId, ((TrainMovement) obj).trainId);
	}

	static class Formatted {

		public String format(TrainMovement t) {
			String orig = t.origin == null ? "N/A" : t.origin.description;
			String dest = t.destination == null ? "N/A" : t.destination.description;
			String oCrs = t.originCrs();
			String dCrs = t.destCrs();
			String current = t.currentLocation.tiploc;
			return String.format("%1$s %2$-3s %3$-32s %4$s %5$-3s %6$-32s %7$2dm %8$s", t.departure, oCrs, orig,
					t.arrival, dCrs, dest, t.delay, current);
		}
	}

	public Location currentLocation() {
		return currentLocation;
	}

	@Override
	public int compareTo(TrainMovement o) {
		return o.timestamp.compareTo(timestamp); // reverse
	}
}
