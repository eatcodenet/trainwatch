package net.eatcode.trainwatch.nr.hazelcast;

import net.eatcode.trainwatch.nr.Crs;

public class CrsSerializer extends CommonSerializer<Crs> {

    @Override
    public int getTypeId() {
        return 1;
    }
}
