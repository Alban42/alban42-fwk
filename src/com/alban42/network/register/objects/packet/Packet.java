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
     * A random unique ID.
     */
    private final String id;

    public Packet() {
        id = UUID.randomUUID().toString();
    }

    /**
     * @return the id
     */
    public String getPacketId() {
        return id;
    }
}
