package net.eatcode.trainwatch.nr;

import java.io.Serializable;

public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Station empty = new Station();

    public String lat = "";
    public String lon = "";
    public String tiploc = "";
    public String name = "";
    public String crs = "";
    public String toc = "";

    @Override
    public String toString() {
        return "Station{" + "lat='" + lat + '\'' + ", lon='" + lon + '\'' + ", tiploc='" + tiploc + '\'' + ", name='" + name
                + '\'' + ", crs='" + crs + '\'' + ", toc='" + toc + '\'' + '}';
    }

    public LatLon latLon() {
        return new LatLon(lat, lon);
    }

}
