package net.jfabricationgames.bunkers_and_badasses.game_character.troop;

public class Bandit extends Troop {
	
	private static final long serialVersionUID = 3187866318355390443L;
	
	public Bandit() {
		strength = 1;
		troopId = 1;
		type = Troop.PLAYER_TROOP;
		loadVariables();
	}
}