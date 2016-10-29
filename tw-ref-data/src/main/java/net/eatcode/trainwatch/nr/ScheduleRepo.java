package net.eatcode.trainwatch.nr;

import java.util.Optional;

public interface ScheduleRepo {

	void put(Schedule schedule);

	Optional<Schedule> getBy(String id, String trainServiceCode, String start, String end);

	Integer count();
}
