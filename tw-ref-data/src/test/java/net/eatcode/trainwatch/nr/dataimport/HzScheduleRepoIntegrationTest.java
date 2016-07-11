package net.eatcode.trainwatch.nr.dataimport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
        repo.put(schedule);
        assertThat(repo.getByIdAndServiceCode("100", "abc").atocCode, is("hola!"));
    }
}
