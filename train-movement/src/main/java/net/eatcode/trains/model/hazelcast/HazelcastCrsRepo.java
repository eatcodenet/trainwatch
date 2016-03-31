package net.eatcode.trains.model.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import net.eatcode.trains.model.Crs;
import net.eatcode.trains.model.CrsRepo;

public class HazelcastCrsRepo implements CrsRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, Crs> map = client.getMap("crs");

    @Override
    public void put(Crs crs) {
        map.put(crs.crs, crs);
    }

    @Override
    public Crs get(String crsCode) {
        return map.get(crsCode);
    }

    public void shutdown() {
        client.shutdown();
    }
}
