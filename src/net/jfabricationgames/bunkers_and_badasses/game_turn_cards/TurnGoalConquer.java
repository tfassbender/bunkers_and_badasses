package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalConquer extends TurnGoal {

	public TurnGoalConquer() {
		imagePath = "turn_goal_conquer.png";
		loadImage();
		description = "<html>Mein Land:<br/>"
				+ "Die Armeen m�ssen an Boden gewinnen! [2 Punkte f�r jedes<br/>"
				+ "eroberte Gebiet (Auch neutrale Gebiete)]</html>";
	}
	
	//TODO override methods
}