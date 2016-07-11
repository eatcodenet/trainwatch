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
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.KryoUtils;

public class HzMovementRepoTest {

    private final HazelcastInstance client = new HzClientBuilder().buildStandalone();

    @Test
    public void evictOlderThan() {

        IMap<String, byte[]> map = client.getMap("trainMovement");
        map.set("1", movement("1", LocalDateTime.now()));
        map.set("2", movement("2", LocalDateTime.now().minusHours(2)));
        map.set("3", movement("3", LocalDateTime.now().minusHours(3)));

        HzMovementRepo repo = new HzMovementRepo(client);
        assertThat(map.size(), is(3));
        repo.evictOlderThan(1);
        assertThat(map.size(), is(1));
        client.shutdown();
    }

    private byte[] movement(String id, LocalDateTime timestamp) {
        return KryoUtils.toByteArray(
                new TrainMovement(id, timestamp, new Location("s", "d", "t", "c", null), "1", "false", new Schedule()));
    }

}
