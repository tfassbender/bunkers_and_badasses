package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.image.BufferedImage;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;

/**
 * A bonus that every player can take at the start of the turn and change when passing.
 * Used to support the players by additional resources, points or commands
 */
public abstract class TurnBonus {
	
	protected static ImageLoader loader;
	
	static {
		loader = new ImageLoader();
		loader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/turn_bonuses/");
	}
	
	protected PointManager pointManager;
	
	protected transient BufferedImage image;

	protected int credits;
	protected int ammo;
	protected int eridium;
	
	protected int raidCommands;
	protected int retreatCommands;
	protected int supportCommands;
	protected int marchCommands;
	protected int recruitmentCommands;
	protected int defenseCommands;
	protected int collectCommands;
	
	protected int bonusId;
	
	protected String imagePath;
	protected String description;
	protected String name;
	
	private static TurnBonusStorage storage;
	
	public TurnBonus() {
		credits = 0;
		ammo = 0;
		eridium = 0;
		raidCommands = 0;
		retreatCommands = 0;
		supportCommands = 0;
		marchCommands = 0;
		recruitmentCommands = 0;
		defenseCommands = 0;
		collectCommands = 0;
	}
	
	protected void loadImage() {
		image = loader.loadImage(imagePath);
	}
	
	protected void loadVariables() {
		credits = storage.getResources()[bonusId][TurnBonusStorage.CREDITS];
		ammo = storage.getResources()[bonusId][TurnBonusStorage.AMMO];
		eridium = storage.getResources()[bonusId][TurnBonusStorage.ERIDIUM];
	}
	
	public void receiveAdditionalResources(User user, Game game) {
		game.getResourceManager().getResources().get(user).collectTurnBonusResources(this);
	}
	
	public void receivePointsFight(User user, Fight fight) {
		//points for won fights / killed neutral troops / supports
	}
	public void receivePointsBuild(User user) {
		//fixed points for building or upgrading a building
	}
	
	/**
	 * Get a description in html format for a tooltip text.
	 */
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPointManager(PointManager pointManager) {
		this.pointManager = pointManager;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public static TurnBonusStorage getStorage() {
		return storage;
	}
	public static void setStorage(TurnBonusStorage storage) {
		TurnBonus.storage = storage;
	}

	public int getCredits() {
		return credits;
	}
	public int getAmmo() {
		return ammo;
	}
	public int getEridium() {
		return eridium;
	}
	
	public int getRaidCommands() {
		return raidCommands;
	}
	public int getRetreatCommands() {
		return retreatCommands;
	}
	public int getSupportCommands() {
		return supportCommands;
	}
	public int getMarchCommands() {
		return marchCommands;
	}
	public int getRecruitmentCommands() {
		return recruitmentCommands;
	}
	public int getDefenseCommands() {
		return defenseCommands;
	}
	public int getCollectCommands() {
		return collectCommands;
	}
}