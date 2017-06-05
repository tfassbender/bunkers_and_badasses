package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatDialog;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatPanel;
import net.jfabricationgames.bunkers_and_badasses.game.BunkersAndBadassesClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game.UserColor;
import net.jfabricationgames.bunkers_and_badasses.game.UserColorManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.HeroSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardPanel;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCardPanel;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class GameFrame extends JFrame implements BoardPanelListener, HeroSelectionListener, ConfirmDialogListener {
	
	private static final long serialVersionUID = 2516173588531625786L;
	
	private JPanel contentPane;

	private ChatClient chatClient;
	private ChatDialog chatDialog;
	private ChatPanel chatPanel;
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	private PlayerOrderPanel orderPanel;
	private PointPanel pointPanel;
	
	private FieldOverviewFrame fieldOverviewFrame;
	private FightExecutionFrame fightExecutionFrame;
	private GameOverviewFrame gameOverviewFrame;
	private ResourceInfoFrame resourceInfoFrame;
	private SelectHeroCardFrame selectHeroCardFrame;
	//private SupportRequestFrame supportRequestDialog;
	private TroopInfoFrame troopInfoFrame;
	private TurnExecutionFrame turnExecutionFrame;
	private TurnGoalTurnBonusDialog turnGoalTurnBonusDialog;
	private TurnPlaningFrame turnPlaningFrame;
	private BoardOverviewFrame boardOverviewFrame;
	
	private Game game;
	
	private Field selectedField;
	
	private JTextField txtPhase;
	private JTextField txtActiveplayer;
	private JTextField txtSelectedfield;
	private JTextField txtCommand;
	
	private static ImageLoader imageLoader;
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
	}

	private DefaultListModel<Hero> heroesListModel = new DefaultListModel<Hero>();
	//private DefaultListModel<GameLogMessage> logListModel = new DefaultListModel<GameLogMessage>();
	
	private JPanel panel_colors_1;
	private TurnGoalCardPanel panel_turn_goal;
	private TurnBonusCardPanel panel_turn_bonus;
	private BoardPanel boardPanel;
	private JButton btnSpielzugAusfhren;
	private JButton btnEinsetzen;
	
	public GameFrame(Game game) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new ConfirmDialog("Spiel wirklich beenden?", GameFrame.this, 0).setVisible(true);
			}
		});
		this.game = game;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		setMinimumSize(new Dimension(1250, 750));
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	       //e.printStackTrace();
	    }
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSpiel = new JMenu("Spiel");
		menuBar.add(mnSpiel);
		
		JMenuItem mntmStatistik = new JMenuItem("Statistik");
		mntmStatistik.setEnabled(false);
		mnSpiel.add(mntmStatistik);
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.setEnabled(false);
		mnSpiel.add(mntmBeenden);
		
		JMenu mnDialog = new JMenu("Dialog");
		menuBar.add(mnDialog);
		
		JMenuItem mntmPlanungsDialogffnen = new JMenuItem("Zug Planung");
		mntmPlanungsDialogffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnPlaningFrame.setVisible(true);
				turnPlaningFrame.requestFocus();
			}
		});
		mnDialog.add(mntmPlanungsDialogffnen);
		
		JMenuItem mntmZugAusfhren = new JMenuItem("Zug Ausf\u00FChren");
		mntmZugAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnExecutionFrame.setVisible(true);
				turnExecutionFrame.requestFocus();
			}
		});
		mnDialog.add(mntmZugAusfhren);
		
		JMenuItem mntmSpielbersicht = new JMenuItem("Spiel\u00FCbersicht");
		mntmSpielbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameOverviewFrame.setVisible(true);
				gameOverviewFrame.requestFocus();
			}
		});
		
		JMenuItem mntmRundenZiele = new JMenuItem("Runden Ziele");
		mntmRundenZiele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				turnGoalTurnBonusDialog.setVisible(true);
				turnGoalTurnBonusDialog.requestFocus();
				turnGoalTurnBonusDialog.showPanel(TurnGoalTurnBonusDialog.TURN_GOAL_PANEL);
			}
		});
		
		JMenuItem mntmkamfAusfhrung = new JMenuItem("Kamf Ausf\u00FChrung");
		mntmkamfAusfhrung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fightExecutionFrame.setVisible(true);
				fightExecutionFrame.requestFocus();
			}
		});
		mnDialog.add(mntmkamfAusfhrung);
		mnDialog.add(mntmRundenZiele);
		
		JMenuItem mntmRundenBoni = new JMenuItem("Runden Boni");
		mntmRundenBoni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnGoalTurnBonusDialog.setVisible(true);
				turnGoalTurnBonusDialog.requestFocus();
				turnGoalTurnBonusDialog.showPanel(TurnGoalTurnBonusDialog.TURN_BONUS_PANEL);
			}
		});
		mnDialog.add(mntmRundenBoni);
		mnDialog.add(mntmSpielbersicht);
		
		JMenuItem mntmGebietsbersicht = new JMenuItem("Gebiets\u00FCbersicht");
		mntmGebietsbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fieldOverviewFrame.setVisible(true);
				fieldOverviewFrame.requestFocus();
			}
		});
		mnDialog.add(mntmGebietsbersicht);
		
		JMenuItem mntmTruppenInfoDialog = new JMenuItem("Truppen Info");
		mntmTruppenInfoDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				troopInfoFrame.setVisible(true);
				troopInfoFrame.requestFocus();
			}
		});
		mnDialog.add(mntmTruppenInfoDialog);
		
		JMenuItem mntmResourcenInfoDialog = new JMenuItem("Resourcen Info");
		mntmResourcenInfoDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resourceInfoFrame.setVisible(true);
				resourceInfoFrame.requestFocus();
			}
		});
		mnDialog.add(mntmResourcenInfoDialog);
		
		JMenuItem mntmHeldenInfoDialog = new JMenuItem("Helden Info");
		mntmHeldenInfoDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectHeroCardFrame.setVisible(true);
				selectHeroCardFrame.requestFocus();
				selectHeroCardFrame.setCardSelectionEnabled(false, null);
			}
		});
		mnDialog.add(mntmHeldenInfoDialog);
		
		JMenuItem mntmChatDialog = new JMenuItem("Chat Dialog");
		mntmChatDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatDialog.setVisible(true);
				chatDialog.requestFocus();
			}
		});
		
		JMenuItem mntmSpielfeldbersicht = new JMenuItem("Spielfeld Übersicht");
		mntmSpielfeldbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardOverviewFrame.setVisible(true);
				boardOverviewFrame.requestFocus();
			}
		});
		mnDialog.add(mntmSpielfeldbersicht);
		mnDialog.add(mntmChatDialog);
		
		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);
		
		JMenuItem mntmSpiel = new JMenuItem("Spiel");
		mnHilfe.add(mntmSpiel);
		
		JMenuItem mntmSpielFunktionen = new JMenuItem("Spiel Funktionen");
		mnHilfe.add(mntmSpielFunktionen);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		chatClient = ((BunkersAndBadassesClientInterpreter) game.getClient().getClientInterpreter()).getChatClient();
		chatPanel = new ChatPanel(chatClient);
		chatPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		chatClient.addChatPanel(chatPanel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[900px,grow][:350px:400px,grow]", "[500px,grow][:200px:200px,grow]"));
		
		boardPanel = new BoardPanel();
		boardPanel.addBoardPanelListener(this);
		panel.add(boardPanel, "cell 0 0,grow");
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel.add(panel_side_bar, "cell 1 0,grow");
		panel_side_bar.setLayout(new MigLayout("", "[grow]", "[150px,grow][:150px:350px,grow][:300px:300px,grow]"));
		
		chatPanel.setBackground(Color.GRAY);
		panel_side_bar.add(chatPanel, "cell 0 0,grow");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(Color.GRAY);
		panel_side_bar.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[][grow]"));
		
		JLabel lblRundenZiel = new JLabel("Runden Ziel:");
		lblRundenZiel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblRundenZiel, "cell 0 0,alignx center");
		
		JLabel lblRundenBonus = new JLabel("Runden Bonus:");
		lblRundenBonus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblRundenBonus, "cell 1 0,alignx center");
		
		panel_turn_goal = new TurnGoalCardPanel();
		panel_turn_goal.setBackground(Color.GRAY);
		panel_1.add(panel_turn_goal, "cell 0 1,grow");
		
		panel_turn_bonus = new TurnBonusCardPanel();
		panel_turn_bonus.setBackground(Color.GRAY);
		panel_1.add(panel_turn_bonus, "cell 1 1,grow");
		
		fieldPanel = new FieldDescriptionPanel("Feld �bersicht", true);
		panel_side_bar.add(fieldPanel, "cell 0 2,grow");
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel.add(panel_low_bar, "cell 0 1 2 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[250px,grow][250px,grow][:200px:200px,grow][:200px:200px,grow][:200px:200px,grow][:200px:200px,grow][::200px,grow 99]", "[100px,grow]"));
		
		JPanel panel_turn = new JPanel();
		panel_turn.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_turn.setBackground(Color.GRAY);
		panel_low_bar.add(panel_turn, "cell 0 0,grow");
		panel_turn.setLayout(new MigLayout("", "[grow][grow]", "[][grow][][10px][][][][][grow]"));
		
		JLabel lblSpielzug = new JLabel("Spielzug:");
		lblSpielzug.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_turn.add(lblSpielzug, "cell 0 0 2 1,alignx center");
		
		JButton btnSpielfeldbersicht = new JButton("Spielfeld \u00DCbersicht");
		btnSpielfeldbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel.showOtherView();
			}
		});
		btnSpielfeldbersicht.setToolTipText("<html>\r\nZwichen einer \u00DCbersicht \u00FCber das <br>\r\ngesammte Spielfeld und einer kleineren <br>\r\ndetailierteren Sicht wechseln\r\n</html>");
		btnSpielfeldbersicht.setBackground(Color.GRAY);
		panel_turn.add(btnSpielfeldbersicht, "cell 0 2,alignx center");
		
		btnSpielzugAusfhren = new JButton("Spielzug Ausf\u00FChren");
		btnSpielzugAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				turnExecutionFrame.setVisible(true);
				turnExecutionFrame.requestFocus();
				turnExecutionFrame.setSelectedField(selectedField);
			}
		});
		btnSpielzugAusfhren.setEnabled(false);
		btnSpielzugAusfhren.setToolTipText("<html>\r\nDen Spielzug des ausgew\u00E4hlten <br>\r\nFeldes ausf\u00FChren (Wie der Befehl <br>\r\nausgef\u00FChrt wird, wird im Folgenden <br>\r\nDialog festgelegt\r\n</html>");
		btnSpielzugAusfhren.setBackground(Color.GRAY);
		panel_turn.add(btnSpielzugAusfhren, "cell 1 2,alignx center");
		
		JLabel lblSpielphase = new JLabel("Spielphase:");
		lblSpielphase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblSpielphase, "cell 0 4");
		
		JLabel lblAusgewhltesFeld = new JLabel("Ausgew\u00E4hltes Feld:");
		lblAusgewhltesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblAusgewhltesFeld, "cell 1 4");
		
		txtPhase = new JTextField();
		txtPhase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPhase.setEditable(false);
		txtPhase.setBackground(Color.LIGHT_GRAY);
		panel_turn.add(txtPhase, "cell 0 5,growx");
		txtPhase.setColumns(10);
		
		txtSelectedfield = new JTextField();
		txtSelectedfield.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSelectedfield.setBackground(Color.LIGHT_GRAY);
		txtSelectedfield.setEditable(false);
		panel_turn.add(txtSelectedfield, "cell 1 5,growx");
		txtSelectedfield.setColumns(10);
		
		JLabel lblAktiverSpieler = new JLabel("Aktiver Spieler:");
		lblAktiverSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblAktiverSpieler, "cell 0 6");
		
		JLabel lblBefehl = new JLabel("Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblBefehl, "cell 1 6");
		
		txtActiveplayer = new JTextField();
		txtActiveplayer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtActiveplayer.setBackground(Color.LIGHT_GRAY);
		txtActiveplayer.setEditable(false);
		panel_turn.add(txtActiveplayer, "cell 0 7,growx");
		txtActiveplayer.setColumns(10);
		
		txtCommand = new JTextField();
		txtCommand.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCommand.setBackground(Color.LIGHT_GRAY);
		txtCommand.setEditable(false);
		panel_turn.add(txtCommand, "cell 1 7,growx");
		txtCommand.setColumns(10);
		
		resourcePanel = new ResourceInfoPanel();
		panel_low_bar.add(resourcePanel, "cell 1 0,grow");
		
		JPanel panel_heroes = new JPanel();
		panel_heroes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_heroes.setBackground(Color.GRAY);
		panel_low_bar.add(panel_heroes, "cell 2 0,grow");
		panel_heroes.setLayout(new MigLayout("", "[grow][grow]", "[][5px][grow][]"));
		
		JLabel lblHeroes = new JLabel("Helden Karten:");
		lblHeroes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_heroes.add(lblHeroes, "cell 0 0 2 1,alignx center");
		
		JScrollPane scrollPane_heroes = new JScrollPane();
		scrollPane_heroes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_heroes.add(scrollPane_heroes, "cell 0 2 2 1,grow");
		
		JList<Hero> list_heroes = new JList<Hero>(heroesListModel);
		list_heroes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_heroes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_heroes.setBackground(Color.LIGHT_GRAY);
		scrollPane_heroes.setViewportView(list_heroes);
		
		JButton btnbersicht = new JButton("\u00DCbersicht");
		btnbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectHeroCardFrame.setVisible(true);
				selectHeroCardFrame.requestFocus();
				selectHeroCardFrame.setCardSelectionEnabled(false, null);
			}
		});
		btnbersicht.setToolTipText("<html>\r\n\u00DCbersicht \u00FCber die vorhandenen <br>\r\nHelden Karten\r\n</html>");
		btnbersicht.setBackground(Color.GRAY);
		panel_heroes.add(btnbersicht, "cell 0 3,alignx center");
		
		btnEinsetzen = new JButton("Einsetzen");
		btnEinsetzen.setEnabled(false);
		btnEinsetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectHeroCardFrame.setVisible(true);
				selectHeroCardFrame.requestFocus();
				selectHeroCardFrame.setCardSelectionEnabled(true, GameFrame.this);
			}
		});
		btnEinsetzen.setToolTipText("<html>\r\nEine der vorhandenen Helden Karten<br>\r\n(deren Spezialfunktion) einsetzen\r\n</html>");
		btnEinsetzen.setBackground(Color.GRAY);
		panel_heroes.add(btnEinsetzen, "cell 1 3,alignx center");
		
		orderPanel = new PlayerOrderPanel();
		panel_low_bar.add(orderPanel, "cell 3 0,grow");
		
		pointPanel = new PointPanel();
		panel_low_bar.add(pointPanel, "cell 4 0,grow");
		
		JPanel panel_player_colors = new JPanel();
		panel_player_colors.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_player_colors.setBackground(Color.GRAY);
		panel_low_bar.add(panel_player_colors, "cell 5 0,grow");
		panel_player_colors.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblFarben = new JLabel("Farben:");
		lblFarben.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_player_colors.add(lblFarben, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_colors = new JScrollPane();
		panel_player_colors.add(scrollPane_colors, "cell 0 1,grow");
		
		panel_colors_1 = new JPanel();
		panel_colors_1.setBackground(Color.GRAY);
		scrollPane_colors.setViewportView(panel_colors_1);
		
		JPanel panel_logo_capture = new JPanel();
		panel_logo_capture.setBackground(Color.GRAY);
		panel_low_bar.add(panel_logo_capture, "cell 6 0,grow");
		panel_logo_capture.setLayout(new MigLayout("", "[grow]", "[grow][:200px:200px,grow]"));
		
		ImagePanel panel_logo = new ImagePanel(imageLoader.loadImage("game_frame/logo_1.png"));
		panel_logo.setImageMinimumSize(new int[] {100, 100});
		panel_logo.setRemoveIfToSmall(true);
		panel_logo.setCentered(true);
		panel_logo.setAdaptSizeKeepProportion(true);
		panel_logo.setBackground(Color.GRAY);
		panel_logo_capture.add(panel_logo, "cell 0 1,grow");
		
		addPlayerColors(panel_colors_1, game.getPlayers(), game.getColorManager());
		
		intitGuis();
		update();
	}
	
	/**
	 * Initialize all the GUI elements needed for the game.
	 */
	private void intitGuis() {
		fieldOverviewFrame = new FieldOverviewFrame(game);
		fightExecutionFrame = new FightExecutionFrame(game);
		gameOverviewFrame = new GameOverviewFrame(game);
		resourceInfoFrame = new ResourceInfoFrame(game);
		selectHeroCardFrame = new SelectHeroCardFrame(game, false);
		//supportRequestDialog = new SupportRequestFrame(game);
		troopInfoFrame = new TroopInfoFrame(game);
		turnExecutionFrame = new TurnExecutionFrame(game);
		turnGoalTurnBonusDialog = new TurnGoalTurnBonusDialog(game, true, false);
		turnPlaningFrame = new TurnPlaningFrame(game);
		boardOverviewFrame = new BoardOverviewFrame(game.getBoard());
		chatDialog = new ChatDialog(chatClient, this);
		SupportRequestFrame.setLocalPlayer(game.getLocalUser());
	}
	
	/**
	 * Display the players color in this panel.
	 */
	private class UserColorPanel extends JPanel {
		
		private static final long serialVersionUID = 1744860767844725855L;
		
		private UserColor color;
		
		public UserColorPanel(UserColor color) {
			this.color = color;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(color.getColor());
			g.fillOval(2, 2, getWidth()-4, getHeight()-4);//draw an oval of the users color on the panel
		}
	}
	
	/**
	 * Add the player names and their colors to a panel.
	 * 
	 * @param panel_colors
	 * 		The panel to which the user colors are added.
	 * 
	 * @param players
	 * 		A list of all players.
	 * 
	 * @param colorManager
	 * 		A ColorManager that knows the players colors.
	 */
	private void addPlayerColors(JPanel panel_colors, List<User> players, UserColorManager colorManager) {
		String singleRow = "[20px:n:20px]"; 
		StringBuilder rows = new StringBuilder();
		for (int i = 0; i < players.size(); i++) {
			rows.append(singleRow);
		}
		panel_colors.setLayout(new MigLayout("", "[grow][25px:n:25px]", rows.toString()));//set the layout
		Font font = new Font("Tahoma", Font.PLAIN, 12);
		for (int i = 0; i < players.size(); i++) {
			//create a label for the players name and a panel for his color
			JLabel lblName = new JLabel(players.get(i).getUsername());
			lblName.setFont(font);
			UserColorPanel colorPanel = new UserColorPanel(colorManager.getUserColors().get(players.get(i)));
			colorPanel.setBackground(Color.GRAY);
			
			//add the components to the panel
			panel_colors.add(lblName, "cell 0 " + i);
			panel_colors.add(colorPanel, "cell 1 " + i + ",grow");
		}
	}
	
	@Override
	public void receiveBoardMouseClick(MouseEvent event) {
		selectCurrentField();
	}
	
	@Override
	public void receiveSelectedHero(Hero hero) {
		//TODO receive the selected hero card (play hero card)
	}

	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			//TODO save the game, inform the other players and logout the player
			System.exit(0);
		}
	}
	
	public void update() {
		updateBoard();
		updateGameTurnPanel();
		updateTurnCards();
		updateHeroCards();
		updateTurnOrder();
		updateResources();
		updatePoints();
		repaint();
	}
	
	private void selectCurrentField() {
		selectedField = game.getBoard().getFieldAtMousePosition();
		fieldPanel.updateField(selectedField);
		updateGameTurnPanel();
	}
	
	private void updateBoard() {
		BufferedImage boardImage = game.getBoard().displayBoard();
		boardPanel.updateBoardImage(boardImage);
		boardOverviewFrame.updateBoardImage(boardImage);
	}
	
	private void updateGameTurnPanel() {
		txtPhase.setText(game.getGameState().getPhaseName());
		if (selectedField != null) {
			txtSelectedfield.setText(selectedField.getName());
		}
		else {
			txtSelectedfield.setText("");
		}
		User activePlayer = game.getTurnManager().getPlayerOrder().getActivePlayer();
		if (activePlayer != null) {
			txtActiveplayer.setText(activePlayer.getUsername());
		}
		else {
			txtActiveplayer.setText("");
		}
		if (selectedField != null && selectedField.getCommand() != null) {
			txtCommand.setText(selectedField.getCommand().getName());
		}
		else {
			txtCommand.setText("");
		}
		if (game.getGameState() == GameState.ACT && selectedField.getAffiliation().equals(game.getLocalUser())) {
			btnSpielzugAusfhren.setEnabled(true);
			btnEinsetzen.setEnabled(true);
		}
		else {
			btnSpielzugAusfhren.setEnabled(false);
			btnEinsetzen.setEnabled(false);
		}
	}
	
	private void updateTurnCards() {
		if (game.getGameTurnBonusManager().getBonuses() != null) {
			panel_turn_bonus.setTurnBonus(game.getGameTurnBonusManager().getUsersBonus(game.getLocalUser()));
			panel_turn_goal.setTurnGoal(game.getTurnManager().getGameTurnGoalManager().getTurnGoal());
		}
	}
	
	private void updateHeroCards() {
		heroesListModel.removeAllElements();
		List<Hero> heros = game.getHeroCardManager().getHeroCards(game.getLocalUser());
		for (int i = 0; i < heros.size(); i++) {
			heroesListModel.addElement(heros.get(i));
		}
	}
	
	private void updateTurnOrder() {
		orderPanel.updateTurnOrder(game);
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
	}
	
	private void updatePoints() {
		pointPanel.updatePoints(game);
	}
	
	public FieldOverviewFrame getFieldOverviewFrame() {
		return fieldOverviewFrame;
	}
	public FightExecutionFrame getFightExecutionFrame() {
		return fightExecutionFrame;
	}
	public GameOverviewFrame getGameOverviewFrame() {
		return gameOverviewFrame;
	}
	public ResourceInfoFrame getResourceInfoFrame() {
		return resourceInfoFrame;
	}
	public SelectHeroCardFrame getSelectHeroCardFrame() {
		return selectHeroCardFrame;
	}
	public TroopInfoFrame getTroopInfoFrame() {
		return troopInfoFrame;
	}
	public TurnExecutionFrame getTurnExecutionFrame() {
		return turnExecutionFrame;
	}
	public TurnGoalTurnBonusDialog getTurnGoalTurnBonusDialog() {
		return turnGoalTurnBonusDialog;
	}
	public TurnPlaningFrame getTurnPlaningFrame() {
		return turnPlaningFrame;
	}
	public BoardOverviewFrame getBoardOverviewFrame() {
		return boardOverviewFrame;
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
}