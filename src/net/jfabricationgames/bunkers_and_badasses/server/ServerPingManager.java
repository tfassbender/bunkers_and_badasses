package net.jfabricationgames.bunkers_and_badasses.server;

import java.util.HashMap;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.server.JFGConnection;

public class ServerPingManager {
	
	public static final int TIMEOUT = 60000;//time till the user gets kicked in milliseconds
	public static final int SLEEP_TIME = 10000;//the sleep time for the thread
	
	private Map<User, Long> pings = new HashMap<User, Long>();
	private Thread pingThread;
	private User timedOutUser;
	private BunkersAndBadassesServer server;
	
	public ServerPingManager(BunkersAndBadassesServer server) {
		this.server = server;
		pingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						//check for the users last ping time
						if (!pings.isEmpty()) {
							timedOutUser = null;
							for (User user : pings.keySet()) {
								if (System.currentTimeMillis() - pings.get(user) > TIMEOUT) {
									//kick the user if he hasn't send a ping in <TIMEOUT> milliseconds
									timedOutUser = user;//kick the user after the loop to avoid concurrent modification
								}
							}
							if (timedOutUser != null) {
								kickUser(timedOutUser);
							}
						}
						Thread.sleep(SLEEP_TIME);
					}
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		});
		pingThread.start();
	}
	
	/**
	 * Kick a user from the logged in list if he doesn't ping for some time.
	 * 
	 * @param user
	 * 		The user to be logged out
	 */
	private void kickUser(User user) {
		JFGConnection conn = server.getUserMap().get(user);
		ServerLogoutMessage message = new ServerLogoutMessage("No Ping received for more than " + TIMEOUT + " ms. Logging you out.");
		conn.sendMessage(message);
		server.logout(conn);
	}
	
	public void ping(User user) {
		pings.put(user, System.currentTimeMillis());
	}
	
	public void addUser(User user) {
		ping(user);//does the same but the name is more clear
	}
	public void removeUser(User user) {
		pings.remove(user);
	}
}