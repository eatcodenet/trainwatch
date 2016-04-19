package net.eatcode.trainwatch.nr.hazelcast;

import java.io.Serializable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.Serializer;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

public class HzClientBuilder {

    private final ClientConfig config = new ClientConfig();

    public HazelcastInstance buildInstance(String addresses) {
        configureSerialization();
        configureNetwork(addresses.split(","));
        return HazelcastClient.newHazelcastClient(config);
    }

    private void configureSerialization() {
        SerializationConfig sc = config.getSerializationConfig();
        sc.addSerializerConfig(add(new LocationSerializer(), Location.class));
        sc.addSerializerConfig(add(new ScheduleSerializer(), Schedule.class));
    }

    private SerializerConfig add(Serializer serializer, Class<? extends Serializable> clazz) {
        SerializerConfig sc = new SerializerConfig();
        sc.setImplementation(serializer).setTypeClass(clazz);
        return sc;
    }

    private void configureNetwork(String[] addresses) {
        config.setNetworkConfig(new ClientNetworkConfig().addAddress(addresses).setConnectionAttemptLimit(1));
    }
}