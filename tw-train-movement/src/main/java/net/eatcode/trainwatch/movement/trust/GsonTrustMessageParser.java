package net.eatcode.trainwatch.movement.trust;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTrustMessageParser implements TrustMessageParser {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public List<TrustMovementMessage> parseJsonArray(String json) {
        return Arrays.asList(gson.fromJson(json, TrustMovementMessage[].class));
    }

}
