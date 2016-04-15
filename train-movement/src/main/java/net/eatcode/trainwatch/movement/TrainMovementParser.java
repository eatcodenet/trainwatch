package net.eatcode.trainwatch.movement;

import java.util.List;

public interface TrainMovementParser {

    List<TrustTrainMovementMessage> parseArray(String jsonArray);
}
