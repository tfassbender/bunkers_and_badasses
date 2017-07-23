package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BunkersAndBadassesMessage;

/**
 * A message without a content. It's only used to ping the server to show the user is still online and his connection is ok.
 */
public class PingMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = 356519488031061112L;
	
}