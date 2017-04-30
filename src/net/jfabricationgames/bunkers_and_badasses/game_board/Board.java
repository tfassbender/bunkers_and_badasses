package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Board implements Serializable {
	
	private static final long serialVersionUID = 4537459302664184784L;
	
	private int boardId;//identify the board to load it from the server
	
	private List<Field> fields;
	private List<Region> regions;
	private transient BufferedImage baseImage;
	private String name;
	
	private int playersMin;
	private int playersMax;
	
	//Decide whether the image shall be wrapped as ImageIcon and serialized
	private boolean storeImage;
	private ImageIcon imageWrapper;
	
	private Robot robot;
	
	private Game game;
	
	public Board() {
		try {
			robot = new Robot();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		storeImage = true;
	}
	
	/**
	 * Merge the data from the new board into this board.
	 * 
	 * @param board
	 * 		The new board.
	 */
	public void merge(Board board) {
		this.boardId = board.getBoardId();
		this.fields = board.getFields();//images in Field are static and don't need to be reloaded
		this.regions = board.getRegions();
		//name, player min/max, robot and image wrappers don't need to be changed
		this.game = board.getGame();
	}
	
	/**
	 * Move troops from one field to another without checking for fights, ...
	 * 
	 * @param start
	 * 		The field the troops are taken from.
	 * 
	 * @param end
	 * 		The field the troops are moved to.
	 * 
	 * @param normalTroops
	 * 		The number of normal troops that move to the new field.
	 * 
	 * @param badassTroops
	 * 		The number of badasses that move to the new field.
	 * 
	 * @throws IllegalArgumentException
	 * 		An IllegalArgumentException is thrown if anything in the input is not correct.
	 */
	public void moveTroops(Field start, Field end, int normalTroops, int badassTroops) throws IllegalArgumentException {
		//TODO
	}
	
	/**
	 * Create the field image with the basic image, the troops and buildings.
	 * The image is created in original size. The scaling for the overview is done by the ImagePanel.
	 * 
	 * @return
	 * 		The board image.
	 */
	public BufferedImage displayBoard() {
		//create a new BufferedImage and draw the basic image on it.
		BufferedImage board = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), baseImage.getType());
	    Graphics g = board.getGraphics();
	    g.drawImage(baseImage, 0, 0, null);
	    //draw the images for troops and buildings
	    for (Field field : fields) {
	    	field.drawField(g);
	    }
	    g.dispose();
		return board;
	}
	
	/**
	 * Get the field at the current mouse position by it's color.
	 * Only works when the mouse is over a field of the board.
	 * 
	 * @return
	 * 		The field the user points to.
	 */
	public Field getFieldAtMousePosition() {
		Point point = MouseInfo.getPointerInfo().getLocation();
		Color color = robot.getPixelColor((int) point.getX(), (int) point.getY());
		return getFieldByColor(color);
	}
	
	/**
	 * Get a Field by the color the player clicked on the board.
	 * The color is chosen by the RGB code with a deviation of 3 on every component.
	 * 
	 * @param color
	 * 		The color underneath the mouse when the player clicked the board.
	 * 
	 * @return
	 * 		The Field the player has chosen.
	 */
	public Field getFieldByColor(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int rc;
		int gc;
		int bc;
		int deviation = 3;
		for (Field field : fields) {
			rc = field.getFieldColor().getRed();
			gc = field.getFieldColor().getGreen();
			bc = field.getFieldColor().getBlue();
			if (Math.abs(rc - r) < deviation && Math.abs(gc - g) < deviation && Math.abs(bc - b) < deviation) {
				return field;
			}
		}
		return null;
	}
	
	/**
	 * Get a Field by it's field name.
	 * 
	 * @param name
	 * 		The name that is searched for.
	 * 
	 * @return
	 * 		The field with the searched name.
	 */
	public Field getFieldByName(String name) {
		Field field = null;
		for (Field f : fields) {
			if (f.getName().equals(name)) {
				field = f;
			}
		}
		return field;
	}
	
	/**
	 * Get a list of all fields of a player.
	 * 
	 * @param user
	 * 		The user who owns the fields.
	 * 
	 * @return
	 * 		A list of all fields the user owns.
	 */
	public List<Field> getUsersFields(User user) {
		List<Field> usersFields = new ArrayList<Field>();
		for (Field f : fields) {
			if (f.getAffiliation().equals(user)) {
				usersFields.add(f);
			}
		}
		return usersFields;
	}
	
	/**
	 * Get a list of all regions that are completely controlled by a player.
	 * 
	 * @param user
	 * 		The player who controlled the regions.
	 * 
	 * @return
	 * 		A list of all regions controlled by this user.
	 */
	public List<Region> getUsersRegions(User user) {
		List<Region> usersRegions = new ArrayList<Region>();
		Map<Field, Boolean> regionsFields;
		for (Region region : regions) {
			regionsFields = new HashMap<Field, Boolean>();
			//initialize all fields of this region with false
			for (Field field : region.getFields()) {
				regionsFields.put(field, false);
			}
			//add all fields and override all false values
			for (Field field : getUsersFields(user)) {
				regionsFields.put(field, true);
			}
			//if a false value is found in the list the region is not controlled by the player
			boolean regionControlled = true;
			for (Field field : regionsFields.keySet()) {
				regionControlled &= regionsFields.get(field);
			}
			if (regionControlled) {
				usersRegions.add(region);
			}
		}
		return usersRegions;
	}
	
	/**
	 * Get all buildings of a player (except the EmptyBuilding instances).
	 * 
	 * @param user
	 * 		The user that owns the buildings.
	 * 
	 * @return
	 * 		The user's buildings.
	 */
	public List<Building> getUsersBuildings(User user) {
		List<Building> buildings = new ArrayList<Building>();
		List<Field> fields = getUsersFields(user);
		for (Field f : fields) {
			if (!(f.getBuilding() instanceof EmptyBuilding)) {
				buildings.add(f.getBuilding());
			}
		}
		return buildings;
	}
	
	/**
	 * Write the object to a serialized file. The not-serializabel parts (the buffered image) is stored separately at the end of the file.
	 * This method is somehow used by the Serializable interface.
	 * 
	 * WARNING: Do not change this method. Other signature would cause Serializable to not use this method.
	 * 
	 * @param out
	 * 		The ObjectOutputStream that is used to write down the file.
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		if (storeImage) {
			imageWrapper = new ImageIcon(baseImage);
		}
		else {
			imageWrapper = null;
		}
		out.defaultWriteObject();//serialize the object but the transient fields (the baseImage)
		//out.writeInt(1);//write the number of images down (needed also if there is only one)
		//ImageIO.write(baseImage, "png", out);//write the image at the end of the file
	}
	/**
	 * Read the object from a serialized file. The not-serializabel parts (the buffered image) is stored separately at the end of the file.
	 * This method is somehow used by the Serializable interface.
	 * 
	 * WARNING: Do not change this method. Other signature would cause Serializable to not use this method.
	 * 
	 * @param in
	 * 		The ObjectInputStream that is used to read the file.
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();//load all the fields that could be serialized (not the transient fields like baseImage)
		if (imageWrapper != null) {
			baseImage = loadBaseImage(imageWrapper);
		}
		imageWrapper = null;
		//in.readInt();//read the number of stored images (its only one so no need to store the value)
		//baseImage = ImageIO.read(in);//read the image file
	}
	
	/**
	 * Create a BufferedImage from an ImageIcon by drawing it.
	 *  
	 * @param imageIcon
	 * 		The ImageIcon.
	 * 
	 * @return
	 * 		The BufferedImage.
	 */
	private BufferedImage loadBaseImage(ImageIcon imageIcon) {
		BufferedImage img = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		return img;
	}
	
	@Override
	public String toString() {
		String players;
		if (playersMin == playersMax) {
			players = Integer.toString(playersMin);
		}
		else {
			players = playersMin + "-" + playersMax;
		}
		return name + " (" + players + " Players)";
	}
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public BufferedImage getBaseImage() {
		return baseImage;
	}
	public void setBaseImage(BufferedImage baseImage) {
		this.baseImage = baseImage;
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPlayersMin() {
		return playersMin;
	}
	public void setPlayersMin(int playersMin) {
		this.playersMin = playersMin;
	}
	
	public int getPlayersMax() {
		return playersMax;
	}
	public void setPlayersMax(int playersMax) {
		this.playersMax = playersMax;
	}
	
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	
	public boolean isStoreImage() {
		return storeImage;
	}
	public void setStoreImage(boolean storeImage) {
		this.storeImage = storeImage;
	}
}