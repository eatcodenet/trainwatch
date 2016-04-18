package net.eatcode.trainwatch.movement.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.movement.TrainMovementRepo;

public class PutInRepoProcessor implements Processor<String, TrainMovement> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TrainMovementRepo repo;

    public PutInRepoProcessor(TrainMovementRepo trainMovementRepo) {
        repo = trainMovementRepo;
    }

    @Override
    public void init(ProcessorContext context) {
        log.info("{}", context);
    }

    @Override
    public void process(String key, TrainMovement movement) {
        repo.put(movement);

    }

    @Override
    public void punctuate(long timestamp) {
        log.info("punctuate: {}", timestamp);
    }

    @Override
    public void close() {
    }

}
