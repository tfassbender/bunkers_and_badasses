package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

public class Angel extends Hero {
	
	private static final long serialVersionUID = -7693149616225773067L;
	
	public Angel() {
		attack = 2;
		defence = 2;
		name = "Angel";
		imagePath = "heros/angel_1.png";
		cardImagePath = "hero_cards/card_angel.png";
		loadImage();
		effectDescription = "Perfekte Aufklährung (Zug):\n\nIn der gesammten Runde dürfen im Kampf keine Karten gegen dich eingesetzt werden";
		//no execution components needed
		executionType = ExecutionType.TURN_EFFECT;
	}
	
	@Override
	public ExecutionData getExecutionData(ExecutionData executionData) {
		if (executionData == null) {
			return new ExecutionData();
		}
		else {
			return executionData;
		}
	}
	@Override
	public boolean execute(ExecutionData executionData) {
		//TODO implement that the player can't be attacked with heros in this turn
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}