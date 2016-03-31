package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import net.eatcode.trainwatch.nr.Crs;
import net.eatcode.trainwatch.nr.CrsRepo;

import java.util.Optional;

public class HazelcastCrsRepo implements CrsRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, Crs> map = client.getMap("crs");

    @Override
    public void put(Crs crs) {
        map.put(crs.crs, crs);
    }

    @Override
    public Optional<Crs> get(String crsCode) {
        Crs crs = map.get(crsCode);
        if (crs == null) {
            return Optional.empty();
        }
        return Optional.of(crs);
    }

    public void shutdown() {
        client.shutdown();
    }
}
