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
import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameCreationMessage;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameStartMessage;
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
		if (board != null) {
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
		if (gameId != -1) {
			startGameFrame();
		}
	}
	
	/**
	 * Create the game frame when the game id and the board are loaded.
	 */
	private void startGameFrame() {
		//TODO start the game frame
	}
	
	/**
	 * Create and start the game. This method is only called by the starting player to create the game in the database.
	 *  
	 * @param players
	 * 		The players taking part in this game.
	 * 
	 * @param board
	 * 		The board on which is played.
	 */
	public void startGameMaster(JFGClient client, List<User> players, Board board) {
		this.client = client;
		changeClientInterpreter(client);
		//send a game start message containing the board id to all players
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
		//wait for the server to send the game id
	}
	
	/**
	 * Start the game and load the board form the server.
	 * 
	 * @param boardId
	 * 		The board's id.
	 */
	public void startGame(JFGClient client, int boardId, int players) {
		this.client = client;
		changeClientInterpreter(client);
		//load the board from the server
		BoardRequestMessage boardRequest = new BoardRequestMessage();
		boardRequest.setId(boardId);
		boardRequest.setPlayers(players);
		client.sendMessage(boardRequest);
		//wait for the server to send the game id
	}
	
	private void changeClientInterpreter(JFGClient client) {
		//replace the main menu interpreter with the game interpreter
		GameStore gameStore = new GameStore(client);
		BunkersAndBadassesClientInterpreter interpreter = new BunkersAndBadassesClientInterpreter(gameStore, this);
		client.setClientInterpreter(interpreter);
	}
}