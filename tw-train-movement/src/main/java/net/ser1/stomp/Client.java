package net.ser1.stomp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * Implements a Stomp client connection to a Stomp server via the network.
 * <p>
 * Example:
 * <pre>
 *   Client c = new Client( "localhost", 61626, "ser", "ser" );
 *   c.subscribe( "/my/channel", new Listener() { ... } );
 *   c.subscribe( "/my/other/channel", new Listener() { ... } );
 *   c.send( "/other/channel", "Some message" );
 *   // ...
 *   c.disconnect();
 * </pre>
 *
 * @see Stomp
 * <p>
 * (c)2005 Sean Russell
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Client extends Stomp implements MessageReceiver {
    private final Thread _listener;
    private final OutputStream _output;
    private final InputStream _input;
    private final Socket _socket;

    /**
     * Connects to a server
     * <p>
     * Example:
     * <pre>
     *   Client stomp_client = new Client( "host.com", 61626 );
     *   stomp_client.subscribe( "/my/messages" );
     *   ...
     * </pre>
     *
     * @param server The IP or host name of the server
     * @param port   The port the server is listening on
     * @see Stomp
     */
    public Client(String server, int port, String login, String pass)
        throws IOException, LoginException {
        _socket = new Socket(server, port);
        _input = _socket.getInputStream();
        _output = _socket.getOutputStream();

        _listener = new Receiver(this, _input);
        _listener.start();

        // Connect to the server
        HashMap header = new HashMap();
        header.put("login", login);
        header.put("passcode", pass);
        transmit(Command.CONNECT, header, null);
        try {
            String error = null;
            while (!isConnected() && ((error = nextError()) == null)) {
                Thread.sleep(50);
            }
            if (error != null) {
                _listener.interrupt();
                throw new LoginException(error);
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public boolean isClosed() {
        return _socket.isClosed();
    }

    @Override
    public void disconnect(Map header) {
        if (!isConnected()) {
            return;
        }
        transmit(Command.DISCONNECT, header, null);
        _listener.interrupt();
        Thread.yield();
        try {
            _input.close();
        } catch (IOException e) {/* We ignore these. */}
        try {
            _output.close();
        } catch (IOException e) {/* We ignore these. */}
        try {
            _socket.close();
        } catch (IOException e) {/* We ignore these. */}
        _connected = false;
    }


    /**
     * Transmit a message to the server
     */
    @Override
    public void transmit(Command c, Map h, String b) {
        try {
            Transmitter.transmit(c, h, b, _output);
        } catch (Exception e) {
            receive(Command.ERROR, null, e.getMessage());
        }
    }
}
