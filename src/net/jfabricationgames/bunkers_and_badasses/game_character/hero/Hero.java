package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;

public abstract class Hero implements Serializable {
	
	private static final long serialVersionUID = 4244972938699657617L;

	protected HeroEffectTime time;
	
	protected int attack;
	protected int defence;
	
	protected int usedAttack;
	protected int usedDefence;
	
	protected String name;
	protected String effectDescription;
	protected String imagePath;
	protected String cardImagePath;
	
	protected transient BufferedImage image;
	protected transient BufferedImage cardImage;
	
	protected static ImageLoader imageLoader;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
	}
	
	public Hero() {
		attack = 0;
		defence = 0;
		usedAttack = 0;
		usedDefence = 0;
	}
	
	public void loadImage() {
		image = imageLoader.loadImage(imagePath);
		cardImage = imageLoader.loadImage(cardImagePath);
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
	
	@Override
	public String toString() {
		return name;
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
	 * The default implementation only updates the statistics because not all hero's special effects can be used as turn.
	 */
	public void executeTurn(Game game) {
		//TODO add to the statistics
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
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getCardImage() {
		return cardImage;
	}
	public void setCardImage(BufferedImage cardImage) {
		this.cardImage = cardImage;
	}
}