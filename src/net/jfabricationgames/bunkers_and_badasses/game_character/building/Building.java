package net.jfabricationgames.bunkers_and_badasses.game_character.building;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

public abstract class Building implements Serializable {
	
	private static final long serialVersionUID = 4515243178671362606L;
	
	//this variables are set to the loaded values from the database
	protected int recruitableTroops;
	protected int ammoMining;
	protected int creditMining;
	protected int eridiumMining;
	protected int landMineVictims;
	protected int additionalDefence;
	protected int moveDistance;
	protected int points;//winning points for holding bases
	
	protected int[] buildingPrice;//the price (credits, ammo, eridium) to build the building
	protected int[] extensionPrice;//the prices (credits, ammo, eridium) to extend the building
	
	//this variables are fixed
	protected boolean attackable;
	protected boolean badassTroopsRecruitable;
	protected boolean advanced;
	protected boolean extendable;
	protected boolean extended;
	protected boolean commandExecutable;
	
	protected int buildingId;
	protected int extendedBuildingId;
	
	protected String imagePath;
	protected String expandedImagePath;
	
	protected transient BufferedImage image;
	
	protected String name;
	
	protected static BuildingStorage storage;
	
	protected static ImageLoader imageLoader;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/buildings/");
	}
	
	public Building() {
		recruitableTroops = 1;
		ammoMining = 0;
		creditMining = 0;
		eridiumMining = 0;
		landMineVictims = 0;
		additionalDefence = 0;
		moveDistance = 1;
		points = 0;
		extensionPrice = new int[] {-1, -1, -1};
		attackable = true;
		badassTroopsRecruitable = false;
		advanced = false;
		extendable = false;
		extended = false;
		commandExecutable = false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Building) {
			Building building = (Building) obj;
			return recruitableTroops == building.recruitableTroops && ammoMining == building.ammoMining && creditMining == building.creditMining && 
					eridiumMining == building.eridiumMining && landMineVictims == building.landMineVictims && additionalDefence == building.additionalDefence && 
					moveDistance == building.moveDistance && points == building.points && attackable == building.attackable && 
					badassTroopsRecruitable == building.badassTroopsRecruitable && advanced == building.advanced && extendable == building.extendable;
		}
		else {
			return super.equals(obj);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public abstract Building newInstance();
	
	protected void loadVariables() {
		int[][] variables = storage.getBuildingVariables();
		int[][] buildingPricesStorage = storage.getBuildingPrices();
		int[][] buildingExtensionPrices = storage.getBuildingExtensionPrices();
		recruitableTroops = variables[buildingId][BuildingStorage.RECRUITABLE_TROOPS];
		ammoMining = variables[buildingId][BuildingStorage.MINING_AMMO];
		creditMining = variables[buildingId][BuildingStorage.MINING_CREDITS];
		eridiumMining = variables[buildingId][BuildingStorage.MINING_ERIDIUM];
		landMineVictims = variables[buildingId][BuildingStorage.LAND_MINE_VICTIMS];
		additionalDefence = variables[buildingId][BuildingStorage.ADDITIONAL_DEFENCE];
		moveDistance = variables[buildingId][BuildingStorage.MOVE_DISTANCE];
		points = variables[buildingId][BuildingStorage.POINTS];
		extensionPrice = buildingExtensionPrices[buildingId];
		buildingPrice = buildingPricesStorage[buildingId];
	}
	
	public void loadImage() {
		if (extended) {
			if (expandedImagePath != null) {
				image = imageLoader.loadImage(expandedImagePath);			
			}
		}
		else {
			if (imagePath != null) {
				image = imageLoader.loadImage(imagePath);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Extend the building.
	 * The default implementation does nothing because not all buildings can be extended.
	 */
	public void extend() {
		buildingId = extendedBuildingId;
		extended = true;
		loadVariables();
		loadImage();
	}
	
	/**
	 * Serialize the object without the transient parts (the images that are not stored in the database)
	 * 
	 * @param out
	 * 		The ObjectOutputStream that is used to write down the file.
	 */
	protected void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
	/**
	 * Read the object without the transient parts (the images that are not stored in the database)
	 * 
	 * @param in
	 * 		The ObjectInputStream that is used to read the file.
	 */
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public static BuildingStorage getStorage() {
		return storage;
	}
	public static void setStorage(BuildingStorage storage) {
		Building.storage = storage;
	}
	
	public int getEridiumMining() {
		return eridiumMining;
	}
	public void setEridiumMining(int eridiumMining) {
		this.eridiumMining = eridiumMining;
	}
	public int getRecruitableTroops() {
		return recruitableTroops;
	}
	public int getAmmoMining() {
		return ammoMining;
	}
	public int getCreditMining() {
		return creditMining;
	}
	public int getLandMineVictims() {
		return landMineVictims;
	}
	public int getAdditionalDefence() {
		return additionalDefence;
	}
	public int getMoveDistance() {
		return moveDistance;
	}
	public int getPoints() {
		return points;
	}
	public int[] getBuildingPrice() {
		return buildingPrice;
	}
	public int[] getExtensionPrice() {
		return extensionPrice;
	}
	public boolean isAttackable() {
		return attackable;
	}
	public boolean isBadassTroopsRecruitable() {
		return badassTroopsRecruitable;
	}
	public boolean isAdvanced() {
		return advanced;
	}
	public boolean isExtendable() {
		return extendable;
	}
	public boolean isExtended() {
		return extended;
	}
	public boolean isCommandExecutable() {
		return commandExecutable;
	}
}