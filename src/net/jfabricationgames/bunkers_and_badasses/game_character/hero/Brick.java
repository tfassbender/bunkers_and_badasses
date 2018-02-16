package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;

public class Brick extends Hero {
	
	private static final long serialVersionUID = -5807273048228466283L;

	public Brick() {
		attack = 5;
		defence = 3;
		name = "Brick";
		imagePath = "heros/brick_1.png";
		cardImagePath = "hero_cards/card_brick.png";
		loadImage();
		effectDescription = "Klopp Sie (Kampf):\n\nBeim Sieg sterben beim Verlierer 3 Einheiten mehr als beim Sieger";
		executionType = ExecutionType.AFTER_FIGHT;
	}
	
	@Override
	public void execute(Fight fight) {
		boolean attacker = fight.getAttackingHero() != null && fight.getAttackingHero().equals(this);
		if ((attacker && fight.getWinner() == Fight.ATTACKERS) || (!attacker && fight.getWinner() == Fight.DEFENDERS)) {
			//remove up to three troops from the retreat field
			Field retreat = fight.getRetreatField();
			if (retreat != null) {
				Field fallenTroopsField = null;
				if (attacker) {
					fallenTroopsField = fight.getDefendingField();
				}
				else {
					fallenTroopsField = fight.getAttackingField();
				}
				int[] fallen = fight.getFallenTroops().get(fallenTroopsField);
				int badasses = Math.min(1, fight.getAttackingBadassTroops() - fallen[1]);
				int normal = 3 - 2*badasses;
				normal = Math.min(normal, fight.getAttackingNormalTroops() - fallen[0]);
				retreat.removeNormalTroops(normal);
				retreat.removeBadassTroops(badasses);
			}
		}
	}
}