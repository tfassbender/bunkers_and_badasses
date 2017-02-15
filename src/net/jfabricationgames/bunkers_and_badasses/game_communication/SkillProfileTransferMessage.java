package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfile;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

/**
 * Send a single skill profile to the server as an update or all skill profiles of a user to the client as answer to a request.
 */
public class SkillProfileTransferMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = -7001194875613444068L;
	
	private boolean request;
	
	private SkillProfile update;
	private SkillProfile[] profiles;
	
	public boolean isRequest() {
		return request;
	}
	public void setRequest(boolean request) {
		this.request = request;
	}
	
	public SkillProfile getUpdate() {
		return update;
	}
	public void setUpdate(SkillProfile update) {
		this.update = update;
	}
	
	public SkillProfile[] getProfiles() {
		return profiles;
	}
	public void setProfiles(SkillProfile[] profiles) {
		this.profiles = profiles;
	}
}