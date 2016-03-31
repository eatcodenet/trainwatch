package net.eatcode.trainwatch.nr.dataimport;

import net.eatcode.trainwatch.nr.Tiploc;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TiplocFileParserTest {

    private final TiplocFileParser parser = new TiplocFileParser("src/test/resources/data/sample-tiplocs.json");

    @Test
    public void shouldParseTiplocs() throws Exception {
        List<Tiploc> results = new ArrayList<>();
        CompletableFuture<Integer> f = parser.parse(item -> results.add(item));
        f.whenCompleteAsync((value, error) -> {
            assertThat(value, is(6));
            assertThat(results.get(0).crsCode, is("C1"));
            assertThat(results.get(0).nlc, is("N1"));
            assertThat(results.get(0).stanox, is("S1"));
            assertThat(results.get(0).tiploc, is("T1"));
        }).get(2, TimeUnit.SECONDS);
    }
}
