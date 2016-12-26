package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Move implements Serializable {
	
	private static final long serialVersionUID = -6954044333547272542L;
	
	private User player;
	private Command command;
	private Field field;
	private Fight fight;
}