package net.eatcode.trainwatch.movement;

import java.util.List;
import java.util.stream.Collectors;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

import net.eatcode.trainwatch.nr.hazelcast.HazelcastClientBuilder;

public class HazelcastTrainMovementRepo implements TrainMovementRepo {

    private final HazelcastInstance client;
    private final MultiMap<Delay, TrainMovement> map;

    public HazelcastTrainMovementRepo(String servers) {
        this.client = new HazelcastClientBuilder().buildInstance(servers);
        this.map = client.getMultiMap("train-movements");
    }

    @Override
    public List<TrainMovement> getAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<TrainMovement> getByMaxDelay(Delay delay) {
        return map.get(delay).stream().collect(Collectors.toList());
    }

    @Override
    public void put(TrainMovement tm) {
        map.put(Delay.from(tm.delayInMins()), tm);
    }

    public void shutdown() {
        client.shutdown();
    }

    // TODO: remove me
    public static void main(String[] args) {
        TrainMovement m1 = new TrainMovement("1", "MAN", "10:00", "EUS", "13:30", 1, false);
        TrainMovement m7 = new TrainMovement("7", "LDS", "10:00", "BFD", "13:30", 7, false);
        TrainMovement m13 = new TrainMovement("13", "VIC", "10:00", "EDS", "13:30", 13, false);
        TrainMovement m17 = new TrainMovement("17", "MOO", "10:00", "POO", "13:30", 17, false);

        HazelcastTrainMovementRepo repo = new HazelcastTrainMovementRepo("docker.local");
        repo.put(m1);
        repo.put(m7);
        repo.put(m13);
        repo.put(m17);
        repo.shutdown();

        HazelcastTrainMovementRepo repo2 = new HazelcastTrainMovementRepo("docker.local");
        List<TrainMovement> movements = repo2.getAll();
        System.out.println(movements);
    }

}
