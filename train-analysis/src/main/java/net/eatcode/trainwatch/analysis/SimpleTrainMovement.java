package net.eatcode.trainwatch.analysis;

public class SimpleTrainMovement {

    private final String train_id;

    public SimpleTrainMovement(String train_id) {
        this.train_id = train_id;
    }

    @Override
    public String toString() {
        return "SimpleTrainMovement [train_id=" + train_id + "]";
    }

}
