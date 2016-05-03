package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.TrainMovement;
import net.eatcode.trainwatch.nr.hazelcast.CommonSerializer;

public class TrainMovementSerializer extends CommonSerializer<TrainMovement> {

    @Override
    public int getTypeId() {
        return 4;
    }

    @Override
    protected Class<TrainMovement> getClassToSerialize() {
        return TrainMovement.class;
    }

}
