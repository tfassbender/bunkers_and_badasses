package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

@Deprecated
public class UserStatistics implements Serializable {
	
	private static final long serialVersionUID = 549893613891342818L;

	private int points;//the winning points at the end of the game
	
	private int troopsKilled;//the total amount of killed troops (also neutrals)
	private int troopsLost;//the total amount of lost troops
	
	private int fieldsMax;//the maximum number of fields the player held
	private int fieldsEnd;//the number of fields the player held at the end
	
	private int battleAttackWin;//won attacks
	private int battleAttackLoss;//lost attacks
	private int battleDefenseWin;//won defense
	private int battleDefenseLoss;//lost defense
	
	private int heroesUsed;//total amount of heros cards used
	
	private int miningEridium;//total amount of eridium received
	private int miningCredits;//total amount of credits received
	private int miningAmmo;//total amount of ammo received
	
	private int buildingsCreated;//total amount of buildings that were created or upgraded
	
	private int supportGiven;//number of times the player gave support to other players
	private int supportReceived;//number of times the player received support from other players
	
	private int commandsRaid;//total amount of raid commands the player gave (only the ones that were executed)
	private int commandsRetreat;//total amount of retreat commands the player gave (only the ones that were executed)
	private int commandsMarch;//total amount of march commands the player gave (only the ones that were executed)
	private int commandsBuild;//total amount of build commands the player gave (only the ones that were executed)
	private int commandsRecruit;//total amount of recruit commands the player gave (only the ones that were executed)
	private int commandsResouce;//total amount of resource commands the player gave (only the ones that were executed)
	private int commandsDefense;//total amount of defense commands the player gave
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getTroopsKilled() {
		return troopsKilled;
	}
	public void setTroopsKilled(int troopsKilled) {
		this.troopsKilled = troopsKilled;
	}
	
	public int getTroopsLost() {
		return troopsLost;
	}
	public void setTroopsLost(int troopsLost) {
		this.troopsLost = troopsLost;
	}
	
	public int getFieldsMax() {
		return fieldsMax;
	}
	public void setFieldsMax(int fieldsMax) {
		this.fieldsMax = fieldsMax;
	}
	
	public int getFieldsEnd() {
		return fieldsEnd;
	}
	public void setFieldsEnd(int fieldsEnd) {
		this.fieldsEnd = fieldsEnd;
	}
	
	public int getBattleAttackWin() {
		return battleAttackWin;
	}
	public void setBattleAttackWin(int battleAttackWin) {
		this.battleAttackWin = battleAttackWin;
	}
	
	public int getBattleAttackLoss() {
		return battleAttackLoss;
	}
	
	public void setBattleAttackLoss(int battleAttackLoss) {
		this.battleAttackLoss = battleAttackLoss;
	}
	public int getBattleDefenseWin() {
		return battleDefenseWin;
	}
	
	public void setBattleDefenseWin(int battleDefenseWin) {
		this.battleDefenseWin = battleDefenseWin;
	}
	
	public int getBattleDefenseLoss() {
		return battleDefenseLoss;
	}
	public void setBattleDefenseLoss(int battleDefenseLoss) {
		this.battleDefenseLoss = battleDefenseLoss;
	}
	
	public int getHeroesUsed() {
		return heroesUsed;
	}
	public void setHeroesUsed(int heroesUsed) {
		this.heroesUsed = heroesUsed;
	}
	
	public int getMiningEridium() {
		return miningEridium;
	}
	public void setMiningEridium(int miningEridium) {
		this.miningEridium = miningEridium;
	}
	
	public int getMiningCredits() {
		return miningCredits;
	}
	public void setMiningCredits(int miningCredits) {
		this.miningCredits = miningCredits;
	}
	
	public int getMiningAmmo() {
		return miningAmmo;
	}
	public void setMiningAmmo(int miningAmmo) {
		this.miningAmmo = miningAmmo;
	}
	
	public int getBuildingsCreated() {
		return buildingsCreated;
	}
	public void setBuildingsCreated(int buildingsCreated) {
		this.buildingsCreated = buildingsCreated;
	}
	
	public int getSupportGiven() {
		return supportGiven;
	}
	public void setSupportGiven(int supportGiven) {
		this.supportGiven = supportGiven;
	}
	
	public int getSupportReceived() {
		return supportReceived;
	}
	public void setSupportReceived(int supportReceived) {
		this.supportReceived = supportReceived;
	}
	
	public int getCommandsRaid() {
		return commandsRaid;
	}
	public void setCommandsRaid(int commandsRaid) {
		this.commandsRaid = commandsRaid;
	}
	
	public int getCommandsRetreat() {
		return commandsRetreat;
	}
	public void setCommandsRetreat(int commandsRetreat) {
		this.commandsRetreat = commandsRetreat;
	}
	
	public int getCommandsMarch() {
		return commandsMarch;
	}
	public void setCommandsMarch(int commandsMarch) {
		this.commandsMarch = commandsMarch;
	}
	
	public int getCommandsBuild() {
		return commandsBuild;
	}
	public void setCommandsBuild(int commandsBuild) {
		this.commandsBuild = commandsBuild;
	}
	
	public int getCommandsRecruit() {
		return commandsRecruit;
	}
	public void setCommandsRecruit(int commandsRecruit) {
		this.commandsRecruit = commandsRecruit;
	}
	
	public int getCommandsResouce() {
		return commandsResouce;
	}
	public void setCommandsResouce(int commandsResouce) {
		this.commandsResouce = commandsResouce;
	}
	
	public int getCommandsDefense() {
		return commandsDefense;
	}
	public void setCommandsDefense(int commandsDefense) {
		this.commandsDefense = commandsDefense;
	}
}