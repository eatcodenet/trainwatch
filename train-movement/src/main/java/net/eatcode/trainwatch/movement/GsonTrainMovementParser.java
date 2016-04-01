package net.eatcode.trainwatch.movement;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTrainMovementParser implements TrainMovementParser {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public TrainMovement parse(String json) {
        return gson.fromJson(json, TrainMovement.class);
    }

    @Override
    public List<TrainMovement> parseArray(String json) {
        return Arrays.asList(gson.fromJson(json, TrainMovement[].class));
    }

}
