package net.jfabricationgames.bunkers_and_badasses.game;

/**
 * A single skill profile for a player.
 */
public class SkillProfile {
	
	private int id;//the id in the database
	
	private int eridium;
	private int credits;
	private int ammo;
	
	private int eridiumBuilding;
	private int creditsBuilding;
	private int ammoBuilding;
	
	private int hero;
	private int points;
	
	/**
	 * Describe the profile.
	 */
	public String describe() {
		StringBuilder sb = new StringBuilder();
		sb.append("Eridium: ");
		sb.append(eridium);
		sb.append(" [");
		sb.append(SkillProfileManager.ERIDIUM_SKILL_LEVEL[eridium]);
		sb.append(" Eridium zus�tzlich (zu Spielbeginn)]\n");
		sb.append("Credits: ");
		sb.append(credits);
		sb.append(" [");
		sb.append(SkillProfileManager.CREDITS_SKILL_LEVEL[credits]);
		sb.append(" Credits zus�tzlich (zu Spielbeginn)]\n");
		sb.append("Munition: ");
		sb.append(ammo);
		sb.append(" [");
		sb.append(SkillProfileManager.AMMO_SKILL_LEVEL[ammo]);
		sb.append(" Munition zus�tzlich (zu Spielbeginn)]\n\n");
		sb.append("Eridium Geb�ude: ");
		sb.append(eridiumBuilding);
		sb.append(" [");
		sb.append(SkillProfileManager.ERIDIUM_BUILDING_SKILL_LEVEL[eridiumBuilding]);
		sb.append(" Eridium zus�tzlich (pro Geb�ude pro Runde)]\n");
		sb.append("Credits Geb�ude: ");
		sb.append(credits);
		sb.append(" [");
		sb.append(SkillProfileManager.CREDITS_BUILDING_SKILL_LEVEL[creditsBuilding]);
		sb.append(" Credits zus�tzlich (pro Geb�ude pro Runde)]\n");
		sb.append("Munition Geb�ude: ");
		sb.append(ammo);
		sb.append(" [");
		sb.append(SkillProfileManager.AMMO_BUILDING_SKILL_LEVEL[ammoBuilding]);
		sb.append(" Munition zus�tzlich (pro Geb�ude pro Runde)]\n\n");
		sb.append("Helden: ");
		sb.append(hero);
		sb.append(" [");
		sb.append(SkillProfileManager.HEROS_SKILL_LEVEL[hero]);
		sb.append(" Helden-Karten zus�tzlich (zu Spielbeginn)]\n\n");
		sb.append("Punkte: ");
		sb.append(points);
		sb.append(" [");
		sb.append(SkillProfileManager.POINTS_SKILL_LEVEL[points]);
		sb.append(" Siegpunkte zus�tzlich (zu Spielbeginn)]");
		return sb.toString();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getEridium() {
		return eridium;
	}
	public void setEridium(int eridium) {
		this.eridium = eridium;
	}
	
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public int getAmmo() {
		return ammo;
	}
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	
	public int getEridiumBuilding() {
		return eridiumBuilding;
	}
	public void setEridiumBuilding(int eridiumBuilding) {
		this.eridiumBuilding = eridiumBuilding;
	}
	
	public int getCreditsBuilding() {
		return creditsBuilding;
	}
	public void setCreditsBuilding(int creditsBuilding) {
		this.creditsBuilding = creditsBuilding;
	}
	
	public int getAmmoBuilding() {
		return ammoBuilding;
	}
	public void setAmmoBuilding(int ammoBuilding) {
		this.ammoBuilding = ammoBuilding;
	}
	
	public int getHero() {
		return hero;
	}
	public void setHero(int hero) {
		this.hero = hero;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}