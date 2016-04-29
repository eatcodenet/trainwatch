package net.eatcode.trainwatch.search;

import java.util.List;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainMovement;

public interface TrainWatchSearch {

    List<Station> listStations();

    List<TrainMovement> trainMovementsByDelay(DelayWindow d, int limit);
}
