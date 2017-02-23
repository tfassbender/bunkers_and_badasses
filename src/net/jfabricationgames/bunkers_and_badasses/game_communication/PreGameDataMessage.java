package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfile;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

/**
 * Send information about the pre-game to the server and the clients.
 */
public class PreGameDataMessage implements JFGClientMessage, JFGServerMessage {
	
	private static final long serialVersionUID = 4148323940215935109L;
	
	public static final int DATA_SKILL_PROFILE = 1;
	public static final int DATA_BASE_POSITION = 2;
	public static final int DATA_STARTING_POSITION = 3;
	public static final int DATA_TURN_BONUS = 4;
	
	private int data;
	
	private User user;
	
	private SkillProfile selectedProfile;
	private TurnBonus selectedBonus;
	
	private Field basePosition;
	private Field[] startingTroopPositions;
	private int[] startingTroops;
	
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public SkillProfile getSelectedProfile() {
		return selectedProfile;
	}
	public void setSelectedProfile(SkillProfile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}
	
	public TurnBonus getSelectedBonus() {
		return selectedBonus;
	}
	public void setSelectedBonus(TurnBonus selectedBonus) {
		this.selectedBonus = selectedBonus;
	}
	
	public Field getBasePosition() {
		return basePosition;
	}
	public void setBasePosition(Field basePosition) {
		this.basePosition = basePosition;
	}
	
	public Field[] getStartingTroopPositions() {
		return startingTroopPositions;
	}
	public void setStartingTroopPositions(Field[] startingTroopPositions) {
		this.startingTroopPositions = startingTroopPositions;
	}
	
	public int[] getStartingTroops() {
		return startingTroops;
	}
	public void setStartingTroops(int[] startingTroops) {
		this.startingTroops = startingTroops;
	}
}