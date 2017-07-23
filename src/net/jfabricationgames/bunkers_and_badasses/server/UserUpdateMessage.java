package net.jfabricationgames.bunkers_and_badasses.server;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_communication.BunkersAndBadassesMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserUpdateMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = -6984320232005291887L;
	
	private List<User> users;
	
	public UserUpdateMessage(List<User> users) {
		this.users = users;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}