package net.eatcode.trainwatch.movement.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.eatcode.trainwatch.movement.TrainActivationRepo;
import net.eatcode.trainwatch.movement.TrainMovementRepo;
import net.eatcode.trainwatch.movement.TrainMovement;

public class TrainMovementProcessor implements Processor<String, TrainMovement> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final TrainMovementRepo movementRepo;

	private final TrainActivationRepo activationRepo;

	public TrainMovementProcessor(TrainMovementRepo movementRepo, TrainActivationRepo activationRepo) {
		this.movementRepo = movementRepo;
		this.activationRepo = activationRepo;
	}

	@Override
	public void init(ProcessorContext context) {
		log.info("context {}", context);
	}

	@Override
	public void process(String key, TrainMovement tm) {
		log.debug("{}",tm.toString());
		if (tm.hasArrivedAtDest()) {
			deleteArriveTrain(tm);
		} else {
			movementRepo.put(tm);
		}
	}

	private void deleteArriveTrain(TrainMovement tm) {
		log.debug("Deleting arrived train: {}, {} - {}", tm.trainId(), tm.originCrs(), tm.destCrs());
		movementRepo.delete(tm);
		activationRepo.delete(tm.trainId());
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
