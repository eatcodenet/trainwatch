package net.eatcode.trainwatch.nr;

import java.io.Serializable;

public class LatLon implements Serializable {

    public final String lat;
    public final String lon;

    public LatLon(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "LatLon(" + lat + ", " + lon + ")";
    }
}
