package net.eatcode.trainwatch.search.hazelcast;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.search.Station;
import net.eatcode.trainwatch.search.TrainWatchSearch;

public class HzTrainWatchSearch implements TrainWatchSearch {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final IMap<String, TrainActivation> departures;
	private final IMap<String, TrainMovement> movements;

	public HzTrainWatchSearch(HazelcastInstance client) {
		this.departures = client.getMap("trainActivation");
		this.movements = client.getMap("trainMovement");
	}

	@Override
	public List<Station> listStations() {
		return departures.values().stream().map(this::makeStation).distinct().sorted().collect(toList());
	}

	private Station makeStation(TrainActivation ta) {
		Schedule s = ta.schedule;
		return new Station(capitalize(s.origin.description), s.origin.crs);
	}

	@Override
	public Map<DelayWindow, List<TrainMovement>> delayedTrainsByAllWindows(int maxResults) {
		return limit(maxResults, collectSortedMovements(maxResults));
	}

	private Map<DelayWindow, List<TrainMovement>> collectSortedMovements(int maxResults) {
		StopWatch sw = startStopWatch();
		Map<DelayWindow, List<TrainMovement>> result = movements.values().stream().sorted()
				.collect(Collectors.groupingBy(tm -> DelayWindow.from(tm.delayInMins())));
		log.debug("movement took {}ms", sw.getTime());
		return result;
	}

	private Map<DelayWindow, List<TrainMovement>> limit(int max, Map<DelayWindow, List<TrainMovement>> movements) {
		Map<DelayWindow, List<TrainMovement>> result = new HashMap<>();
		DelayWindow.sortedValues().forEach(dw -> {
			List<TrainMovement> values = movements.get(dw);
			if (values != null) {
				result.put(dw, values.subList(0, Math.min(max, values.size())));
			}
		});
		return result;
	}

	@Override
	public List<TrainDeparture> departuresWithinOneHour(int maxResults) {
		LocalDateTime cutOff = LocalDateTime.now().plusMinutes(180);
		StopWatch sw = startStopWatch();
		List<TrainDeparture> result = departures.values().stream().limit(maxResults).map(this::toDeparture)
				.filter(td -> td.scheduledDeparture().isBefore(cutOff)).sorted().collect(toList());
		log.info("departures search took {}ms", sw.getTime());
		return result;
	}

	@Override
	public List<TrainDeparture> departuresWithinOneHour(Station station, int maxResults) {
		LocalDateTime cutOff = LocalDateTime.now().plusMinutes(60);
		StopWatch sw = startStopWatch();
		List<TrainDeparture> result = departures.values().stream()
				.filter(t -> t.schedule.origin.description.equals(station.name)).limit(maxResults)
				.map(this::toDeparture).filter(td -> td.scheduledDeparture().isBefore(cutOff)).sorted()
				.collect(toList());
		log.info("departures search took {}ms", sw.getTime());
		return result;
	}

	private TrainDeparture toDeparture(TrainActivation t) {
		return new TrainDeparture(t.trainId, t.wttDeparture, t.schedule);
	}

	private StopWatch startStopWatch() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}
}
