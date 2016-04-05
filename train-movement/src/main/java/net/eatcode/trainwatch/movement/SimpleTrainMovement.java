package net.eatcode.trainwatch.movement;

import java.io.Serializable;

public class SimpleTrainMovement implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String train_id;

    public SimpleTrainMovement(String train_id) {
        this.train_id = train_id;
    }

    @Override
    public String toString() {
        return "SimpleTrainMovement [train_id=" + train_id + "]";
    }

}
