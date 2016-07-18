/**
 *
 */
package com.alban42.network.server.logic;

import com.esotericsoftware.kryonet.Server;

/**
 * @author Alban
 */
public abstract class ServerLogic implements Runnable {

    protected final Server server;
    private boolean stop;

    /**
     * Constructor.
     *
     * @param server the server associated to the {@link ServerLogic}.
     */
    public ServerLogic(final Server server) {
        this.server = server;
        this.stop = false;
    }

    @Override
    public void run() {
        while (!this.stop) {
            execute();
        }
    }

    /**
     * Method called into the while loop of the thread (loop until {@link #stop()} is called).
     */
    protected abstract void execute();

    /**
     * Stop the thread loop.
     */
    public void stop() {
        this.stop = true;
    }
}
