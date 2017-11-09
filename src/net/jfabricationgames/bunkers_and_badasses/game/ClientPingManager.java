package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.server.PingMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class ClientPingManager {
	
	public static final int PING_TIME = 30000;//the time between two pings in milliseconds
	
	private Thread pingThread;
	
	private PingMessage pingMessage;
	
	/**
	 * Create a new thread that sends ping messages the server.
	 * The thread runs till the main thread stops because it's a garbage collector root.
	 */
	public ClientPingManager(JFGClient client) {
		pingMessage = new PingMessage();
		pingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						client.sendMessageUnshared(pingMessage);
						Thread.sleep(PING_TIME);
					}
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}, "ping_thread");
	}
	
	/**
	 * Start the ping thread.
	 */
	public void start() {
		pingThread.start();
	}
}