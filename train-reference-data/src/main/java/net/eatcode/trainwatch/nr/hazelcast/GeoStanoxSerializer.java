package net.eatcode.trainwatch.nr.hazelcast;

import net.eatcode.trainwatch.nr.GeoStanox;

public class GeoStanoxSerializer extends CommonSerializer<GeoStanox> {

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    protected Class<GeoStanox> getClassToSerialize() {
        return GeoStanox.class;
    }

}
