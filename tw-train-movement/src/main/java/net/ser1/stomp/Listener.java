package net.ser1.stomp;

import java.util.Map;

/**
 * (c)2005 Sean Russell
 */
@SuppressWarnings({ "rawtypes" })
public interface Listener {
    public void message(Map headers, String body);
}
