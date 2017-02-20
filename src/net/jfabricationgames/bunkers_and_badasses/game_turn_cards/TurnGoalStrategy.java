package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

public class TurnGoalStrategy extends TurnGoal {

	public TurnGoalStrategy() {
		imagePath = "turn_goal_strategy.png";
		loadImage();
		description = "<html>Strategie:<br/>"
				+ "Nichts �bereilen. Strategie dauert seine Zeit. [Beim Beenden<br/>"
				+ "der Runde erh�llt jeder Spieler so viele Punkte wie Spieler vor<br/>"
				+ "ihm schon gepasst haben]</html>";
	}
	
	//TODO override methods
}