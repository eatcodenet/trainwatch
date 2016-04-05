package net.eatcode.trainwatch.movement;

import java.util.Optional;

public interface TrainActivationRepo {

    Optional<String> getScheduleId(String trainId);

    void putScheduleId(String trainId, String scheduleId);
}
