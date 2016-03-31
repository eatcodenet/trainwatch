package net.eatcode.trainwatch.nr.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.Serializer;
import net.eatcode.trainwatch.nr.GeoStanox;

public class ClientBuilder {

    private final ClientConfig config = new ClientConfig();

    public HazelcastInstance build() {
        configureSerialization();
        configureNetwork();
        return HazelcastClient.newHazelcastClient(config);
    }

    private void configureSerialization() {
        SerializationConfig sc = config.getSerializationConfig();
        sc.addSerializerConfig(add(new GeoStanoxSerializer(), GeoStanox.class));
    }

    private SerializerConfig add(Serializer serializer, Class clazz) {
        SerializerConfig sc = new SerializerConfig();
        sc.setImplementation(serializer).setTypeClass(clazz);
        return sc;
    }

    private void configureNetwork() {
        config.setNetworkConfig(new ClientNetworkConfig()
                .addAddress("localhost", "dev.docker-machine", "dev.hazelcast")
                .setConnectionAttemptLimit(1));
    }
}
