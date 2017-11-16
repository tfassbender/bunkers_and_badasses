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

import net.jfabricationgames.bunkers_and_badasses.error.MovementException;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Stalker;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Thresher;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Troop;
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
	
	private transient Robot robot;
	
	private Game game;
	
	public Board() {
		try {
			robot = new Robot();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		storeImage = false;
	}
	
	/**
	 * Merge the data from the new board into this board.
	 * 
	 * @param board
	 * 		The new board.
	 */
	public void merge(Board board) {
		this.boardId = board.getBoardId();
		//this.fields = board.getFields();//images in Field are static and don't need to be reloaded
		//this.regions = board.getRegions();
		//name, player min/max, robot and image wrappers don't need to be changed
		//this.game = board.getGame();
		//reload the images of possible buildings on the field
		for (Field field : fields) {
			field.merge(board.getFieldByName(field.getName()));
			field.getBuilding().loadImage();
		}
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
	public void moveTroops(Field start, Field end, int normalTroops, int badassTroops) throws MovementException {
		if (start.getAffiliation() != null && end.getAffiliation() != null && !start.getAffiliation().equals(end.getAffiliation()) && end.getTroopStrength() != 0) {
			throw new MovementException("Can't move troops to a non empty enemy field (" + start.getName() + " -> " + end.getName() + ").");
		}
		else if (start.getNormalTroops() < normalTroops || start.getBadassTroops() < badassTroops) {
			throw new MovementException("Can't move more troops than there are in the field(" + start.getName() + " -> " + end.getName() + ").");
		}
		/*if (end.getAffiliation() != null || !end.getAffiliation().equals(start.getAffiliation())) {
			end.setAffiliation(start.getAffiliation());
		}*/
		if (start.getAffiliation() != null) {
			//move player troops
			end.setAffiliation(start.getAffiliation());
			start.removeNormalTroops(normalTroops);
			start.removeBadassTroops(badassTroops);
			end.addNormalTroops(normalTroops);
			end.addBadassTroops(badassTroops);			
		}
		else {
			//move neutral troops
			end.addNeutralTroops(normalTroops, Troop.getNeutralTroopType(start.getTroops().get(0)));
			start.removeNormalTroops(normalTroops);
		}
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
		if (baseImage == null) {
			return null;
		}
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
	 * Add the neutral troops when the game is started and return a Map containing the information.
	 * 
	 * Half of the neutral fields get neutral troops. The fields are chosen randomly.
	 * The number of neutral troops is chosen randomly from 1 to 3.
	 * 
	 * @return
	 * 		A Map of the neutral troops that are added to the board.
	 */
	public Map<Field, int[]> addNeutralTroops() {
		List<Field> neutralFields = new ArrayList<Field>();
		Map<Field, int[]> neutralTroops = new HashMap<Field, int[]>();
		for (Field field : fields) {
			if (field.getAffiliation() == null) {
				neutralFields.add(field);
			}
		}
		//remove half of the neutral fields from the list
		int halfFields = neutralFields.size()/2;
		for (int i = 0; i < halfFields; i++) {
			int random = (int) (Math.random()*neutralFields.size());
			neutralFields.remove(random);
		}
		//add neutral troops to the selected fields
		for (Field field : neutralFields) {
			int type = (int) (Math.random()*3 + 1);
			int random = 1;
			switch (type) {
				case 0:
					//Skags
					random = (int) (Math.random()*3 + 1);
					break;
				case 1:
				case 2:
					//Threshers, Stalkers
					random = (int) (Math.random() * 2 + 1);
					break;
			}
			field.addNeutralTroops(random, type);
			neutralTroops.put(field, new int[] {random, type});
		}
		return neutralTroops;
	}
	/**
	 * Move the neutral troops. All the neutral troops may be moved to another empty, neutral field. All troops are moved individually.
	 * 
	 * Skags: don't move
	 * 
	 * Threshers: move single fields or increase their number by one
	 * 		move chance: 1/3 (targets are equally distributed) (2/3 chance for each thresher to rely move)
	 * 		increase chance: 1/3 (always by one on one field if there are less than 5 on this field)
	 * 		stay chance: 1/3 
	 * 
	 * Stalkers: move any distance (also through player fields)
	 * 		move chance: 2/3
	 * 		stay chance: 1/3
	 */
	public void moveNeutralTroops() {
		List<Field> thresherFields = new ArrayList<Field>();//all fields that contain threshers
		List<Field> stalkerFields = new ArrayList<Field>();//all fields that contain stalkers
		List<Field> neutralFields = new ArrayList<Field>();//all fields that contain stalkers or neutral fields
		for (Field field : fields) {
			if (field.getAffiliation() == null) {
				if (field.getTroopStrength() > 0) {
					if (field.getTroops().get(0) instanceof Thresher) {
						thresherFields.add(field);
					}
					else if (field.getTroops().get(0) instanceof Stalker) {
						stalkerFields.add(field);
						neutralFields.add(field);
					}
				}
				else {
					neutralFields.add(field);
				}
			}
		}
		//move threshers first
		double random;
		for (Field field : thresherFields) {
			random = Math.random();
			if (random < 0.33) {
				//move (each thresher individually)
				List<Field> targetFields = new ArrayList<Field>();
				for (Field neighbour : field.getNeighbours()) {
					if (neighbour.getAffiliation() == null) {
						if (neighbour.getTroopStrength() == 0) {
							targetFields.add(neighbour);
						}
						else if (neighbour.getTroops().get(0) instanceof Thresher) {
							targetFields.add(neighbour);
						}
					}
				}
				if (!targetFields.isEmpty()) {
					for (int i = 0; i < field.getTroops().size(); i++) {
						random = Math.random();
						if (random < 0.66) {
							//move in 2 of 3 cases
							int toField = (int) (Math.random() * targetFields.size());
							moveTroops(field, targetFields.get(toField), 1, 0);
						}
					}					
				}
			}
			else if (random < 0.66) {
				//increase if there are less than 5 threshers on this field
				if (field.getTroops().size() < 5) {
					field.addNeutralTroops(1, 1);					
				}
			}
			//else do nothing
		}
		for (Field field : stalkerFields) {
			random = Math.random();
			if (random < 0.66) {
				//move (each stalker individually with chance 2/3)
				for (int i = 0; i < field.getTroops().size(); i++) {
					random = Math.random();
					if (random < 0.66) {
						int toField = (int) (Math.random() * neutralFields.size());
						moveTroops(field, neutralFields.get(toField), 1, 0);
					}
				}
			}
			//else do nothing
		}
	}
	/**
	 * Add the neutral troops by the info of another player.
	 * 
	 * @param neutralTroops
	 * 		The neutral troops that are to be added to the board.
	 */
	public void addNeutralTroops(Map<Field, int[]> neutralTroops) {
		for (Field field : neutralTroops.keySet()) {
			int[] troops = neutralTroops.get(field);
			getFieldByName(field.getName()).addNeutralTroops(troops[0], troops[1]);
		}
	}
	
	/**
	 * Get the field at the current mouse position by it's color.
	 * Only works when the mouse is over a field of the board.
	 * 
	 * @return
	 * 		The field the user points to.
	 */
	public Field getFieldAtMousePosition() {
		if (robot == null) {
			try {
				robot = new Robot();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
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
			if (f.getAffiliation() != null && f.getAffiliation().equals(user)) {
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
	 * Remove all the commands that are left on the field at the end of the turn.
	 */
	public void removeAllCommands() {
		for (Field field : fields) {
			field.setCommand(null);
		}
	}
	
	/**
	 * Remove the executable commands of a user when he passes.
	 * 
	 * @param user
	 * 		The user who's commands are removed. 
	 */
	public void removeExecutableCommands(User user) {
		for (Field field : fields) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(user) && field.getCommand() != null && field.getCommand().isExecutable()) {
				field.setCommand(null);
			}
		}
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
		storeImage = false;//TODO
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
		return name + " (" + players + " Spieler)";
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