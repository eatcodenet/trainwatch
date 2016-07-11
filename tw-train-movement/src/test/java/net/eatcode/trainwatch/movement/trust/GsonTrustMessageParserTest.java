package net.eatcode.trainwatch.movement.trust;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import net.eatcode.trainwatch.movement.trust.GsonTrustMessageParser;
import net.eatcode.trainwatch.movement.trust.TrustMovementMessage;

public class GsonTrustMessageParserTest {

    @Test
    public void parseArrayOfMessages() throws Exception {
        String json = readFile("src/test/resources/sampledata/32TrainMovementsArray.json");
        List<TrustMovementMessage> movements = new GsonTrustMessageParser().parseJsonArray(json);
        assertThat(movements.size(), is(32));
        assertThat(movements.get(31).body.train_id, is("861H47MT01"));
    }

    private String readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName)).stream().collect(Collectors.joining());
    }
}
