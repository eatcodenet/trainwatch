package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementParser {

    TrainMovementMessage parse(String json);

    List<TrainMovementMessage> parseArray(String jsonArray);
}
