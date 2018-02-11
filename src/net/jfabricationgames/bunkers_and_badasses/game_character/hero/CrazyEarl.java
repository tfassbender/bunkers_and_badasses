package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.UserResource;

public class CrazyEarl extends Hero {
	
	private static final long serialVersionUID = -1504504671280272179L;

	public CrazyEarl() {
		attack = 0;
		defence = 3;
		name = "Crazy Earl";
		imagePath = "heros/earl_1.png";
		cardImagePath = "hero_cards/card_crazy_earl.png";
		loadImage();
		effectDescription = "Schwarzmarkt (Zug):\n\nDu erhällst gratis Eridium (15) und Credits (50)";
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
		UserResource resource = game.getResourceManager().getResources().get(game.getLocalUser());
		resource.addCredits(50);
		resource.addEridium(15);
		addHeroEffectExecutionToStatistics(executionData);
		game.getPlayerOrder().nextMove();
		game.getTurnExecutionManager().commit();
		game.getGameFrame().updateAllFrames();
		return true;
	}
}