package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;

public class Board implements Serializable {
	
	private static final long serialVersionUID = 4537459302664184784L;
	
	private List<Field> fields;
	private List<Region> regions;
	private transient BufferedImage baseImage;
	private String name;
	
	/**
	 * Create the field image with the basic image, the troops and buildings.
	 * The image is created in original size. The scaling for the overview is done by the ImagePanel.
	 * 
	 * @return
	 * 		The board image.
	 */
	public BufferedImage displayField() {
		//create a new BufferedImage and draw the basic image on it.
		BufferedImage board = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), baseImage.getType());
	    Graphics g = board.getGraphics();
	    g.drawImage(baseImage, 0, 0, null);
	    //draw the images for troops and buildings
	    for (Field field : fields) {
	    	
	    }
	    g.dispose();
		return board;
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
			if (rc >= r - deviation && rc <= r + deviation && gc >= g - deviation && gc <= g + deviation && bc >= b - deviation && bc <= b + deviation) {
				return field;
			}
		}
		return null;
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
		out.defaultWriteObject();//serialize the object but the transient fields (the baseImage)
		out.writeInt(1);//write the number of images down (needed also if there is only one)
		ImageIO.write(baseImage, "png", out);//write the image at the end of the file
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
		in.readInt();//read the number of stored images (its only one so no need to store the value)
		baseImage = ImageIO.read(in);//read the image file
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
}