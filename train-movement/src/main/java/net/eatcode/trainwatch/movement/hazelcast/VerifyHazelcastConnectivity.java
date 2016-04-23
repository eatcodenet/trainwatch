package net.eatcode.trainwatch.movement.hazelcast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class VerifyHazelcastConnectivity {

    private static final Logger log = LoggerFactory.getLogger(VerifyHazelcastConnectivity.class);
    private static final String hzServer = "trainwatch.eatcode.net:5701";

    public static void main(String[] args) {
        HazelcastInstance client = new HzClientBuilder().buildInstance(hzServer);
        try {
            runSomeQueries(client);
        } finally {
            client.shutdown();
        }
    }

    private static void runSomeQueries(HazelcastInstance client) {

        log.info("Server: {}", hzServer);

        IMap<String, Location> locations = client.getMap("locationByTiploc");
        Location location = locations.get("SNDYPL1");
        log.info("{}", locations.size());
        System.out.println(location);

        if (!location.description.equals("SANDY SOUTH")) {
            throw new RuntimeException("could not get location from hazelcast!");
        }

        IMap<Object, Object> schedules = client.getMap("schedule");
        System.out.println("Schedule count:");
        System.out.println(schedules.size());

        IMap<Object, Object> activations = client.getMap("trainActivation");
        //activations.clear();
        System.out.println("Activations: " + activations.size());

        MultiMap<DelayWindow, TrainMovement> movements = client.getMultiMap("trainMovement");
        //movements.clear();
        System.out.println("Movements: " + movements.size());
        movements.get(DelayWindow.over15mins).stream().limit(1000).forEach(System.out::println);

    }

    private static Collection<TrainMovement> sort(Collection<TrainMovement> values) {
        List<TrainMovement> sorted = new ArrayList<>(values);
        Collections.sort(sorted, new Comparator<TrainMovement>() {

            @Override
            public int compare(TrainMovement o1, TrainMovement o2) {
                return o1.timestamp().compareTo(o2.timestamp());
            }
        });
        return sorted;
    }
}
