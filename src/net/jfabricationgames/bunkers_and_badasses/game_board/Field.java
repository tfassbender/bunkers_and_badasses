package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game.UserColor;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Bandit;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.CrimsonRaider;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Troop;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameFrame;
import net.jfabricationgames.bunkers_and_badasses.server.BunkersAndBadassesServer;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Field implements Serializable {
	
	private static final long serialVersionUID = 1904454857903005846L;
	
	private List<Field> neighbours;
	private User affiliation;
	private List<Troop> troops;
	private Building building;
	private String name;
	private Region region;
	private Command command;
	
	private Point fieldPosition;
	private Point normalTroopsPosition;
	private Point badassTroopsPosition;
	private Point buildingPosition;
	private Point commandMarkerPosition;
	private Point playerMarkerPosition;
	
	private Color fieldColor;
	
	private Board board;
	
	private static transient BufferedImage normalTroopImage;
	private static transient BufferedImage badassTroopImage;
	private static transient BufferedImage neutralTroopImage;
	
	static {
		//load the troop images
		if (!BunkersAndBadassesServer.IS_SERVER_APPLICATION) {
			normalTroopImage = GameFrame.getImageLoader().loadImage("troops/bandit_3_small.png");
			badassTroopImage = GameFrame.getImageLoader().loadImage("troops/lance_4_small.png");
			neutralTroopImage = GameFrame.getImageLoader().loadImage("troops/skag_1_small.png");
		}
	}
	
	public Field() {
		neighbours = new ArrayList<Field>();
		troops = new ArrayList<Troop>();
		building = new EmptyBuilding();
	}
	
	public void merge(Field field) {
		this.affiliation = field.getAffiliation();
		this.building = field.getBuilding();
		this.command = field.getCommand();
		if (command != null) {
			this.command.loadImage();//reload the image that was not sent			
		}
		this.troops = field.getTroops();
	}
	
	@Override
	public String toString() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Field) {
			Field field = (Field) obj;
			boolean troopsEqual = getTroops().size() == field.getTroops().size();
			if (troopsEqual) {
				for (int i = 0; i < getTroops().size(); i++) {
					troopsEqual &= getTroops().get(i).getType() == field.getTroops().get(i).getType() && getTroops().get(i).getStrength() == field.getTroops().get(i).getStrength();
				}				
			}
			return field.getAffiliation() != null && getAffiliation() != null && field.getAffiliation().equals(getAffiliation()) && field.getBuilding().equals(getBuilding()) && field.getName().equals(getName()) && troopsEqual;
		}
		else {
			return super.equals(obj);
		}
	}
	
	/**
	 * Draw the fields components to the graphics object.
	 * The drawn components are:
	 * 	- The troop images and numbers
	 * 	- The building
	 * 	- (A color code for the player that rules this field)
	 * 
	 * @param g
	 * 		The graphics object on that is drawn.
	 * 		The graphics object is passed on from the board image drawing method.
	 */
	public void drawField(Graphics g) {
		UserColor color;
		int normalTroops = getNormalTroops();
		int badassTroops = getBadassTroops();
		boolean neutrals = false;
		if (affiliation == null) {
			neutrals = true;
			if (troops.isEmpty()) {
				//neutral empty field
				color = UserColor.EMPTY;
			}
			else {
				//neutral field
				color = UserColor.NEUTRAL;
			}
		}
		else {
			color = board.getGame().getColorManager().getUserColors().get(affiliation);
		}
		//set font and color
		//g.setFont(new Font("Arial", Font.PLAIN, normalTroopImage.getHeight()));
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		//draw the troop images and numbers
		if (neutrals && normalTroops > 0) {
			g.drawImage(neutralTroopImage, (int) normalTroopsPosition.getX(), (int) normalTroopsPosition.getY(), null);
			g.drawString(Integer.toString(normalTroops), (int) normalTroopsPosition.getX()+neutralTroopImage.getWidth(), (int) normalTroopsPosition.getY()+neutralTroopImage.getHeight()-15);
		}
		else {
			if (normalTroops > 0) {
				g.drawImage(normalTroopImage, (int) normalTroopsPosition.getX(), (int) normalTroopsPosition.getY(), null);
				g.drawString(Integer.toString(normalTroops), (int) normalTroopsPosition.getX()+normalTroopImage.getWidth(), (int) normalTroopsPosition.getY()+normalTroopImage.getHeight()-15);				
			}
			if (badassTroops > 0) {
				g.drawImage(badassTroopImage, (int) badassTroopsPosition.getX(), (int) badassTroopsPosition.getY(), null);
				g.drawString(Integer.toString(badassTroops), (int) badassTroopsPosition.getX()+badassTroopImage.getWidth(), (int) badassTroopsPosition.getY()+badassTroopImage.getHeight()-15);				
			}
		}
		//draw the building image
		g.drawImage(building.getImage(), (int) buildingPosition.getX(), (int) buildingPosition.getY(), null);
		//draw the user color
		g.setColor(color.getColor());
		g.fillOval((int) playerMarkerPosition.getX(), (int) playerMarkerPosition.getY(), 15, 15);
		//draw the command
		if (command != null) {
			g.drawImage(command.getImage(), (int) commandMarkerPosition.getX(), (int) commandMarkerPosition.getY(), 50, 50, null); 
		}
	}
	
	/**
	 * Count the number of normal troops (Bandits or Skags)
	 * 
	 * @return
	 * 		The normal troops.
	 */
	public int getNormalTroops() {
		int normalTroops = 0;
		//count the troops with odd strength (only strength 1 and 2 possible)
		for (int i = 0; i < troops.size(); i++) {
			normalTroops += (troops.get(i).getStrength() & 1);//if (strength % 2 == 1) {n++}
		}
		return normalTroops;
	}
	/**
	 * Count the number of badass troops (Crimson Raiders)
	 * 
	 * @return
	 * 		The badass troops.
	 */
	public int getBadassTroops() {
		int badassTroops = 0;
		//count the troops with even strength (only strength 1 and 2 possible)
		for (int i = 0; i < troops.size(); i++) {
			badassTroops += (troops.get(i).getStrength() & 2);//if (strength % 2 == 0) {n += 2}
		}
		return badassTroops/2;
	}
	
	public void addNormalTroops(int num) {
		for (int i = 0; i < num; i++) {
			troops.add(new Bandit());
		}
	}
	public void removeNormalTroops(int num) throws IllegalArgumentException {
		int removedTroops = 0;
		if (getNormalTroops() < num) {
			throw new IllegalArgumentException("Not enough normal troops to remove.");
		}
		for (int i = 0; i < troops.size(); i++) {
			if (removedTroops < num && troops.get(i).getStrength() == 1) {
				troops.remove(i);
				i--;
				removedTroops++;
			}
		}
	}
	public void addBadassTroops(int num) {
		for (int i = 0; i < num; i++) {
			troops.add(new CrimsonRaider());
		}
	}
	public void removeBadassTroops(int num) throws IllegalArgumentException {
		int removedTroops = 0;
		if (getBadassTroops() < num) {
			throw new IllegalArgumentException("Not enough badass troops to remove.");
		}
		for (int i = 0; i < troops.size(); i++) {
			if (removedTroops < num && troops.get(i).getStrength() == 2) {
				troops.remove(i);
				i--;
				removedTroops++;
			}
		}
	}
	
	/**
	 * Get the strength of the field only by the troops (no additional defense).
	 * 
	 * @return
	 * 		The troop strength.
	 */
	public int getTroopStrength() {
		int strength = 0;
		for (Troop troop : troops) {
			strength += troop.getStrength();
		}
		return strength;
	}
	/**
	 * Get the strength of this field with the additional defensive strength.
	 * 
	 * @return
	 * 		The total defense strength of this field.
	 */
	public int getDefenceStrength() {
		return getTroopStrength() + building.getAdditionalDefence();
	}
	
	/**
	 * Checks whether this field can get a command.
	 * Fields can get commands if they host troops or special buildings (Arschgauls Palace or MoxxisTavern).
	 */
	public boolean isCommandPlaceable() {
		return !troops.isEmpty() || building.isCommandExecutable();
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Point getCommandMarkerPosition() {
		return commandMarkerPosition;
	}
	public void setCommandMarkerPosition(Point commandMarkerPosition) {
		this.commandMarkerPosition = commandMarkerPosition;
	}
	
	public Point getPlayerMarkerPosition() {
		return playerMarkerPosition;
	}
	public void setPlayerMarkerPosition(Point playerMarkerPosition) {
		this.playerMarkerPosition = playerMarkerPosition;
	}
	
	public static BufferedImage getNormalTroopImage() {
		return normalTroopImage;
	}
	public static BufferedImage getBadassTroopImage() {
		return badassTroopImage;
	}
	public static BufferedImage getNeutralTroopImage() {
		return neutralTroopImage;
	}
	
	public List<Field> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(List<Field> neighbours) {
		this.neighbours = neighbours;
	}
	
	public User getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(User affiliation) {
		this.affiliation = affiliation;
	}
	
	public List<Troop> getTroops() {
		return troops;
	}
	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}
	
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public Point getFieldPosition() {
		return fieldPosition;
	}
	public void setFieldPosition(Point fieldPosition) {
		this.fieldPosition = fieldPosition;
	}
	
	public Point getNormalTroopsPosition() {
		return normalTroopsPosition;
	}
	public void setNormalTroopsPosition(Point normalTroopsPosition) {
		this.normalTroopsPosition = normalTroopsPosition;
	}
	
	public Point getBadassTroopsPosition() {
		return badassTroopsPosition;
	}
	public void setBadassTroopsPosition(Point badassTroopsPosition) {
		this.badassTroopsPosition = badassTroopsPosition;
	}
	
	public Point getBuildingPosition() {
		return buildingPosition;
	}
	public void setBuildingPosition(Point buildingPosition) {
		this.buildingPosition = buildingPosition;
	}
	
	public Color getFieldColor() {
		return fieldColor;
	}
	public void setFieldColor(Color fieldColor) {
		this.fieldColor = fieldColor;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}