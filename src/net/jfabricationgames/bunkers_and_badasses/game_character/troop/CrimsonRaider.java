package net.jfabricationgames.bunkers_and_badasses.game_character.troop;

public class CrimsonRaider extends Troop {
	
	private static final long serialVersionUID = -565747041773507111L;
	
	public CrimsonRaider() {
		strength = 2;
		troopId = 2;
		type = Troop.PLAYER_TROOP;
		loadVariables();
	}
}