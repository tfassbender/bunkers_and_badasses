package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

import net.jfabricationgames.bunkers_and_badasses.game_board.Region;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameEndFrame;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameTurnManager implements Serializable {
	
	private static final long serialVersionUID = -5200571555220181340L;
	
	private int turn;
	
	private static int numTurns;
	
	private Game game;
	
	private PlayerOrder playerOrder;
	private GameTurnGoalManager gameTurnGoalManager;
	private UserResourceManager resourceManager;
	private PointManager pointManager;
	
	public GameTurnManager(Game game) {
		this.game = game;
		this.playerOrder = game.getPlayerOrder();
		this.gameTurnGoalManager = game.getGameTurnGoalManager();
		this.resourceManager = game.getResourceManager();
		this.pointManager = game.getPointManager();
		numTurns = Game.getGameVariableStorage().getGameTurns();
		turn = 9;
	}
	
	public void merge(GameTurnManager manager) {
		this.turn = manager.getTurn();
	}
	
	public int getTurn() {
		return turn;
	}
	public void nextTurn() {
		giveOutPoints();
		game.getBoard().removeAllCommands();
		game.getPlanManager().turnEnded();
		game.setState(GameState.PLAN);
		playerOrder.nextTurn();
		resourceManager.collectTurnStartResources();
		resourceManager.payFixCosts();
		game.getHeroCardManager().resetHeroCardsTaken();
		turn++;
		if (turn >= numTurns) {
			//TODO save the game in the database and create the statistics
			//send a game end message
			GameTransferMessage message = new GameTransferMessage();
			message.setType(GameTransferMessage.TransferType.GAME_ENDED);
			message.setGame(game);
			game.getClient().resetOutput();
			game.getClient().sendMessage(message);
			//dispose all frames and open the game end frame
			game.getGameFrame().disposeAll();
			new GameEndFrame(game).setVisible(true);
		}
	}
	public static int getNumTurns() {
		return numTurns;
	}
	
	public void giveOutPoints() {
		for (User player : game.getPlayers()) {
			//points for fields
			int fields = game.getBoard().getUsersFields(player).size();
			int points = fields/Game.getGameVariableStorage().getFieldPointCount();
			points *= Game.getGameVariableStorage().getFieldPoints();
			pointManager.addPoints(player, points);
			//points for regions
			for (Region region : game.getBoard().getUsersRegions(player)) {
				pointManager.addPoints(player, region.getPoints());
			}
			//points for turn goals
			gameTurnGoalManager.receivePointsTurnEnd(player, game);
		}
	}
	
	public PlayerOrder getPlayerOrder() {
		return playerOrder;
	}
	
	public GameTurnGoalManager getGameTurnGoalManager() {
		return gameTurnGoalManager;
	}
	
	public UserResourceManager getResourceManager() {
		return resourceManager;
	}
}