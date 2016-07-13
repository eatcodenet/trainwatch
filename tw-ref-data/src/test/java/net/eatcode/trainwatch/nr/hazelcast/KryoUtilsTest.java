package net.eatcode.trainwatch.nr.hazelcast;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import net.eatcode.trainwatch.nr.LatLon;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.Schedule;
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

	@Test
	public void serializesLocalTime() {
		Schedule s1 = new Schedule();
		s1.id = "schedule id";
		s1.arrival = LocalTime.of(6, 30);
		
		byte[] data = KryoUtils.toByteArray(s1);
		Schedule s2 = KryoUtils.fromByteArray(data, Schedule.class);
		assertThat(s2.id, is("schedule id"));
		assertThat(s2.arrival, is(LocalTime.of(6, 30)));
	}
	
	@Test
	public void serializesLocalDate() {
		Schedule s1 = new Schedule();
		s1.id = "schedule id";
		s1.startDate = LocalDate.now();
		
		byte[] data = KryoUtils.toByteArray(s1);
		Schedule s2 = KryoUtils.fromByteArray(data, Schedule.class);
		assertThat(s2.id, is("schedule id"));
		assertThat(s2.startDate, is(LocalDate.now()));
	}
	
	@Test
	public void serializesLocalDateTime() {
		LocalDateTime now = LocalDateTime.now();
		byte[] data = KryoUtils.toByteArray(now);
		assertThat(KryoUtils.fromByteArray(data, LocalDateTime.class), is(now));
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
