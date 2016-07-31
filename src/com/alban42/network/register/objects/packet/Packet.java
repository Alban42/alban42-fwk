/**
 *
 */
package com.alban42.network.register.objects.packet;

import java.util.UUID;

/**
 * @author Alban
 */
public abstract class Packet {

    /**
     * A random unique ID for the packet.
     */
    private final String id;

    public Packet() {
        id = UUID.randomUUID().toString();
    }

    /**
     * @return the id of the packet sent/received.
     */
    public String getPacketId() {
        return id;
    }
}
