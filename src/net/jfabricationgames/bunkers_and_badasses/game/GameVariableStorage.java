package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

/**
 * Store and load variables that are dynamically loaded from the database.
 */
public class GameVariableStorage implements Serializable {
	
	private static final long serialVersionUID = -8252916093434896527L;
	
	private int skillPoints;
	private int gameTurns;
	private int maxHerosCards;
	private int startTroops;
	private int heroCardCosts;
	private int additionalCommandCosts;
	private int fieldCosts;
	
	private int[] userCommands;
	
	private int[] creditsSkillLevel;
	private int[] ammoSkillLevel;
	private int[] eridiumSkillLevel;
	
	private int[] creditsBuildingSkillLevel;
	private int[] ammoBuildingSkillLevel;
	private int[] eridiumBuildingSkillLevel;
	
	private int[] pointsSkillLevel;
	private int[] herosSkillLevel;
	
	private int startCredits;
	private int startAmmo;
	private int startEridium;
	
	private int turnStartCredits;
	private int turnStartAmmo;
	private int turnStartEridium;
	
	private int fieldPoints;
	private int fieldPointCount;
	private int fightAttackerPoints;
	private int fightWinnerPoints;
	private int fightSupporterPoints;
	private int fieldConquerPoints;

	public GameVariableStorage() {
		creditsSkillLevel = new int[6];
		ammoSkillLevel = new int[6];
		eridiumSkillLevel = new int[6];
		creditsBuildingSkillLevel = new int[6];
		ammoBuildingSkillLevel = new int[6];
		eridiumBuildingSkillLevel = new int[6];
		pointsSkillLevel = new int[6];
		herosSkillLevel = new int[6];
		userCommands = new int[8];
	}
	
	public int[] getCreditsSkillLevel() {
		return creditsSkillLevel;
	}
	public int[] getAmmoSkillLevel() {
		return ammoSkillLevel;
	}
	public int[] getEridiumSkillLevel() {
		return eridiumSkillLevel;
	}
	public int[] getCreditsBuildingSkillLevel() {
		return creditsBuildingSkillLevel;
	}
	public int[] getAmmoBuildingSkillLevel() {
		return ammoBuildingSkillLevel;
	}
	public int[] getEridiumBuildingSkillLevel() {
		return eridiumBuildingSkillLevel;
	}
	public int[] getPointsSkillLevel() {
		return pointsSkillLevel;
	}
	public int[] getHerosSkillLevel() {
		return herosSkillLevel;
	}
	
	public int[] getUserCommands() {
		return userCommands;
	}
	
	public int getStartCredits() {
		return startCredits;
	}
	public void setStartCredits(int startCredits) {
		this.startCredits = startCredits;
	}
	
	public int getStartAmmo() {
		return startAmmo;
	}
	public void setStartAmmo(int startAmmo) {
		this.startAmmo = startAmmo;
	}
	
	public int getStartEridium() {
		return startEridium;
	}
	public void setStartEridium(int startEridium) {
		this.startEridium = startEridium;
	}
	
	public int getTurnStartCredits() {
		return turnStartCredits;
	}
	public void setTurnStartCredits(int turnStartCredits) {
		this.turnStartCredits = turnStartCredits;
	}
	
	public int getTurnStartAmmo() {
		return turnStartAmmo;
	}
	public void setTurnStartAmmo(int turnStartAmmo) {
		this.turnStartAmmo = turnStartAmmo;
	}
	
	public int getTurnStartEridium() {
		return turnStartEridium;
	}
	public void setTurnStartEridium(int turnStartEridium) {
		this.turnStartEridium = turnStartEridium;
	}
	
	public int getSkillPoints() {
		return skillPoints;
	}
	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}
	
	public int getGameTurns() {
		return gameTurns;
	}
	public void setGameTurns(int gameTurns) {
		this.gameTurns = gameTurns;
	}
	
	public int getMaxHerosCards() {
		return maxHerosCards;
	}
	public void setMaxHerosCards(int maxHerosCards) {
		this.maxHerosCards = maxHerosCards;
	}
	
	public int getStartTroops() {
		return startTroops;
	}
	public void setStartTroops(int startTroops) {
		this.startTroops = startTroops;
	}
	
	public int getHeroCardCosts() {
		return heroCardCosts;
	}
	public void setHeroCardCosts(int heroCardCosts) {
		this.heroCardCosts = heroCardCosts;
	}
	
	public int getAdditionalCommandCosts() {
		return additionalCommandCosts;
	}
	public void setAdditionalCommandCosts(int additionalCommandCosts) {
		this.additionalCommandCosts = additionalCommandCosts;
	}
	
	public int getFieldCosts() {
		return fieldCosts;
	}
	public void setFieldCosts(int fieldCosts) {
		this.fieldCosts = fieldCosts;
	}
	
	public int getFieldPoints() {
		return fieldPoints;
	}
	public void setFieldPoints(int fieldPoints) {
		this.fieldPoints = fieldPoints;
	}
	
	public int getFieldPointCount() {
		return fieldPointCount;
	}
	public void setFieldPointCount(int fieldPointCount) {
		this.fieldPointCount = fieldPointCount;
	}
	
	public int getFightAttackerPoints() {
		return fightAttackerPoints;
	}
	public void setFightAttackerPoints(int fightAttackerPoints) {
		this.fightAttackerPoints = fightAttackerPoints;
	}
	
	public int getFightWinnerPoints() {
		return fightWinnerPoints;
	}
	public void setFightWinnerPoints(int fightWinnerPoints) {
		this.fightWinnerPoints = fightWinnerPoints;
	}
	
	public int getFightSupporterPoints() {
		return fightSupporterPoints;
	}
	public void setFightSupporterPoints(int fightSupporterPoints) {
		this.fightSupporterPoints = fightSupporterPoints;
	}
	
	public int getFieldConquerPoints() {
		return fieldConquerPoints;
	}
	public void setFieldConquerPoints(int fieldConquerPoints) {
		this.fieldConquerPoints = fieldConquerPoints;
	}
}