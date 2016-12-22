package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.awt.image.BufferedImage;
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
	
	public transient BufferedImage image;
	
	protected static BufferedImage staticImage;//load the image in a static content in the subclasses
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
		image = staticImage;
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
}