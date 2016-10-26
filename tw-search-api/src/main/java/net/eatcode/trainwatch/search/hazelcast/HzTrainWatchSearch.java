package net.eatcode.trainwatch.search.hazelcast;

import static com.hazelcast.mapreduce.aggregation.Aggregations.count;
import static com.hazelcast.mapreduce.aggregation.Aggregations.integerMax;
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
import com.hazelcast.mapreduce.aggregation.Supplier;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.Stats;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.search.Station;
import net.eatcode.trainwatch.search.TrainWatchSearch;

public class HzTrainWatchSearch implements TrainWatchSearch {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final IMap<String, TrainDeparture> departures;
	private final IMap<String, TrainMovement> movements;

	public HzTrainWatchSearch(HazelcastInstance client) {
		this.departures = client.getMap("trainDeparture");
		this.movements = client.getMap("trainMovement");
	}

	@Override
	public List<Station> listStations() {
		return departures.values().stream().map(data -> makeStation(data)).distinct().sorted().collect(toList());
	}

	private Station makeStation(TrainDeparture td) {
		return new Station(capitalize(td.originName().toLowerCase()), td.originCrs());
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
			result.put(dw, values.subList(0, Math.min(max, values.size())));
		});
		return result;
	}

	@Override
	public List<TrainDeparture> departuresBy(Station station, int maxResults) {
		LocalDateTime cutOff = LocalDateTime.now().minusMinutes(2);
		StopWatch sw = startStopWatch();
		List<TrainDeparture> result = departures.values().stream()
				.filter(td -> td.origin().crs.equals(station.crs) && td.scheduledDeparture().isAfter(cutOff)).sorted()
				.limit(maxResults).collect(toList());
		log.info("departures search took {}ms", sw.getTime());
		return result;
	}

	@Override
	public Stats getStats() {
		Long numTrains = movements.aggregate(Supplier.all(), count());
		Integer highestDelay = movements.aggregate(Supplier.all(value -> value.delayInMins()), integerMax());
		return new Stats(numTrains, highestDelay);
	}

	private StopWatch startStopWatch() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}
}
