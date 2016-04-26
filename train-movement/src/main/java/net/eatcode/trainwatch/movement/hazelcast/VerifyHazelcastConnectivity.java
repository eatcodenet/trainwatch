package net.eatcode.trainwatch.movement.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.movement.DelayWindow;
import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainDeparture;
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
        // schedules.clear();
        System.out.println("Schedule count:");
        System.out.println(schedules.size());

        IMap<String, TrainActivation> activations = client.getMap("trainActivation");
        //activations.clear();
        System.out.println("Activations: " + activations.size());

        MultiMap<String, TrainDeparture> liveDepartures = client.getMultiMap("trainDeparture");
        //liveDepartures.clear();
        System.out.println("Live departures: " + liveDepartures.size());
        liveDepartures.values().stream().limit(100).forEach(System.out::println);

        MultiMap<DelayWindow, TrainMovement> movements = client.getMultiMap("trainMovement");
        //movements.clear();
        System.out.println("\nMovements: " + movements.size());
        movements.get(DelayWindow.over15mins).stream().limit(100).forEach(System.out::println);

    }

}
