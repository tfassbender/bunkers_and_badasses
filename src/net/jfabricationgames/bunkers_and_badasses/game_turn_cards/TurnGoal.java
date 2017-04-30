package net.jfabricationgames.bunkers_and_badasses.game_turn_cards;

import java.awt.image.BufferedImage;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

/**
 * A special goal for one turn that is chosen by random at the game start.
 * Used to give the players that reach the goals more points.
 * 
 * The matching functions are called from the points where they were executed.
 * The functions need to be overwritten in the subclasses.
 */
public abstract class TurnGoal {
	
	protected static ImageLoader loader;
	
	static {
		loader = new ImageLoader();
		loader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/turn_goals/");
	}
	
	protected PointManager pointManager;
	protected transient BufferedImage image;
	protected String imagePath;
	protected String description;
	
	public TurnGoal() {
		
	}
	
	protected void loadImage() {
		image = loader.loadImage(imagePath);
	}
	
	public void receivePointsFight(User user, Fight fight) {
		
	}
	public void receivePointsTurnEnd(User user, Game game) {
		
	}
	public void receivePointsRecruitment(User user, int recruitedTroops) {
		
	}
	public void receivePointsPlaning(User user, int ammoConsumption) {
		
	}
	public void receivePointsPassing(User user, int passingOrder, int players) {
		
	}
	public void receivePointsMoving(User user, Field startField, boolean fieldConquered) {
		
	}
	
	/**
	 * Get a description in html format for a tooltip text.
	 */
	public String getDescription() {
		return description;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setPointManager(PointManager pointManager) {
		this.pointManager = pointManager;
	}
}