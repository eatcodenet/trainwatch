package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	public final String stanox;
	public final String description;
	public final String crs;
	public final LatLon latLon;
	public final String tiploc;

	public Location(String stanox, String description, String tiploc, String crs, LatLon latLon) {
		this.stanox = stanox;
		this.description = description;
		this.tiploc = tiploc;
		this.crs = crs;
		this.latLon = latLon;
	}

	@Override
	public boolean equals(Object o) {
		return Objects.equals(stanox, ((Location) o).stanox);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stanox);
	}

	@Override
	public String toString() {
		return "Location [stanox=" + stanox + ", description=" + description + ", crs=" + crs + ", latLon=" + latLon
				+ ", tiploc=" + tiploc + "]";
	}

}
