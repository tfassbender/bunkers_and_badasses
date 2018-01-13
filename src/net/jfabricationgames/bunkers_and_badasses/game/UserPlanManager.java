package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.error.CommandException;
import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_command.BuildCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.CollectCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_command.DefendCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.MarchCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RaidCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RecruitCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RetreatCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.SupportCommand;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameStatistic;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserPlanManager implements Serializable {
	
	private static final long serialVersionUID = -7503526894497955261L;
	
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
	private int[] commands = new int[START_COMMANDS.length];
	private UserResource previouseResource;
	private UserResource currentResource;
	//private GameTurnBonusManager gameTurnBonusManager;
	
	public UserPlanManager(Game game) {
		this.game = game;
		//this.gameTurnBonusManager = gameTurnBonusManager;
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
		TurnBonus turnBonus = game.getGameTurnBonusManager().getUsersBonus(game.getLocalUser());
		commands[COMMAND_SUPPORT] += turnBonus.getSupportCommands();
		commands[COMMAND_RAID] += turnBonus.getRaidCommands();
		commands[COMMAND_MARCH] += turnBonus.getMarchCommands();
		commands[COMMAND_RETREAT] += turnBonus.getRetreatCommands();
		//commands[COMMAND_BUILD] += turnBonus.getBuildCommands();
		//commands[COMMAND_RECRUIT] += turnBonus.getRecruitCommands();
		commands[COMMAND_COLLECT] += turnBonus.getCollectCommands();
		//commands[COMMAND_DEFEND] += turnBonus.getDefendCommands();
		//build, recruit and defend commands can not be added by turn bonuses
		currentResource = game.getResourceManager().getResources().get(game.getLocalUser());
		previouseResource = currentResource.clone();
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
			throw new CommandException("Can't add a commmand. The field already has a command.", "Du kannst nicht zwei Befehle an ein Feld erteilen.\n\nDie Truppen haben es schon schwer genug sich einen Befehl zu merken");
		}
		if (commands[command.getIdentifier()] <= 0) {
			throw new CommandException("Can't add this command. No more commands of this type left.", "Du hast keine Befehle von diesem Typ mehr.\n\nBefehle wachsen nunmal nicht an Bäumen. Kauf neue!");
		}
		try {
			currentResource.payCommand(command, field);
		}
		catch (ResourceException re) {
			throw new CommandException("The command can't be payed.", re, "Du hast nicht genug Resourcen um den Befehl zu bezahlen.\n\nAnschreiben lassen geht hier leider nicht.");
		}
		fieldCommands.put(field, command.getInstance());
		commands[command.getIdentifier()]--;
		//also add the command to the field to let the user see what he does
		field.setCommand(command);
	}
	
	/**
	 * Delete a command from the field.
	 */
	public void deleteCommand(Field field) throws CommandException {
		if (fieldCommands.get(field) == null) {
			throw new CommandException("Can't delete a command. No command found on this field.", "Du kannst keinen Befehl löschen wenn kein Befehl da ist.");
		}
		currentResource.payBackCommand(fieldCommands.get(field), field);
		commands[fieldCommands.get(field).getIdentifier()]++;
		fieldCommands.put(field, null);
		//remove the command from the field
		field.setCommand(null);
	}
	
	/**
	 * Add the commands to the board and commit them to the server.
	 */
	public void commit() {
		//calculate the used resources and give out points (capitalism bonus)
		UserResource usedResource = previouseResource.clone();//TODO previouseResources are wrong (for non starting player); check why
		usedResource.setCredits(usedResource.getCredits()-currentResource.getCredits());
		usedResource.setAmmo(usedResource.getAmmo()-currentResource.getAmmo());
		usedResource.setEridium(usedResource.getEridium()-currentResource.getEridium());
		game.getResourceManager().receiveUsedResources(game.getLocalUser(), game.getTurnManager().getTurn(), usedResource);
		game.getGameTurnGoalManager().receivePointsPlaning(game.getLocalUser(), previouseResource.getAmmo()-currentResource.getAmmo());
		//add statistics
		GameStatistic stats = game.getStatisticManager().getStatistics(game.getLocalUser());
		stats.setUsed_credits(stats.getUsed_credits() + usedResource.getCredits());
		stats.setUsed_ammo(stats.getUsed_ammo() + usedResource.getAmmo());
		stats.setUsed_eridium(stats.getUsed_eridium() + usedResource.getEridium());
		for (Field field : game.getBoard().getUsersFields(game.getLocalUser())) {
			if (field.getCommand() != null) {
				if (field.getCommand() instanceof RaidCommand) {
					stats.setCommands_raid(stats.getCommands_raid() + 1);
				}
				else if (field.getCommand() instanceof RetreatCommand) {
					stats.setCommands_retreat(stats.getCommands_retreat() + 1);
				}
				else if (field.getCommand() instanceof MarchCommand) {
					stats.setCommands_march(stats.getCommands_march() + 1);
				}
				else if (field.getCommand() instanceof BuildCommand) {
					stats.setCommands_build(stats.getCommands_build() + 1);
				}
				else if (field.getCommand() instanceof RecruitCommand) {
					stats.setCommands_recruit(stats.getCommands_recruit() + 1);
				}
				else if (field.getCommand() instanceof CollectCommand) {
					stats.setCommands_resources(stats.getCommands_resources() + 1);
				}
				else if (field.getCommand() instanceof SupportCommand) {
					stats.setCommands_support(stats.getCommands_support() + 1);
				}
				else if (field.getCommand() instanceof DefendCommand) {
					stats.setCommands_defense(stats.getCommands_defense() + 1);
				}
			}
		}
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
			game.getClient().resetOutput();//reset before sending a game
			game.getClient().sendMessageUnshared(message);
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
			//find the field reference in this game
			Field localField = this.game.getBoard().getFieldByName(field.getName());
			allCommands.put(localField, fieldCommands.get(field));
		}
		//add the game's local user (not this game's local user) to the committed map
		playerCommitted.put(game.getLocalUser(), game.getResourceManager().getResources().get(game.getLocalUser()));
		if (playerCommitted.size() == game.getPlayers().size()) {
			//all players have committed their planes -> apply the changes and send an update
			for (Field field : allCommands.keySet()) {
				field.setCommand(allCommands.get(field));
				if (field.getCommand() != null) {
					field.getCommand().loadImage();
				}
			}
			User commitedUser = game.getLocalUser();
			int turn = game.getTurnManager().getTurn();
			this.game.getResourceManager().receiveChanges(playerCommitted);
			this.game.getResourceManager().receiveUsedResources(commitedUser, turn, game.getResourceManager().getResourceUse().get(commitedUser).get(turn));
			this.game.setState(GameState.ACT);
			GameTransferMessage message = new GameTransferMessage();
			message.setGame(this.game);
			message.setType(GameTransferMessage.TransferType.TURN_OVER);//start the next (first) turn (broadcasted) (turn is not really over but the other game knows what's to do)
			this.game.getClient().resetOutput();
			this.game.getClient().sendMessage(message);
			this.game.getGameFrame().updateAllFrames();
			//all players have committed -> store the current game state
			Game.getGameStore().storeGame(this.game, false);
		}
	}
	
	public void turnEnded() {
		fieldCommands = new HashMap<Field, Command>();
		allCommands = new HashMap<Field, Command>();
		playerCommitted = new HashMap<User, UserResource>();
		countCommands();
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