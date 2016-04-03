package net.eatcode.trainwatch.nr.hazelcast;

import net.eatcode.trainwatch.nr.GeoLocation;

public class GeoStanoxSerializer extends CommonSerializer<GeoLocation> {

    @Override
    public int getTypeId() {
        return 1;
    }


    @Override
    protected Class<GeoLocation> getClassToSerialize() {
        return GeoLocation.class;
    }

}
