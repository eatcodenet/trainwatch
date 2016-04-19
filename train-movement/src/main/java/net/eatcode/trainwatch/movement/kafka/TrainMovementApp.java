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
        String hazelcastServers = args[1];
        String networkRailUsername = args[2];
        String networkRailPassword = args[3];
        checkTopicExists(hazelcastServers);

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

    private static void checkTopicExists(String kafkaServers) {
        Topics topics = new Topics(kafkaServers);
        if (!topics.topicExists(Topic.trustMessages)) {
            topics.createTopic(Topic.trustMessages);
        }

    }
}
