package net.eatcode.trainwatch.nr.hazelcast;

import java.util.Optional;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.ScheduleRepo;

public class HzScheduleRepo implements ScheduleRepo {

	private final IMap<String, Schedule> map;

	public HzScheduleRepo(HazelcastInstance client) {
		this.map = client.getMap("schedule");
	}

	@Override
	public void put(Schedule schedule) {
		map.set(schedule.uniqueKey(), schedule);
	}

	@Override

	public Optional<Schedule> getBy(String id, String serviceCode, String start, String end) {
		return Optional.ofNullable(map.get(id + serviceCode + start + end));
	}

	@Override
	public Integer count() {
		return map.size();
	}
}
