package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

class KafkaPropertiesBuilder {

    Properties producerProperties(String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", "all");
        props.put("batch.size", 16384);
        props.put("buffer.memory", 33554432);
        props.put("client.id", "");
        props.put("compression.type", "none");
        props.put("connections.max.idle.ms", 540000);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", 0);
        props.put("max.block.ms", 60000);
        props.put("max.in.flight.requests.per.connection", "5");
        props.put("max.request.size", 1048576);
        props.put("metadata.max.age.ms", 300000);
        props.put("metric.reporters", "");
        props.put("metrics.num.samples", 2);
        props.put("metrics.sample.window.ms", 30000);
        props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        props.put("receive.buffer.bytes", 32768);
        props.put("reconnect.backoff.ms", 50);
        props.put("request.timeout.ms", 1000);
        props.put("retries", 0);
        props.put("retry.backoff.ms", 100);
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        return props;
    }

    Properties consumerProperties(String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.commit.interval.ms", 1000);
        props.put("auto.offset.reset", "latest");
        props.put("check.crcs", true);
        props.put("client.id", "");
        props.put("connections.max.idle.ms", 540000);
        props.put("enable.auto.commit", "true");
        props.put("enable.auto.commit", true);
        props.put("fetch.max.wait.ms", 500);
        props.put("fetch.min.bytes", 1);
        props.put("group.id", "eatcode.net");
        props.put("heartbeat.interval.ms", 3000);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("max.partition.fetch.bytes", 1048576);
        props.put("metadata.max.age.ms", 300000);
        props.put("metric.reporters", "");
        props.put("metrics.num.samples", 2);
        props.put("metrics.sample.window.ms", 30000);
        props.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.RangeAssignor");
        props.put("receive.buffer.bytes", 32768);
        props.put("reconnect.backoff.ms", 50);
        props.put("request.timeout.ms", 40000);
        props.put("retry.backoff.ms", 100);
        props.put("security.protocol", "PLAINTEXT");
        props.put("send.buffer.bytes", 131072);
        props.put("session.timeout.ms", "30000");
        props.put("session.timeout.ms", 30000);
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        return props;
    }

}
