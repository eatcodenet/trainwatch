package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementParser {

    TrainMovement parse(String json);

    List<TrainMovement> parseArray(String jsonArray);
}
