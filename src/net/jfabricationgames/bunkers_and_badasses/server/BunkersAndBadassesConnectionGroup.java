package net.jfabricationgames.bunkers_and_badasses.server;

import net.jfabricationgames.jfgserver.server.DefaultJFGConnectionGroup;

public class BunkersAndBadassesConnectionGroup extends DefaultJFGConnectionGroup {
	
	@Override
	public void groupStarted() {
		//do nothing here
		//no need to send a message that confirms the group creation
	}
}