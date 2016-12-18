package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Troop;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserResource {
	
	private User user;
	
	private int credits;
	private int ammo;
	private int eridium;
	
	private List<Troop> troops;
	private List<Building> buildings;
}