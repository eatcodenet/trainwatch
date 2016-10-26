package net.eatcode.trainwatch.nr.dataimport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import net.eatcode.trainwatch.nr.Schedule;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.HzScheduleRepo;

public class HzScheduleRepoIntegrationTest {

	private final HzScheduleRepo repo = new HzScheduleRepo(new HzClientBuilder().buildStandalone());

	@Test
	public void getsSchedule() {
		Schedule schedule = new Schedule();
		schedule.id = "100";
		schedule.trainServiceCode = "abc";
		schedule.atocCode = "hola!";
		schedule.startDate = LocalDate.parse("2005-12-15");
		schedule.endDate = LocalDate.parse("2005-12-22");
		repo.put(schedule);
		assertThat(repo.getBy("100", "abc", "2005-12-15", "2005-12-22").atocCode, is("hola!"));
	}
}
