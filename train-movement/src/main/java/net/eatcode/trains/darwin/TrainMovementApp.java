package net.eatcode.trains.darwin;

import net.eatcode.trains.model.Schedule;
import net.eatcode.trains.model.hazelcast.HazelcastScheduleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TrainMovementApp {

    private static final Logger log = LoggerFactory.getLogger(TrainMovementApp.class);

    public static void main(String[] args) {
        String username = System.getProperty("darwin.username", "");
        String password = System.getProperty("darwin.password", "");

        HazelcastScheduleRepo repo = new HazelcastScheduleRepo();

        TrainMovementStompSubscription tmSubscription = new TrainMovementStompSubscription(username, password);
        tmSubscription.subscribe(tm -> {
            //log.info("movement: {} {}", tm.body.train_service_code, tm.body.loc_stanox);
            List<Schedule> schedules = repo.getForServiceCode(tm.body.train_service_code);
            schedules.forEach(s -> {
                String hc = hc(tm.body.train_id);
                if (hc.equals(s.headcode)) {
                    //log.info("s: {} {}", s.trainServiceCode, s.headcode);
                    log.info("{} {} {} {} {} {} {} {}", tm.body.train_service_code, hc, s.runDays, s.origin, s.publicDeparture, s.destination, s.publicArrival, tm.body.timetable_variation);
                }
            });
        });
    }

    private static String hc(String train_id) {
        return train_id.substring(2, 6);
    }
}
