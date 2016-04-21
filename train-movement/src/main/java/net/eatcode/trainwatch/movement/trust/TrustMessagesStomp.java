package net.eatcode.trainwatch.movement.trust;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ser1.stomp.Client;

public class TrustMessagesStomp {

    private final Logger log = LoggerFactory.getLogger(TrustMessagesStomp.class);

    private final String host = "datafeeds.networkrail.co.uk";
    private final Integer port = 61618;
    private final String trainMovementTopic = "/topic/TRAIN_MVT_ALL_TOC";

    private final String username;
    private final String password;
    private Client client;

    public TrustMessagesStomp(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void subscribe(TrustMessageListener listener) {
        try {
            connectToHost();
            subscribeToTopic(listener);
        } catch (IOException | LoginException e) {
            log.error("Stomp error", e);
        }
    }

    private void subscribeToTopic(TrustMessageListener listener) {
        client.subscribe(trainMovementTopic, (headers, body) -> {
            listener.onTrainMovement(body);
        });
    }

    private void connectToHost() throws IOException, LoginException {
        client = new Client(host, port, username, password);
        if (client.isConnected()) {
            log.info("connected to {}", host);
        }
    }

}
