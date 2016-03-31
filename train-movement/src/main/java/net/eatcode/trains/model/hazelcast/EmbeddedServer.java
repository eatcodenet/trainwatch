package net.eatcode.trains.model.hazelcast;

import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedServer {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedServer.class);

    private final HazelcastInstance instance = Hazelcast.newHazelcastInstance(new Config());

    public void shutdown() {
        this.instance.shutdown();
    }

    public static void main(String[] args) {
        new EmbeddedServer();
        log.info("Hazelcast is running...");
    }
}
