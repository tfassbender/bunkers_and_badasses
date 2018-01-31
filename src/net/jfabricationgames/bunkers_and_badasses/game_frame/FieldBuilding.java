package net.jfabricationgames.bunkers_and_badasses.game_frame;

import net.jfabricationgames.bunkers_and_badasses.game.SkillProfile;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;

public class FieldBuilding implements Comparable<FieldBuilding> {

	private Field field;
	private Building building;
	private SkillProfile skillProfile;
	
	public FieldBuilding(Field field, Building building) {
		this(field, building, null);
	}
	public FieldBuilding(Field field, Building building, SkillProfile skillProfile) {
		this.field = field;
		this.building = building;
		this.skillProfile = skillProfile;
	}
	
	@Override
	public int compareTo(FieldBuilding fb) {
		if (building.getBuildingId() == 3) {
			//id 3 is an empty building
			if (fb.getBuilding().getBuildingId() == 3) {
				return 0;
			}
			else {
				return 1;
			}
		}
		else if (fb.getBuilding().getBuildingId() == 3) {
			return -1;
		}
		return building.getBuildingId() - fb.getBuilding().getBuildingId();
	}
	
	@Override
	public String toString() {
		String text = field.getName() + ": " + building.getName();
		if (skillProfile != null) {
			int[] resources = new int[3];
			resources[0] = building.getCreditMining();
			resources[1] = building.getAmmoMining();
			resources[2] = building.getEridiumMining();
			if (resources[0] > 0) {
				resources[0] += SkillProfileManager.CREDITS_BUILDING_SKILL_LEVEL[skillProfile.getCreditsBuilding()];
			}
			if (resources[1] > 0) {
				resources[1] += SkillProfileManager.AMMO_BUILDING_SKILL_LEVEL[skillProfile.getAmmoBuilding()];
			}
			if (resources[2] > 0) {
				resources[2] += SkillProfileManager.ERIDIUM_BUILDING_SKILL_LEVEL[skillProfile.getEridiumBuilding()];
			}
			text += " [" + resources[0] + "C, " + resources[1] + "M, " + resources[2] + "E]";
		}
		return text;
	}
	
	public Field getField() {
		return field;
	}
	public Building getBuilding() {
		return building;
	}
}