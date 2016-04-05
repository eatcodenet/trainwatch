package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;

class PropertiesBuilder {

    private final Properties props = new Properties();

    PropertiesBuilder forProducer(String bootstrapServers) {
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 50);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return this;
    }

    PropertiesBuilder forConsumer(String bootstrapServers) {
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "net.eatcode");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return this;
    }

    PropertiesBuilder forStream(String bootstrapServers) {
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.JOB_ID_CONFIG, "simplifyMovements");
        props.put(StreamsConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(StreamsConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put(StreamsConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(StreamsConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return this;
    }

    Properties build() {
        return props;
    }

    public PropertiesBuilder withByteArrayValueSerializer() {
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        return this;
    }

    public PropertiesBuilder withByteArrayValueDeserializer() {
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        return this;
    }

}
