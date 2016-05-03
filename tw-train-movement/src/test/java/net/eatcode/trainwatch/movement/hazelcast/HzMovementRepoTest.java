package net.eatcode.trainwatch.movement.hazelcast;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.hazelcast.HazelcastLocal;

public class HzMovementRepoTest {

    private final HazelcastInstance client = new HazelcastLocal().getInstance();

    @Test
    public void evictOlderThan() {

        IMap<String, TrainMovement> map = client.getMap("trainMovement");
        map.put("1", new StubMovement("1", LocalDateTime.now()));
        map.put("2", new StubMovement("2", LocalDateTime.now().minusHours(2)));
        map.put("3", new StubMovement("3", LocalDateTime.now().minusHours(3)));

        HzMovementRepo repo = new HzMovementRepo(client);
        assertThat(map.size(), is(3));
        repo.evictOlderThan(1);
        assertThat(map.size(), is(1));
        client.shutdown();
    }

    static class StubMovement extends TrainMovement {
        private static final long serialVersionUID = 1L;

        StubMovement(String id, LocalDateTime timestamp) {
            super(id, timestamp, new Location("stanox", "desc", "tiploc", "crs", null), "1", "false", new Schedule());
        }
    }
}
