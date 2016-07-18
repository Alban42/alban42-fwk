package com.alban42.network.runner;

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
