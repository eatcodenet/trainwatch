package net.eatcode.trainwatch.nr.hazelcast;

import net.eatcode.trainwatch.nr.Location;

public class LocationSerializer extends CommonSerializer<Location> {

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    protected Class<Location> getClassToSerialize() {
        return Location.class;
    }

}
