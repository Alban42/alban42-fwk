package com.alban42.network.register;

import com.esotericsoftware.minlog.Log;

public abstract class ConnectionRunner implements Runnable {
	
	public Integer connectionID;
	private boolean stop;
	
	@Override
    public void run() {
        Log.info("Thread started for player " + connectionID);
        while (!this.stop) {
        	
        	execute();
        	
        }
        Log.info("Thread stoped for player " + connectionID);
    }

    protected abstract void execute();

	public void stop() {
        this.stop = true;
    }
}
