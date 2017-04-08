package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnGoalBuilding extends TurnGoal {
	
	public TurnGoalBuilding() {
		imagePath = "turn_goal_building.png";
		loadImage();
		description = "<html>Bauabnahme:<br/>"
				+ "Eine erfolgreiche Armee braucht nicht nur Truppen sondern auch Gebäude.<br/>"
				+ "[Alle Spieler erhallten am ende der Runde: 1 Punkt für jedes normale Gebäude,<br/>"
				+ "2 Punkte für jedes aufgerüstete Gebäude, 3 Punkte für jede gehalltene Basis]</html>";
	}
	
	@Override
	public void receivePointsTurnEnd(User user, Game game) {
		int points = 0;
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation().equals(user) && !(field.getBuilding() instanceof EmptyBuilding)) {
				if (field.getBuilding() instanceof ArschgaulsPalace) {
					points += 3;
				}
				else if (field.getBuilding().isExtended()) {
					points += 2;
				}
				else {
					points += 1;
				}
			}
		}
		pointManager.addPoints(user, points);
	}
}