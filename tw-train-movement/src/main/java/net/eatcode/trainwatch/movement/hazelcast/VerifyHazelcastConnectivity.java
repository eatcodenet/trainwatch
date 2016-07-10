package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.LatLon;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

public class VerifyHazelcastConnectivity {

    private static final Logger log = LoggerFactory.getLogger(VerifyHazelcastConnectivity.class);
    private static final String hzServer = System.getProperty("trainwatch.hzServer", "localhost");

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
        log.info("{} {}", locations.size(), location);

//        locations.put("1", new Location("sx1", "desc", "tt", "crs", new LatLon("1", "3")));
//        log.info("{} {}", locations.size(), locations.values(new SqlPredicate("stanox = 'sx1'")));
//
//        IMap<Object, Object> schedules = client.getMap("schedule");
//        // schedules.clear();
//        System.out.println("Schedule count: " + schedules.size());
//
//        IMap<String, TrainActivation> activations = client.getMap("trainActivation");
//        // activations.clear();
//        System.out.println("Activations: " + activations.size());
//
//        IMap<String, TrainDeparture> liveDepartures = client.getMap("trainDeparture");
//        // liveDepartures.clear();
//        System.out.println("Live departures: " + liveDepartures.size());
//
//        IMap<String, TrainMovement> movements = client.getMap("trainMovement");
//        //movements.clear();
//        System.out.println("Movements: " + movements.size());

    }

}
