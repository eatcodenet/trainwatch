package net.eatcode.trainwatch.nr;

import java.util.List;

public interface TiplocRepo {

    void putByStanox(Tiploc tiploc);

    Tiploc getByStanox(String stanox);

    List<Tiploc> findAll();
}
