package net.eatcode.trainwatch.nr.hazelcast;

import java.io.Serializable;

import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.serialization.Serializer;

public class HzClientBuilder {

    static {
        System.setProperty("hazelcast.logging.type", "slf4j");
    }

    private final ClientConfig config = new ClientConfig();
    private final SerializationConfig serialization = config.getSerializationConfig();

    public HazelcastInstance buildInstance(String addresses) {
        configureDefaultSerializers();
        configureNetwork(addresses.split(","));
        return HazelcastClient.newHazelcastClient(config);
    }

    private void configureDefaultSerializers() {
        addSerializer(new LocationSerializer(), Location.class);
        addSerializer(new ScheduleSerializer(), Schedule.class);
    }

    public HzClientBuilder addSerializer(Serializer serializer, Class<? extends Serializable> clazz) {
        serialization.addSerializerConfig(makeCfg(serializer, clazz));
        return this;
    }

    private SerializerConfig makeCfg(Serializer serializer, Class<? extends Serializable> clazz) {
        SerializerConfig scfg = new SerializerConfig();
        scfg.setImplementation(serializer).setTypeClass(clazz);
        return scfg;
    }

    private void configureNetwork(String[] addresses) {
        config.setNetworkConfig(new ClientNetworkConfig().addAddress(addresses).setConnectionAttemptLimit(1));
    }
}
