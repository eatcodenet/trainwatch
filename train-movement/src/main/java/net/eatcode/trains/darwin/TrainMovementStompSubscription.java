package net.eatcode.trains.darwin;

import com.google.gson.Gson;
import net.ser1.stomp.Client;
import net.ser1.stomp.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Map;

public class TrainMovementStompSubscription {

    private final Logger log = LoggerFactory.getLogger(TrainMovementStompSubscription.class);

    private final String host = "datafeeds.networkrail.co.uk";
    private final Integer port = 61618;
    private final String trainMovementTopic = "/topic/TRAIN_MVT_ALL_TOC";

    private final String username;
    private final String password;

    private Client client;

    private final Gson gson = new Gson();

    public TrainMovementStompSubscription(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void subscribe(TrustTrainMovementListener listener) {
        try {
            connectToHost();
            subscribeToTopic(listener);
        } catch (IOException | LoginException e) {
            log.error("Stomp error", e);
        }
    }

    private void subscribeToTopic(TrustTrainMovementListener listener) {
        client.subscribe(trainMovementTopic, (headers, body) -> {
            notifyListenerOfTrainMovements(listener, body);
        });
    }

    private void notifyListenerOfTrainMovements(TrustTrainMovementListener listener, String body) {
        for (TrustTrainMovement tm : gson.fromJson(body, TrustTrainMovement[].class)) {
            listener.onTrainMovement(tm);
        }
    }

    private void connectToHost() throws IOException, LoginException {
        client = new Client(host, port, username, password);
        if (client.isConnected()) {
            log.info("connected to {}", host);
        }
    }

    public void stop() {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

}
