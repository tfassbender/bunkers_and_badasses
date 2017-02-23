package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserResourceManager {
	
	private static int startingCredits;
	private static int startingAmmo;
	private static int startingEridium;
	
	private Map<User, UserResource> resources;
	
	public UserResourceManager(List<User> players) {
		for (User u : players) {
			resources.put(u, new UserResource());
		}
	}
	
	/**
	 * Collect the game start resources for a user.
	 * 
	 * @param user
	 * 		The user that collects the resources.
	 */
	public void collectStartingResources(User user) {
		UserResource resource = resources.get(user);
		resource.addCredits(startingCredits);
		resource.addAmmo(startingAmmo);
		resource.addEridium(startingEridium);
	}
	
	public Map<User, UserResource> getResources() {
		return resources;
	}
	public void setResources(Map<User, UserResource> resources) {
		this.resources = resources;
	}
	
	public static int getStartingCredits() {
		return startingCredits;
	}
	public static void setStartingCredits(int startingCredits) {
		UserResourceManager.startingCredits = startingCredits;
	}
	public static int getStartingAmmo() {
		return startingAmmo;
	}
	public static void setStartingAmmo(int startingAmmo) {
		UserResourceManager.startingAmmo = startingAmmo;
	}
	public static int getStartingEridium() {
		return startingEridium;
	}
	public static void setStartingEridium(int startingEridium) {
		UserResourceManager.startingEridium = startingEridium;
	}
}