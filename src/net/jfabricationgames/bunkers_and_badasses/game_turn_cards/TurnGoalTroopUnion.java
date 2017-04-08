package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalTroopUnion extends TurnGoal {

	public TurnGoalTroopUnion() {
		imagePath = "turn_goal_troop_union.png";
		loadImage();
		description = "<html>Truppenverband:<br/>"
				+ "Einer für alle; Und alle für mich [Alle Spieler erhallten am<br/>"
				+ "Ende der Runde Punkte für ihren größten Truppenzusammenschluss:<br/>"
				+ "1 Punkt pro Truppe im Zusammenschluss (egal ob normale oder<br/>"
				+ "verstärkte Einheit)]</html>";
	}
	
	@Override
	public void receivePointsTurnEnd(User user, Game game) {
		int troops = 0;
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation().equals(user)) {
				troops = Math.max(troops, field.getTroops().size());
			}
		}
		pointManager.addPoints(user, troops);
	}
}