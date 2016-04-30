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

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.DeparturesRepo;
import net.eatcode.trainwatch.movement.TrainActivation;
import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.hazelcast.HzActivationRepo;
import net.eatcode.trainwatch.movement.hazelcast.HzDeparturesRepo;
import net.eatcode.trainwatch.movement.trust.GsonTrustMessageParser;
import net.eatcode.trainwatch.movement.trust.TrustMessageParser;
import net.eatcode.trainwatch.movement.trust.TrustMessagesStomp;
import net.eatcode.trainwatch.movement.trust.TrustMovementMessage;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.LocationRepo;
import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class TrainMovementProducer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final KafkaProducer<String, byte[]> producer;
    private final TrustMessageParser parser = new GsonTrustMessageParser();
    private final ActivationRepo activationRepo;
    private final LocationRepo locationRepo;
    private final ScheduleRepo scheduleRepo;
    private final DeparturesRepo departuresRepo;

    public TrainMovementProducer(String kafkaServers, ActivationRepo activationRepo, ScheduleRepo scheduleRepo,
            LocationRepo locationRepo, DeparturesRepo departuresRepo) {
        this.activationRepo = activationRepo;
        this.scheduleRepo = scheduleRepo;
        this.locationRepo = locationRepo;
        this.departuresRepo = departuresRepo;
        Properties props = new PropertiesBuilder().forProducer(kafkaServers).withByteArrayValueSerializer().build();
        this.producer = new KafkaProducer<>(props);
    }

    public void produceMessages(String nrUsername, String nrPassword) {
        TrustMessagesStomp stomp = new TrustMessagesStomp(nrUsername, nrPassword);
        log.debug("created stomp service");
        stomp.subscribe(movements -> {
            parser.parseArray(movements).forEach((tm) -> sendMessage(tm));
        });
    }

    private void sendMessage(TrustMovementMessage msg) {
        Optional<Schedule> schedule = lookupSchedule(msg);
        if (msg.isActivation()) {
            activationRepo.put(new TrainActivation(msg.body.train_id, msg.body.train_service_code, msg.body.train_uid));
            departuresRepo.put(schedule
                    .map(s -> new TrainDeparture(msg.body.train_id, msg.body.origin_dep_timestamp, s)).get());
        } else {
            trainMovementFrom(msg, schedule).map(KryoUtils::toByteArray).ifPresent(bytes -> {
                producer.send(new ProducerRecord<>(trainMovement.topicName(), msg.body.train_service_code, bytes));
            });
        }
    }

    private Optional<TrainMovement> trainMovementFrom(TrustMovementMessage msg, Optional<Schedule> schedule) {
        return schedule.map(s -> {
            Location current = locationRepo.getByStanox(msg.body.loc_stanox);
            return new TrainMovement(msg.body.train_id, dateTime(msg),
                    current, msg.body.timetable_variation, msg.body.train_terminated, s);
        });
    }

    public Optional<Schedule> lookupSchedule(TrustMovementMessage msg) {
        return activationRepo.get(msg.body.train_id)
                .map(ta -> scheduleRepo.getByIdAndServiceCode(ta.scheduleId(), ta.serviceCode()));
    }

    private LocalDateTime dateTime(TrustMovementMessage msg) {
        return LocalDateTime.ofEpochSecond(Long.parseLong(msg.body.actual_timestamp) / 1000, 0, ZoneOffset.UTC);
    }

    public static void main(String[] args) {
        String kafkaServers = args[0];
        String zookeeperServers = args[1];
        String hazelcastServers = args[2];
        String networkRailUsername = args[3];
        String networkRailPassword = args[4];
        checkTopicExists(zookeeperServers);

        ActivationRepo activationRepo = new HzActivationRepo(hazelcastServers);
        ScheduleRepo scheduleRepo = new HzScheduleRepo(hazelcastServers);
        LocationRepo locationRepo = new HzLocationRepo(hazelcastServers);
        DeparturesRepo departuresRepo = new HzDeparturesRepo(hazelcastServers);
        new TrainMovementProducer(kafkaServers, activationRepo, scheduleRepo, locationRepo, departuresRepo)
                .produceMessages(networkRailUsername, networkRailPassword);
    }

    private static void checkTopicExists(String zookeeperServers) {
        Topics topics = new Topics(zookeeperServers);
        if (!topics.topicExists(trainMovement)) {
            throw new RuntimeException("Topic does not exist: " + trainMovement);
        }

    }
}
