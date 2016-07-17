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
     * @param server
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

    protected abstract void execute();

    public void stop() {
        this.stop = true;
    }
}
