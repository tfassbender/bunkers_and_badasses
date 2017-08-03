package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnBonusAmmoBuild extends TurnBonus {
	
	private static final long serialVersionUID = -2095892554504000838L;
	
	public TurnBonusAmmoBuild() {
		bonusId = 1;
		loadVariables();
		imagePath = "turn_bonus_ammo_1_build.png";
		loadImage();
		name = "Munition - Aufbau";
		description = "<html>Zusätzliche " + ammo + " Munition zu Beginn der Runde. <br/>2 Punkte Bonus für jedes Bauen oder Aufrüsten eines Gebäudes.</html>";
	}
	
	@Override
	public void receivePointsBuild(User user) {
		pointManager.addPoints(user, 2);
	}
}