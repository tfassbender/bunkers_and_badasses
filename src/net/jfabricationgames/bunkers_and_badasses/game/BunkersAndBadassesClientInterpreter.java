package net.jfabricationgames.bunkers_and_badasses.game;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.FightTransfereMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameSaveMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStartMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.PreGameDataMessage;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameStartDialog;
import net.jfabricationgames.bunkers_and_badasses.game_frame.PreGameSelectionFrame;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.bunkers_and_badasses.server.ServerLogoutMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class BunkersAndBadassesClientInterpreter implements JFGClientInterpreter {
	
	private ChatClient chatClient;
	
	private GameStore gameStore;
	private GameStartDialog gameStartDialog;
	private PreGameSelectionFrame preGameSelectionFrame;
	private Game game;//set in the pre-game selection frame
	
	public BunkersAndBadassesClientInterpreter(GameStore gameStore, GameStartDialog gameStartDialog, ChatClient chatClient) {
		this.gameStore = gameStore;
		this.gameStartDialog = gameStartDialog;
		this.chatClient = chatClient;
	}

	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		//chat message
		if (message instanceof ChatMessage) {
			interpreteChatMessage((ChatMessage) message);
		}
		//board loading message
		else if (message instanceof BoardTransfereMessage) {
			interpreteBoardTransfereMessage((BoardTransfereMessage) message);
		}
		//game save message
		else if (message instanceof GameSaveMessage) {
			interpreteGameSaveMessage((GameSaveMessage) message);
		}
		//game loading messages are implemented in main menu interpreter
		//game start message
		else if (message instanceof GameStartMessage) {
			interpreteGameStartMessage((GameStartMessage) message);
		}
		//server logout
		else if (message instanceof ServerLogoutMessage) {
			interpreteServerLogoutMessage((ServerLogoutMessage) message);
		}
		//data transfer messages
		else if (message instanceof GameTransferMessage) {
			interpreteGameTransferMessage((GameTransferMessage) message);
		}
		else if (message instanceof PreGameDataMessage) {
			interpretePreGameDataMessage((PreGameDataMessage) message);
		}
		else if (message instanceof FightTransfereMessage) {
			interpreteFightTransfereMessage((FightTransfereMessage) message);
		}
	}
	
	private void interpreteChatMessage(ChatMessage message) {
		chatClient.receiveMessage(message.getMessage());
	}
	
	private void interpreteBoardTransfereMessage(BoardTransfereMessage message) {
		gameStartDialog.receiveBoard(message.getBoard());
	}
	
	private void interpreteGameSaveMessage(GameSaveMessage message) {
		gameStore.addServerAnswer(message.isSaveSuccessful());
	}
	
	private void interpreteGameStartMessage(GameStartMessage message) {
		gameStartDialog.receiveGameId(message.getGameId());
	}
	
	private void interpreteServerLogoutMessage(ServerLogoutMessage message) {
		new ServerLogoutDialog(message).setVisible(true);
	}
	
	private void interpreteGameTransferMessage(GameTransferMessage message) {
		switch (message.getType()) {
			case NEW_GAME:
				//pass on the new game
				gameStartDialog.receiveGame(message.getGame());
				break;
			case PLANING_COMMIT:
				//merge the players planes in the UserPlanManager
				game.getPlanManager().mergeGame(message.getGame());
				break;
			case TURN_OVER:
				//merge the complete game
				game.merge(message.getGame());
				game.getPlanManager().turnEnded();
				game.getFightManager().setCurrentFight(null);//the turn ended so the fight did too
				break;
		}
	}
	
	private void interpretePreGameDataMessage(PreGameDataMessage message) {
		preGameSelectionFrame.receivePreGameData(message);
	}
	
	private void interpreteFightTransfereMessage(FightTransfereMessage message) {
		game.getFightManager().receiveFight(message.getFight());
	}
	
	public PreGameSelectionFrame getPreGameSelectionFrame() {
		return preGameSelectionFrame;
	}
	public void setPreGameSelectionFrame(PreGameSelectionFrame preGameSelectionFrame) {
		this.preGameSelectionFrame = preGameSelectionFrame;
	}
	
	public GameStore getGameStore() {
		return gameStore;
	}
	public void setGameStore(GameStore gameStore) {
		this.gameStore = gameStore;
	}
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	public ChatClient getChatClient() {
		return chatClient;
	}
}