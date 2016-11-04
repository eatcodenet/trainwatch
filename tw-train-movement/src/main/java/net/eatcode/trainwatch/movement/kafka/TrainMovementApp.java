package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trainMovement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.MovementRepo;
import net.eatcode.trainwatch.movement.hazelcast.HzActivationRepo;
import net.eatcode.trainwatch.movement.hazelcast.HzCleanup;
import net.eatcode.trainwatch.movement.hazelcast.HzMovementRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementApp {

	private static final Logger log = LoggerFactory.getLogger(TrainMovementApp.class);

	public static void main(String[] args) throws InterruptedException {
		String kafkaServers = args[0] + ":9092";
		String zookeeperServers = args[1];
		String hazelcastServers = args[2];
		String networkRailUsername = args[3];
		String networkRailPassword = args[4];
		checkTopicExists(zookeeperServers);

		HazelcastInstance hzClient = new HzClientBuilder().build(hazelcastServers);
		ActivationRepo activationRepo = new HzActivationRepo(hzClient);
		MovementRepo movementRepo = new HzMovementRepo(hzClient);

		Runnable movementProducer = () -> {
			log.info("running train movement producer");
			new TrainMovementProducer(kafkaServers, activationRepo, new HzScheduleRepo(hzClient),
					new HzLocationRepo(hzClient)).produceMessages(networkRailUsername, networkRailPassword);
		};

		Runnable movementStream = () -> {
			log.info("running movement stream");
			new TrainMovementStream(kafkaServers, new TrainMovementProcessor(movementRepo, activationRepo))
					.processMessages();
		};

		new Thread(movementProducer).start();
		new Thread(movementStream).start();
		new HzCleanup(movementRepo, activationRepo).start();
	}

	private static void checkTopicExists(String zookeeperServers) {
		Topics topics = new Topics(zookeeperServers);
		if (!topics.topicExists(trainMovement)) {
			throw new RuntimeException("Topic does not exist: " + trainMovement);
		}
	}
}
