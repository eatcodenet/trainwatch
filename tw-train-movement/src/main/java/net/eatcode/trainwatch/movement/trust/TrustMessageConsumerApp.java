package net.eatcode.trainwatch.movement.trust;

import java.util.List;

public class TrustMessageConsumerApp {

    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];

        GsonTrustMessageParser parser = new GsonTrustMessageParser();

        new TrustMessagesStomp(username, password).subscribe(json -> print(parser.parseJsonArray(json)));
    }

    private static void print(List<TrustMovementMessage> msgs) {
        msgs.forEach(msg -> {
            System.out.println(msg.header.msg_type + " t: " + msg);
        });
    }

}
