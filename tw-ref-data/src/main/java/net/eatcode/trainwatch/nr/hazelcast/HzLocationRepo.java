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
        byte[] data = KryoUtils.toByteArray(location);
        byStanoxMap.set(location.stanox, data);
        byTiplocMap.set(location.tiploc, data);
    }

    @Override
    public Location getByStanox(String stanox) {
        byte[] data = byStanoxMap.get(stanox);
        if (data == null) {
            return null;
        }
        return KryoUtils.fromByteArray(data, Location.class);

    }

    @Override
    public Location getByTiploc(String tiploc) {
        byte[] data = byTiplocMap.get(tiploc);
        if (data == null) {
            return null;
        }
        return KryoUtils.fromByteArray(data, Location.class);
    }
}
