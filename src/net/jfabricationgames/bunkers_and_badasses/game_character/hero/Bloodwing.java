package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Bloodwing extends Hero {
	
	private static final long serialVersionUID = 5514081444025611690L;

	public Bloodwing() {
		attack = 2;
		defence = 1;
		name = "Bloodwing";
		imagePath = "heros/bloodwing_1.png";
		cardImagePath = "hero_cards/card_bloodwing.png";
		loadImage();
		effectDescription = "Luftunterstützung (Kampf):\n\nDie Kampfstärke der gegnerischen Karte wird um 3 gesenkt (Angriff und Verteidigung; Der wert kann nicht unter 0 fallen)";
		executionType = ExecutionType.DURING_FIGHT;
		executionPriority = -1;
	}
	
	@Override
	public void execute(Fight fight) {
		Hero other = null;
		boolean effectUsed = true;
		boolean attack = true;
		int strength = 0;
		if (fight.getAttackingHero() != null && fight.getAttackingHero().equals(this)) {
			other = fight.getDefendingHero();
			effectUsed = fight.isUseDefendingHeroEffect();
			strength = other.getDefence();
			attack = false;
		}
		else if (fight.getDefendingHero() != null && fight.getDefendingHero().equals(this)) {
			other = fight.getAttackingHero();
			effectUsed = fight.isUseAttackingHeroEffect();
			strength = other.getAttack();
			attack = true;
		}
		if (other != null && !effectUsed) {
			int decrease = Math.min(strength, 3);
			if (attack) {
				fight.setCurrentAttackingStrength(fight.getCurrentAttackingStrength() - decrease);
			}
			else {
				fight.setCurrentDefendingStrength(fight.getCurrentDefendingStrength() - decrease);
			}
		}
	}
}
