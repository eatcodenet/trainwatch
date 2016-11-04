package net.eatcode.trainwatch.search;

import java.util.Objects;

public class Station implements Comparable<Station> {

	public final String name;
	public final String crs;

	public Station(String name, String crs) {
		this.name = name;
		this.crs = crs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(name, ((Station) obj).name);
	}

	@Override
	public String toString() {
		return "Station [" + name + " " + crs + "]";
	}

	@Override
	public int compareTo(Station o) {
		return name.compareTo(o.name);
	}

}
