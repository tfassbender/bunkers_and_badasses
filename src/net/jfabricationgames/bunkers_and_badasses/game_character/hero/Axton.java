package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Axton extends Hero {
	
	private static final long serialVersionUID = 6188313370223500688L;

	public Axton() {
		attack = 4;
		defence = 3;
		name = "Axton";
		imagePath = "heros/axton_1.png";
		cardImagePath = "hero_cards/card_axton.png";
		loadImage();
		effectDescription = "Stratege (Kampf):\n\nDie gegnerische Karte kann nicht eingesetzt werden";
		executionType = ExecutionType.BEFORE_FIGHT;
		executionPriority = 1;
	}
	
	@Override
	public void execute(Fight fight) {
		if (fight.getAttackingHero() != null && fight.getAttackingHero().equals(this)) {
			fight.setDefendingHero(null);
		}
		else if (fight.getDefendingHero() != null && fight.getDefendingHero().equals(this)) {
			fight.setAttackingHero(null);
		}
		fight.setBlockHeroExecution(true);
	}
}