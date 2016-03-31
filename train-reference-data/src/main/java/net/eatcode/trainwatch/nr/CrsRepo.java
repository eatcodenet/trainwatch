package net.eatcode.trainwatch.nr;

import java.util.Optional;

public interface CrsRepo {

    void put(Crs crs);

    Optional<Crs> get(String crsCode);
}
