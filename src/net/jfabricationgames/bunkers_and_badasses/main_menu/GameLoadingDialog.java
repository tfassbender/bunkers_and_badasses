package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameLoadingDialog extends JFrame {
	
	private static final long serialVersionUID = 5567858686487512818L;
	
	private JFGClient client;
	
	private GameLoadingAnswerDialog answerDialog;
	
	private List<User> answers;
	private List<User> invitedUsers;
	private boolean abort;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtBoard;
	private JTextField txtStoringDate;
	private JTextField txtTurn;
	
	private DefaultListModel<User> playersListModel = new DefaultListModel<User>();
	private DefaultListModel<GameOverview> gamesListModel = new DefaultListModel<GameOverview>();
	private JList<GameOverview> list_games;
	private JLabel lblLade;
	private JLabel lblError;
	
	private MainMenuFrame mainMenu;
	private List<Board> playableBoards;
	
	public GameLoadingDialog(JFGClient client, MainMenuFrame callingFrame, List<Board> playableBoards, List<GameOverview> gameOverviews) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameLoadingDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				callingFrame.disposeGameLoadingDialog();
			}
		});
		
		this.client = client;
		this.mainMenu = callingFrame;
		this.playableBoards = playableBoards;
		
		setResizable(false);
		setTitle("Spiel Laden - Bunkers and Badasses");
		setBounds(100, 100, 450, 600);
		setLocationRelativeTo(callingFrame);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[300px,grow][300px,grow]", "[][10px][][200px,grow][10px][][grow][50px][]"));
		{
			JLabel lblSpielLaden = new JLabel("Spiel Laden");
			lblSpielLaden.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielLaden, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblGespeicherteSpiele = new JLabel("Gespeicherte Spiele:");
			lblGespeicherteSpiele.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblGespeicherteSpiele, "cell 0 2");
		}
		{
			lblLade = new JLabel("Laden...");
			lblLade.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPanel.add(lblLade, "cell 1 2");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 3 2 1,grow");
			{
				list_games = new JList<GameOverview>(gamesListModel);
				list_games.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						GameOverview overview = list_games.getSelectedValue();
						showGameOverview(overview);
					}
				});
				list_games.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list_games.setBackground(Color.LIGHT_GRAY);
				list_games.setFont(new Font("Tahoma", Font.PLAIN, 12));
				scrollPane.setViewportView(list_games);
			}
		}
		{
			JLabel lblSpielDetails = new JLabel("Spiel Details:");
			lblSpielDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblSpielDetails, "cell 0 5 2 1");
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 6 2 1,grow");
			panel.setLayout(new MigLayout("", "[][150px][grow]", "[][][][][grow]"));
			{
				JLabel lblSpielfeld = new JLabel("Spielfeld:");
				panel.add(lblSpielfeld, "cell 0 0");
				lblSpielfeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				txtBoard = new JTextField();
				panel.add(txtBoard, "cell 1 0,growx");
				txtBoard.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtBoard.setBackground(Color.LIGHT_GRAY);
				txtBoard.setEditable(false);
				txtBoard.setColumns(10);
			}
			{
				JLabel lblSpeicherDatum = new JLabel("Speicher Datum:");
				panel.add(lblSpeicherDatum, "cell 0 1");
				lblSpeicherDatum.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				txtStoringDate = new JTextField();
				panel.add(txtStoringDate, "cell 1 1,growx");
				txtStoringDate.setBackground(Color.LIGHT_GRAY);
				txtStoringDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtStoringDate.setColumns(10);
			}
			{
				JLabel lblSpielRunde = new JLabel("Spiel Runde:");
				panel.add(lblSpielRunde, "cell 0 2");
				lblSpielRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				txtTurn = new JTextField();
				txtTurn.setFont(new Font("Tahoma", Font.PLAIN, 12));
				panel.add(txtTurn, "cell 1 2,growx");
				txtTurn.setBackground(Color.LIGHT_GRAY);
				txtTurn.setEditable(false);
				txtTurn.setColumns(10);
			}
			{
				JLabel lblSpieler = new JLabel("Spieler:");
				panel.add(lblSpieler, "cell 0 3");
				lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, "cell 0 4 3 1,grow");
				{
					JList<User> list_players = new JList<User>(playersListModel);
					list_players.setFont(new Font("Tahoma", Font.PLAIN, 12));
					list_players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list_players.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(list_players);
				}
			}
		}
		{
			JButton btnSpielLaden = new JButton("Spiel Laden");
			btnSpielLaden.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sendRequests();
				}
			});
			{
				lblError = new JLabel("");
				lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
				contentPanel.add(lblError, "cell 0 7 2 1,alignx center");
			}
			btnSpielLaden.setBackground(Color.GRAY);
			contentPanel.add(btnSpielLaden, "cell 0 8,alignx right");
		}
		{
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAbbrechen.setBackground(Color.GRAY);
			contentPanel.add(btnAbbrechen, "cell 1 8");
		}
		
		//receive the game overviews after the frame was build
		if (gameOverviews != null) {
			receiveGameOverviews(gameOverviews);
		}
	}
	
	@Override
	public void dispose() {
		if (answerDialog != null) {
			answerDialog.dispose();			
		}
		super.dispose();
	}
	
	/**
	 * Check whether all players are online and if so send them the requests for the selected game.
	 */
	private void sendRequests() {
		GameOverview overview = list_games.getSelectedValue();
		List<User> players = overview.getPlayers();
		List<User> allUsers = UserManager.getUsers();
		User localUser = UserManager.getLocalUser();
		boolean playersOnline = true;
		if (players.contains(localUser)) {
			for (User p : players) {
				//find the user p in the user list of the UserManager class
				//the users from the overview may not contain the right online state
				int userPos = allUsers.indexOf(p);
				User user;
				if (userPos != -1) {
					user = allUsers.get(userPos);
					playersOnline &= user.isOnline() && !user.isInGame();
				}
				else {
					playersOnline = false;
				}
			}
			if (playersOnline) {
				//all players that play in the game are online
				//remove the local player from the invitation list
				players.remove(localUser);
				//send the game requests
				MainMenuMessage gameRequest = new MainMenuMessage();
				gameRequest.setMessageType(MainMenuMessage.MessageType.GAME_LOADING_REQUEST);
				gameRequest.setInvitedPlayers(players);
				gameRequest.setPlayer(new User(UserManager.getUsername()));
				gameRequest.setOverview(overview);
				answers = new ArrayList<User>();
				invitedUsers = players;
				abort = false;
				//send the request message
				//client.resetOutput();
				client.sendMessage(gameRequest);
				//select the board from the list of playable boards
				Board board = null;
				for (Board b : playableBoards) {
					if (b.getName().equals(overview.getBoardName())) {
						board = b;
					}
				}
				//start the answer dialog
				answerDialog = new GameLoadingAnswerDialog(this, client, invitedUsers, mainMenu, board, overview);
				answerDialog.setVisible(true);
				//hide this frame
				setVisible(false);
			}
			else {
				lblError.setText("Nicht alle Spieler Online");
			}
		}
		else {
			lblError.setText("Du hast an diesem Spiel nicht teilgenommen");
		}
	}
	
	private void showGameOverview(GameOverview overview) {
		//add general info
		txtBoard.setText(overview.getBoardName());
		txtStoringDate.setText(overview.getDateStored());
		txtTurn.setText(Integer.toString(overview.getTurn()));
		//add the players
		playersListModel.clear();
		for (User user : overview.getPlayers()) {
			playersListModel.addElement(user);
		}
	}
	
	public void receiveGameOverviews(List<GameOverview> gameOverviews) {
		lblLade.setText("");
		//sort the game overviews by date
		Collections.sort(gameOverviews);
		//add the game overviews to the list
		gamesListModel.clear();
		for (GameOverview overview : gameOverviews) {
			gamesListModel.addElement(overview);
		}
	}
	
	private void sendGameCreationAbort(String cause) {
		MainMenuMessage gameCreationAbort = new MainMenuMessage();
		gameCreationAbort.setMessageType(MainMenuMessage.MessageType.GAME_CREATEION_ABORT);
		gameCreationAbort.setPlayer(new User(UserManager.getUsername()));
		gameCreationAbort.setInvitedPlayers(invitedUsers);
		gameCreationAbort.setAbortCause(cause);
		client.resetOutput();
		client.sendMessage(gameCreationAbort);
	}
	public void receiveGameLoadingAnswer(User player, boolean joining) {
		if (answerDialog != null) {
			answerDialog.receiveGameLoadingAnswer(player, joining);
		}
		answers.remove(player);
		answers.add(player);
		if (!joining) {
			sendGameCreationAbort("Absage von " + player.getUsername() + ".");
			abort = true;
			answerDialog.disableGameStart();
		}
		else if (!abort && answers.size() == invitedUsers.size()) {
			answerDialog.enableGameStart();
		}
	}
}