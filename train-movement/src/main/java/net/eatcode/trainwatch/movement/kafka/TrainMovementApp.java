package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trainMovement;

import net.eatcode.trainwatch.movement.hazelcast.HzLiveDeparturesRepo;
import net.eatcode.trainwatch.movement.hazelcast.HzTrainActivationRepo;
import net.eatcode.trainwatch.movement.hazelcast.HzTrainMovementRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementApp {

    public static void main(String[] args) throws InterruptedException {
        String kafkaServers = args[0];
        String zookeeperServers = args[1];
        String hazelcastServers = args[2];
        String networkRailUsername = args[3];
        String networkRailPassword = args[4];
        checkTopicExists(zookeeperServers);

        Runnable movementProducer = () -> {
            System.out.println("running producer");
            new TrainMovementProducer(kafkaServers, new HzTrainActivationRepo(hazelcastServers),
                    new HzScheduleRepo(hazelcastServers), new HzLocationRepo(hazelcastServers))
                            .produceMessages(networkRailUsername, networkRailPassword);
        };

        Runnable movements = () -> {
            System.out.println("running movements");
            new TrainMovementStream(kafkaServers, new HzTrainMovementRepo(hazelcastServers)).process();
        };

        Runnable liveDepartures = () -> {
            System.out.println("running liveDepartures");
            new LiveDepartureStream(kafkaServers, new HzLiveDeparturesRepo(hazelcastServers)).process();
        };

        new Thread(movementProducer).start();
        new Thread(movements).start();
        new Thread(liveDepartures).start();
    }

    private static void checkTopicExists(String zookeeperServers) {
        Topics topics = new Topics(zookeeperServers);
        if (!topics.topicExists(trainMovement)) {
            throw new RuntimeException("Topic does not exist: " + trainMovement);
        }
    }
}
