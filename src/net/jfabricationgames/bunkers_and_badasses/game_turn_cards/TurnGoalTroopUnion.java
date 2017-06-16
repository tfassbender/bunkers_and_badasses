package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalTroopUnion extends TurnGoal {

	private static final long serialVersionUID = -1474453959743655739L;
	
	public TurnGoalTroopUnion() {
		imagePath = "turn_goal_troop_union.png";
		loadImage();
		description = "<html>Truppenverband:<br/>"
				+ "Einer f�r alle; Und alle f�r mich [Alle Spieler erhallten am<br/>"
				+ "Ende der Runde Punkte f�r ihren gr��ten Truppenzusammenschluss:<br/>"
				+ "2 Punkte pro Truppe im Zusammenschluss (egal ob normale oder<br/>"
				+ "verst�rkte Einheit)]</html>";
	}
	
	@Override
	public void receivePointsTurnEnd(User user, Game game) {
		int troops = 0;
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation().equals(user)) {
				troops = Math.max(troops, field.getTroops().size());
			}
		}
		pointManager.addPoints(user, 2*troops);
	}
}