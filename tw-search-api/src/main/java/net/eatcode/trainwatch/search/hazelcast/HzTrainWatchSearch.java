package net.eatcode.trainwatch.search.hazelcast;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.search.Station;
import net.eatcode.trainwatch.search.TrainWatchSearch;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.PagingPredicate;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;

public class HzTrainWatchSearch implements TrainWatchSearch {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HazelcastInstance client;
    private final IMap<String, TrainDeparture> departures;
    private final IMap<String, TrainMovement> movements;

    public HzTrainWatchSearch(String servers) {
        this.client = new HzClientBuilder().buildInstance(servers);
        this.departures = client.getMap("trainDeparture");
        this.movements = client.getMap("trainMovement");
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
        List<TrainMovement> list = collectSortedMovements(delay, maxResults).get(delay);
        return list == null ? new ArrayList<TrainMovement>(0) : list;
    }

    @SuppressWarnings("rawtypes")
    private Map<DelayWindow, List<TrainMovement>> collectSortedMovements(DelayWindow delay, int maxResults) {
        StopWatch sw = new StopWatch();
        Predicate running = Predicates.equal("hasArrived", Boolean.FALSE);
        PagingPredicate pagingPredicate = new PagingPredicate(running, maxResults);
        sw.start();
        Collection<TrainMovement> values = movements.values();
        Map<DelayWindow, List<TrainMovement>> result = values.stream()
                .sorted((o1, o2) -> o2.timestamp().compareTo(o1.timestamp()))
                .limit(maxResults)
                .collect(Collectors.groupingBy(tm -> DelayWindow.from(tm.delayInMins())));
        sw.stop();
        log.debug("movement took {}ms", sw.getTime());
        return result;
    }

    @Override
    public Map<DelayWindow, List<TrainMovement>> delayedTrainsByAllWindows(int maxResults) {
        return collectSortedMovements(DelayWindow.max15mins, maxResults);
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
        String hazelcastServers = (args.length == 0) ? "trainwatch.eatcode.net" : args[0];
        HzTrainWatchSearch search = new HzTrainWatchSearch(hazelcastServers);
        try {
            while (true) {
                listTrainMovements(search);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            search.shutdown();
        }

    }

    private static void listTrainMovements(HzTrainWatchSearch search) {
        Map<DelayWindow, List<TrainMovement>> delays = search.delayedTrainsByAllWindows(16);
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                            Train Movements                                                             ");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        for (DelayWindow d : DelayWindow.sortedValues()) {
            System.out.println("\n" + d.name());
            printList(delays, d);
        }
    }

    private static void printList(Map<DelayWindow, List<TrainMovement>> delays, DelayWindow d) {
        List<TrainMovement> list = delays.get(d);
        if (list == null) {
            return;
        }
        for (TrainMovement tm : list) {
            System.out.println(tm);
        }
    }

}
