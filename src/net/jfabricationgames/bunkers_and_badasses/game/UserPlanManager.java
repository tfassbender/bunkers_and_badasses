package net.jfabricationgames.bunkers_and_badasses.game;

import java.util.HashMap;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.error.CommandException;
import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserPlanManager {
	
	private Game game;
	private Map<Field, Command> fieldCommands;
	private Map<Field, Command> allCommands;//store the commands of all players that committed their planes
	private Map<User, UserResource> playerCommitted;//if the player committed there must be his resources
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
	private GameTurnBonusManager gameTurnBonusManager;
	
	public UserPlanManager(Game game, GameTurnBonusManager gameTurnBonusManager) {
		this.game = game;
		this.gameTurnBonusManager = gameTurnBonusManager;
		fieldCommands = new HashMap<Field, Command>();
		allCommands = new HashMap<Field, Command>();
		playerCommitted = new HashMap<User, UserResource>();
	}
	
	/**
	 * Count the commands the user gets by start commands, turn bonus, ...
	 */
	public void countCommands() {
		//copy the default start commands
		commands = new int[START_COMMANDS.length];
		for (int i = 0; i < commands.length; i++) {
			commands[i] = START_COMMANDS[i];
		}
		//add the commands gained by turn bonuses
		TurnBonus turnBonus = gameTurnBonusManager.getUsersBonus(game.getLocalUser());
		commands[COMMAND_SUPPORT] += turnBonus.getSupportCommands();
		commands[COMMAND_RAID] += turnBonus.getRaidCommands();
		commands[COMMAND_MARCH] += turnBonus.getMarchCommands();
		commands[COMMAND_RETREAT] += turnBonus.getRetreatCommands();
		//commands[COMMAND_BUILD] += turnBonus.getBuildCommands();
		//commands[COMMAND_RECRUIT] += turnBonus.getRecruitCommands();
		commands[COMMAND_COLLECT] += turnBonus.getCollectCommands();
		//commands[COMMAND_DEFEND] += turnBonus.getDefendCommands();
		//build, recruit and defend commands can not be added by turn bonuses
	}
	
	/**
	 * Add a command that was bought by the player.
	 * 
	 * @param commandId
	 * 		The id of the command that was bought.
	 */
	public void addCommand(int commandId) {
		commands[commandId]++;
	}
	
	/**
	 * Add a command to the field.
	 */
	public void addCommand(Field field, Command command) throws CommandException {
		if (fieldCommands.get(field) != null) {
			throw new CommandException("Can't add a commmand. The field already has a command.");
		}
		if (commands[command.getIdentifier()] <= 0) {
			throw new CommandException("Can't add this command. No more commands of this type left.");
		}
		try {
			currentResource.payCommand(command, field);
		}
		catch (ResourceException re) {
			throw new CommandException("The command can't be payed.", re);
		}
		fieldCommands.put(field, command.getInstance());
		commands[command.getIdentifier()]--;
	}
	
	/**
	 * Delete a command from the field.
	 */
	public void deleteCommand(Field field) throws CommandException {
		if (fieldCommands.get(field) == null) {
			throw new CommandException("Can't delete a command. No command found on this field.");
		}
		currentResource.payBackCommand(fieldCommands.get(field), field);
		commands[fieldCommands.get(field).getIdentifier()]++;
		fieldCommands.put(field, null);
	}
	
	/**
	 * Add the commands to the board and commit them to the server.
	 */
	public void commit() {
		//commands are added to the field in the merge method
		/*for (Field field : fieldCommands.keySet()) {
			field.setCommand(fieldCommands.get(field));
		}*/
		if (game.getLocalUser().equals(game.getStartingPlayer())) {
			mergeGame(game);
		}
		else {
			GameTransferMessage message = new GameTransferMessage();
			message.setGame(game);
			message.setType(GameTransferMessage.TransferType.PLANING_COMMIT);
			game.getClient().sendMessage(message);			
		}
		fieldCommands = new HashMap<Field, Command>();
	}
	
	/**
	 * Merge the committed planes of a player to a temporary game.
	 * 
	 * @param game
	 * 		The game the player committed.
	 */
	public void mergeGame(Game game) {
		Map<Field, Command> fieldCommands = game.getPlanManager().getFieldCommands();
		for (Field field : fieldCommands.keySet()) {
			allCommands.put(field, fieldCommands.get(field));
		}
		playerCommitted.put(game.getLocalUser(), game.getResourceManager().getResources().get(game.getLocalUser()));
		if (playerCommitted.size() == game.getPlayers().size()) {
			//all players have committed their planes -> apply the changes and send an update
			for (Field field : allCommands.keySet()) {
				field.setCommand(allCommands.get(field));
			}
			this.game.getResourceManager().receiveChanges(playerCommitted);
			game.setState(GameState.ACT);
			GameTransferMessage message = new GameTransferMessage();
			message.setGame(game);
			message.setType(GameTransferMessage.TransferType.TURN_OVER);//start the next (first) turn (broadcasted)
			game.getClient().sendMessage(message);
		}
	}
	
	public static void receiveStartCommands(int[] startCommands) {
		START_COMMANDS = startCommands;
	}
	
	public int getCommandsLeft(int type) {
		return commands[type];
	}
	
	private Map<Field, Command> getFieldCommands() {
		return fieldCommands;
	}
	
	public UserResource getCurrentResource() {
		return currentResource;
	}
	public void setCurrentResource(UserResource currentResource) {
		this.currentResource = currentResource;
	}
}