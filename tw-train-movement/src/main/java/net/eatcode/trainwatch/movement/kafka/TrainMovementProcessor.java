package net.eatcode.trainwatch.movement.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.ActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.MovementRepo;

public class TrainMovementProcessor implements Processor<String, TrainMovement> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MovementRepo movementRepo;

    private final ActivationRepo activationRepo;

    public TrainMovementProcessor(MovementRepo movementRepo, ActivationRepo activationRepo) {
        this.movementRepo = movementRepo;
        this.activationRepo = activationRepo;
    }

    @Override
    public void init(ProcessorContext context) {
        log.info("context {}", context);
    }

    @Override
    public void process(String key, TrainMovement tm) {
        log.debug("{}", tm);
        if (tm.hasArrivedAtDest()) {
            log.debug("Deleting arrived movement: {}, {} - {}", tm.trainId(), tm.originCrs(), tm.destCrs());
            deleteMovement(tm);
        } else {
            movementRepo.put(tm);
        }

    }

    private void deleteMovement(TrainMovement tm) {
        activationRepo.delete(tm);
        movementRepo.delete(tm);
    }

    @Override
    public void punctuate(long timestamp) {
        log.info("punctuate: {}", timestamp);
    }

    @Override
    public void close() {
        log.info("close");
    }

}
