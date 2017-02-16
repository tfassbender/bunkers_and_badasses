package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.image.BufferedImage;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;

/**
 * A bonus that every player can take at the start of the turn and change when passing.
 * Used to support the players by additional resources, points or commands
 */
public abstract class TurnBonus {
	
	protected PointManager pointManager;
	
	protected transient BufferedImage image;

	protected int credits;
	protected int ammo;
	protected int eridium;
	
	protected int raidCommands;
	protected int retreadCommands;
	protected int buildCommand;
	protected int marchCommand;
	protected int recruitmentCommand;
	protected int defenseCommand;
	protected int collectCommand;
	
	public TurnBonus() {
		credits = 0;
		ammo = 0;
		eridium = 0;
		raidCommands = 0;
		retreadCommands = 0;
		buildCommand = 0;
		marchCommand = 0;
		recruitmentCommand = 0;
		defenseCommand = 0;
		collectCommand = 0;
		loadImage();
	}
	
	protected abstract void loadImage();
	
	public void receiveAdditionalResources(User user, Game game) {
		//TODO add the resources to the game
	}
	
	public void receivePointsFight(User user, Fight fight) {
		//points for won fights / killed neutral troops / supports
	}
	public void receivePointsBuild(User user) {
		//fixed points for building or upgrading a building
	}
	
	public void setPointManager(PointManager pointManager) {
		this.pointManager = pointManager;
	}
}