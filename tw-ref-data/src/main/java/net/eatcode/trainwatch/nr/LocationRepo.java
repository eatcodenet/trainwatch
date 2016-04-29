package net.eatcode.trainwatch.nr;

public interface LocationRepo {

    void put(Location location);

    Location getByStanox(String stanox);

    Location getByTiploc(String tiploc);
}
