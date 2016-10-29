package net.eatcode.trainwatch.movement.kafka;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.hazelcast.HzActivationRepo;
import net.eatcode.trainwatch.movement.trust.GsonTrustMessageParser;
import net.eatcode.trainwatch.movement.trust.TrustMessageParser;
import net.eatcode.trainwatch.movement.trust.TrustMessagesStomp;
import net.eatcode.trainwatch.movement.trust.TrustMovementMessage;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementProducer {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final KafkaProducer<String, byte[]> producer;
	private final TrustMessageParser parser = new GsonTrustMessageParser();
	private final ActivationRepo activationRepo;
	private final LocationRepo locationRepo;
	private final ScheduleRepo scheduleRepo;

	public TrainMovementProducer(String kafkaServers, ActivationRepo activationRepo, ScheduleRepo scheduleRepo,
			LocationRepo locationRepo) {
		this.activationRepo = activationRepo;
		this.scheduleRepo = scheduleRepo;
		this.locationRepo = locationRepo;
		this.producer = new KafkaProducer<>(new PropertiesBuilder().forProducer(kafkaServers).build());
	}

	public void produceMessages(String nrUsername, String nrPassword) {
		TrustMessagesStomp stomp = new TrustMessagesStomp(nrUsername, nrPassword);
		log.info("created stomp service");
		stomp.subscribe(movements -> {
			parser.parseJsonArray(movements).forEach((tm) -> sendMessage(tm));
		});
	}

	private void sendMessage(TrustMovementMessage msg) {
		if (msg.isActivation()) {
			lookupSchedule(msg).ifPresent(s -> {
				activationRepo.put(new TrainActivation(msg.body.train_id, s));
			});
		} else {
			try {
				trainMovementFrom(msg).map(this::toByteArray).ifPresent(data -> {
					producer.send(new ProducerRecord<>(Topic.trainMovement, msg.body.train_service_code, data.get()));
				});
			} catch (Exception e) {
				log.error("{}", e);
			}
		}
	}

	private Optional<byte[]> toByteArray(TrainMovement msg) {
		return Optional.of(SerializationUtils.serialize(msg));
	}

	private Optional<TrainMovement> trainMovementFrom(TrustMovementMessage msg) {
		Optional<TrainActivation> ta = activationRepo.get(msg.body.train_id);
		return ta.map(t -> {
			Location current = locationRepo.getByStanox(msg.body.loc_stanox);
			return new TrainMovement(msg.body.train_id, dateTime(msg), current, calculateDelay(msg),
					msg.body.train_terminated, t.schedule);
		});
	}

	private String calculateDelay(TrustMovementMessage msg) {
		if (msg.body.variation_status == null) {
			return msg.body.timetable_variation;
		}
		return msg.body.variation_status.equals("EARLY") ? "-" + msg.body.timetable_variation
				: msg.body.timetable_variation;
	}

	public Optional<Schedule> lookupSchedule(TrustMovementMessage msg) {
		return scheduleRepo.getBy(msg.body.train_uid, msg.body.train_service_code, msg.body.schedule_start_date,
				msg.body.schedule_end_date);
	}

	private LocalDateTime dateTime(TrustMovementMessage msg) {
		if (msg.body.actual_timestamp == null) {
			return LocalDateTime.now();
		}
		return LocalDateTime.ofEpochSecond(Long.parseLong(msg.body.actual_timestamp) / 1000, 0, ZoneOffset.UTC);
	}

	public static void main(String[] args) {
		String kafkaServers = args[0];
		String zookeeperServers = args[1];
		String hazelcastServers = args[2];
		String networkRailUsername = args[3];
		String networkRailPassword = args[4];
		checkTopicExists(zookeeperServers);
		HazelcastInstance hzClient = new HzClientBuilder().build(hazelcastServers);
		ActivationRepo activationRepo = new HzActivationRepo(hzClient);
		ScheduleRepo scheduleRepo = new HzScheduleRepo(hzClient);
		LocationRepo locationRepo = new HzLocationRepo(hzClient);
		new TrainMovementProducer(kafkaServers, activationRepo, scheduleRepo, locationRepo)
				.produceMessages(networkRailUsername, networkRailPassword);
	}

	private static void checkTopicExists(String zookeeperServers) {
		Topics topics = new Topics(zookeeperServers);
		if (!topics.topicExists(Topic.trainMovement)) {
			throw new RuntimeException("Topic does not exist: " + Topic.trainMovement);
		}

	}
}
