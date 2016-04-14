package net.eatcode.trainwatch.nr.hazelcast;

import java.io.Serializable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.Serializer;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.Location;

public class HazelcastClientBuilder {

    private final ClientConfig config = new ClientConfig();

    public HazelcastInstance buildInstance() {
        return buildInstance("192.168.99.100", "hazelcast");
    }

    public HazelcastInstance buildInstance(String... addresses) {
        configureSerialization();
        configureNetwork(addresses);
        return HazelcastClient.newHazelcastClient(config);
    }

    private void configureSerialization() {
        SerializationConfig sc = config.getSerializationConfig();
        sc.addSerializerConfig(add(new LocationSerializer(), Location.class));
        sc.addSerializerConfig(add(new DayScheduleSerializer(), DaySchedule.class));
    }

    private SerializerConfig add(Serializer serializer, Class<? extends Serializable> clazz) {
        SerializerConfig sc = new SerializerConfig();
        sc.setImplementation(serializer).setTypeClass(clazz);
        return sc;
    }

    private void configureNetwork(String[] addresses) {
        config.setNetworkConfig(new ClientNetworkConfig()
                .addAddress(addresses)
                .setConnectionAttemptLimit(1));
    }
}
