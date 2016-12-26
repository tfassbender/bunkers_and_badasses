package net.jfabricationgames.bunkers_and_badasses.game_character.building;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

public abstract class Building {
	
	protected int recruitableTroops;
	protected int ammoMining;
	protected int creditMining;
	protected int eridiumMining;
	protected int landMineVictims;
	protected int additionalDefence;
	protected int moveDistance;
	protected int points;//winning points for holding bases
	
	protected int[] extensionPrice;//the prices (credits, ammo, eridium) to extend the building
	
	protected boolean attackable;
	protected boolean badassTroopsRecruitable;
	protected boolean advanced;
	protected boolean extendable;
	
	protected BufferedImage image;
	
	protected String name;
	
	protected static BufferedImage staticImage;//load the image in a static content in the subclasses
	protected static ImageLoader imageLoader;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/buildings/");
	}
	
	public Building() {
		recruitableTroops = 1;
		ammoMining = 0;
		creditMining = 0;
		eridiumMining = 0;
		landMineVictims = 0;
		additionalDefence = 0;
		moveDistance = 1;
		points = 0;
		extensionPrice = new int[] {-1, -1, -1};
		attackable = true;
		badassTroopsRecruitable = false;
		advanced = false;
		extendable = false;
		image = staticImage;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Extend the building.
	 * The default implementation does nothing because not all buildings can be extended.
	 */
	public void extend() {
		
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
	
	public BufferedImage getImage() {
		return image;
	}
}