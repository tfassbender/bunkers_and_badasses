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
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameStartDialog;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameCreationDialog extends JDialog {
	
	private static final long serialVersionUID = 6165525170674954371L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	private MainMenuFrame mainMenu;
	
	private JList<User> list_players;
	private JTextArea txtrAnswers;
	
	private boolean requestSent = false;
	private boolean allRequestAnswered = false;
	
	private List<User> invitedUsers;
	private List<User> rejections;
	private List<User> answers;
	private JButton okButton;
	private JLabel lblError;
	private JList<Board> list_map;
	
	private DefaultListModel<Board> listModelBoard = new DefaultListModel<Board>();
	
	private Board selectedBoard;
	
	public GameCreationDialog(JFGClient client, List<Board> playableBoards, MainMenuFrame callingFrame) {
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				callingFrame.disposeGameCreationDialog();
			}
		});
		setResizable(false);
		setTitle("Bunkers and Badasses - Spiel Erstellen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameCreationDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		
		this.client = client;
		this.mainMenu = callingFrame;
		
		setBounds(100, 100, 500, 650);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[250px,grow][200px,grow]", "[][10px][][100px,grow][10px][][50px,grow][][10px][][75px,grow][25px:n:25px][]"));
		{
			JLabel lblSpielErstellen = new JLabel("Spiel Erstellen");
			lblSpielErstellen.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielErstellen, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblSpielerAuswhlen = new JLabel("Spieler Ausw\u00E4hlen:");
			lblSpielerAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblSpielerAuswhlen, "cell 0 2");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.GRAY);
			contentPanel.add(scrollPane, "cell 0 3,grow");
			{
				ListModel<User> userListModel = createUserListModel();
				list_players = new JList<User>(userListModel);
				list_players.setBackground(Color.LIGHT_GRAY);
				scrollPane.setViewportView(list_players);
			}
		}
		{
			ImagePanel panel = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("login/moxxi_1.png"));
			panel.setCentered(true);
			panel.setAdaptSizeKeepProportion(true);
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 1 3 1 8,grow");
		}
		{
			JLabel lblKarte = new JLabel("Karte Ausw\u00E4hlen:");
			lblKarte.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblKarte, "cell 0 5");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 6,grow");
			{
				list_map = new JList<Board>(listModelBoard);
				list_map.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list_map.setBackground(Color.LIGHT_GRAY);
				//add the boards to the list
				for (Board b : playableBoards) {
					listModelBoard.addElement(b);
				}
				scrollPane.setViewportView(list_map);
			}
		}
		{
			JButton btnKarteAnzeigen = new JButton("Karte Anzeigen");
			btnKarteAnzeigen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Board board = list_map.getSelectedValue();
					if (board != null) {
						new BoardPreviewDialog(GameCreationDialog.this, board).setVisible(true);
					}
				}
			});
			btnKarteAnzeigen.setBackground(Color.GRAY);
			contentPanel.add(btnKarteAnzeigen, "cell 0 7");
		}
		{
			JLabel lblAntworten = new JLabel("Antworten:");
			lblAntworten.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblAntworten, "cell 0 9");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane, "cell 0 10,grow");
			{
				txtrAnswers = new JTextArea();
				txtrAnswers.setEditable(false);
				txtrAnswers.setBackground(Color.LIGHT_GRAY);
				scrollPane.setViewportView(txtrAnswers);
			}
		}
		{
			lblError = new JLabel("");
			lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblError, "cell 0 11 2 1,alignx center");
		}
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "cell 0 12 2 1,alignx center");
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new MigLayout("", "[][]", "[]"));
			{
				okButton = new JButton("Erstellen");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (allRequestAnswered) {
							startGame();
						}
						else if (!requestSent) {
							requestSent = true;
							invitedUsers = list_players.getSelectedValuesList();
							if (!invitedUsers.isEmpty()) {
								Board board = list_map.getSelectedValue();
								if (board != null) {
									if (invitedUsers.size()+1 >= board.getPlayersMin() && invitedUsers.size()+1 <= board.getPlayersMax()) {//+1 is the player himself
										sendGameRequest(invitedUsers, board);
										selectedBoard = board;
									}
									else {
										lblError.setText("Zu viele/wenig Spieler für diese Karte");
									}
								}
								else {
									lblError.setText("Karte auswählen!");
								}
							}
							else {
								lblError.setText("Spieler auswählen!");
							}
						}
					}
				});
				okButton.setBackground(Color.GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 0 0");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Abbrechen");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sendGameCreationAbort();
						callingFrame.disposeGameCreationDialog();
						dispose();
					}
				});
				cancelButton.setBackground(Color.GRAY);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 1 0");
			}
		}
	}
	
	private void startGame() {
		//create a new GameStartDialog and pass on the data
		GameStartDialog startDialog = new GameStartDialog();
		startDialog.setVisible(true);
		mainMenu.dispose();//dispose all frames of the main menu
		List<User> players = new ArrayList<User>();
		players.add(UserManager.getLocalUser());
		for (User user : answers) {
			if (!rejections.contains(user)) {
				players.add(user);
			}
		}
		startDialog.startGameMaster(client, players, selectedBoard);
	}
	
	private DefaultListModel<User> createUserListModel() {
		DefaultListModel<User> model = new DefaultListModel<User>();
		for (User user : UserManager.getUsers()) {
			if (user.isOnline() && !user.isInGame() && !user.getUsername().equals(UserManager.getUsername())) {
				model.addElement(user);
			}
		}
		return model;
	}
	
	private void sendGameRequest(List<User> players, Board board) {
		MainMenuMessage gameRequest = new MainMenuMessage();
		gameRequest.setMessageType(MainMenuMessage.MessageType.GAME_CREATION_REQUEST);
		gameRequest.setInvitedPlayers(players);
		gameRequest.setPlayer(new User(UserManager.getUsername()));
		gameRequest.setMap(board.getName());
		gameRequest.setBoardId(board.getBoardId());
		client.resetOutput();
		client.sendMessage(gameRequest);
		okButton.setEnabled(false);
		rejections = new ArrayList<User>();
		answers = new ArrayList<User>();
	}
	private void sendGameCreationAbort() {
		sendGameCreationAbort("Der Startspieler hat die Anfrage zurückgezogen");
	}
	private void sendGameCreationAbort(String cause) {
		if (requestSent) {
			MainMenuMessage gameCreationAbort = new MainMenuMessage();
			gameCreationAbort.setMessageType(MainMenuMessage.MessageType.GAME_CREATEION_ABORT);
			gameCreationAbort.setPlayer(new User(UserManager.getUsername()));
			gameCreationAbort.setInvitedPlayers(invitedUsers);
			gameCreationAbort.setAbortCause(cause);
			client.resetOutput();
			client.sendMessage(gameCreationAbort);
		}
	}
	public void receiveClientAnswer(User user, boolean joining) {
		//if the user has already answered delete him before adding him again
		answers.remove(user);
		answers.add(user);
		boolean gameActive = true;
		if (joining) {
			txtrAnswers.append("Zusage von: ");
		}
		else {
			txtrAnswers.append("Absage von: ");
			rejections.add(user);
			if (invitedUsers.size() - rejections.size() + 1 < selectedBoard.getPlayersMin()) {//invited - rejected + player himself
				//not enough players left
				lblError.setText("Nicht mehr genügend Spieler vorhanden.");
				gameActive = false;
				sendGameCreationAbort("Absage von " + user.getUsername() +  ". Zu wenig Spieler.");
			}
		}
		txtrAnswers.append(user.getUsername() + "\n");
		if (gameActive && answers.size() == invitedUsers.size()) {
			//all users have answered the request
			lblError.setText("Alle Spieler haben geantwortet.");
			allRequestAnswered = true;
			okButton.setText("Starten");
			okButton.setEnabled(true);
		}
	}
}