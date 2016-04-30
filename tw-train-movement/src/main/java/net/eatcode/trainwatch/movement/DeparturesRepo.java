package net.eatcode.trainwatch.movement;

import java.util.List;

public interface DeparturesRepo {

    void put(TrainDeparture td);

    List<String> getAvailableCrsCodes();

    List<TrainDeparture> getByCrs(String crs);
}
