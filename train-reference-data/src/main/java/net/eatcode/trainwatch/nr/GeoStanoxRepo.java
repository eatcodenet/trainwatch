package net.eatcode.trainwatch.nr;

public interface GeoStanoxRepo {

    void put(GeoStanox stanox);

    GeoStanox get(String stanox);
}
