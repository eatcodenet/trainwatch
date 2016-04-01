package net.eatcode.trainwatch.movement.kafka;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class CreateTopic {

    private final boolean notSecure = false;
    private final int connectTimeout = 3000;
    private final int sessionTimeout = 7000;
    private final int partitions = 1;
    private final int replication = 1;

    public void createTopic(String zookeeperHosts, String topicName) {
        ZkUtils zkUtils = zkUtils(zookeeperHosts);
        Properties topicConfig = new Properties();
        AdminUtils.createTopic(zkUtils, topicName, partitions, replication, topicConfig);
        zkUtils.close();
    }

    public boolean topicExists(String zookeeperHosts, String topicName) {
        ZkUtils zkUtils = zkUtils(zookeeperHosts);
        return AdminUtils.topicExists(zkUtils, topicName);
    }

    private ZkUtils zkUtils(String zookeeperHosts) {
        ZkClient zkClient = new ZkClient(zookeeperHosts, sessionTimeout, connectTimeout, ZKStringSerializer$.MODULE$);
        return new ZkUtils(zkClient, new ZkConnection(zookeeperHosts), notSecure);
    }
}