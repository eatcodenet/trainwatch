package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.TrainDeparture;
import net.eatcode.trainwatch.nr.hazelcast.CommonSerializer;

public class TrainDepartureSerializer extends CommonSerializer<TrainDeparture> {

    @Override
    public int getTypeId() {
        return 3;
    }

    @Override
    protected Class<TrainDeparture> getClassToSerialize() {
        return TrainDeparture.class;
    }

}
