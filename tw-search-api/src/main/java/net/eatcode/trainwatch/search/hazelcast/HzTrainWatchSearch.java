package net.eatcode.trainwatch.search.hazelcast;

import java.time.LocalTime;
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
        LocalTime timeWindow = LocalTime.now().minusMinutes(2);
        return map.values()
                .stream().collect(Collectors.toList());
    }

    private void shutdown() {
        client.shutdown();
    }

    public static void main(String[] args) {
        HzTrainWatchSearch search = new HzTrainWatchSearch("trainwatch.eatcode.net");
        List<TrainDeparture> deps = search.getDeparturesBy(new Station("", "MAN"));
        System.out.println(deps);
        search.shutdown();
    }
}
