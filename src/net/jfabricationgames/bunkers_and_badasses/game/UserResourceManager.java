package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserResourceManager {
	
	private Map<User, UserResource> resources;

	public Map<User, UserResource> getResources() {
		return resources;
	}
	public void setResources(Map<User, UserResource> resources) {
		this.resources = resources;
	}
}