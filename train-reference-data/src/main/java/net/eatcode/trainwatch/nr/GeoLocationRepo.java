package net.eatcode.trainwatch.nr;

public interface GeoLocationRepo {

    void put(GeoLocation stanox);

    GeoLocation get(String stanox);
}
