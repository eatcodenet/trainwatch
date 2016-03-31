package net.eatcode.trains.model.hazelcast;

import net.eatcode.trains.model.Crs;

public class CrsSerializer extends CommonSerializer<Crs> {

    @Override
    public int getTypeId() {
        return 1;
    }
}
