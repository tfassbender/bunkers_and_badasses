package net.jfabricationgames.bunkers_and_badasses.game_character.troop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Troop {
	
	public static final int PLAYER_TROOP = 1;
	public static final int NEUTRAL_TROOP = 2;
	
	protected int strength;
	protected int type;
	
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
}