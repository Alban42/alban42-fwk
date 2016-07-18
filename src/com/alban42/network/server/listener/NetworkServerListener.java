package com.alban42.network.server.listener;

import com.alban42.network.register.objects.packet.Packet;
import com.alban42.network.runner.ConnectionRunner;
import com.alban42.network.runner.RunnerFactory;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.util.HashMap;
import java.util.Map;

public abstract class NetworkServerListener extends Listener {

    private static final String TAG = NetworkServerListener.class.getSimpleName();
    private final Map<Integer, ConnectionRunner> activeConnections;
    public Server server;
    private RunnerFactory factory;

    /**
     * Constructor.
     *
     * @param factory the factory for creating {@link ConnectionRunner}.
     */
    public NetworkServerListener(RunnerFactory factory) {
        super();
        this.activeConnections = new HashMap<>();
        this.factory = factory;
    }

    public Map<Integer, ConnectionRunner> getActiveConnections() {
        return activeConnections;
    }

    @Override
    public void connected(final Connection connection) {
        Log.info(NetworkServerListener.TAG, "New client connected ! " + connection.getID());
        final ConnectionRunner runner = factory.newRunner(connection.getID());
        this.activeConnections.put(connection.getID(), runner);
        new Thread(runner).start();
        super.connected(connection);
    }

    @Override
    public void disconnected(final Connection connection) {
        final int connectionId = connection.getID();
        Log.info(NetworkServerListener.TAG, "Client disconnected ! " + connectionId);
        final ConnectionRunner runner = this.activeConnections.get(connectionId);
        if (runner != null) {
            // stop the runner
            runner.stop();
            // remove the runner from the list of runners
            this.activeConnections.remove(connectionId);
        }
        super.disconnected(connection);
    }

    @Override
    public void received(final Connection connection, final Object object) {
        Log.debug(NetworkServerListener.TAG, "Packet received");
        if (object instanceof Packet) {
            final Packet packet = (Packet) object;

            execute(connection, packet);
        }
    }

    /**
     * When the server receive a {@link Packet} then this method is called.
     *
     * @param connection the connection from whom the packet is sent
     * @param packet     the packet.
     */
    protected abstract void execute(Connection connection, Packet packet);
}
