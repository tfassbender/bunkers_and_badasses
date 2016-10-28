package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
	
	private GameCreationDialog gameCreationDialog;
	
	private static ImageLoader imageLoader;
	
	private JPanel contentPane;
	private JPanel panel_buttons;
	private ChatPanel panel_chat;
	private ChatClient chatClient;
	private JTextArea txtrPlayers;
	
	public MainMenuFrame(JFGClient client) {
		this.client = client;
		
		dynamicLoader = new MainMenuDynamicLoader(client);
		chatClient = new ChatClient(client);
		MainMenuClientInterpreter interpreter = new MainMenuClientInterpreter(chatClient, dynamicLoader, this);
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
		mntmSpielLaden.setEnabled(false);
		mnSpiel.add(mntmSpielLaden);
		
		JMenuItem mntmSpielEinladungAnzeigen = new JMenuItem("Spiel Einladung anzeigen");
		mntmSpielEinladungAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO remove after tests
				showGameRequest(null, null);
			}
		});
		mnSpiel.add(mntmSpielEinladungAnzeigen);
		
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
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBackground(Color.GRAY);
		panel_content.add(scrollPane_2, "cell 0 3 3 2,grow");
		
		panel_chat = new ChatPanel(chatClient);
		chatClient.setChatPanel(panel_chat);
		panel_chat.setBackground(Color.GRAY);
		scrollPane_2.setViewportView(panel_chat);
		
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
	
	private void startGameCreationDialog() {
		if (gameCreationDialog == null) {
			gameCreationDialog = new GameCreationDialog(client, this);
			gameCreationDialog.setVisible(true);
		}
	}
	private void showAccountSettings() {
		new AccountSettingsDialog(client, this).setVisible(true);
	}
	public void showGameRequest(User startingPlayer, List<User> invitedPlayers) {
		if (startingPlayer == null) {
			//TODO remove after test cases
			new GameRequestDialog(client, this, new User("user"), null).setVisible(true);
		}
		else {
			new GameRequestDialog(client, this, startingPlayer, invitedPlayers).setVisible(true);
		}
	}
	
	protected void disposeGameCreationDialog() {
		gameCreationDialog = null;
	}
	public void receiveGameCreationAnswer(User player, boolean joining) {
		if (gameCreationDialog != null) {
			gameCreationDialog.receiveClientAnswer(player, joining);
		}
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	public JFGClient getClient() {
		return client;
	}
}