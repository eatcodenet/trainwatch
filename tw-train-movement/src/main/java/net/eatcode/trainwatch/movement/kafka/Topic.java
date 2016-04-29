package net.eatcode.trainwatch.movement.kafka;

public enum Topic {

    trainMovement("tw-train-movement");

    private String topicName;

    private Topic(String name) {
        this.topicName = name;
    }

    public String topicName() {
        return topicName;
    }

    @Override
    public String toString() {
        return this.topicName;
    }
}
