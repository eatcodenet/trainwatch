package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.GeoLocation;
import net.eatcode.trainwatch.nr.GeoLocationRepo;

public class HazelcastGeoLocationRepo implements GeoLocationRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, GeoLocation> byStanoxMap = client.getMap("geolocation_by_stanox");
    private final IMap<String, GeoLocation> byTiplocMap = client.getMap("geolocation_by_tiplox");

    @Override
    public void put(GeoLocation location) {
        byStanoxMap.put(location.stanox, location);
        byTiplocMap.put(location.tiploc, location);
    }

    @Override
    public GeoLocation getByStanox(String stanox) {
        return byStanoxMap.get(stanox);
    }

    @Override
    public GeoLocation getByTiploc(String tiploc) {
        return byTiplocMap.get(tiploc);
    }

    public void shutdown() {
        client.shutdown();
    }
}
