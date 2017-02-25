package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserResourceManager {
	
	private static int startingCredits;
	private static int startingAmmo;
	private static int startingEridium;
	
	private Map<User, UserResource> resources;
	private Map<User, Map<Integer, UserResource>> resourceUse;//store the resources the used used in every turn
	
	public UserResourceManager() {
		startingCredits = Game.getGameVariableStorage().getStartCredits();
		startingAmmo = Game.getGameVariableStorage().getStartAmmo();
		startingEridium = Game.getGameVariableStorage().getStartEridium();
	}
	
	public UserResourceManager(List<User> players) {
		resources = new HashMap<User, UserResource>();
		resourceUse = new HashMap<User, Map<Integer, UserResource>>();
		for (User u : players) {
			resources.put(u, new UserResource());
			resourceUse.put(u, new HashMap<Integer, UserResource>());
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
	public Map<User, Map<Integer, UserResource>> getResourceUse() {
		return resourceUse;
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