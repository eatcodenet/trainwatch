package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementParser {

    TrainMovementCombinedMessage parse(String json);

    List<TrainMovementCombinedMessage> parseArray(String jsonArray);
}
