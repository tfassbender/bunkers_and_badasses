package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_character.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.Troop;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Field {
	
	private List<Field> neighbours;
	private User affiliation;
	private List<Troop> troops;
	private Building building;
	private String name;
	
	public String getName() {
		return name;
	}
}