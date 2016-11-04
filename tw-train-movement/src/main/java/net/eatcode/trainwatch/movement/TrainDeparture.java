package net.eatcode.trainwatch.movement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

public class TrainDeparture implements Serializable, Comparable<TrainDeparture> {

	private static final long serialVersionUID = 1L;

	private final String trainId;
	private final Location origin;
	private final LocalTime departure;

	private final Location destination;
	private final LocalTime arrival;

	private final LocalDateTime wtt;

	private final long cutOffInMins = 3;

	public TrainDeparture(String trainId, String wttDeparture, Schedule schedule) {
		this.trainId = trainId;
		this.origin = schedule.origin;
		this.wtt = makeDateTimeFrom(wttDeparture);
		this.departure = wtt.toLocalTime();
		this.destination = schedule.destination;
		this.arrival = schedule.arrival;
	}

	public LocalDateTime scheduledDeparture() {
		return wtt;
	}

	public String trainId() {
		return trainId;
	}

	public String originCrs() {
		if (origin == null) {
			return "";
		}
		return origin.crs;
	}

	public String originName() {
		return origin.description;
	}

	public Location origin() {
		return origin;
	}

	public String destCrs() {
		if (destination == null) {
			return "";
		}
		return destination.crs;
	}

	public String destName() {
		return destination.description;
	}

	public LocalTime departure() {
		return departure;
	}

	private LocalDateTime makeDateTimeFrom(String timestamp) {
		return LocalDateTime.ofEpochSecond(Long.parseLong(timestamp) / 1000, 0, ZoneOffset.UTC);
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
		return Objects.equals(trainId, ((TrainDeparture) obj).trainId);
	}

	@Override
	public int compareTo(TrainDeparture o) {
		return scheduledDeparture().compareTo(o.scheduledDeparture());
	}

	public boolean hasDepartedAccordingToSchedule() {
		LocalTime cutOff = departure.plusMinutes(cutOffInMins);
		return LocalTime.now().isAfter(cutOff);
	}

	static class Formatted {

		public String format(TrainDeparture t) {
			String orig = t.origin == null ? "N/A" : t.origin.description;
			String dest = t.destination == null ? "N/A" : t.destination.description;
			return String.format("%1$s %2$s %3$s %4$s %5$s", t.departure, orig, t.arrival, dest, t.wtt);
		}
	}

}
