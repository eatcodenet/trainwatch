package net.eatcode.trainwatch.search.hazelcast;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;

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
        return null;
    }

    @Override
    public List<TrainMovement> trainMovementsByDelay(DelayWindow d, int maxResults) {
        return movements.get(d).stream()
                .sorted((m1, m2) -> m2.timestamp().compareTo(m1.timestamp()))
                .limit(maxResults).collect(toList());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List<TrainDeparture> departuresBy(Station station, int maxResults) {
        LocalDateTime maxDeparture = LocalDateTime.now().minusMinutes(2);
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("wtt").greaterThan(maxDeparture).and(e.get("origin.crs").equal(station.getCrs()));
        long start = System.currentTimeMillis();
        List<TrainDeparture> result = departures.values(predicate)
                .stream().sorted((o1, o2) -> o1.departure().compareTo(o2.departure()))
                .limit(maxResults)
                .collect(toList());
        long ms = System.currentTimeMillis() - start;
        log.info("took {}ms", ms);
        return result;
    }

    private void shutdown() {
        client.shutdown();
    }

    public static void main(String[] args) {
        HzTrainWatchSearch search = new HzTrainWatchSearch("trainwatch.eatcode.net");
        List<TrainDeparture> deps = search.departuresBy(new Station("", "MAN"), 10);
        System.out.println(deps.size());
        List<TrainMovement> movements = search.trainMovementsByDelay(DelayWindow.over15mins, 10);
        for (TrainMovement tm : movements) {
            System.out.println(tm);
        }
        search.shutdown();
    }
}
