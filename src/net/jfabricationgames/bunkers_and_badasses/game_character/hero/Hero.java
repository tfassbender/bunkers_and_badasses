package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public abstract class Hero implements Serializable {
	
	private static final long serialVersionUID = 4244972938699657617L;

	protected HeroEffectTime time;
	
	protected int attack;
	protected int defence;
	
	protected int usedAttack;
	protected int usedDefence;
	
	protected String name;
	protected String effectDescription;
	
	protected transient BufferedImage image;
	
	protected static ImageLoader imageLoader;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/heros/");
	}
	
	public Hero() {
		attack = 0;
		defence = 0;
		usedAttack = 0;
		usedDefence = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hero) {
			return name.equals(((Hero) obj).getName());
		}
		else {
			return super.equals(obj);
		}
	}
	
	/**
	 * Execute the fight special effect of the hero.
	 * The default implementation is empty because not every hero can use his special effect in a fight.
	 * 
	 * @param fight
	 * 		The Fight object.
	 */
	public void executeFight(Fight fight) {
		
	}
	
	/**
	 * Execute the hero's special effect as a game turn.
	 * The default implementation is empty because not all hero's special effects can be used as turn.
	 */
	public void executeTurn() {
		
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
	
	public int getAttack() {
		return attack;
	}
	public int getDefence() {
		return defence;
	}
	
	public String getName() {
		return name;
	}
	public String getEffectDescription() {
		return effectDescription;
	}
}