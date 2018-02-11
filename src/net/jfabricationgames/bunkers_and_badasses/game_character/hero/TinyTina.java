package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_frame.ErrorDialog;

public class TinyTina extends Hero {
	
	private static final long serialVersionUID = -7358701771719372456L;

	public TinyTina() {
		attack = 2;
		defence = 3;
		name = "Tiny Tina";
		imagePath = "heros/tina_1.png";
		cardImagePath = "hero_cards/card_tiny_tina.png";
		loadImage();
		effectDescription = "Kabummabumms (Zug):\n\nEin beliebiges Gebäude (außer Arschgauls Palast) in einem angrenzenden Feld wird zerstört";
		componentsNeeded = Arrays.asList(ExecutionComponent.FIELD_TARGET);
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			//send the start execution data with only the possible start fields
			ExecutionData data = new ExecutionData();
			data.setPossibleTargetFields(game.getBoard().getFields().stream().filter(localPlayersField.negate()).filter(hasBuilding).
					filter(nextToLocalPlayersField).collect(Collectors.toList()));
			return data;
		}
		else {
			return executionData;			
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		if (executionData.getTargetFields().isEmpty()) {
			new ErrorDialog("Du solltest ein Ziel aussuchen wenn du willst das deine Truppen etwas in die Luft sprengen.\n\n"
					+ "Man sollte denen wirklich keinen Sprenngstoff ohne klare Anweisungen geben.\n"
					+ "Naja eigentlich sollte man ihnen garkeinen Sprenngstoff geben aber...").setVisible(true);
			return false;
		}
		if (executionData.getTargetFields().size() > 1) {
			new ErrorDialog("Du kannst nur ein Feld aussuchen in dem ein Gebäude gesprengt werden soll.\n\n"
					+ "Diese Kabummabumms sind nicht billig!").setVisible(true);
			return false;
		}
		Field target = executionData.getTargetFields().get(0);
		if (target.getBuilding() instanceof ArschgaulsPalace) {
			new ErrorDialog("Du kannst doch nicht Arschgauls Palast abreißen!!!\nWas soll der Scheiß?!");
			return false;
		}
		if (target.getBuilding() instanceof EmptyBuilding) {
			new ErrorDialog("Du kannst kein Gebäude auf einem Feld sprengen auf dem kein Gebäude steht.\n\n"
					+ "Obwohl das bestimmt auch ein schönes Feuerwerk gibt...");
			return false;
		}
		target.setBuilding(new EmptyBuilding());
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}