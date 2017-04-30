package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.user.User;

/**
 * A collection of the players skill profiles and the profits for every skill in a skill tree.
 */
public class SkillProfileManager {
	
	public static transient int[] CREDITS_SKILL_LEVEL;
	public static transient int[] AMMO_SKILL_LEVEL;
	public static transient int[] ERIDIUM_SKILL_LEVEL;

	public static transient int[] CREDITS_BUILDING_SKILL_LEVEL;
	public static transient int[] AMMO_BUILDING_SKILL_LEVEL;
	public static transient int[] ERIDIUM_BUILDING_SKILL_LEVEL;
	
	public static transient int[] POINTS_SKILL_LEVEL;
	
	public static transient int[] HEROS_SKILL_LEVEL;
	
	private static transient SkillProfile defaultSkillProfile;
	
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
	 * Merge the data from the new skill profile manager.
	 * 
	 * @param profileManager
	 * 		The new skill profile manager.
	 */
	public void merge(SkillProfileManager profileManager) {
		selectedProfile = profileManager.getSelectedProfiles();
		userResourceManager = profileManager.getUserResourceManager();
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
	private Map<User, SkillProfile> getSelectedProfiles() {
		return selectedProfile;
	}
	
	private UserResourceManager getUserResourceManager() {
		return userResourceManager;
	}
	public void setUserResourceManager(UserResourceManager userResourceManager) {
		this.userResourceManager = userResourceManager;
	}
}