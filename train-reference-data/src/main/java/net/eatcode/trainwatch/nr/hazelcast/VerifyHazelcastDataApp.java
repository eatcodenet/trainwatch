package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Location;

public class VerifyHazelcastDataApp {

    public static void main(String[] args) {
        HazelcastInstance client = new HazelcastClientBuilder().buildInstance();
        IMap<String, Location> locations = client.getMap("locationByStanox");

        System.out.println(locations.size());
//        Location loc1 = locations.get("SNDYPL1");
//        if (!loc1.description.equals("meh")) {
//            throw new RuntimeException("could not get location from hazelcast!");
//        }

    }
}
