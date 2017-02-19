package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_frame.TurnExecutionFrame;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class TurnExecutionManager {
	
	public static final int RESOURCE_MINING_TYPE_CREDITS = 1;
	public static final int RESOURCE_MINING_TYPE_AMMO = 2;
	public static final int RESOURCE_MINING_TYPE_ERIDIUM = 3;
	
	public static final int BUILDING_TYPE_BUILD = 1;
	public static final int BUILDING_TYPE_UPGRADE = 2;
	public static final int BUILDING_TYPE_BREAK_DOWN = 3;
	
	private Board board;
	private transient User localUser;
	private Field selection;
	private Field target;
	private int resourceMiningType;
	private int buildType;
	private int movingTroopsNormal;
	private int movingTroopsBadass;
	private Building building;
	private UserResourceManager userResourceManager;
	private GameTurnBonusManager gameTurnBonusManager;
	private GameTurnGoalManager gameTurnGoalManager;
	private PointManager pointManager;
	private transient TurnExecutionFrame turnExecutionFrame;
	
	public TurnExecutionManager(User localUser, UserResourceManager userResourceManager, GameTurnBonusManager gameTurnBonusManager, 
			GameTurnGoalManager gameTurnGoalManager, PointManager pointManager) {
		this.localUser = localUser;
		this.userResourceManager = userResourceManager;
		this.gameTurnBonusManager = gameTurnBonusManager;
		this.gameTurnGoalManager = gameTurnGoalManager;
		this.pointManager = pointManager;
	}
	
	public void execute() {
		//TODO
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public User getLocalUser() {
		return localUser;
	}
	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}
	
	public Field getSelection() {
		return selection;
	}
	public void setSelection(Field selection) {
		this.selection = selection;
	}
	
	public Field getTarget() {
		return target;
	}
	public void setTarget(Field target) {
		this.target = target;
	}
	
	public int getResourceMiningType() {
		return resourceMiningType;
	}
	public void setResourceMiningType(int resourceMiningType) {
		this.resourceMiningType = resourceMiningType;
	}
	
	public int getBuildType() {
		return buildType;
	}
	public void setBuildType(int buildType) {
		this.buildType = buildType;
	}
	
	public int getMovingTroopsNormal() {
		return movingTroopsNormal;
	}
	public void setMovingTroopsNormal(int movingTroopsNormal) {
		this.movingTroopsNormal = movingTroopsNormal;
	}
	
	public int getMovingTroopsBadass() {
		return movingTroopsBadass;
	}
	public void setMovingTroopsBadass(int movingTroopsBadass) {
		this.movingTroopsBadass = movingTroopsBadass;
	}
	
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public UserResourceManager getUserResourceManager() {
		return userResourceManager;
	}
	public void setUserResourceManager(UserResourceManager userResourceManager) {
		this.userResourceManager = userResourceManager;
	}
	
	public GameTurnBonusManager getGameTurnBonusManager() {
		return gameTurnBonusManager;
	}
	public void setGameTurnBonusManager(GameTurnBonusManager gameTurnBonusManager) {
		this.gameTurnBonusManager = gameTurnBonusManager;
	}
	
	public GameTurnGoalManager getGameTurnGoalManager() {
		return gameTurnGoalManager;
	}
	public void setGameTurnGoalManager(GameTurnGoalManager gameTurnGoalManager) {
		this.gameTurnGoalManager = gameTurnGoalManager;
	}
	
	public PointManager getPointManager() {
		return pointManager;
	}
	public void setPointManager(PointManager pointManager) {
		this.pointManager = pointManager;
	}
	
	public TurnExecutionFrame getTurnExecutionFrame() {
		return turnExecutionFrame;
	}
	public void setTurnExecutionFrame(TurnExecutionFrame turnExecutionFrame) {
		this.turnExecutionFrame = turnExecutionFrame;
	}
}