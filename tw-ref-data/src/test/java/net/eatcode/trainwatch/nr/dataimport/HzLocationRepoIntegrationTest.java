package net.eatcode.trainwatch.nr.dataimport;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.eatcode.trainwatch.nr.LatLon;
import net.eatcode.trainwatch.nr.Location;
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder;
import net.eatcode.trainwatch.nr.hazelcast.HzLocationRepo;

public class HzLocationRepoIntegrationTest {

    private final HzLocationRepo repo = new HzLocationRepo(new HzClientBuilder().buildStandalone());

    @Test
    public void getsLocation() {
        repo.put(new Location("stanox", "description", "tiploc", "crs", new LatLon("lat", "lon")));
        assertThat(repo.getByStanox("stanox").description, is("description"));
        assertThat(repo.getByTiploc("tiploc").description, is("description"));
    }
}
