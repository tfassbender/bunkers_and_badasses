package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.GameVariableStorage;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.BuildingStorage;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.TroopStorage;
import net.jfabricationgames.bunkers_and_badasses.game_command.CommandStorage;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusStorage;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class DynamicVariableRequestMessage implements JFGServerMessage, JFGClientMessage {
	
	private static final long serialVersionUID = 5552024155044358210L;
	
	private BuildingStorage buildingStorage;
	private TroopStorage troopStorage;
	private CommandStorage commandStorage;
	private GameVariableStorage gameStorage;
	private TurnBonusStorage turnBonusStorage;
	
	public BuildingStorage getBuildingStorage() {
		return buildingStorage;
	}
	public void setBuildingStorage(BuildingStorage buildingStorage) {
		this.buildingStorage = buildingStorage;
	}
	
	public TroopStorage getTroopStorage() {
		return troopStorage;
	}
	public void setTroopStorage(TroopStorage troopStorage) {
		this.troopStorage = troopStorage;
	}
	
	public CommandStorage getCommandStorage() {
		return commandStorage;
	}
	public void setCommandStorage(CommandStorage commandStorage) {
		this.commandStorage = commandStorage;
	}
	
	public GameVariableStorage getGameStorage() {
		return gameStorage;
	}
	public void setGameStorage(GameVariableStorage gameStorage) {
		this.gameStorage = gameStorage;
	}
	
	public TurnBonusStorage getTurnBonusStorage() {
		return turnBonusStorage;
	}
	public void setTurnBonusStorage(TurnBonusStorage turnBonusStorage) {
		this.turnBonusStorage = turnBonusStorage;
	}
}