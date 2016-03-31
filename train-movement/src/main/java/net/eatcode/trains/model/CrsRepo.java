package net.eatcode.trains.model;

public interface CrsRepo {

    void put(Crs crs);

    Crs get(String crsCode);
}
