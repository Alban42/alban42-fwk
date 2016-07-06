/**
 *
 */
package com.alban42.network.client.listener;

import java.sql.Connection;

import com.alban42.network.client.NetworkClient;
import com.alban42.network.register.objects.ICaller;
import com.alban42.network.register.objects.packet.Packet;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * This class is the listener of {@link NetworkClient}. All the interactions with the
 * server will be catched by this class.
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

	private void callback(final Packet response) {
		final ICaller caller = network.getPackets().get(response.getPacketId());
		if (caller != null) {
			network.getPackets().remove(response.getPacketId());
			caller.responseReceived(response);
		}
	}

	public void connected(final Connection arg0) {
		Log.info("Connected !");
		network.connected = true;
	}

	public void disconnected(final Connection arg0) {
		Log.info("Disconnected !");
		network.connected = false;
	}

	public void received(final Connection connection, final Object object) {
		if (object instanceof Packet) {
			final Packet response = (Packet) object;

			execute(object);

			callback(response);
		}
	}

	protected abstract void execute(Object objectReceived);
}
