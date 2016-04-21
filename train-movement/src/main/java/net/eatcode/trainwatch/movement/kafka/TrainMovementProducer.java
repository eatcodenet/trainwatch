package net.eatcode.trainwatch.movement.kafka;

import static net.eatcode.trainwatch.movement.kafka.Topic.trainMovement;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.GsonTrainMovementParser;
import net.eatcode.trainwatch.movement.HzTrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementParser;
import net.eatcode.trainwatch.movement.TrainMovementStomp;
import net.eatcode.trainwatch.movement.TrustTrainMovementMessage;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementProducer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final KafkaProducer<String, byte[]> producer;
    private final TrainMovementParser parser = new GsonTrainMovementParser();
    private final TrainActivationRepo activationRepo;
    private final LocationRepo locationRepo;
    private final ScheduleRepo scheduleRepo;

    public TrainMovementProducer(String kafkaServers, TrainActivationRepo activationRepo, ScheduleRepo scheduleRepo,
            LocationRepo locationRepo) {
        this.activationRepo = activationRepo;
        this.scheduleRepo = scheduleRepo;
        this.locationRepo = locationRepo;
        Properties props = new PropertiesBuilder().forProducer(kafkaServers).withByteArrayValueSerializer().build();
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessages(String nrUsername, String nrPassword) {
        TrainMovementStomp stomp = new TrainMovementStomp(nrUsername, nrPassword);
        stomp.subscribe(movements -> {
            parser.parseArray(movements).forEach((tm) -> sendMessage(tm));
        });
    }

    private void sendMessage(TrustTrainMovementMessage msg) {
        if (msg.isActivation()) {
            activationRepo.putScheduleId(msg.body.train_id, msg.body.train_uid);
        } else {
            producer.send(new ProducerRecord<>(trainMovement.topicName(), msg.body.train_id,
                    KryoUtils.toByteArray(toTrainMovement(msg))));
        }
    }

    private TrainMovement toTrainMovement(TrustTrainMovementMessage msg) {
        Optional<Schedule> schedule = lookupSchedule(msg);
        return schedule.map(s -> {
            Location current = locationRepo.getByStanox(msg.body.loc_stanox);
            TrainMovement trainMovement = new TrainMovement(msg.body.train_id, dateTime(msg), current,
                    msg.body.timetable_variation, msg.body.train_terminated, s);
            log.debug("{}", trainMovement);
            return trainMovement;
        }).orElse(null);
    }

    public Optional<Schedule> lookupSchedule(TrustTrainMovementMessage msg) {
        return activationRepo.getScheduleId(msg.body.train_id)
                .map(value -> scheduleRepo.get(activationRepo.getScheduleId(msg.body.train_id).get()));
    }

    private LocalDateTime dateTime(TrustTrainMovementMessage msg) {
        return LocalDateTime.ofEpochSecond(Long.parseLong(msg.body.actual_timestamp) / 1000, 0, ZoneOffset.UTC);
    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String zookeeperServers = args[1];
        String hazelcastServers = args[2];
        String networkRailUsername = args[3];
        String networkRailPassword = args[4];
        checkTopicExists(zookeeperServers);

        TrainActivationRepo activationRepo = new HzTrainActivationRepo(hazelcastServers);
        ScheduleRepo scheduleRepo = new HzScheduleRepo(hazelcastServers);
        LocationRepo locationRepo = new HzLocationRepo(hazelcastServers);

        new TrainMovementProducer(kafkaServers, activationRepo, scheduleRepo, locationRepo)
                .produceMessages(networkRailUsername, networkRailPassword);
    }

    private static void checkTopicExists(String zookeeperServers) {
        Topics topics = new Topics(zookeeperServers);
        if (!topics.topicExists(trainMovement)) {
            throw new RuntimeException("Topic does not exist: " + trainMovement);
        }

    }
}
