package net.eatcode.trainwatch.nr.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastLocal {

    private static final Logger log = LoggerFactory.getLogger(HazelcastLocal.class);

    private final HazelcastInstance instance;

    public HazelcastLocal() {
        Config config = new Config();
        config.setProperty("hazelcast.logging.type", "slf4j");
        config.setProperty("hazelcast.phone.home.enabled", "false");
        config.setProperty("hazelcast.shutdownhook.enabled", "false");
        NetworkConfig network = config.getNetworkConfig();
        network.getJoin()
                .getTcpIpConfig().setEnabled(false);
        network.getJoin().getMulticastConfig()
                .setEnabled(false);
        instance = Hazelcast.newHazelcastInstance(config);
    }

    public void shutdown() {
        this.instance.shutdown();
    }

    public HazelcastInstance getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new HazelcastLocal();
        log.info("Hazelcast is running...");
    }
}
