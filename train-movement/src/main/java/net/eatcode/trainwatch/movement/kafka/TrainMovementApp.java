package net.eatcode.trainwatch.movement.kafka;

import net.eatcode.trainwatch.movement.HzScheduleLookup;
import net.eatcode.trainwatch.movement.HzTrainActivationRepo;
import net.eatcode.trainwatch.movement.HzTrainMovementRepo;
import net.eatcode.trainwatch.movement.ScheduleLookup;
import net.eatcode.trainwatch.movement.TrainMovementRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementApp {

    public static void main(String[] args) throws InterruptedException {
        String kafkaServers = args[0];
        String zookeeperServers = args[1];
        String hazelcastServers = args[2];
        String networkRailUsername = args[3];
        String networkRailPassword = args[4];
        checkTopicExists(zookeeperServers);

        Runnable producer = () -> {
            System.out.println("running producer");
            new TrainMovementProducer(kafkaServers, new HzTrainActivationRepo(hazelcastServers))
                    .produceMessages(networkRailUsername, networkRailPassword);
        };

        Runnable consumer = () -> {
            ScheduleLookup lookup = new HzScheduleLookup(new HzTrainActivationRepo(hazelcastServers),
                    new HzScheduleRepo(hazelcastServers));
            TrainMovementRepo trainMovementRepo = new HzTrainMovementRepo(hazelcastServers);
            new TrainMovementStream(kafkaServers, lookup, trainMovementRepo).process();
            System.out.println("running consumer");
        };

        new Thread(producer).start();
        Thread.sleep(3000);
        new Thread(consumer).start();
    }

    private static void checkTopicExists(String zookeeperServers) {
        Topics topics = new Topics(zookeeperServers);
        if (!topics.topicExists(Topic.trustMessages)) {
            topics.createTopic(Topic.trustMessages);
        }

    }
}
