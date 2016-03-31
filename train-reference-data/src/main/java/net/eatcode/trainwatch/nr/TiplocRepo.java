package net.eatcode.trainwatch.nr;

public interface TiplocRepo {

    void putByStanox(Tiploc tiploc);

    Tiploc getByStanox(String stanox);
}
