package net.eatcode.trainwatch.nr.hazelcast;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.eatcode.trainwatch.nr.LatLon;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.KryoInstances;
import net.eatcode.trainwatch.nr.hazelcast.KryoUtils;

public class KryoUtilsTest {

    private final Location location = new Location("stanox", "description", "tiploc", "crs", new LatLon("lat", "lon"));

    @Test
    public void toByteArray() {
        byte[] buffer = KryoUtils.toByteArray(location);
        Location loc = deserialize(buffer);
        assertThat(loc.description, is("description"));
    }

    @Test
    public void fromByteArray() {
        Output output = serialize(location);
        Location loc = KryoUtils.fromByteArray(output.getBuffer(), Location.class);
        assertThat(loc.description, is("description"));
    }

    private Output serialize(Location loc) {
        Kryo kryo = KryoInstances.get(loc.getClass());
        Output output = new Output(1024, 16384);
        kryo.writeObject(output, loc);
        output.flush();
        KryoInstances.release(kryo);
        return output;
    }

    private Location deserialize(byte[] buffer) {
        Kryo kryo = KryoInstances.get(Location.class);
        Location loc = kryo.readObject(new Input(buffer), Location.class);
        KryoInstances.release(kryo);
        return loc;
    }
}
