/**
 *
 */
package com.alban42.network.client;

import com.alban42.network.client.listener.NetworkClientListener;
import com.alban42.network.register.ClassRegister;
import com.alban42.network.register.objects.ICaller;
import com.alban42.network.register.objects.packet.Packet;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provide an easy way to send packet to the server.<br/>
 * This class is an Singleton, {@link NetworkClient#INSTANCE} must be used to call any
 * method of this class.<br/>
 * The {@link NetworkClientListener} is used as the listener of the responses of by
 * the server.
 *
 * @author Alban
 */
public class NetworkClient {

    public static final NetworkClient INSTANCE = new NetworkClient();
    public static final int DEFAULT_TCP_PORT = 27960;
    public static final int DEFAULT_UDP_PORT = 27961;

    private final Client client;
    private final Map<String, ICaller> packets;
    public boolean connected;

    /**
     * Constructor that initialise
     */
    private NetworkClient() {
        connected = false;
        client = new Client();
        packets = new HashMap<>();
    }

    public void connect(String host, int tcpPort, int udpPort, ClassRegister classRegister, NetworkClientListener listener) throws IOException {
        classRegister.register(client);
        client.addListener(listener);
        client.start();
        try {
            client.connect(5000, host, tcpPort, udpPort);
        } catch (final IOException e) {
            Log.info("Cannot connect : " + e.getMessage());
            throw e;
        }
        connected = true;
    }

    /**
     * Disconnect from the server.
     */
    public void disconnect() {
        client.stop();
    }

    /**
     * The list of the packets sent.
     *
     * @return the packets
     */
    public Map<String, ICaller> getPackets() {
        return packets;
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Send a packet through the network (via TCP) and call the
     * {@link ICaller#responseReceived(Packet)} method when a response is
     * received by the server.
     *
     * @param packet the packet to send.
     * @param caller the caller to callback.
     * @return the number of bytes sent.
     * @throws IOException
     */
    public int sendAndWaitPacketTCP(final Packet packet, final ICaller caller) throws IOException {
        packets.put(packet.getPacketId(), caller);
        return sendPacketTCP(packet);
    }

    /**
     * Send a packet through the network (via UDP) and call the
     * {@link ICaller#responseReceived(Packet)} method when a response is
     * received by the server.
     *
     * @param packet the packet to send.
     * @param caller the caller to callback.
     * @return the number of bytes sent.
     * @throws IOException
     */
    public int sendAndWaitPacketUDP(final Packet packet, final ICaller caller) throws IOException {
        packets.put(packet.getPacketId(), caller);
        return sendPacketUDP(packet);
    }

    /**
     * Send the packet through the network to the server via TCP protocol.
     *
     * @param packet the packet to send
     * @return the number of bytes sent.
     */
    public int sendPacketTCP(final Packet packet) throws IOException {
        if (!isConnected()) {
            client.reconnect();
        }
        return client.sendTCP(packet);
    }

    /**
     * Send the packet through the network to the server via UDP protocol.
     *
     * @param packet the packet to send
     * @return the number of bytes sent.
     */
    public int sendPacketUDP(final Packet packet) throws IOException {
        if (!isConnected()) {
            client.reconnect();
        }
        return client.sendUDP(packet);
    }
}
