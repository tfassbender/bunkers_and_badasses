package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_character.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.Troop;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameFrame;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Field implements Serializable {
	
	private static final long serialVersionUID = 1904454857903005846L;
	
	private List<Field> neighbours;
	private User affiliation;
	private List<Troop> troops;
	private Building building;
	private String name;
	private Region region;
	
	private Point fieldPosition;
	private Point normalTroupsPosition;
	private Point badassTroupsPosition;
	private Point buildingPosition;
	
	private Color fieldColor;
	
	private static BufferedImage normalTroopImage;
	private static BufferedImage badassTroopImage;
	private static BufferedImage neutralTroopImage;
	
	static {
		//load the troop images
		normalTroopImage = GameFrame.getImageLoader().loadImage("troops/bandit_3_small.png");
		badassTroopImage = GameFrame.getImageLoader().loadImage("troops/lance_4_small.png");
		neutralTroopImage = GameFrame.getImageLoader().loadImage("troops/skag_1_small.png");
	}

	public Field() {
		neighbours = new ArrayList<Field>();
		troops = new ArrayList<Troop>();
	}
	
	@Override
	public String toString() {
		return name;
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
	
	public Point getFieldPosition() {
		return fieldPosition;
	}
	public void setFieldPosition(Point fieldPosition) {
		this.fieldPosition = fieldPosition;
	}
	
	public Point getNormalTroupsPosition() {
		return normalTroupsPosition;
	}
	public void setNormalTroupsPosition(Point normalTroupsPosition) {
		this.normalTroupsPosition = normalTroupsPosition;
	}
	
	public Point getBadassTroupsPosition() {
		return badassTroupsPosition;
	}
	public void setBadassTroupsPosition(Point badassTroupsPosition) {
		this.badassTroupsPosition = badassTroupsPosition;
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