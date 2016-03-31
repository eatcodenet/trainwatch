package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import net.eatcode.trainwatch.nr.Tiploc;
import net.eatcode.trainwatch.nr.TiplocRepo;

import java.util.List;
import java.util.stream.Collectors;

public class HazelcastTiplocRepo implements TiplocRepo {

    private final HazelcastInstance client = new ClientBuilder().build();

    private final IMap<String, Tiploc> map = client.getMap("tiploc");

    @Override
    public void putByStanox(Tiploc tiploc) {
        map.put(tiploc.stanox, tiploc);
    }

    @Override
    public Tiploc getByStanox(String stanox) {
        return map.get(stanox);
    }

    @Override
    public List<Tiploc> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    public void shutdown() {
        client.shutdown();
    }
}
