package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class SkillProfileTransfereMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = -7001194875613444068L;
	
	private String username;
	
	private int eridium;
	private int credits;
	private int ammo;
	private int eridiumBuilding;
	private int creditsBuilding;
	private int ammoBuilding;
	private int heros;
	private int points;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
	public int getHeros() {
		return heros;
	}
	public void setHeros(int heros) {
		this.heros = heros;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}