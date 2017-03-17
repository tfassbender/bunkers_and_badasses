package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class FightManager {
	
	private Map<Integer, List<Fight>> fights;//executed fights sorted by the game turns
	
	private Fight currentFight;
	
	private User localPlayer;
	
	private JFGClient client;
	
	public FightManager(JFGClient client, User localPlayer) {
		this.client = client;
		this.localPlayer = localPlayer;
	}
	
	/**
	 * Start a new fight and inform the other players.
	 * 
	 * @param startField
	 * 		The field from which the fight was stared.
	 * 
	 * @param targetField
	 * 		The field that is fought for.
	 * 
	 * @param normalTroops
	 * 		The normal troops the attacking player wants to use.
	 * 
	 * @param badassTroops
	 * 		The badass troops the attacking player wants to use.
	 */
	public void startFight(Field startField, Field targetField, int normalTroops, int badassTroops) {
		currentFight = new Fight();
		currentFight.setAttackingField(startField);
		currentFight.setDefendingField(targetField);
		currentFight.setAttackingNormalTroops(normalTroops);
		currentFight.setAttackingBadassTroops(badassTroops);
		//TODO send the fight object to the other players
	}
	
	/**
	 * Send an update to the (fight-)starting player or the other player if the local player is the starter.
	 */
	public void sendUpdate() {
		//TODO
	}
	
	/**
	 * Receive a fight object from the server.
	 * The (fight-)starting player merges the new information and sends the new fight object to all other players.
	 * The other players just receive the fight object and use the new one instead of their old one.
	 * 
	 * @param fight
	 * 		The fight object sent from the server.
	 */
	public void receiveFight(Fight fight) {
		if (fight.getAttackingPlayer().equals(localPlayer)) {
			mergeFight(fight);
		}
		else {
			overrideFight(fight);
		}
	}
	/**
	 * Merge the new information about the fight to the current fight object.
	 * 
	 * @param fight
	 * 		The fight object that is to be merged with the current fight.
	 */
	private void mergeFight(Fight fight) {
		//merge the supports and add the strength
		if (fight.getAttackSupporters().size() > currentFight.getAttackSupporters().size()) {
			currentFight.setAttackSupporters(fight.getAttackSupporters());
			currentFight.calculateCurrentStrength();
		}
		if (fight.getDefenceSupporters().size() > currentFight.getDefenceSupporters().size()) {
			currentFight.setDefenceSupporters(fight.getDefenceSupporters());
			currentFight.calculateCurrentStrength();
		}
		if (fight.getSupportRejections().size() > currentFight.getSupportRejections().size()) {
			currentFight.setSupportRejections(fight.getSupportRejections());
		}
		//merge the defenders hero card (if one)
		if (fight.isDefendingHeroChosen()) {
			currentFight.setDefendingHeroChosen(true);
			currentFight.setDefendingHero(fight.getDefendingHero());
			currentFight.setUseDefendingHeroEffect(fight.isUseDefendingHeroEffect());
		}
		//merge the retreat field
		if (fight.isRetreatFieldChosen()) {
			currentFight.setRetreatFieldChosen(true);
			currentFight.setRetreatField(fight.getRetreatField());
		}
		//merge the falling troops (chosen by the winner)
		if (fight.isFallingTroopsChosen()) {
			currentFight.setFallingTroopsChosen(true);
			currentFight.setFallingTroopsTotal(fight.getFallingTroopsTotal());
			currentFight.setFallingTroopsLooser(fight.getFallingTroopsLooser());
			currentFight.setFallingTroopsSupport(fight.getFallingTroopsSupport());
		}
		//merge the fallen troops (as selected by all losing players)
		if (fight.isFallenTroopsChosen()) {
			currentFight.setFallenTroopsChosen(true);
			currentFight.setFallenTroops(fight.getFallenTroops());
		}
		//TODO send the fight object to the other players
	}
	/**
	 * Just override the current fight with the update from the server.
	 * 
	 * @param fight
	 * 		The fight update from the server.
	 */
	private void overrideFight(Fight fight) {
		this.currentFight = fight;
	}
	
	public Map<Integer, List<Fight>> getFights() {
		return fights;
	}
	public void setFights(Map<Integer, List<Fight>> fights) {
		this.fights = fights;
	}
	
	public Fight getCurrentFight() {
		return currentFight;
	}
	public void setCurrentFight(Fight currentFight) {
		this.currentFight = currentFight;
	}
}