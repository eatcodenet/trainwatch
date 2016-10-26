package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

class PropertiesBuilder {

	private Properties properties = new Properties();

	PropertiesBuilder forProducer(String bootstrapServers) {
		properties = new Properties();
		properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 50);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		return this;
	}

	PropertiesBuilder forConsumer(String bootstrapServers) {
		properties = new Properties();
		properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put("bootstrap.servers", bootstrapServers);
		properties.put("group.id", "net.eatcode");
		properties.put("enable.auto.commit", "true");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		return this;
	}

	PropertiesBuilder forStream(String bootstrapServers, String streamName) {
		properties = new Properties();
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, streamName);
		properties.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		properties.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
		return this;
	}

	Properties build() {
		return properties;
	}

	public PropertiesBuilder withByteArrayValueSerializer() {
		properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		return this;
	}

	public PropertiesBuilder withByteArrayValueDeserializer() {
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		return this;
	}

}
