package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.GeoLocation;
import net.eatcode.trainwatch.nr.GeoLocationRepo;

public class HazelcastGeoLocationRepo implements GeoLocationRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, GeoLocation> map = client.getMap("geolocation");

    @Override
    public void put(GeoLocation location) {
        map.put(location.stanox, location);
    }

    @Override
    public GeoLocation get(String location) {
        return map.get(location);
    }

    public void shutdown() {
        client.shutdown();
    }
}
