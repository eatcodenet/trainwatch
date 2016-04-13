package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Location;

public class VerifyHazelcastDataApp {

    public static void main(String[] args) {
        HazelcastInstance client = new HazelcastClientBuilder().buildInstance();
        IMap<String, Location> locations = client.getMap("locationByTiploc");

        Location location = locations.get("SNDYPL1");

        System.out.println(locations.size());
        System.out.println(location);
        client.shutdown();

        if (!location.description.equals("SANDY SOUTH")) {
            throw new RuntimeException("could not get location from hazelcast!");
        }

    }
}
