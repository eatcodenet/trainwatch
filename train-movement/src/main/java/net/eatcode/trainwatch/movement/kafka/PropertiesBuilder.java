package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

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
