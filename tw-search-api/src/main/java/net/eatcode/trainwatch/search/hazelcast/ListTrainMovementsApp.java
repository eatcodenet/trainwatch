package net.eatcode.trainwatch.search.hazelcast;

import java.util.List;
import java.util.Map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class ListTrainMovementsApp {

    public static void main(String[] args) {
        HazelcastInstance client = null;
        try {
            client = new HzClientBuilder().build("localhost");
            IMap<String, TrainMovement> map = client.getMap("trainMovement");
            System.out.println("SIZE: " + map.size());
            map.values().stream().sorted().limit(100).forEach(t -> {
                System.out.println(t);
            });
//            HzTrainWatchSearch search = new HzTrainWatchSearch(client);
//            listDepartures(search);
//            listTrainMovements(search);
        } finally {
            client.shutdown();
        }

    }

    private static void listDepartures(HzTrainWatchSearch search) {
        List<TrainDeparture> deps = search.departuresWithinOneHour(50);
        deps.stream().forEach(System.out::println);
    }

    private static void listTrainMovements(HzTrainWatchSearch search) {
        Map<DelayWindow, List<TrainMovement>> delays = search.delayedTrainsByAllWindows(16);
        System.out.println("\nTrain Movements");

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
