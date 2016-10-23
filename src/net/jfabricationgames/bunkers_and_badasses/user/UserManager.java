package net.jfabricationgames.bunkers_and_badasses.user;

import java.util.List;

public abstract class UserManager {
	
	private static List<User> users;
	
	public static List<User> getUsers() {
		return users;
	}
	public static void setUsers(List<User> users) {
		UserManager.users = users;
	}
}