package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;

public class HzLocationRepo implements LocationRepo {

    private final IMap<String, byte[]> byStanoxMap;
    private final IMap<String, byte[]> byTiplocMap;

    public HzLocationRepo(HazelcastInstance client) {
        this.byStanoxMap = client.getMap("locationByStanox");
        this.byTiplocMap = client.getMap("locationByTiploc");
    }

    @Override
    public void put(Location location) {
        byte[] bytes = KryoUtils.toByteArray(location);
        byStanoxMap.put(location.stanox, bytes);
        byTiplocMap.put(location.tiploc, bytes);
    }

    @Override
    public Location getByStanox(String stanox) {
        return KryoUtils.fromByteArray(byStanoxMap.get(stanox), Location.class);
    }

    @Override
    public Location getByTiploc(String tiploc) {
        return KryoUtils.fromByteArray(byTiplocMap.get(tiploc), Location.class);
    }
}
