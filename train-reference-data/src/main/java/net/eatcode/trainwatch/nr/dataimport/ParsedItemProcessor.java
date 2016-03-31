package net.eatcode.trainwatch.nr.dataimport;

@FunctionalInterface
public interface ParsedItemProcessor<I> {
    void process(I i);
}
