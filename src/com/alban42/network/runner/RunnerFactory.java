package com.alban42.network.runner;

/**
 * This class is a factory for the creation of a new {@link ConnectionRunner}.
 * Used by the {@link com.alban42.network.server.listener.NetworkServerListener} when a client connect to the {@link com.alban42.network.server.NetworkServer}.
 */
@FunctionalInterface
public interface RunnerFactory {

    /**
     * Create a new instance of {@link ConnectionRunner}.
     * Called when the {@link com.alban42.network.server.NetworkServer} received a new connection.
     *
     * @param connectionID the id of the new connection to be created.
     * @return a new instance of {@link ConnectionRunner}.
     */
    ConnectionRunner newRunner(Integer connectionID);

}
