package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalArmAGeddon extends TurnGoal {
	
	public TurnGoalArmAGeddon() {
		imagePath = "turn_goal_arm_a_geddon.png";
		loadImage();
		description = "<html>Arm-A-Geddon:<br/>"
				+ "Dr. Zed braucht Ersatzkörperteile. Dafür müssen nur <br/>"
				+ "ein paar Leute sterben... [1 Punkt pro im Kampf fallender<br/>"
				+ " Truppe (gegnerische und eigene Truppen; Punkte nur für <br/>"
				+ "den Angreifer)]</html>";
	}
	
	//TODO override methods
}