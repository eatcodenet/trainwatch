package net.eatcode.trainwatch.movement.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;

public class ClearHazelcastSchedules {

    private static final Logger log = LoggerFactory.getLogger(ClearHazelcastSchedules.class);
    private static final String hzServer = System.getProperty("trainwatch.hzServer", "localhost");

    public static void main(String[] args) {
        HazelcastInstance client = new HzClientBuilder().build(hzServer);
        try {
            clearRepo(client);
        } finally {
            client.shutdown();
        }
    }

    private static void clearRepo(HazelcastInstance client) {

        log.info("Server: {}", hzServer);

        IMap<String, Schedule> schedules = client.getMap("schedule");
        log.info("Schedule count: {} ", schedules.size());
        schedules.clear();
        log.info("Schedule count: {} ", schedules.size());

    }

}
