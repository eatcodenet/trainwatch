package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class VerifyHazelcastConnectivity {

    public static void main(String[] args) {
        HazelcastInstance client = new HzClientBuilder().buildInstance("52.49.248.138:5701");
//        IMap<String, Location> locations = client.getMap("locationByTiploc");
//
//        Location location = locations.get("SNDYPL1");
//
//        System.out.println(locations.size());
//        System.out.println(location);
//
//        if (!location.description.equals("SANDY SOUTH")) {
//            throw new RuntimeException("could not get location from hazelcast!");
//        }

        IMap<Object, Object> schedules = client.getMap("schedule");
        System.out.println("Schedule count:");
        System.out.println(schedules.size());

        client.shutdown();

    }
}
