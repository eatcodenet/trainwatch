package net.eatcode.trainwatch.search.hazelcast;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.search.Station;
import net.eatcode.trainwatch.search.TrainWatchSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

public class HzTrainWatchSearch implements TrainWatchSearch {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;
    private final IMap<String, TrainDeparture> departures;
    private final MultiMap<DelayWindow, TrainMovement> movements;

    public HzTrainWatchSearch(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.departures = client.getMap("trainDeparture");
        this.movements = client.getMultiMap("trainMovement");
    }

    @Override
    public List<Station> listStations() {
        return departures.values().stream()
                .map(td -> makeStation(td))
                .distinct().sorted().collect(toList());
    }

    private Station makeStation(TrainDeparture td) {
        return new Station(capitalize(td.originName().toLowerCase()), td.originCrs());
    }

    @Override
    public List<TrainMovement> delayedTrainsByWindow(DelayWindow delay, int maxResults) {
        return collectSortedMovements(delay, maxResults);
    }

    private List<TrainMovement> collectSortedMovements(DelayWindow delay, int maxResults) {
        return movements.get(delay).stream()
                .sorted((m1, m2) -> m2.timestamp().compareTo(m1.timestamp()))
                .limit(maxResults).collect(toList());
    }

    @Override
    public Map<DelayWindow, List<TrainMovement>> delayedTrainsByAllWindows(int maxResults) {
        Map<DelayWindow, List<TrainMovement>> results = new HashMap<DelayWindow, List<TrainMovement>>(maxResults * 4);
        movements.keySet().forEach(delay -> {
            results.put(delay, collectSortedMovements(delay, maxResults));
        });
        return results;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List<TrainDeparture> departuresBy(Station station, int maxResults) {
        LocalDateTime maxDeparture = LocalDateTime.now().minusMinutes(2);
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("origin.crs")
                .equal(station.getCrs()).and(e.get("wtt").greaterThan(maxDeparture));
        long start = System.currentTimeMillis();
        List<TrainDeparture> result = departures.values(predicate)
                .stream().sorted().limit(maxResults).collect(toList());
        log.info("departures search took {}ms", System.currentTimeMillis() - start);
        return result;
    }

    private void shutdown() {
        client.shutdown();
    }

    public static void main(String[] args) {
        HzTrainWatchSearch search = new HzTrainWatchSearch("trainwatch.eatcode.net");
        List<TrainDeparture> deps = search.departuresBy(new Station("", "ABA"), 100);
        System.out.println("departures count:" + deps.size());

        List<Station> listStations = search.listStations();
        System.out.println("Station count:" + listStations.size());
        search.shutdown();
    }
}
