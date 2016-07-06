package com.alban42.network.server.listener;

import java.util.HashMap;
import java.util.Map;

import com.alban42.network.register.ConnectionRunner;
import com.alban42.network.register.RunnerFactory;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public abstract class NetworkServerListener extends Listener {

    private static final String TAG = NetworkServerListener.class.getSimpleName();
    private final Map<Integer, ConnectionRunner> connections;
    public Server server;
    private RunnerFactory factory;

    /**
     * @param server
     */
    public NetworkServerListener(RunnerFactory factory) {
        super();
        this.connections = new HashMap<>();
    }

    @Override
    public void connected(final Connection connection) {
        Log.info(NetworkServerListener.TAG, "New client connected ! " + connection.getID());
        final ConnectionRunner runner = factory.newRunner();
		this.connections.put(connection.getID(), runner);
        new Thread(runner).start();
        super.connected(connection);
    }

    @Override
    public void disconnected(final Connection connection) {
        final int connectionId = connection.getID();
        Log.info(NetworkServerListener.TAG, "Client disconnected ! " + connectionId);
        final ConnectionRunner runner = this.connections.get(connectionId);
        if (runner != null) {
            // stop the runner thread
        	runner.stop();
            // remove the runner from the list of runners
            this.connections.remove(connectionId);
        }
        super.disconnected(connection);
    }

    @Override
    public void received(final Connection connection, final Object object) {
        Log.debug(NetworkServerListener.TAG, "Packet received");

    }
}
