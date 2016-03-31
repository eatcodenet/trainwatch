package net.eatcode.trains.model.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import net.eatcode.trains.model.Crs;

public class ClientBuilder {

    private ClientConfig config = new ClientConfig();

    public HazelcastInstance build() {
        configureSerialization();
        configureNetwork();
        return HazelcastClient.newHazelcastClient(config);
    }

    private void configureSerialization() {
        // TODO: this is not the best way
        SerializerConfig sc = new SerializerConfig();
        sc.setImplementation(new CrsSerializer()).setTypeClass(Crs.class);
        config.getSerializationConfig().addSerializerConfig(sc);
    }

    private void configureNetwork() {
        config.setNetworkConfig(new ClientNetworkConfig()
                .addAddress("localhost", "dev.docker-machine", "dev.hazelcast")
                .setConnectionAttemptLimit(1));
    }
}
