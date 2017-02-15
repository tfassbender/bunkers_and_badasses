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