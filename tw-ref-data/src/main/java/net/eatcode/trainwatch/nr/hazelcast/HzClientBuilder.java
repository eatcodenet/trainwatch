package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HzClientBuilder {

	static {
		System.setProperty("hazelcast.logging.type", "slf4j");
	}

	private final ClientConfig config = new ClientConfig();

	public HazelcastInstance build(String addresses) {
		configureNetwork(addresses.split(","));
		return HazelcastClient.newHazelcastClient(config);
	}

	private void configureNetwork(String[] addresses) {
		config.setNetworkConfig(new ClientNetworkConfig().addAddress(addresses).setConnectionAttemptLimit(1));
	}

	public HazelcastInstance buildStandalone() {
		Config config = new Config();
		config.setProperty("hazelcast.shutdownhook.enabled", "false");
		NetworkConfig network = config.getNetworkConfig();
		network.getJoin().getTcpIpConfig().setEnabled(false);
		network.getJoin().getMulticastConfig().setEnabled(false);
		return Hazelcast.newHazelcastInstance(config);
	}
}
