package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatPanel;
import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_communication.BoardOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.bunkers_and_badasses.server.UserLogoutMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class MainMenuFrame extends JFrame {
	
	private static final long serialVersionUID = 8543413745165916921L;
	
	private MainMenuDynamicLoader dynamicLoader;
	private JFGClient client;
	//private List<User> playersOnline;
	//private List<User> playersInGame;
	
	private GameStore gameStore;
	
	private List<Board> playableBoards;//no complete boards; only name and image
	
	private Map<User, GameRequestDialog> requestDialogs = new HashMap<User, GameRequestDialog>();
	private GameCreationDialog gameCreationDialog;
	private GameLoadingDialog gameLoadingDialog;
	private AccountSettingsDialog accountSettingsDialog;
	
	private static ImageLoader imageLoader;
	
	private JPanel contentPane;
	private JPanel panel_buttons;
	private ChatClient chatClient;
	private JTextArea txtrPlayers;
	private ChatPanel chatPanel;
	
	public MainMenuFrame(JFGClient client) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.sendMessage(new UserLogoutMessage());
			}
		});
		this.client = client;
		
		gameStore = new GameStore(client);
		dynamicLoader = new MainMenuDynamicLoader(client);
		chatClient = new ChatClient(client);
		MainMenuClientInterpreter interpreter = new MainMenuClientInterpreter(chatClient, dynamicLoader, this, gameStore);
		client.setClientInterpreter(interpreter);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	       //e.printStackTrace();
	    }
		
		setTitle("Bunkers and Badasses - Hauptmen\u00FC");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenuFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setMinimumSize(new Dimension(900, 600));
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProfil = new JMenu("Profil");
		menuBar.add(mnProfil);
		
		JMenuItem mntmProfilEinstellungen = new JMenuItem("Profil Einstellungen");
		mntmProfilEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAccountSettings();
			}
		});
		mnProfil.add(mntmProfilEinstellungen);
		
		JMenuItem mntmSkillProfil = new JMenuItem("Skill Profil");
		mntmSkillProfil.setEnabled(false);
		mnProfil.add(mntmSkillProfil);
		
		JMenuItem mntmStatistiken = new JMenuItem("Statistiken");
		mntmStatistiken.setEnabled(false);
		mnProfil.add(mntmStatistiken);
		
		JMenu mnSpiel = new JMenu("Spiel");
		menuBar.add(mnSpiel);
		
		JMenuItem mntmSpielErstellen = new JMenuItem("Spiel Erstellen");
		mntmSpielErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGameCreationDialog();
			}
		});
		mnSpiel.add(mntmSpielErstellen);
		
		JMenuItem mntmSpielLaden = new JMenuItem("Spiel Laden");
		mntmSpielLaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGameLoadingDialog();
			}
		});
		mnSpiel.add(mntmSpielLaden);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_content = new JPanel();
		panel_content.setBackground(Color.GRAY);
		contentPane.add(panel_content, "cell 0 0,grow");
		panel_content.setLayout(new MigLayout("", "[100px:150px:200px,grow][400px,grow][100px:150px:200px,grow][250px:300px,grow]", "[60px:n:60px][::25px][250px,grow][200px,grow][100px]"));
		
		ImagePanel panel_headline = new ImagePanel(imageLoader.loadImage("jfg/headline.png"));
		panel_headline.setCentered(true);
		panel_headline.setAdaptSizeKeepProportion(true);
		panel_headline.setBackground(Color.GRAY);
		panel_content.add(panel_headline, "cell 0 0 4 1,grow");
		
		ImagePanel panel_img_1 = new ImagePanel(imageLoader.loadImage("main_menu/athena_1.png"));
		panel_img_1.setBackground(Color.GRAY);
		panel_img_1.setCentered(true);
		panel_img_1.setAdaptSizeKeepProportion(true);
		panel_content.add(panel_img_1, "cell 0 2,grow");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.GRAY);
		panel_content.add(scrollPane, "cell 1 2,grow");
		
		JTextArea txtrDynamicContent = new JTextArea();
		txtrDynamicContent.setBackground(Color.LIGHT_GRAY);
		txtrDynamicContent.setEditable(false);
		txtrDynamicContent.setWrapStyleWord(true);
		txtrDynamicContent.setLineWrap(true);
		scrollPane.setViewportView(txtrDynamicContent);
		
		dynamicLoader.addDynamicContent(txtrDynamicContent);
		
		ImagePanel panel_img_2 = new ImagePanel(imageLoader.loadImage("login/arschgaul_2.png"));
		panel_img_2.setBackground(Color.GRAY);
		panel_img_2.setCentered(true);
		panel_img_2.setAdaptSizeKeepProportion(true);
		panel_content.add(panel_img_2, "cell 2 2,grow");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel_content.add(panel, "cell 3 2 1 2,grow");
		panel.setLayout(new MigLayout("", "[47px,grow]", "[39px,grow]"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane_1, "cell 0 0,grow");
		scrollPane_1.setBackground(Color.GRAY);
		
		txtrPlayers = new JTextArea();
		txtrPlayers.setEditable(false);
		txtrPlayers.setWrapStyleWord(true);
		txtrPlayers.setLineWrap(true);
		txtrPlayers.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(txtrPlayers);
		
		chatPanel = new ChatPanel(chatClient);
		chatClient.addChatPanel(chatPanel);
		chatPanel.setBackground(Color.GRAY);
		panel_content.add(chatPanel, "cell 0 3 3 2,grow");
		
		panel_buttons = new JPanel();
		panel_buttons.setBackground(Color.GRAY);
		panel_content.add(panel_buttons, "cell 3 4,grow");
		panel_buttons.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JButton btnSpielErstellen = new JButton("Spiel Erstellen");
		btnSpielErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGameCreationDialog();
			}
		});
		btnSpielErstellen.setBackground(Color.GRAY);
		panel_buttons.add(btnSpielErstellen, "cell 0 0,alignx center,aligny center");
		
		updateUserList();
		requireBoards();
	}
	
	@Override
	public void dispose() {
		for (GameRequestDialog request : requestDialogs.values()) {
			request.dispose();
		}
		gameCreationDialog.dispose();
		gameLoadingDialog.dispose();
		accountSettingsDialog.dispose();
		super.dispose();
	}
	
	public void updateUserList() {
		StringBuilder inGameUsers = new StringBuilder();
		StringBuilder onlineUsers = new StringBuilder();
		StringBuilder offlineUsers = new StringBuilder();
		for (User user : UserManager.getUsers()) {
			if (user.isInGame()) {
				inGameUsers.append("[in game] ");
				inGameUsers.append(user.getUsername());
				inGameUsers.append("\n");
			}
			else if (user.isOnline()) {
				onlineUsers.append("[online] ");
				onlineUsers.append(user.getUsername());
				onlineUsers.append("\n");
			}
			else {
				offlineUsers.append("[offline] ");
				offlineUsers.append(user.getUsername());
				offlineUsers.append("\n");
			}
		}
		inGameUsers.append(onlineUsers.toString());
		inGameUsers.append("\n");
		inGameUsers.append(offlineUsers.toString());
		inGameUsers.append("\n");
		txtrPlayers.setText(inGameUsers.toString());
		revalidate();
		repaint();
	}
	
	private void requireBoards() {
		BoardOverviewRequestMessage message = new BoardOverviewRequestMessage();
		client.sendMessage(message);
	}
	
	private void startGameCreationDialog() {
		if (gameCreationDialog == null) {
			gameCreationDialog = new GameCreationDialog(client, playableBoards, this);
			gameCreationDialog.setVisible(true);
		}
		else {
			gameCreationDialog.requestFocus();
		}
	}
	private void startGameLoadingDialog() {
		if (gameLoadingDialog == null) {
			gameLoadingDialog = new GameLoadingDialog(client, this, playableBoards);
			gameLoadingDialog.setVisible(true);
		}
		else {
			gameLoadingDialog.requestFocus();
		}
	}
	private void showAccountSettings() {
		if (accountSettingsDialog != null) {
			accountSettingsDialog.dispose();			
		}
		accountSettingsDialog = new AccountSettingsDialog(client, this);
		accountSettingsDialog.setVisible(true);
	}
	public void showGameRequest(User startingPlayer, List<User> invitedPlayers, String map) {
		showGameRequest(startingPlayer, invitedPlayers, map, null);
	}
	public void showGameRequest(User startingPlayer, List<User> invitedPlayers, String map, GameOverview overview) {
		//map the requests to the starting player
		GameRequestDialog request = new GameRequestDialog(client, this, startingPlayer, invitedPlayers, map, overview);
		GameRequestDialog last = requestDialogs.get(startingPlayer);
		if (last != null) {
			//if the same player sent another request earlier, dispose the old request 
			last.dispose();
		}
		requestDialogs.put(startingPlayer, request);
		request.setVisible(true);
	}
	
	protected void disposeGameCreationDialog() {
		gameCreationDialog = null;
	}
	protected void disposeGameLoadingDialog() {
		gameLoadingDialog = null;
	}
	public void receiveGameCreationAnswer(User player, boolean joining) {
		if (gameCreationDialog != null) {
			gameCreationDialog.receiveClientAnswer(player, joining);
		}
	}
	public void receiveGameLoadingAnswer(User player, boolean joining) {
		if (gameLoadingDialog != null) {
			gameLoadingDialog.receiveGameLoadingAnswer(player, joining);
		}
	}
	
	public void receiveGameCreationAbort(User startingPlayer, String cause) {
		GameRequestDialog request = requestDialogs.get(startingPlayer);
		if (request != null) {
			request.receiveAbort(cause);
		}
	}
	
	public void receiveGameStartMessage(User startingPlayer, int boardId, int players) {
		requestDialogs.get(startingPlayer).startGame(boardId, players);
	}
	
	public void receiveAccoutUpdateAnswer(boolean answer, String username) {
		if (accountSettingsDialog != null) {
			accountSettingsDialog.receiveUpdateAnswer(answer, username);
		}
	}
	
	public void receiveGameOverviews(List<GameOverview> gameOverviews) {
		if (gameLoadingDialog != null) {
			gameLoadingDialog.receiveGameOverviews(gameOverviews);
		}
	}
	public void receiveBoardOverviews(List<Board> boards) {
		this.playableBoards = boards;
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	public JFGClient getClient() {
		return client;
	}
}