package net.eatcode.trainwatch.movement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class GsonTrainMovementParserTest {

    @Test
    public void parseSingleMessage() throws Exception {
        String json = readFile("src/test/resources/sampledata/singleTrainMovement.json");
        TrainMovementCombinedMessage tm = new GsonTrainMovementParser().parse(json);
        assertThat(tm.header.msg_type, is("0003"));
        assertThat(tm.body.train_id, is("401V97MP01"));
    }

    @Test
    public void parseArrayOfMessages() throws Exception {
        String json = readFile("src/test/resources/sampledata/32TrainMovementsArray.json");
        List<TrainMovementCombinedMessage> movements = new GsonTrainMovementParser().parseArray(json);
        assertThat(movements.size(), is(32));
        assertThat(movements.get(31).body.train_id, is("861H47MT01"));
    }

    private String readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName)).stream().collect(Collectors.joining());
    }
}
