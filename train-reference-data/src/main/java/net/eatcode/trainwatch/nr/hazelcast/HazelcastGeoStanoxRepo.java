package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import net.eatcode.trainwatch.nr.GeoStanox;
import net.eatcode.trainwatch.nr.GeoStanoxRepo;

public class HazelcastGeoStanoxRepo implements GeoStanoxRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, GeoStanox> map = client.getMap("geostanox");

    @Override
    public void put(GeoStanox stanox) {
        map.put(stanox.stanox, stanox);
    }

    @Override
    public GeoStanox get(String stanox) {
        return map.get(stanox);
    }

    public void shutdown() {
        client.shutdown();
    }
}
