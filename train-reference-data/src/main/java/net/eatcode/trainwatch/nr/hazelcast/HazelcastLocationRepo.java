package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;

public class HazelcastLocationRepo implements LocationRepo {

    private final HazelcastInstance client;
    private final IMap<String, Location> byStanoxMap;
    private final IMap<String, Location> byTiplocMap;

    public HazelcastLocationRepo(String hazelcastServers) {
        this.client = new HazelcastClientBuilder().buildInstance(hazelcastServers);
        this.byStanoxMap = client.getMap("locationByStanox");
        this.byTiplocMap = client.getMap("locationByTiploc");
    }

    @Override
    public void put(Location location) {
        byStanoxMap.put(location.stanox, location);
        byTiplocMap.put(location.tiploc, location);
    }

    @Override
    public Location getByStanox(String stanox) {
        return byStanoxMap.get(stanox);
    }

    @Override
    public Location getByTiploc(String tiploc) {
        return byTiplocMap.get(tiploc);
    }

    public void shutdown() {
        client.shutdown();
    }
}
