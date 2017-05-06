package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserResourceManager {
	
	private static int startingCredits;
	private static int startingAmmo;
	private static int startingEridium;
	
	private Game game;
	
	private Map<User, UserResource> resources;
	private Map<User, Map<Integer, UserResource>> resourceUse;//store the resources the used used in every turn
	
	public UserResourceManager() {
		startingCredits = Game.getGameVariableStorage().getStartCredits();
		startingAmmo = Game.getGameVariableStorage().getStartAmmo();
		startingEridium = Game.getGameVariableStorage().getStartEridium();
	}
	
	public UserResourceManager(List<User> players, Game game) {
		this.game = game;
		resources = new HashMap<User, UserResource>();
		resourceUse = new HashMap<User, Map<Integer, UserResource>>();
		for (User u : players) {
			resources.put(u, new UserResource());
			resourceUse.put(u, new HashMap<Integer, UserResource>());
		}
	}
	
	/**
	 * Receive changes from the plan manager that apply the changes of other users planing phases.
	 * 
	 * @param newResources
	 * 		The new resources of all users.
	 */
	public void receiveChanges(Map<User, UserResource> newResources) {
		for (User user : newResources.keySet()) {
			resources.put(user, newResources.get(user));
		}
	}
	
	/**
	 * Collect the game start resources for a user.
	 * 
	 * @param user
	 * 		The user that collects the resources.
	 */
	public void collectGameStartResources(User user) {
		resources.get(user).collectGameStartResources();
	}
	/**
	 * Collect all resources for the turn start (default, buildings, skill, turn bonus).
	 */
	public void collectTurnStartResources() {
		for (User user : game.getPlayers()) {
			resources.get(user).collectTurnStartResources(game);
		}
	}
	/**
	 * Pay all fixed costs like field costs
	 */
	public void payFixCosts() {
		int fields;
		for (User user : game.getPlayers()) {
			fields = game.getBoard().getUsersFields(user).size();
			resources.get(user).payFields(fields);
		}
	}
	/**
	 * Collect the resources for a collect command.
	 * 
	 * @param user
	 * 		The user that executed the command.
	 */
	public void collectCommandResources(User user) {
		resources.get(user).collectCommandResources();
	}
	
	public void payBuilding(Building building, User user) throws ResourceException {
		resources.get(user).payBuilding(building);
	}
	public void payBuildingUpgrade(Building building, User user) throws ResourceException {
		resources.get(user).payBuildingUpgrade(building);
	}
	
	public void payRecroutedTroops(int normal, int badass, int upgrades, User user) throws ResourceException {
		resources.get(user).payRecroutedTroops(normal, badass, upgrades);
	}
	
	public void payHeroCards(int cards, User user) throws ResourceException {
		resources.get(user).payHeroCards(cards);
	}
	
	public void payAdditionalCommand(User user) throws ResourceException {
		resources.get(user).payAdditionalCommand();
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