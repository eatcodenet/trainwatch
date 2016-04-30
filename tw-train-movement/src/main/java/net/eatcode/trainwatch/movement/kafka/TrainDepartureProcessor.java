package net.eatcode.trainwatch.movement.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

import net.eatcode.trainwatch.movement.DeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;

public class TrainDepartureProcessor implements Processor<String, TrainDeparture> {

    private final DeparturesRepo repo;

    public TrainDepartureProcessor(DeparturesRepo repo) {
        this.repo = repo;
    }

    @Override
    public void init(ProcessorContext context) {
    }

    @Override
    public void process(String key, TrainDeparture value) {
       repo.put(value);
    }

    @Override
    public void punctuate(long timestamp) {
    }

    @Override
    public void close() {
    }

}
