package net.jfabricationgames.bunkers_and_badasses.game;

public class UserResource {
	
	//the current amounts of credits, ammo and eridium
	private int credits;
	private int ammo;
	private int eridium;
	
	//the amounts of resources added to every buildings collections from the skill profiles
	private int eridiumBuilding;
	private int ammoBuilding;
	private int creditsBuilding;
	
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
	
	public int getEridium() {
		return eridium;
	}
	public void setEridium(int eridium) {
		this.eridium = eridium;
	}
	
	public int getEridiumBuilding() {
		return eridiumBuilding;
	}
	public void setEridiumBuilding(int eridiumBuilding) {
		this.eridiumBuilding = eridiumBuilding;
	}
	
	public int getAmmoBuilding() {
		return ammoBuilding;
	}
	public void setAmmoBuilding(int ammoBuilding) {
		this.ammoBuilding = ammoBuilding;
	}
	
	public int getCreditsBuilding() {
		return creditsBuilding;
	}
	public void setCreditsBuilding(int creditsBuilding) {
		this.creditsBuilding = creditsBuilding;
	}
}