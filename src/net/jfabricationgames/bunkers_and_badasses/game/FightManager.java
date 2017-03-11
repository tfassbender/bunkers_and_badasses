package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.List;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class FightManager {
	
	private Map<Integer, List<Fight>> fights;//executed fights sorted by the game turns
	
	private Fight currentFight;
	
	private JFGClient client;
	
	public FightManager(JFGClient client) {
		this.client = client;
	}
	
	public void startFight(Field startField, Field targetField, int normalTroops, int badassTroops) {
		//TODO
	}
}