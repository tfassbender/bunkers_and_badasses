package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class Salvadore extends Hero {
	
	private static final long serialVersionUID = -8121803559587845378L;

	public Salvadore() {
		attack = 4;
		defence = 3;
		name = "Salvadore";
		imagePath = "heros/salvador_1.png";
		cardImagePath = "hero_cards/card_salvador.png";
		loadImage();
		effectDescription = "Sperrfeuer (Kampf):\n\nBei Niederlage dürfen nur die Hälfte der Truppen (abgerundet) weiter Ziehen (auch 0 möglich; das Feld wird aber erobert)";
		executionType = ExecutionType.AFTER_FIGHT;
	}
	
	@Override
	public void execute(Fight fight) {
		if (fight.getDefendingHero() != null && fight.getDefendingHero().equals(this) && fight.getWinner() == Fight.ATTACKERS) {
			int[] fallenTroops = fight.getFallenTroops().get(fight.getAttackingField());
			int[] movingTroops = {fight.getAttackingNormalTroops() - fallenTroops[0], fight.getAttackingBadassTroops() - fallenTroops[1]};
			int movingTroopsStrength = movingTroops[0] + 2*movingTroops[1];
			movingTroopsStrength = movingTroopsStrength / 2 + (movingTroopsStrength % 2 == 0 ? 0 : 1);
			//move half of the troops back to their starting field
			int movingBadasses = Math.min(movingTroopsStrength / 2, movingTroops[2]);
			movingTroopsStrength -= 2*movingBadasses;
			int movingNormals = Math.min(movingTroopsStrength, movingTroops[1]);
			game.getBoard().moveTroops(fight.getDefendingField(), fight.getAttackingField(), movingNormals, movingBadasses);
		}
	}
}