package net.eatcode.trainwatch.movement.hazelcast;

import net.eatcode.trainwatch.movement.Stats;
import net.eatcode.trainwatch.nr.hazelcast.CommonSerializer;

public class StatsSerializer extends CommonSerializer<Stats> {

    @Override
    public int getTypeId() {
        return 5;
    }

    @Override
    protected Class<Stats> getClassToSerialize() {
        return Stats.class;
    }

}
