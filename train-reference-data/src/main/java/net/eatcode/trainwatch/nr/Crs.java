package net.eatcode.trainwatch.nr;

import java.io.Serializable;

public class Crs implements Serializable {

    public String lat;
    public String lon;
    public String tiploc;
    public String name;
    public String crs;
    public String toc;

    @Override
    public String toString() {
        return "Crs{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", tiploc='" + tiploc + '\'' +
                ", name='" + name + '\'' +
                ", crs='" + crs + '\'' +
                ", toc='" + toc + '\'' +
                '}';
    }

    public LatLon latLon() {
        return new LatLon(lat, lon);
    }
}
