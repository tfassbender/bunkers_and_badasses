package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

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
	
	public static final int[] POINTS_SKILL_LEVEL = new int[] {};
	
	public static final int[] HEROES_SKILL_LEVEL = new int[] {};
	
	private static SkillProfile defaultSkillProfile;
	
	private transient SkillProfile[] skillProfiles;//local skill profiles (not sent to others)
	
	private Map<User, SkillProfile> selectedProfile;
	
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
	
	/**
	 * Add the start resources for all users to their resource managers.
	 */
	public void addStartResources() {
		
	}
	
	public SkillProfile[] getSkillProfiles() {
		return skillProfiles;
	}
	public void setSkillProfiles(SkillProfile[] skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
	
	public void setSelectedProfile(User user, SkillProfile profile) {
		selectedProfile.put(user, profile);
	}
	public SkillProfile getSelectedProfile(User user) {
		return selectedProfile.get(user);
	}
}