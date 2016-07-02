package net.eatcode.trainwatch.search;

import java.util.Objects;

public class Station implements Comparable<Station> {

    private final String name;
    private final String crs;

    public Station(String name, String crs) {
        this.name = name;
        this.crs = crs;
    }

    public String getName() {
        return name;
    }

    public String getCrs() {
        return crs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(crs);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(crs, ((Station) obj).crs);
    }

    @Override
    public String toString() {
        return "Station [" + crs + " " + name + "]";
    }

    @Override
    public int compareTo(Station o) {
        return crs.compareTo(o.crs);
    }

}
