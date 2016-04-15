package net.eatcode.trainwatch.movement.kafka;

public enum Topic {
    trustMovement("FACK1");

    private String topicName;

    Topic(String name) {
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
