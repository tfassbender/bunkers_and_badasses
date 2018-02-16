package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game_command.MarchCommand;

public class Wilhelm extends Hero {
	
	private static final long serialVersionUID = -3924768912224518016L;

	public Wilhelm() {
		attack = 4;
		defence = 3;
		name = "Wilhelm";
		imagePath = "heros/wilhelm_1.png";
		cardImagePath = "hero_cards/card_wilhelm.png";
		loadImage();
		effectDescription = "Krieg (Kampf):\n\nBeim Sieg dürfen nur die Hälfte der Truppen (aufgerundet) weiterziehen, aber der Marschbefehl darf mitgenommen werden";
		executionType = ExecutionType.AFTER_FIGHT;
	}
	
	@Override
	public void execute(Fight fight) {
		if (fight.getAttackingHero() != null && fight.getAttackingHero().equals(this) && fight.getWinner() == Fight.ATTACKERS) {
			int[] fallenTroops = fight.getFallenTroops().get(fight.getAttackingField());
			int[] movingTroops = {fight.getAttackingNormalTroops() - fallenTroops[0], fight.getAttackingBadassTroops() - fallenTroops[1]};
			int movingTroopsStrength = movingTroops[0] + 2*movingTroops[1];
			movingTroopsStrength = movingTroopsStrength / 2;
			//move half of the troops back to their starting field
			int movingBadasses = Math.min(movingTroopsStrength / 2, movingTroops[2]);
			movingTroopsStrength -= 2*movingBadasses;
			int movingNormals = Math.min(movingTroopsStrength, movingTroops[1]);
			game.getBoard().moveTroops(fight.getDefendingField(), fight.getAttackingField(), movingNormals, movingBadasses);
			fight.getDefendingField().setCommand(new MarchCommand());
		}
	}
}
