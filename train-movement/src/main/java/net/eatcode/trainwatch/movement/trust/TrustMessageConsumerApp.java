package net.eatcode.trainwatch.movement.trust;

import java.util.List;

import net.eatcode.trainwatch.movement.trust.TrustMovementMessage.Body;

public class TrustMessageConsumerApp {

    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];

        GsonTrustMessageParser parser = new GsonTrustMessageParser();

        new TrustMessagesStomp(username, password).subscribe(json -> print(parser.parseArray(json)));
    }

    private static void print(List<TrustMovementMessage> msgs) {
        msgs.forEach(msg -> {
            Body b = msg.body;
            System.out.println(msg.header.msg_type + " t: " + b.train_id + " sc:" + b.train_service_code + " " + b.train_terminated);
        });
    }

}
