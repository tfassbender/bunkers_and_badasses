package net.jfabricationgames.bunkers_and_badasses.game_character.troop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Troop implements Serializable {
	
	private static final long serialVersionUID = -9008608458269567516L;
	
	public static final int PLAYER_TROOP = 1;
	public static final int NEUTRAL_TROOP = 2;
	
	private static TroopStorage storage;
	
	protected String name;
	
	protected int strength;
	protected int type;
	protected int troopId;
	
	protected int baseCostsCredits;
	protected int baseCostsAmmo;
	protected int recruitCostsCredits;
	protected int recruitCostsAmmo;
	protected int recruitCostsEridium;
	
	public static int getNeutralTroopType(Troop troop) {
		if (troop instanceof Skag) {
			return 0;
		}
		else if (troop instanceof Thresher) {
			return 1;
		}
		else if (troop instanceof Stalker) {
			return 2;
		}
		else {
			return 0;//skag as default
		}
	}
	public static Troop getNeutralTroop(int type) {
		switch (type) {
			case 0:
				return new Skag();
			case 1:
				return new Thresher();
			case 2:
				return new Stalker();
			default:
				return new Skag();
		}
	}
	
	protected void loadVariables() {
		int[][] costs = storage.getTroopCosts();
		baseCostsCredits = costs[troopId][TroopStorage.BASE_COSTS_CREDITS];
		baseCostsAmmo = costs[troopId][TroopStorage.BASE_COSTS_AMMO];
		recruitCostsCredits = costs[troopId][TroopStorage.RECRUIT_COSTS_CREDITS];
		recruitCostsAmmo = costs[troopId][TroopStorage.RECRUIT_COSTS_AMMO];
		recruitCostsEridium = costs[troopId][TroopStorage.RECRUIT_COSTS_ERIDIUM];
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getType() {
		return type;
	}
	
	/**
	 * Serialize the object without the transient parts (the images that are not stored in the database)
	 * 
	 * @param out
	 * 		The ObjectOutputStream that is used to write down the file.
	 */
	protected void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
	
	/**
	 * Read the object without the transient parts (the images that are not stored in the database)
	 * 
	 * @param in
	 * 		The ObjectInputStream that is used to read the file.
	 */
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	public int getTroopId() {
		return troopId;
	}

	public int getBaseCostsCredits() {
		return baseCostsCredits;
	}
	public int getBaseCostsAmmo() {
		return baseCostsAmmo;
	}
	public int getRecruitCostsCredits() {
		return recruitCostsCredits;
	}
	public int getRecruitCostsAmmo() {
		return recruitCostsAmmo;
	}
	public int getRecruitCostsEridium() {
		return recruitCostsEridium;
	}
	
	public static TroopStorage getStorage() {
		return storage;
	}
	public static void setStorage(TroopStorage storage) {
		Troop.storage = storage;
	}
	
	public String getName() {
		return name;
	}
}