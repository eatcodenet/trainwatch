package net.eatcode.trainwatch.nr;

public interface CrsRepo {

    void put(Crs crs);

    Crs get(String crsCode);
}
