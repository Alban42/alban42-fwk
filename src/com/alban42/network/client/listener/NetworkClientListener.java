package com.alban42.network.client.listener;

import com.alban42.network.client.NetworkClient;
import com.alban42.network.register.objects.ICaller;
import com.alban42.network.register.objects.packet.Packet;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * This class is the listener of {@link NetworkClient}. All the interactions
 * with the server will be catched by this class.
 *
 * @author Alban
 */
public abstract class NetworkClientListener extends Listener {

	private final NetworkClient network;

	/**
	 * Constructor.
	 *
	 * @param network
	 *            The parent of the listener.
	 */
	public NetworkClientListener(final NetworkClient network) {
		this.network = network;
	}

	/**
	 * If the packet expect a callback when the server respond, then the
	 * {@link ICaller#responseReceived(Packet)} method is called.
	 *
	 * @param response
	 *            the response packet.
	 */
	private void callback(final Packet response) {
		final ICaller caller = network.getPackets().get(response.getPacketId());
		if (caller != null) {
			network.getPackets().remove(response.getPacketId());
			caller.responseReceived(response);
		}
	}

	@Override
	public void connected(final Connection connection) {
		Log.info("Connected to the server !");
	}

	@Override
	public void disconnected(final Connection connection) {
		Log.info("Disconnected from the server !");
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof Packet) {
			final Packet response = (Packet) object;

			execute(connection, response);

			callback(response);
		}
	}

	/**
	 * When the client receive a {@link Packet} then this method is called.
	 *
	 * @param connection
	 *            the connection from whom the packet is sent
	 * @param response
	 *            the response packet.
	 */
	protected abstract void execute(Connection connection, Packet response);
}
