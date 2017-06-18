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
	protected boolean removable;//can be removed by a raid command
	protected boolean support;
	protected List<Class<? extends Building>> executionBuildings;//a list of buildings that can execute the command without troops
	protected transient BufferedImage image;
	protected int identifier;
	protected String imagePath;
	
	protected int costsCredits;
	protected int costsAmmo;
	protected boolean costDependencyCredits;//indicates whether the costs depend on the number of troops
	protected boolean costDependencyAmmo;
	
	protected static ImageLoader imageLoader;
	
	protected static CommandStorage storage;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/command_markers/");
	}
	
	public Command() {
		executionBuildings = new ArrayList<Class<? extends Building>>();
	}
	
	protected void loadVariables() {
		costsCredits = storage.getCosts()[identifier][CommandStorage.CREDITS];
		costsAmmo = storage.getCosts()[identifier][CommandStorage.AMMO];
		costDependencyCredits = storage.getDependencies()[identifier][CommandStorage.CREDITS];
		costDependencyAmmo = storage.getDependencies()[identifier][CommandStorage.AMMO];
	}
	
	public void loadImage() {
		image = imageLoader.loadImage(imagePath);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public abstract Command getInstance();
	
	public boolean isExecutable() {
		return executable;
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
	
	public int getCostsCredits() {
		return costsCredits;
	}
	public void setCostsCredits(int costsCredits) {
		this.costsCredits = costsCredits;
	}
	public int getCostsAmmo() {
		return costsAmmo;
	}
	public void setCostsAmmo(int costsAmmo) {
		this.costsAmmo = costsAmmo;
	}
	public boolean isCostDependencyCredits() {
		return costDependencyCredits;
	}
	public void setCostDependencyCredits(boolean costDependencyCredits) {
		this.costDependencyCredits = costDependencyCredits;
	}
	public boolean isCostDependencyAmmo() {
		return costDependencyAmmo;
	}
	public void setCostDependencyAmmo(boolean costDependencyAmmo) {
		this.costDependencyAmmo = costDependencyAmmo;
	}
	
	public String getName() {
		return name;
	}
	
	public static CommandStorage getStorage() {
		return storage;
	}
	public static void setStorage(CommandStorage storage) {
		Command.storage = storage;
	}
}