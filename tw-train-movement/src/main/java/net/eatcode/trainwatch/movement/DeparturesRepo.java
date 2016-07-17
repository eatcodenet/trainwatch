package net.eatcode.trainwatch.movement;

public interface DeparturesRepo {

    void put(TrainDeparture td);

    void delete(String trainId);


}
