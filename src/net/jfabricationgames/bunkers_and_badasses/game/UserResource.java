package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.IOException;
import java.io.Serializable;

import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.BuildingStorage;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Troop;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.TroopStorage;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_command.CommandStorage;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.logger.JFGLogger;
import net.jfabricationgames.logger.JFGLoggerManager;

public class UserResource implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 425801597894620740L;
	
	//the current amounts of credits, ammo and eridium
	private int credits;
	private int ammo;
	private int eridium;
	
	private static JFGLogger resourceLogger;
	
	static {
		try {
			resourceLogger = new JFGLogger("bunkers_and_badasses_resource_log", 1000);
			JFGLoggerManager.addLogger(resourceLogger);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private User user;
	
	//the amounts of resources added to every buildings collections from the skill profiles
	//building skill variables are selected from the skill profile manager
	/*private int eridiumBuilding;
	private int ammoBuilding;
	private int creditsBuilding;*/
	
	public UserResource(User user) {
		this.user = user;
	}
	
	public UserResource clone() {
		UserResource clone = new UserResource(user);
		clone.credits = credits;
		clone.ammo = ammo;
		clone.eridium = eridium;
		return clone;
	}
	
	@Override
	public String toString() {
		return "UserResource (Credits: " + credits + "; Ammo: " + ammo + "; Eridium: " + eridium + ")";
	}
	
	public static int getCreditsForCommand(Command command, Field field) {
		int costs = 0;
		if (command.isCostDependencyCredits()) {
			for (Troop troop : field.getTroops()) {
				costs += troop.getBaseCostsCredits();
			}
			costs += command.getCostsCredits() * Math.pow(field.getTroops().size(), 2);
		}
		else {
			costs += command.getCostsCredits();
		}
		return costs;
	}
	public static  int getAmmoForCommand(Command command, Field field) {
		int costs = 0;
		if (command.isCostDependencyAmmo()) {
			for (Troop troop : field.getTroops()) {
				costs += troop.getBaseCostsAmmo();
			}
			costs += command.getCostsAmmo() * Math.pow(field.getTroops().size(), 2);
		}
		else {
			costs = command.getCostsAmmo();
		}
		return costs;
	}
	
	/**
	 * Collect the resources for a user for the game start.
	 */
	public void collectGameStartResources() {
		credits += UserResourceManager.getStartingCredits();
		ammo += UserResourceManager.getStartingAmmo();
		eridium += UserResourceManager.getStartingEridium();
		resourceLogger.addLog("user [" + user + "]: game start resources collected [" + UserResourceManager.getStartingCredits() + " credits " + 
				UserResourceManager.getStartingAmmo() + " ammo " + UserResourceManager.getStartingEridium() + "eririum]. Current resources [" + 
				credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	/**
	 * Collect the users skill resources for the game start.
	 * 
	 * @param skill
	 * 		The users skill profile.
	 */
	public void collectSkillResources(SkillProfile skill) {
		credits += SkillProfileManager.CREDITS_SKILL_LEVEL[skill.getCredits()];
		ammo += SkillProfileManager.AMMO_SKILL_LEVEL[skill.getAmmo()];
		eridium += SkillProfileManager.ERIDIUM_SKILL_LEVEL[skill.getEridium()];
		resourceLogger.addLog("user [" + user + "]: skill resources collected [" + SkillProfileManager.CREDITS_SKILL_LEVEL[skill.getCredits()] + 
				" credits " + SkillProfileManager.AMMO_SKILL_LEVEL[skill.getAmmo()] + " ammo " + SkillProfileManager.ERIDIUM_SKILL_LEVEL[skill.getEridium()] + 
				"eririum]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	/**
	 * Collect all resources for the turn start (default, buildings, skill, turn bonus).
	 */
	public void collectTurnStartResources(Game game, User user) {
		int[] turnStartResources = calculateTurnStartResources(game, user);
		credits += turnStartResources[0];
		ammo += turnStartResources[1];
		eridium += turnStartResources[2];
		resourceLogger.addLog("user [" + user + "]: turn start resources collected [" + turnStartResources[0] + " credits " + turnStartResources[1] + 
				" ammo " + turnStartResources[2] + "eririum]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	public int[] calculateTurnStartResources(Game game, User user) {
		int credits = Game.getGameVariableStorage().getTurnStartCredits();
		int ammo = Game.getGameVariableStorage().getTurnStartAmmo();
		int eridium = Game.getGameVariableStorage().getTurnStartEridium();
		//buildings, skills
		SkillProfile skill = game.getSkillProfileManager().getSelectedProfile(user);
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(user)) {
				if (field.getBuilding().getCreditMining() > 0) {
					credits += field.getBuilding().getCreditMining();
					credits += SkillProfileManager.CREDITS_BUILDING_SKILL_LEVEL[skill.getCreditsBuilding()];
				}
				if (field.getBuilding().getAmmoMining() > 0) {
					ammo += field.getBuilding().getAmmoMining();
					ammo += SkillProfileManager.AMMO_BUILDING_SKILL_LEVEL[skill.getAmmoBuilding()];
				}
				if (field.getBuilding().getEridiumMining() > 0) {
					eridium += field.getBuilding().getEridiumMining();
					eridium += SkillProfileManager.ERIDIUM_BUILDING_SKILL_LEVEL[skill.getEridiumBuilding()];
				}
			}
		}
		//turn bonuses
		/*TurnBonus bonus = game.getGameTurnBonusManager().getUsersBonus(game.getLocalUser());
		if (bonus != null) {
			credits += bonus.getCredits();
			ammo += bonus.getAmmo();
			eridium += bonus.getEridium();			
		}*/
		return new int[] {credits, ammo, eridium};
	}
	
	public void collectTurnBonusResources(TurnBonus turnBonus) {
		credits += turnBonus.getCredits();
		ammo += turnBonus.getAmmo();
		eridium += turnBonus.getEridium();
		resourceLogger.addLog("user [" + user + "]: turn bonus resources collected [" + turnBonus.getCredits() + " credits " + turnBonus.getAmmo() + 
				" ammo " + turnBonus.getEridium() + " eririum]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	public void collectCommandResources(int type) {
		int[] resources = Command.getStorage().getResourceReception();
		switch (type) {
			case 1:
				credits += resources[CommandStorage.CREDITS];
				resourceLogger.addLog("user [" + user + "]: command resources collected [" + resources[CommandStorage.CREDITS] + " credits]. Current resources [" + 
						credits + " credits " + ammo + " ammo " + eridium + " eririum]");
				break;
			case 2:
				ammo += resources[CommandStorage.AMMO];
				resourceLogger.addLog("user [" + user + "]: command resources collected [" + resources[CommandStorage.AMMO] + " ammo]. Current resources [" + 
						credits + " credits " + ammo + " ammo " + eridium + " eririum]");
				break;
			case 3:
				eridium += resources[CommandStorage.ERIDIUM];
				resourceLogger.addLog("user [" + user + "]: command resources collected [" + resources[CommandStorage.ERIDIUM] + " eririum]. Current resources [" + 
						credits + " credits " + ammo + " ammo " + eridium + " eririum]");
				break;
		}
	}
	
	/**
	 * Pay for a command that is added to the field.
	 * This method is directly called from the UserPlanManager and is not implemented in the UserResourceManager.
	 * 
	 * @param command
	 * 		The command the is placed on the field.
	 * 
	 * @param field
	 * 		The field on which the command is placed.
	 * 
	 * @throws ResourceException
	 * 		A ResourceException is thrown if there are not enough resources left to pay for the command.
	 */
	public void payCommand(Command command, Field field) throws ResourceException {
		int commandCredits = getCreditsForCommand(command, field);
		int commandAmmo = getAmmoForCommand(command, field);
		if (commandCredits > credits || commandAmmo > ammo) {
			throw new ResourceException("Not enough credits or ammo left to pay this command.");
		}
		credits -= commandCredits;
		ammo -= commandAmmo;
		resourceLogger.addLog("user [" + user + "]: command costs payed [" + commandCredits + " credits " + commandAmmo + " ammo " + 
				"]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	/**
	 * Get back the resources for  a command that was removed from the field in the turn planing phase.
	 * This method is directly called from the UserPlanManager and is not implemented in the UserResourceManager.
	 * 
	 * @param command
	 * 		The removed command.
	 * 
	 * @param field
	 * 		The field on which the command is placed.
	 */
	public void payBackCommand(Command command, Field field) {
		int commandCredits = getCreditsForCommand(command, field);
		int commandAmmo = getAmmoForCommand(command, field);
		credits += commandCredits;
		ammo += commandAmmo;
		resourceLogger.addLog("user [" + user + "]: command payback collected [" + commandCredits + " credits " + commandAmmo + " ammo " + 
				"]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	public void payBuilding(Building building) throws ResourceException {
		int[] costs = building.getBuildingPrice();
		payBuildingCosts(costs);
	}
	public void payBuildingUpgrade(Building building) throws ResourceException {
		int[] costs = building.getExtensionPrice();
		payBuildingCosts(costs);
	}
	private void payBuildingCosts(int[] costs) throws ResourceException {
		if (costs[BuildingStorage.PRICE_CREDITS] > credits || costs[BuildingStorage.PRICE_AMMO] > ammo || costs[BuildingStorage.PRICE_ERIDIUM] > eridium) {
			throw new ResourceException("Not enough resources to pay this building.");
		}
		else {
			credits -= costs[BuildingStorage.PRICE_CREDITS];
			ammo -= costs[BuildingStorage.PRICE_AMMO];
			eridium -= costs[BuildingStorage.PRICE_ERIDIUM];
			resourceLogger.addLog("user [" + user + "]: building costs payed [" + costs[BuildingStorage.PRICE_CREDITS] + " credits " + 
					costs[BuildingStorage.PRICE_AMMO] + " ammo " + costs[BuildingStorage.PRICE_ERIDIUM] + " eririum]. Current resources [" + 
					credits + " credits " + ammo + " ammo " + eridium + " eririum]");
		}
	}
	
	public static int[] getRecroutedTroopCosts(int normal, int badass, int upgrades) {
		int[] costs = new int[3];
		costs[0] += (normal + upgrades) * Troop.getStorage().getTroopCosts()[TroopStorage.NORMAL_TROOP][TroopStorage.RECRUIT_COSTS_CREDITS];
		costs[0] += badass * Troop.getStorage().getTroopCosts()[TroopStorage.BADASS_TROOP][TroopStorage.RECRUIT_COSTS_CREDITS];
		costs[1] += (normal + upgrades) * Troop.getStorage().getTroopCosts()[TroopStorage.NORMAL_TROOP][TroopStorage.RECRUIT_COSTS_AMMO];
		costs[1] += badass * Troop.getStorage().getTroopCosts()[TroopStorage.BADASS_TROOP][TroopStorage.RECRUIT_COSTS_AMMO];
		costs[2] += (normal + upgrades) * Troop.getStorage().getTroopCosts()[TroopStorage.NORMAL_TROOP][TroopStorage.RECRUIT_COSTS_ERIDIUM];
		costs[2] += badass * Troop.getStorage().getTroopCosts()[TroopStorage.BADASS_TROOP][TroopStorage.RECRUIT_COSTS_ERIDIUM];
		return costs;
	}
	public void payRecroutedTroops(int normal, int badass, int upgrades) throws ResourceException {
		int[] costs = getRecroutedTroopCosts(normal, badass, upgrades);
		pay(costs);
		resourceLogger.addLog("user [" + user + "]: troop recruiting costs payed [" + costs[0] + " credits " + costs[1] + " ammo " + costs[2] + 
				" eririum]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	public void payHeroCards(int cards) throws ResourceException {
		pay(new int[] {0, 0, Game.getGameVariableStorage().getHeroCardCosts()*cards});
		resourceLogger.addLog("user [" + user + "]: hero card costs payed [" + Game.getGameVariableStorage().getHeroCardCosts()*cards + 
				" eririum]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	public void payAdditionalCommand() throws ResourceException {
		pay(new int[] {0, 0, Game.getGameVariableStorage().getAdditionalCommandCosts()});
		resourceLogger.addLog("user [" + user + "]: additional command costs payed [" + Game.getGameVariableStorage().getAdditionalCommandCosts() + 
				" eririum]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}

	private void pay(int[] costs) {
		if (costs[0] > credits || costs[1] > ammo || costs[2] > eridium) {
			throw new ResourceException("Not enough resources.", "Du hast nicht genug Resourcen.\n\nAnschreiben lassen geht hier nicht.");
		}
		else {
			credits -= costs[0];
			ammo -= costs[1];
			eridium -= costs[2];
		}
	}
	
	/**
	 * Pay for the fields a player holds at the beginning of the turn.
	 * No exception is thrown because nothing happens if there are not enough resources left.
	 */
	public void payFields(int fields) {
		credits -= fields * Game.getGameVariableStorage().getFieldCosts();
		credits = Math.max(credits, 0);
		resourceLogger.addLog("user [" + user + "]: field costs payed [" + fields * Game.getGameVariableStorage().getFieldCosts() + 
				" credits]. Current resources [" + credits + " credits " + ammo + " ammo " + eridium + " eririum]");
	}
	
	public int getCredits() {
		return credits;
	}
	protected void setCredits(int credits) {
		this.credits = credits;
	}
	/*public void addCredits(int credits) {
		this.credits += credits;
	}*/
	
	public int getAmmo() {
		return ammo;
	}
	protected void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	/*public void addAmmo(int ammo) {
		this.ammo += ammo;
	}*/
	
	public int getEridium() {
		return eridium;
	}
	protected void setEridium(int eridium) {
		this.eridium = eridium;
	}
	/*public void addEridium(int eridium) {
		this.eridium += eridium;
	}*/
	
	/*public int getEridiumBuilding() {
		return eridiumBuilding;
	}
	public void setEridiumBuilding(int eridiumBuilding) {
		this.eridiumBuilding = eridiumBuilding;
	}
	
	public int getAmmoBuilding() {
		return ammoBuilding;
	}
	public void setAmmoBuilding(int ammoBuilding) {
		this.ammoBuilding = ammoBuilding;
	}
	
	public int getCreditsBuilding() {
		return creditsBuilding;
	}
	public void setCreditsBuilding(int creditsBuilding) {
		this.creditsBuilding = creditsBuilding;
	}*/
}