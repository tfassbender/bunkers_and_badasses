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
	
	private static SkillProfile defaultSkillProfile;
	
	static {
		defaultSkillProfile = new SkillProfile();
		defaultSkillProfile.setEridium(2);
		defaultSkillProfile.setCredits(2);
		defaultSkillProfile.setAmmo(2);
		defaultSkillProfile.setEridiumBuilding(1);
		defaultSkillProfile.setCreditsBuilding(2);
		defaultSkillProfile.setAmmoBuilding(2);
		defaultSkillProfile.setPoints(0);
		defaultSkillProfile.setHero(0);
	}
	
	public static SkillProfile getDefaultSkillProfile() {
		return defaultSkillProfile;
	}
	
	private SkillProfile[] skillProfiles;
	
	public SkillProfile[] getSkillProfiles() {
		return skillProfiles;
	}
	public void setSkillProfiles(SkillProfile[] skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
}