package com.alban42.network.server;

import com.alban42.network.register.ClassRegister;
import com.alban42.network.server.listener.NetworkServerListener;
import com.alban42.network.server.logic.ServerLogic;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import javax.annotation.Nullable;
import java.io.IOException;

public class NetworkServer {

	public static final int DEFAULT_TCP_PORT = 27960;
	public static final int DEFAULT_UDP_PORT = 27961;
	private static final String TAG = NetworkServerListener.class.getSimpleName();
	private final ServerLogic serverLogic;
	private final Thread serverLogicThread;
	private final Server server;

	/**
	 * Default constructor, uses the DEFAULT_TCP_PORT and DEFAULT_UDP_PORT
	 * constants.
	 */
	public NetworkServer(ServerLogic serverLogic, NetworkServerListener listener, ClassRegister classRegister) {
		this(DEFAULT_TCP_PORT, DEFAULT_UDP_PORT, serverLogic, listener, classRegister);
	}

	public NetworkServer(NetworkServerListener listener, ClassRegister classRegister) {
		this(DEFAULT_TCP_PORT, DEFAULT_UDP_PORT, listener, classRegister);
	}

	public NetworkServer(final int tcpPort, NetworkServerListener listener, ClassRegister classRegister) {
		this(tcpPort, DEFAULT_UDP_PORT, null, listener, classRegister);
	}

	public NetworkServer(final int tcpPort, final Integer udpPort, NetworkServerListener listener,
			ClassRegister classRegister) {
		this(tcpPort, udpPort, null, listener, classRegister);
	}

	/**
	 * Constructor of a new {@link NetworkServer}.
	 *
	 * @param tcpPort
	 *            the TCP port that will be used for TCP communication.
	 * @param udpPort
	 *            the UDP port that will be used for UDP communication.
	 * @param serverLogic
	 *            the server logic, can be null.
	 * @param listener
	 *            the listener of the server
	 * @param classRegister
	 *            le class register.
	 */
	public NetworkServer(final int tcpPort, final Integer udpPort, @Nullable ServerLogic serverLogic,
			NetworkServerListener listener, ClassRegister classRegister) {
		server = new Server();
		classRegister.register(server);
		server.addListener(listener);
		listener.server = server;
		try {
			server.bind(tcpPort, udpPort);
		} catch (final IOException e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}

		if (serverLogic != null) {
			this.serverLogic = serverLogic;
			serverLogicThread = new Thread(serverLogic);
		} else {
			this.serverLogic = null;
			this.serverLogicThread = null;
		}
	}

	/**
	 * Start the server and the serverLogic in a new thread (if not null)
	 */
	public void start() {
		server.start();
		Log.info("[" + TAG + "] Server Started !");
		if (serverLogicThread != null && serverLogic != null) {
			serverLogicThread.start();
			Log.info("[" + serverLogic.getClass().getSimpleName() + "] Started !");
		}
	}

	/**
	 * Stop the server and the serverLogic (if not null)
	 */
	public void stop() {
		server.stop();
		Log.info("[" + TAG + "] Server Stopped !");
		if (serverLogic != null) {
			serverLogic.stop();
			Log.info("[" + serverLogic.getClass().getSimpleName() + "] Stoped !");
		}
	}
}
