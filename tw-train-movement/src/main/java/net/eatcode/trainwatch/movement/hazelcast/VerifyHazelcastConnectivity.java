package net.eatcode.trainwatch.movement.hazelcast;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.KryoUtils;

public class VerifyHazelcastConnectivity {

    private static final Logger log = LoggerFactory.getLogger(VerifyHazelcastConnectivity.class);
    private static final String hzServer = System.getProperty("trainwatch.hzServer", "localhost");

    public static void main(String[] args) {
        HazelcastInstance client = new HzClientBuilder().build(hzServer);
        try {
            runSomeQueries(client);
        } finally {
            client.shutdown();
        }
    }

    private static void runSomeQueries(HazelcastInstance client) {

        log.info("Server: {}", hzServer);

        IMap<String, byte[]> locations = client.getMap("locationByTiploc");
        log.info("locations size: {}", locations.size());

        IMap<String, byte[]> schedules = client.getMap("schedule");
        // schedules.clear();
        log.info("Schedule count: {} ", schedules.size());
        List<Schedule> collect = schedules.values().stream().map(data -> KryoUtils.fromByteArray(data, Schedule.class))
                .filter(s -> s.id.equals("H38661")).collect(Collectors.toList());
        System.out.println(collect);

        IMap<String, byte[]> activations = client.getMap("trainActivation");
        // activations.values().stream().map(data ->
        // KryoUtils.fromByteArray(data,
        // TrainActivation.class)).forEach(System.out::println);
        // activations.clear();
        log.info("Activations: {} ", activations.size());

        IMap<String, TrainDeparture> liveDepartures = client.getMap("trainDeparture");
        // liveDepartures.clear();
        log.info("Live departures: {} ", liveDepartures.size());

        IMap<String, TrainMovement> movements = client.getMap("trainMovement");
        // movements.clear();
        log.info("Movements: {}", movements.size());

    }

}
