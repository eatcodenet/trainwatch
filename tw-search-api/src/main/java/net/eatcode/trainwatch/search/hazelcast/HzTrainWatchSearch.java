package net.eatcode.trainwatch.search.hazelcast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.hazelcast.TrainDepartureSerializer;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.search.Station;
import net.eatcode.trainwatch.search.TrainWatchSearch;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class HzTrainWatchSearch implements TrainWatchSearch {

    private static final String trainDeparture = "trainDeparture";
    private final HazelcastInstance client;

    public HzTrainWatchSearch(String servers) {
        this.client = new HzClientBuilder()
                .addSerializer(new TrainDepartureSerializer(), TrainDeparture.class)
                .buildInstance(servers);
    }

    @Override
    public List<Station> listStations() {
        return null;
    }

    @Override
    public List<TrainMovement> trainMovementsByDelay(DelayWindow d, int limit) {
        return null;
    }

    @Override
    public List<TrainDeparture> getDeparturesBy(Station station) {
        MultiMap<String, TrainDeparture> map = client.getMultiMap(trainDeparture);
        LocalDateTime timeWindow = LocalDateTime.now().minusMinutes(2);
        long start = System.currentTimeMillis();
        List<TrainDeparture> res = map.values()
                .stream()
                .filter(td -> td.scheduledDeparture() .isAfter(timeWindow))
                .sorted((t1, t2) -> t1.departure().compareTo(t2.departure()))
                .collect(Collectors.toList());
        long end = System.currentTimeMillis() - start;
        System.out.println("took " + end);
        return res;
    }

    private void shutdown() {
        client.shutdown();
    }

    public static void main(String[] args) {
        HzTrainWatchSearch search = new HzTrainWatchSearch("trainwatch.eatcode.net");
        List<TrainDeparture> deps = search.getDeparturesBy(new Station("", "MAN"));
        for (TrainDeparture td : deps) {
            System.out.println(td.scheduledDeparture() + " " + td.originCrs() + " " + td.destCrs());
        }
        System.out.println(deps.size());
        search.shutdown();
    }
}
