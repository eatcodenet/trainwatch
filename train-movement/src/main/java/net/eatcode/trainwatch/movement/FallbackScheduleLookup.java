package net.eatcode.trainwatch.movement;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.nr.DaySchedule;
import net.eatcode.trainwatch.nr.DayScheduleRepo;

public class FallbackScheduleLookup implements ScheduleLookup {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TrainActivationRepo activationRepo;
    private final DayScheduleRepo scheduleRepo;

    public FallbackScheduleLookup(TrainActivationRepo activationRepo, DayScheduleRepo scheduleRepo) {
        this.activationRepo = activationRepo;
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public DaySchedule lookup(TrainMovementCombinedMessage message) {
        Optional<String> scheduleId = activationRepo.getScheduleId(headcode(message));
        log.debug("SCHEDID: {}", scheduleId);
        if (scheduleId.isPresent()) {
            return scheduleRepo.get(scheduleId.get());
        }
        return fallbackSchedule(message);
    }

    private DaySchedule fallbackSchedule(TrainMovementCombinedMessage m) {
        String key = m.body.train_service_code + headcode(m) + runDay();
        log.debug("FALLBACK KEY: {}", key);
        return scheduleRepo.getByDaykey(key);
    }

    private String headcode(TrainMovementCombinedMessage m) {
        return m.body.train_id.substring(2, 6);
    }

    private int runDay() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

}
