package net.ser1.stomp;

import java.util.Map;

/**
 * A client that is connected directly to a server. Messages sent via this client do not go through a network interface,
 * except when being delivered to clients connected via the network... all messages to other IntraVMClients are
 * delivered entirely in memory.
 * <p>
 * (c)2005 Sean Russell
 */
@SuppressWarnings({ "rawtypes" })
public class IntraVMClient extends Stomp implements Listener, Authenticatable {
    private Server _server;

    protected IntraVMClient(Server server) {
        _server = server;
        _connected = true;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public Object token() {
        return "IntraVMClient";
    }

    /**
     * Transmit a message to clients and listeners.
     */
    @Override
    public void transmit(Command c, Map h, String b) {
        _server.receive(c, h, b, this);
    }

    @Override
    public void disconnect(Map h) {
        _server.receive(Command.DISCONNECT, null, null, this);
        _server = null;
    }

    @Override
    public void message(Map headers, String body) {
        receive(Command.MESSAGE, headers, body);
    }

    public void receipt(Map headers) {
        receive(Command.RECEIPT, headers, null);
    }

    @Override
    public void error(Map headers, String body) {
        receive(Command.ERROR, headers, body);
    }
}
