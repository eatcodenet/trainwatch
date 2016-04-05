package net.eatcode.trainwatch.movement;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTrainMovementParser implements TrainMovementParser {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public List<TrainMovementCombinedMessage> parseArray(String json) {
        return Arrays.asList(gson.fromJson(json, TrainMovementCombinedMessage[].class));
    }

}
