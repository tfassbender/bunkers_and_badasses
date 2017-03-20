package net.jfabricationgames.bunkers_and_badasses.game_command;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;

public abstract class Command implements Serializable {
	
	private static final long serialVersionUID = 3756160188138590020L;
	
	protected String name;
	
	protected boolean executable;
	protected boolean ammoNeeded;
	protected boolean creditsNeeded;
	protected boolean removable;//can be removed by a raid command
	protected boolean support;
	protected List<Class<? extends Building>> executionBuildings;//a list of buildings that can execute the command without troops
	protected BufferedImage image;
	protected int identifier;
	
	protected static ImageLoader imageLoader;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/command_markers/");
	}
	
	public Command() {
		executionBuildings = new ArrayList<Class<? extends Building>>();
	}
	
	public abstract Command getInstance();
	
	public boolean isExecutable() {
		return executable;
	}
	public boolean isAmmoNeeded() {
		return ammoNeeded;
	}
	public boolean isCreditsNeeded() {
		return creditsNeeded;
	}
	public boolean isRemovable() {
		return removable;
	}
	public boolean isSupport() {
		return support;
	}
	public List<Class<? extends Building>> getExecutionBuildings() {
		return executionBuildings;
	}
	public BufferedImage getImage() {
		return image;
	}
	public int getIdentifier() {
		return identifier;
	}
	
	public String getName() {
		return name;
	}
}