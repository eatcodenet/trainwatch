package net.eatcode.trainwatch.movement.kafka;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class Topics {

    private final boolean notSecure = false;
    private final int connectTimeout = 3000;
    private final int sessionTimeout = 7000;
    private final String zkServers;

    public Topics(String zkServers) {
        this.zkServers = zkServers;
    }

    public boolean topicExists(Topic topic) {
        ZkUtils zkUtils = zkUtils();
        return AdminUtils.topicExists(zkUtils, topic.topicName());
    }

    private ZkUtils zkUtils() {
        ZkClient zkClient = new ZkClient(zkServers, sessionTimeout, connectTimeout, ZKStringSerializer$.MODULE$);
        return new ZkUtils(zkClient, new ZkConnection(zkServers), notSecure);
    }
}