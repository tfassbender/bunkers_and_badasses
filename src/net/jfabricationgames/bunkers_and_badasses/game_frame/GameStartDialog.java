package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.BunkersAndBadassesClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game.GameTurnManager;
import net.jfabricationgames.bunkers_and_badasses.game.HeroCardManager;
import net.jfabricationgames.bunkers_and_badasses.game.PlayerOrder;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager;
import net.jfabricationgames.bunkers_and_badasses.game.UserColorManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameCreationMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStartMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStorageException;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameStartDialog extends JDialog {
	
	private static final long serialVersionUID = 8041792943271736351L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	private Board board;
	private int gameId = -1;
	private Game game;
	
	private boolean isLoaded;
	
	public GameStartDialog() {
		setResizable(false);
		setTitle("Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameStartDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[grow][][grow]", "[50px][100px,grow][150px,grow]"));
			{
				ImagePanel panel_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("jfg/headline.png"));
				panel_1.setBackground(Color.GRAY);
				panel_1.setAdaptSizeKeepProportion(true);
				panel.add(panel_1, "cell 0 0 3 1,grow");
			}
			{
				ImagePanel panel_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/hammerlock_1.png"));
				panel_1.setAdaptSizeKeepProportion(true);
				panel_1.setBackground(Color.GRAY);
				panel.add(panel_1, "cell 0 1 1 2,grow");
			}
			{
				JLabel lblSpielWirdGeladen = new JLabel("Spiel wird geladen...");
				lblSpielWirdGeladen.setFont(new Font("Tahoma", Font.BOLD, 25));
				panel.add(lblSpielWirdGeladen, "cell 1 1,alignx center");
			}
			{
				ImagePanel panel_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/logo_2.png"));
				panel_1.setCentered(true);
				panel_1.setAdaptSizeKeepProportion(true);
				panel_1.setBackground(Color.GRAY);
				panel.add(panel_1, "cell 1 2,grow");
			}
			{
				ImagePanel panel_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("login/moxxi_1.png"));
				panel_1.setCentered(true);
				panel_1.setAdaptSizeKeepProportion(true);
				panel_1.setBackground(Color.GRAY);
				panel.add(panel_1, "cell 2 1 1 2,grow");
			}
		}
	}
	
	/**
	 * Receive the game id from the server and start the game (if the board is loaded).
	 * 
	 * @param id
	 * 		The game's id in the database.
	 */
	public void receiveGameId(int id) {
		this.gameId = id;
		if (board != null && game != null) {
			startGameFrame();
		}
	}
	
	/**
	 * Receive the board from the server.
	 * 
	 * @param board
	 * 		The board for the game that is started.
	 */
	public void receiveBoard(Board board) {
		this.board = board;
		if (gameId != -1 && game != null) {
			startGameFrame();
		}
	}
	
	public void receiveGame(Game game) {
		this.game = game;
		if (board != null && gameId != -1) {
			startGameFrame();
		}
	}
	
	/**
	 * Create the game frame when the game id and the board are loaded.
	 */
	private void startGameFrame() {
		if (board != null && game != null && gameId != -1) {
			if (!isLoaded) {
				game.setBoard(board);
				game.setId(gameId);				
			}
			else {
				//set only the image because it's not included in the loaded board
				game.getBoard().setBaseImage(board.getBaseImage());
			}
			//TODO start the game frame			
		}
	}
	
	/**
	 * Create and start the game. This method is only called by the starting player to create the game in the database.
	 * 
	 * @param client
	 * 		The JFGClient to send messages to the server.
	 *  
	 * @param players
	 * 		The players taking part in this game.
	 * 
	 * @param board
	 * 		The board on which is played. Needs to be loaded by the server because it's incomplete.
	 */
	public void startGameMaster(JFGClient client, List<User> players, Board board) {
		this.client = client;
		this.isLoaded = false;
		changeClientInterpreter(client);
		//send a game start message containing the board id to all players
		//this message is received by the MainMenuClientInterpreter and starts the GameStartDialog that adds the BunkersAndBadassesClientInterpreter
		GameStartMessage message = new GameStartMessage();
		message.setBoardId(board.getBoardId());
		message.setPlayers(players);
		message.setLoaded(false);
		client.sendMessage(message);
		//load the board from the server
		BoardRequestMessage boardRequest = new BoardRequestMessage();
		boardRequest.setId(board.getBoardId());
		boardRequest.setPlayers(players.size());
		client.sendMessage(boardRequest);
		//create the game entry in the database
		GameCreationMessage creationMessage = new GameCreationMessage();
		creationMessage.setBoardId(board.getBoardId());
		creationMessage.setPlayers(players);
		client.sendMessage(creationMessage);
		//create a game object with the game managers
		Game game = new Game();
		//board and id are not sent to the server (added later by the clients)
		game.setState(GameState.PLAN);
		game.setPlayers(players);
		game.setTurnManager(new GameTurnManager());
		UserColorManager colorManager = new UserColorManager();
		colorManager.chooseRandomColors(players);//select random colors for all users
		game.setColorManager(colorManager);
		PlayerOrder playerOrder = new PlayerOrder();
		playerOrder.chooseRandomOrder(players);
		game.setOrder(playerOrder);
		PointManager pointManager = new PointManager();
		pointManager.initialize(players);
		game.setPointManager(pointManager);
		HeroCardManager heroCardMananger = new HeroCardManager();
		heroCardMananger.intitialize(players);
		//resources are added by the server because of the skills
		GameTransferMessage gameTransferMessage = new GameTransferMessage();
		gameTransferMessage.setGame(game);
		gameTransferMessage.setNewGame(true);
		client.sendMessage(gameTransferMessage);
		//wait for the server to send the game id, the board and the completed game object
	}
	
	/**
	 * Load an existing game from the database and start it. This method is only called by the starting player.
	 * 
	 * @param client
	 * 		The JFGClient to send messages to the server.
	 *  
	 * @param players
	 * 		The players taking part in this game.
	 * 
	 * @param board
	 * 		The board on which is played. Needs to be loaded by the server because it's incomplete.
	 * 
	 * @param overview
	 * 		The GameOverview to identify the game that is to be loaded from the server.
	 */
	public void loadGameMaster(JFGClient client, List<User> players, Board board, GameOverview overview) {
		this.client = client;
		this.isLoaded = true;
		BunkersAndBadassesClientInterpreter interpreter = changeClientInterpreter(client);
		GameStore gameStore = interpreter.getGameStore();
		//send a game start message containing the board id to all players
		//this message is received by the MainMenuClientInterpreter and starts the GameStartDialog that adds the BunkersAndBadassesClientInterpreter
		GameStartMessage message = new GameStartMessage();
		message.setBoardId(board.getBoardId());
		message.setPlayers(players);
		message.setLoaded(true);
		client.sendMessage(message);
		//load the board (image) from the server
		BoardRequestMessage boardRequest = new BoardRequestMessage();
		boardRequest.setId(board.getBoardId());
		boardRequest.setPlayers(players.size());
		client.sendMessage(boardRequest);
		//load the game from the server using the GameStore
		Thread gameLoadingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					game = gameStore.loadGame(overview);
					gameId = game.getId();
					GameStartDialog.this.board = game.getBoard();
					//the loaded game already contains the board and the id so the game can be started
					startGameFrame();
				}
				catch (GameStorageException gse) {
					gse.printStackTrace();
				}
			}
		}, "GameLoadingThread");
		gameLoadingThread.start();
	}
	
	/**
	 * Start the game and load the board form the server.
	 * 
	 * @param client
	 * 		The JFGClient to communicate with the server.
	 * 
	 * @param boardId
	 * 		The board's id.
	 * 
	 * @param players
	 * 		The number of players in this game.
	 * 
	 * @param loadedGame
	 * 		Indicates whether this game is a new game or loaded from the server database.
	 * 
	 * @param overview
	 * 		The GameOverview to identify the game that is to be loaded (or null if the game is new).
	 */
	public void startGame(JFGClient client, int boardId, int players, boolean loadedGame, GameOverview overview) {
		this.client = client;
		this.isLoaded = loadedGame;
		BunkersAndBadassesClientInterpreter interpreter = changeClientInterpreter(client);
		GameStore gameStore = interpreter.getGameStore();
		if (loadedGame) {
			//if the game needs to be loaded from the server use the GameStore to load it
			//load the game in a new thread because the method is modal
			Thread gameLoadingThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						game = gameStore.loadGame(overview);
						gameId = game.getId();
						board = game.getBoard();
						//the loaded game already contains the board and the id so the game can be started
						startGameFrame();
					}
					catch (GameStorageException gse) {
						gse.printStackTrace();
					}
				}
			}, "GameLoadingThread");
			gameLoadingThread.start();
		}
		//load the board from the server (for loaded games only the image is used)
		BoardRequestMessage boardRequest = new BoardRequestMessage();
		boardRequest.setId(boardId);
		boardRequest.setPlayers(players);
		client.sendMessage(boardRequest);
		//wait for the server to send the game id
	}
	
	private BunkersAndBadassesClientInterpreter changeClientInterpreter(JFGClient client) {
		//replace the main menu interpreter with the game interpreter
		GameStore gameStore = new GameStore(client);
		BunkersAndBadassesClientInterpreter interpreter = new BunkersAndBadassesClientInterpreter(gameStore, this);
		client.setClientInterpreter(interpreter);
		return interpreter;
	}
}