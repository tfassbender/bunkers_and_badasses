package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.TurnPlaningFrame;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserPlanManager {
	
	private Board board;
	private User localUser;
	private Map<Field, Command> fieldCommands;
	public final int[] START_COMMANDS = new int[] {1, 1, 1, 1, 1, 1, 1, 1};//the commands that every user gets by default
	public final int COMMAND_SUPPORT = 0;
	public final int COMMAND_RAID = 1;
	public final int COMMAND_MARCH = 2;
	public final int COMMAND_RETREAD = 3;
	public final int COMMAND_BUILD = 4;
	public final int COMMAND_RECRUIT = 5;
	public final int COMMAND_COLLECT = 6;
	public final int COMMAND_DEFEND = 7;
	private int[] commands;
	private UserResource currentResource;
	private UserResourceManager userResourceManager;
	private TurnPlaningFrame turnPlaningFrame;
	
	public UserPlanManager(User localUser, UserResourceManager userResourceManager) {
		this.localUser = localUser;
		this.userResourceManager = userResourceManager;
	}
	
	/**
	 * Count the commands the user gets by start commands, turn bonus, ...
	 */
	public void countCommands() {
		//TODO
	}
	
	/**
	 * Add a command to the field.
	 */
	public void addCommand(Field field, Command command) {
		//TODO
	}
	
	/**
	 * Delete a command from the field.
	 */
	public void deleteCommand(Field field) {
		//TODO
	}
	
	/**
	 * Commit the commands to the server.
	 */
	public void commit() {
		//TODO
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
}