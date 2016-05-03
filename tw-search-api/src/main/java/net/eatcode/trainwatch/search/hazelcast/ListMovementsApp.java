package net.eatcode.trainwatch.search.hazelcast;

import java.util.List;
import java.util.Map;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class ListMovementsApp {

    public static void main(String[] args) {
        String hazelcastServers = (args.length == 0) ? "trainwatch.eatcode.net" : args[0];
        HazelcastInstance client = new HzClientBuilder().buildInstance(hazelcastServers);
        HzTrainWatchSearch search = new HzTrainWatchSearch(client);
        listTrainMovements(search);
        client.shutdown();

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
