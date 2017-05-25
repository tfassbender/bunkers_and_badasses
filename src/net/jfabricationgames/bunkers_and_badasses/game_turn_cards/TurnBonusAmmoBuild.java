package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusAmmoBuild extends TurnBonus {
	
	private static final long serialVersionUID = -2095892554504000838L;
	
	public TurnBonusAmmoBuild() {
		bonusId = 1;
		loadVariables();
		imagePath = "turn_bonus_ammo_1_build.png";
		loadImage();
		name = "Munition - Aufbau";
		description = "<html>Zus�tzliche " + SkillProfileManager.AMMO_SKILL_LEVEL[1] + " Munition zu Beginn der Runde.<br/>2 Punkte Bonus f�r jedes Bauen oder Aufr�sten eines Geb�udes.</html>";
	}
	
	@Override
	public void receivePointsBuild(User user) {
		pointManager.addPoints(user, 2);
	}
}