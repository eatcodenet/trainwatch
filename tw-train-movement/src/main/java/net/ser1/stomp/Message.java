package net.ser1.stomp;

import java.util.Map;

/**
 * (c)2005 Sean Russell
 */
@SuppressWarnings({ "rawtypes" })
public class Message {
    private final Command _command;
    private final Map _headers;
    private final String _body;

    protected Message(Command c, Map h, String b) {
        _command = c;
        _headers = h;
        _body = b;
    }

    public Map headers() {
        return _headers;
    }

    public String body() {
        return _body;
    }

    public Command command() {
        return _command;
    }
}
