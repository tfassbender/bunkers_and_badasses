package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

/**
 * A collection of the players skill profiles and the profits for every skill in a skill tree.
 */
public class SkillProfileManager {
	
	public static int[] CREDITS_SKILL_LEVEL;
	public static int[] AMMO_SKILL_LEVEL;
	public static int[] ERIDIUM_SKILL_LEVEL;

	public static int[] CREDITS_BUILDING_SKILL_LEVEL;
	public static int[] AMMO_BUILDING_SKILL_LEVEL;
	public static int[] ERIDIUM_BUILDING_SKILL_LEVEL;
	
	public static int[] POINTS_SKILL_LEVEL;
	
	public static int[] HEROS_SKILL_LEVEL;
	
	private static SkillProfile defaultSkillProfile;
	
	private transient SkillProfile[] skillProfiles;//local skill profiles (not sent to others)
	
	private Map<User, SkillProfile> selectedProfile;
	
	private UserResourceManager userResourceManager;
	
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
	
	public SkillProfileManager() {
		GameVariableStorage storage = Game.getGameVariableStorage();
		CREDITS_SKILL_LEVEL = storage.getCreditsSkillLevel();
		AMMO_SKILL_LEVEL = storage.getAmmoSkillLevel();
		ERIDIUM_SKILL_LEVEL = storage.getEridiumSkillLevel();
		CREDITS_BUILDING_SKILL_LEVEL = storage.getCreditsBuildingSkillLevel();
		AMMO_BUILDING_SKILL_LEVEL = storage.getAmmoBuildingSkillLevel();
		ERIDIUM_BUILDING_SKILL_LEVEL = storage.getEridiumBuildingSkillLevel();
		POINTS_SKILL_LEVEL = storage.getPointsSkillLevel();
		HEROS_SKILL_LEVEL = storage.getHerosSkillLevel();
	}
	
	public static SkillProfile getDefaultSkillProfile() {
		return defaultSkillProfile;
	}
	
	/**
	 * Add the start resources (from the skill profile) for all users to their resource managers.
	 */
	public void collectSkillResources(User user) {
		UserResource resource = userResourceManager.getResources().get(user);
		SkillProfile skill = selectedProfile.get(user);
		//add the starting resources
		resource.collectSkillResources(skill);
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
	
	public void setUserResourceManager(UserResourceManager userResourceManager) {
		this.userResourceManager = userResourceManager;
	}
}