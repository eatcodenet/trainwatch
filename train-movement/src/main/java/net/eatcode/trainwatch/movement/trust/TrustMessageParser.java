package net.eatcode.trainwatch.movement.trust;

import java.util.List;

public interface TrustMessageParser {

    List<TrustMovementMessage> parseArray(String jsonArray);
}