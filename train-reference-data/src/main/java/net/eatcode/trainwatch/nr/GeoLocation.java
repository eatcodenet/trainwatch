package net.eatcode.trainwatch.nr;

import java.io.Serializable;
import java.util.Objects;

public class GeoLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String stanox;
    public final String description;
    public final String crs;
    public final LatLon latLon;
    public final String tiploc;

    public GeoLocation(String stanox, String description, String tiploc, String crs, LatLon latLon) {
        this.stanox = stanox;
        this.description = description;
        this.tiploc = tiploc;
        this.crs = crs;
        this.latLon = latLon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation location = (GeoLocation) o;
        return Objects.equals(stanox, location.stanox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stanox);
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "stanox='" + stanox + '\'' +
                ", description='" + description + '\'' +
                ", tiploc='" + tiploc + '\'' +
                ", crs='" + crs + '\'' +
                ", latLon=" + latLon +
                '}';
    }
}
