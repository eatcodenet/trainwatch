package net.eatcode.trains.model;

import net.eatcode.trains.model.Tiploc;

public interface TiplocRepo {

    void putByStanox(Tiploc tiploc);

    Tiploc getByStanox(String stanox);
}
