package net.jfabricationgames.bunkers_and_badasses.game;

/**
 * A collection of the players skill profiles and the profits for every skill in a skill tree.
 */
public class SkillProfileManager {
	
	//TODO add the amount of credits, ammo or eridium for every skill level
	public static final int[] CREDITS_SKILL_LEVEL = new int[] {};
	public static final int[] AMMO_SKILL_LEVEL = new int[] {};
	public static final int[] ERIDIUM_SKILL_LEVEL = new int[] {};

	public static final int[] CREDITS_BUILDING_SKILL_LEVEL = new int[] {};
	public static final int[] AMMO_BUILDING_SKILL_LEVEL = new int[] {};
	public static final int[] ERIDIUM_BUILDING_SKILL_LEVEL = new int[] {}; 
	
	public static final int[] POINTS = new int[] {};
	
	public static final int[] HEROES = new int[] {};
	
	private SkillProfile[] skillProfiles;
	
	public SkillProfile[] getSkillProfiles() {
		return skillProfiles;
	}
	public void setSkillProfiles(SkillProfile[] skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
}