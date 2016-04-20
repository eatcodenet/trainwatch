package net.eatcode.trainwatch.movement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class VerifyHazelcastConnectivity {

    private static final Logger log = LoggerFactory.getLogger(VerifyHazelcastConnectivity.class);

    public static void main(String[] args) {
        HazelcastInstance client = new HzClientBuilder().buildInstance("trainwatch.eatcode.net:5701");
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
        System.out.println("Activations: " + activations.size());

        MultiMap<DelayWindow, TrainMovement> movements = client.getMultiMap("trainMovement");
        System.out.println("Movements: " + movements.size());
//        for (TrainMovement tm : movements.values()) {
//            System.out.println("tm: " + tm);
//        }
        client.shutdown();
    }
}
