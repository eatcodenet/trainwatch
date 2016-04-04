package net.eatcode.trainwatch.nr;

public interface GeoLocationRepo {

    void put(GeoLocation location);

    GeoLocation getByStanox(String stanox);

    GeoLocation getByTiploc(String tiploc);
}
