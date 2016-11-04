package net.eatcode.trainwatch.search;

import java.util.List;
import java.util.Map;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;

public interface TrainWatchSearch {

	List<Station> listStations();

	Map<DelayWindow, List<TrainMovement>> delayedTrainsByAllWindows(int maxResults);

	List<TrainDeparture> departuresWithinOneHour(int maxResults);

	List<TrainDeparture> departuresWithinOneHour(Station station, int maxResults);

}
