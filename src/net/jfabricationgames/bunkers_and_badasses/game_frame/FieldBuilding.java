package net.jfabricationgames.bunkers_and_badasses.game_frame;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;

public class FieldBuilding {

	private Field field;
	private Building building;
	
	public FieldBuilding(Field field, Building building) {
		this.field = field;
		this.building = building;
	}
	
	@Override
	public String toString() {
		return field.getName() + ": " + building.getName();
	}
	
	public Field getField() {
		return field;
	}
	public Building getBuilding() {
		return building;
	}
}