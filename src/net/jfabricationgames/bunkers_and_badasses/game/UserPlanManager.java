package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_frame.TurnPlaningFrame;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserPlanManager {
	
	private Board board;
	private User localUser;
	private Map<Field, Command> fieldCommands;
	public static int[] START_COMMANDS;//the commands that every user gets by default (loaded from the database)
	public static final int COMMAND_SUPPORT = 0;
	public static final int COMMAND_RAID = 1;
	public static final int COMMAND_MARCH = 2;
	public static final int COMMAND_RETREAT = 3;
	public static final int COMMAND_BUILD = 4;
	public static final int COMMAND_RECRUIT = 5;
	public static final int COMMAND_COLLECT = 6;
	public static final int COMMAND_DEFEND = 7;
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
	public void addCommand(Field field, Command command) throws IllegalArgumentException {
		if (field.getCommand() != null) {
			throw new IllegalArgumentException("Can't add a commmand. The field already has a command.");
		}
		if (commands[command.getIdentifier()] <= 0) {
			throw new IllegalArgumentException("Can't add this command. No more commands of this type left.");
		}
		field.setCommand(command);
		commands[command.getIdentifier()]--;
		//TODO reduce the users resources
	}
	
	/**
	 * Delete a command from the field.
	 */
	public void deleteCommand(Field field) throws IllegalArgumentException {
		if (field.getCommand() == null) {
			throw new IllegalArgumentException("Can't delete a command. No command found on this field.");
		}
		commands[field.getCommand().getIdentifier()]++;
		field.setCommand(null);
		//TODO add the resources used for the command
	}
	
	/**
	 * Commit the commands to the server.
	 */
	public void commit() {
		//TODO
	}
	
	public static void receiveStartCommands(int[] startCommands) {
		START_COMMANDS = startCommands;
	}
	
	public int getCommandsLeft(int type) {
		return commands[type];
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
}