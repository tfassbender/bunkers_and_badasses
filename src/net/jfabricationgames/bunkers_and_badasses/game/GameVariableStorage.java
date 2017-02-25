package net.jfabricationgames.bunkers_and_badasses.game;

/**
 * Store and load variables that are dynamically loaded from the database.
 */
public class GameVariableStorage {
	
	private int skillPoints;
	private int gameTurns;
	private int maxHerosCards;
	private int startTroops;
	
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

	public GameVariableStorage() {
		creditsSkillLevel = new int[4];
		ammoSkillLevel = new int[4];
		eridiumSkillLevel = new int[4];
		creditsBuildingSkillLevel = new int[4];
		ammoBuildingSkillLevel = new int[4];
		eridiumBuildingSkillLevel = new int[4];
		pointsSkillLevel = new int[6];
		herosSkillLevel = new int[4];
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
}