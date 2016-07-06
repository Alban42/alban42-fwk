package com.alban42.network.server;

import java.io.IOException;

import com.alban42.network.register.ClassRegister;
import com.alban42.network.server.listener.NetworkServerListener;
import com.alban42.network.server.logic.ServerLogic;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class NetworkServer {
	
	private static final String TAG = NetworkServerListener.class.getSimpleName();
	private static final int DEFAULT_TCP_PORT = 27960;
	private static final int DEFAULT_UDP_PORT = 27961;
	
	private final ServerLogic serverLogic;
	private final Thread serverLogicThread;
	private final Server server;

	/**
	 * Default constructor, use the DEFAULT_TCP_PORT and DEFAULT_UDP_PORT
	 * constants.
	 */
	public NetworkServer(ServerLogic serverLogic, NetworkServerListener listener) {
		this(DEFAULT_TCP_PORT, DEFAULT_UDP_PORT, serverLogic, listener);
	}

	public NetworkServer(final int tcpPort, final int udpPort, ServerLogic serverLogic, NetworkServerListener listener) {
		server = new Server();
		ClassRegister.register(server);
		server.addListener(listener);
		listener.server = server;
		try {
			server.bind(tcpPort, udpPort);
		} catch (final IOException e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}
		
		this.serverLogic = serverLogic;
		serverLogicThread = new Thread(serverLogic);
	}

	public void start() {
		server.start();
		Log.info("[" + TAG + "] Server Started !");
		serverLogicThread.start();
		Log.info("[" + serverLogic.getClass().getSimpleName() + "] Started !");
	}

	public void stop() {
		server.stop();
		Log.info("[" + TAG + "] Server Stoped !");
		serverLogic.stop();
		Log.info("[" + serverLogic.getClass().getSimpleName() + "] Stoped !");
	}
}
