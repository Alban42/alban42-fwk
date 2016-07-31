package com.alban42.network.runner;

import com.esotericsoftware.minlog.Log;

/**
 * The connection runner represent is the logic that will be executed when a client is connected to the server.
 * The {@link ConnectionRunner} start a loop when the client is connected until it's disconnected.
 * The {@link #execute()} method is called inside the loop.
 */
public abstract class ConnectionRunner implements Runnable {

    private final Integer connectionID;
    private boolean stop;

    /**
     * Constructor for a new Runner.
     *
     * @param connectionID the id of the runner (provided by the server).
     */
    public ConnectionRunner(Integer connectionID) {
        this.connectionID = connectionID;
    }

    @Override
    public void run() {
        Log.info("Thread started for connection " + connectionID);
        while (!this.stop) {
            // Call the execute method that will be implemented by the implementation of this abstract class
            execute();
        }
        Log.info("Thread stopped for connection " + connectionID);
    }

    /**
     * This method is called in a while loop (in a thread) while the the runner is not stopped (disconnected).
     */
    protected abstract void execute();

    /**
     * Stop the runner thread loop and call the {@link #disconnected()} method.
     */
    public void stop() {
        this.stop = true;
        disconnected();
    }

    /**
     * Called when the runner is disconnected from the server.
     */
    protected abstract void disconnected();
}
