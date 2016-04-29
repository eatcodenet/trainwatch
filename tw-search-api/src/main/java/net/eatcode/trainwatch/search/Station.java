package net.eatcode.trainwatch.search;

public class Station {

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
}
