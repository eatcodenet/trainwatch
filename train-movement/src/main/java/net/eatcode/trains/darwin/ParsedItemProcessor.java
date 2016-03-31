package net.eatcode.trains.darwin;

@FunctionalInterface
public interface ParsedItemProcessor<I> {
    void process(I i);
}
