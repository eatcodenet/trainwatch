package net.eatcode.trainwatch.movement;

import java.util.List;

public interface LiveDeparturesRepo {

    void put(TrainDeparture td);

    List<String> getAvailableCrsCodes();

    List<TrainDeparture> getByCrs(String crs);
}
