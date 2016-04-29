package net.eatcode.trainwatch.movement.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

import net.eatcode.trainwatch.movement.LiveDeparturesRepo;
import net.eatcode.trainwatch.movement.TrainDeparture;

public class LiveDepartureProcessor implements Processor<String, TrainDeparture> {

    private final LiveDeparturesRepo repo;

    public LiveDepartureProcessor(LiveDeparturesRepo repo) {
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
