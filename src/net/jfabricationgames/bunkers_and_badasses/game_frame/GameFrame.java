package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;
import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;
import com.jfabricationgames.toolbox.properties.event.PropertiesWindowListener;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatDialog;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatPanel;
import net.jfabricationgames.bunkers_and_badasses.game.BunkersAndBadassesClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.HeroSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero.ExecutionType;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardPanel;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoal;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCardPanel;
import net.jfabricationgames.bunkers_and_badasses.help.HelpMenuFrame;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame;
import net.jfabricationgames.bunkers_and_badasses.server.UserLogoutMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class GameFrame extends JFrame implements BoardPanelListener, HeroSelectionListener, ConfirmDialogListener {
	
	private static final long serialVersionUID = 2516173588531625786L;
	
	private JPanel contentPane;

	private ChatClient chatClient;
	private ChatDialog chatDialog;
	private ChatPanel chatPanel;
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	private PlayerOrderPanel orderPanel;
	private PlayerOrderPanel orderPanel_2;
	private PointPanel pointPanel;
	private PointPanel pointPanel_2;
	
	private FieldOverviewFrame fieldOverviewFrame;
	private FightExecutionFrame fightExecutionFrame;
	private GameOverviewFrame gameOverviewFrame;
	private ResourceInfoFrame resourceInfoFrame;
	private SelectHeroCardFrame selectHeroCardFrame;
	//private SupportRequestFrame supportRequestDialog;
	private TroopInfoFrame troopInfoFrame;
	private TurnExecutionFrame turnExecutionFrame;
	private HeroEffectExecutionFrame heroEffectExecutionFrame;
	private TurnGoalTurnBonusDialog turnGoalTurnBonusDialog;
	private TurnPlaningFrame turnPlaningFrame;
	private BoardOverviewFrame boardOverviewFrame;
	private HelpMenuFrame helpMenuFrame;
	
	private Game game;
	
	private Field selectedField;
	
	private JTextField txtPhase;
	private JTextField txtActiveplayer;
	private JTextField txtSelectedfield;
	private JTextField txtCommand;
	
	private static ImageLoader imageLoader;
	
	private static final String REDUCED_INFO_VIEW = "reduced_info";
	private static final String COMPLETE_INFO_VIEW = "complete_info";
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
	}
	
	//private DefaultListModel<GameLogMessage> logListModel = new DefaultListModel<GameLogMessage>();
	
	private TurnGoalCardPanel panel_turn_goal;
	private TurnGoalCardPanel panel_turn_goal_2;
	private TurnBonusCardPanel panel_turn_bonus;
	private TurnBonusCardPanel panel_turn_bonus_2;
	private BoardPanel boardPanel;
	private JButton btnSpielzugAusfhren;
	private HeroPanel panel_heroes;
	private HeroPanel panel_heroes_2;
	private UserColorPanel userColorPanel;
	private UserColorPanel userColorPanel_2;
	private JTextField txtActiveplayer_2;
	private JTextField txtPhase_2;
	private BoardPanel boardPanel_2;
	private JPanel panel_game_type;
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
	public GameFrame(Game game) {
		addWindowListener(new PropertiesWindowListener(propsFile, PropertiesWindowListener.WINDOW_CLOSING_EVENT));
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
		setMinimumSize(new Dimension(1000, 600));
		
		propsFile.alignWindow();
		
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
		mntmStatistik.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmStatistik.setEnabled(false);
		mnSpiel.add(mntmStatistik);
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.setEnabled(false);
		mnSpiel.add(mntmBeenden);
		
		JMenu mnDialog = new JMenu("Dialog");
		menuBar.add(mnDialog);
		
		JMenuItem mntmPlanungsDialogffnen = new JMenuItem("Zug Planung");
		mntmPlanungsDialogffnen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmPlanungsDialogffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnPlaningFrame.setVisible(true);
				turnPlaningFrame.requestFocus();
			}
		});
		mnDialog.add(mntmPlanungsDialogffnen);
		
		JMenuItem mntmZugAusfhren = new JMenuItem("Zug Ausf\u00FChren");
		mntmZugAusfhren.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
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
		mntmRundenZiele.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmRundenZiele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				turnGoalTurnBonusDialog.setVisible(true);
				turnGoalTurnBonusDialog.requestFocus();
				turnGoalTurnBonusDialog.showPanel(TurnGoalTurnBonusDialog.TURN_GOAL_PANEL);
			}
		});
		
		JMenuItem mntmkamfAusfhrung = new JMenuItem("Kampf Ausf\u00FChrung");
		mntmkamfAusfhrung.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmkamfAusfhrung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fightExecutionFrame.setVisible(true);
				fightExecutionFrame.requestFocus();
			}
		});
		mnDialog.add(mntmkamfAusfhrung);
		mnDialog.add(mntmRundenZiele);
		
		JMenuItem mntmRundenBoni = new JMenuItem("Runden Boni");
		mntmRundenBoni.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
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
		mntmChatDialog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmChatDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showChatDialog();
			}
		});
		
		JMenuItem mntmSpielfeldbersicht = new JMenuItem("Spielfeld Übersicht");
		mntmSpielfeldbersicht.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
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
		
		JMenuItem mntmHilfeMenu = new JMenuItem("Hilfe Menü");
		mntmHilfeMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmHilfeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpMenuFrame.setVisible(true);
				helpMenuFrame.requestFocus();
			}
		});
		mnHilfe.add(mntmHilfeMenu);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		chatClient = ((BunkersAndBadassesClientInterpreter) game.getClient().getClientInterpreter()).getChatClient();
		
		panel_game_type = new JPanel();
		contentPane.add(panel_game_type, "cell 0 0,grow");
		panel_game_type.setLayout(new CardLayout(0, 0));
		chatPanel = new ChatPanel(chatClient);
		chatPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		chatClient.addChatPanel(chatPanel);
		
		JPanel panel_complete_info_set = new JPanel();
		panel_game_type.add(panel_complete_info_set, COMPLETE_INFO_VIEW);
		panel_complete_info_set.setBackground(Color.GRAY);
		panel_complete_info_set.setLayout(new MigLayout("", "[900px,grow][:350px:400px,grow]", "[500px,grow][:200px:200px,grow]"));
		
		boardPanel = new BoardPanel();
		boardPanel.addBoardPanelListener(this);
		panel_complete_info_set.add(boardPanel, "cell 0 0,grow");
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel_complete_info_set.add(panel_side_bar, "cell 1 0,grow");
		panel_side_bar.setLayout(new MigLayout("", "[grow]", "[150px,grow][:150px:350px,grow][:300px:300px,grow]"));
		
		chatPanel.setBackground(Color.GRAY);
		panel_side_bar.add(chatPanel, "cell 0 0,grow");
		
		JPanel panel_turn_goals_bonuses = new JPanel();
		panel_turn_goals_bonuses.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_turn_goals_bonuses.setBackground(Color.GRAY);
		panel_side_bar.add(panel_turn_goals_bonuses, "cell 0 1,grow");
		panel_turn_goals_bonuses.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[][grow]"));
		
		JLabel lblRundenZiel = new JLabel("Runden Ziel:");
		lblRundenZiel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_turn_goals_bonuses.add(lblRundenZiel, "cell 0 0,alignx center");
		
		JLabel lblRundenBonus = new JLabel("Runden Bonus:");
		lblRundenBonus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_turn_goals_bonuses.add(lblRundenBonus, "cell 1 0,alignx center");
		
		panel_turn_goal = new TurnGoalCardPanel();
		panel_turn_goal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					turnGoalTurnBonusDialog.setVisible(true);
					turnGoalTurnBonusDialog.requestFocus();
					turnGoalTurnBonusDialog.showPanel(TurnGoalTurnBonusDialog.TURN_GOAL_PANEL);
				}
			}
		});
		panel_turn_goal.setBackground(Color.GRAY);
		panel_turn_goals_bonuses.add(panel_turn_goal, "cell 0 1,grow");
		
		panel_turn_bonus = new TurnBonusCardPanel();
		panel_turn_bonus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					turnGoalTurnBonusDialog.setVisible(true);
					turnGoalTurnBonusDialog.requestFocus();
					turnGoalTurnBonusDialog.showPanel(TurnGoalTurnBonusDialog.TURN_BONUS_PANEL);
				}
			}
		});
		panel_turn_bonus.setBackground(Color.GRAY);
		panel_turn_goals_bonuses.add(panel_turn_bonus, "cell 1 1,grow");
		
		fieldPanel = new FieldDescriptionPanel("Feld Übersicht", true);
		panel_side_bar.add(fieldPanel, "cell 0 2,grow");
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel_complete_info_set.add(panel_low_bar, "cell 0 1 2 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[250px,grow][250px,grow][:200px:200px,grow][:200px:200px,grow][:200px:200px,grow][:200px:200px,grow][::200px,grow 99]", "[100px,grow]"));
		
		JPanel panel_turn = new JPanel();
		panel_turn.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_turn.setBackground(Color.GRAY);
		panel_low_bar.add(panel_turn, "cell 0 0,grow");
		panel_turn.setLayout(new MigLayout("", "[grow][grow]", "[][grow][][][10px][][][][][grow]"));
		
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
		
		JButton btnWenigerEinblenden = new JButton("Weniger Einblenden");
		btnWenigerEinblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showReducedInfo();
			}
		});
		btnWenigerEinblenden.setBackground(Color.GRAY);
		panel_turn.add(btnWenigerEinblenden, "cell 0 3 2 1,alignx center");
		
		JLabel lblSpielphase = new JLabel("Spielphase:");
		lblSpielphase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblSpielphase, "cell 0 5");
		
		JLabel lblAusgewhltesFeld = new JLabel("Ausgew\u00E4hltes Feld:");
		lblAusgewhltesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblAusgewhltesFeld, "cell 1 5");
		
		txtPhase = new JTextField();
		txtPhase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPhase.setEditable(false);
		txtPhase.setBackground(Color.LIGHT_GRAY);
		panel_turn.add(txtPhase, "cell 0 6,growx");
		txtPhase.setColumns(10);
		
		txtSelectedfield = new JTextField();
		txtSelectedfield.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSelectedfield.setBackground(Color.LIGHT_GRAY);
		txtSelectedfield.setEditable(false);
		panel_turn.add(txtSelectedfield, "cell 1 6,growx");
		txtSelectedfield.setColumns(10);
		
		JLabel lblAktiverSpieler = new JLabel("Aktiver Spieler:");
		lblAktiverSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblAktiverSpieler, "cell 0 7");
		
		JLabel lblBefehl = new JLabel("Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_turn.add(lblBefehl, "cell 1 7");
		
		txtActiveplayer = new JTextField();
		txtActiveplayer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtActiveplayer.setBackground(Color.LIGHT_GRAY);
		txtActiveplayer.setEditable(false);
		panel_turn.add(txtActiveplayer, "cell 0 8,growx");
		txtActiveplayer.setColumns(10);
		
		txtCommand = new JTextField();
		txtCommand.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCommand.setBackground(Color.LIGHT_GRAY);
		txtCommand.setEditable(false);
		panel_turn.add(txtCommand, "cell 1 8,growx");
		txtCommand.setColumns(10);
		
		resourcePanel = new ResourceInfoPanel();
		panel_low_bar.add(resourcePanel, "cell 1 0,grow");
		
		panel_heroes = new HeroPanel(this);
		panel_heroes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_heroes.setBackground(Color.GRAY);
		panel_low_bar.add(panel_heroes, "cell 2 0,grow");
		
		orderPanel = new PlayerOrderPanel();
		panel_low_bar.add(orderPanel, "cell 3 0,grow");
		
		pointPanel = new PointPanel();
		panel_low_bar.add(pointPanel, "cell 4 0,grow");
		
		userColorPanel = new UserColorPanel();
		userColorPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_low_bar.add(userColorPanel, "cell 5 0,grow");
		
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
		
		JPanel panel_reduced_info_set = new JPanel();
		panel_reduced_info_set.setBackground(Color.GRAY);
		panel_game_type.add(panel_reduced_info_set, REDUCED_INFO_VIEW);
		panel_reduced_info_set.setLayout(new MigLayout("", "[800px,grow][:400px:400px,grow]", "[grow]"));
		
		boardPanel_2 = new BoardPanel();
		boardPanel_2.addBoardPanelListener(this);
		panel_reduced_info_set.add(boardPanel_2, "cell 0 0,grow");
		
		JPanel panel_side_bar_2 = new JPanel();
		panel_side_bar_2.setBackground(Color.GRAY);
		panel_reduced_info_set.add(panel_side_bar_2, "cell 1 0,grow");
		panel_side_bar_2.setLayout(new MigLayout("", "[grow][grow]", "[:175px:175px,grow][200px][175px][][grow 99]"));
		
		JPanel panel_turn_goals_bonuses_2 = new JPanel();
		panel_turn_goals_bonuses_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_turn_goals_bonuses_2.setBackground(Color.GRAY);
		panel_side_bar_2.add(panel_turn_goals_bonuses_2, "cell 0 0 2 1,grow");
		panel_turn_goals_bonuses_2.setLayout(new MigLayout("", "[100px,grow][100px,grow]", "[][grow]"));
		
		JLabel lblRundenZiel_2 = new JLabel("Runden Ziel:");
		lblRundenZiel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_turn_goals_bonuses_2.add(lblRundenZiel_2, "cell 0 0,alignx center");
		
		JLabel lblRundenBonus_2 = new JLabel("Runden Bonus:");
		lblRundenBonus_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_turn_goals_bonuses_2.add(lblRundenBonus_2, "cell 1 0,alignx center");
		
		panel_turn_goal_2 = new TurnGoalCardPanel();
		panel_turn_goal_2.setBackground(Color.GRAY);
		panel_turn_goals_bonuses_2.add(panel_turn_goal_2, "cell 0 1,grow");
		
		panel_turn_bonus_2 = new TurnBonusCardPanel();
		panel_turn_bonus_2.setBackground(Color.GRAY);
		panel_turn_goals_bonuses_2.add(panel_turn_bonus_2, "cell 1 1,grow");
		
		panel_heroes_2 = new HeroPanel(this);
		panel_heroes_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_heroes_2.setBackground(Color.GRAY);
		panel_side_bar_2.add(panel_heroes_2, "cell 0 1,grow");
		
		pointPanel_2 = new PointPanel();
		panel_side_bar_2.add(pointPanel_2, "cell 1 1,grow");
		
		orderPanel_2 = new PlayerOrderPanel();
		panel_side_bar_2.add(orderPanel_2, "cell 0 2,grow");
		
		userColorPanel_2 = new UserColorPanel();
		userColorPanel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_side_bar_2.add(userColorPanel_2, "cell 1 2,grow");
		
		userColorPanel.addPlayerColors(game.getPlayers(), game.getColorManager());
		userColorPanel_2.addPlayerColors(game.getPlayers(), game.getColorManager());
		
		JPanel panel_controlls = new JPanel();
		panel_controlls.setBackground(Color.GRAY);
		panel_side_bar_2.add(panel_controlls, "cell 0 3 2 1,grow");
		panel_controlls.setLayout(new MigLayout("", "[][grow][]", "[][]"));
		
		JLabel lblAktiverSpieler_1 = new JLabel("Aktiver Spieler:");
		lblAktiverSpieler_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_controlls.add(lblAktiverSpieler_1, "cell 0 0,alignx trailing");
		
		txtActiveplayer_2 = new JTextField();
		txtActiveplayer_2.setEditable(false);
		txtActiveplayer_2.setBackground(Color.LIGHT_GRAY);
		txtActiveplayer_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_controlls.add(txtActiveplayer_2, "cell 1 0,growx");
		txtActiveplayer_2.setColumns(10);
		
		JButton btnSpielfeldbersicht_1 = new JButton("Spielfeld Übersicht");
		btnSpielfeldbersicht_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel_2.showOtherView();
			}
		});
		btnSpielfeldbersicht_1.setBackground(Color.GRAY);
		panel_controlls.add(btnSpielfeldbersicht_1, "cell 2 0,growx");
		
		JLabel label = new JLabel("Spielphase:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_controlls.add(label, "flowx,cell 0 1,alignx trailing");
		
		txtPhase_2 = new JTextField();
		txtPhase_2.setEditable(false);
		txtPhase_2.setBackground(Color.LIGHT_GRAY);
		panel_controlls.add(txtPhase_2, "cell 1 1,growx");
		txtPhase_2.setColumns(10);
		
		JButton btnMehrInformationenEinblenden = new JButton("Mehr Einblenden");
		btnMehrInformationenEinblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showExtendedInfo();
			}
		});
		btnMehrInformationenEinblenden.setBackground(Color.GRAY);
		panel_controlls.add(btnMehrInformationenEinblenden, "cell 2 1,growx");
		
		JPanel panel_image_wrapper = new JPanel();
		panel_image_wrapper.setBackground(Color.GRAY);
		panel_side_bar_2.add(panel_image_wrapper, "cell 0 4 2 1,grow");
		panel_image_wrapper.setLayout(new MigLayout("", "[50px,grow][:200px:300px,grow][50px,grow]", "[20px,grow][:100px:300px,grow][5px,grow]"));
		
		ImagePanel panel_logo_2 = new ImagePanel(imageLoader.loadImage("game_frame/logo_1.png"));
		panel_logo_2.setImageMinimumSize(new int[] {100, 100});
		panel_logo_2.setRemoveIfToSmall(true);
		panel_logo_2.setCentered(true);
		panel_logo_2.setAdaptSizeKeepProportion(true);
		panel_logo_2.setBackground(Color.GRAY);
		panel_image_wrapper.add(panel_logo_2, "cell 1 1,grow");
		
		
		showReducedInfo();
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
		helpMenuFrame = MainMenuFrame.getHelpMenu();
		heroEffectExecutionFrame = new HeroEffectExecutionFrame(game);
		SupportRequestFrame.setLocalPlayer(game.getLocalUser());
		game.getFightManager().setFightExecutionFrame(fightExecutionFrame);
	}
	
	@Override
	public void receiveBoardMouseClick(MouseEvent event, boolean doubleClick) {
		selectCurrentField();
		if (doubleClick && selectedField != null) {
			//field double clicked -> open dialog
			if (game.getGameState() == GameState.ACT) {
				turnExecutionFrame.setSelectedField(selectedField);
				turnExecutionFrame.update();
				turnExecutionFrame.setVisible(true);
				turnExecutionFrame.requestFocus();
			}
			else if (game.getGameState() == GameState.PLAN) {
				turnPlaningFrame.setSelectedField(selectedField);
				turnPlaningFrame.update();
				turnPlaningFrame.setVisible(true);
				turnPlaningFrame.requestFocus();
			}
		}
	}
	
	@Override
	public void receiveSelectedHero(Hero hero) {
		if (hero.getExecutionType() == ExecutionType.TURN_EFFECT) {
			heroEffectExecutionFrame.startHeroEffectExecution(hero);			
		}
		else {
			new ErrorDialog("Der Effekt dieses Helden kann nicht als Zug sondern nur in einem Kampf eingesetzt werden.").setVisible(true);
		}
	}
	
	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			game.getClient().sendMessage(new UserLogoutMessage());
			System.exit(0);
		}
	}
	
	public void disposeAll() {
		fieldOverviewFrame.dispose();
		fightExecutionFrame.dispose();
		gameOverviewFrame.dispose();
		resourceInfoFrame.dispose();
		selectHeroCardFrame.dispose();
		troopInfoFrame.dispose();
		turnExecutionFrame.dispose();
		turnGoalTurnBonusDialog.dispose();
		turnPlaningFrame.dispose();
		boardOverviewFrame.dispose();
		dispose();
	}
	
	public void updateAllFrames() {
		update();
		fieldOverviewFrame.update();
		fightExecutionFrame.update();
		gameOverviewFrame.update();
		resourceInfoFrame.update();
		selectHeroCardFrame.update();
		troopInfoFrame.update();
		turnExecutionFrame.update();
		turnGoalTurnBonusDialog.update();
		turnPlaningFrame.update();
		boardOverviewFrame.update();
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
	
	private void showReducedInfo() {
		CardLayout layout = (CardLayout) panel_game_type.getLayout();
		layout.show(panel_game_type, REDUCED_INFO_VIEW);
	}
	private void showExtendedInfo() {
		CardLayout layout = (CardLayout) panel_game_type.getLayout();
		layout.show(panel_game_type, COMPLETE_INFO_VIEW);
	}
	
	public void showChatDialog() {
		chatDialog.setVisible(true);
		chatDialog.requestFocus();
	}
	
	private void selectCurrentField() {
		selectedField = game.getBoard().getFieldAtMousePosition();
		fieldPanel.updateField(selectedField);
		updateGameTurnPanel();
	}
	
	private void updateBoard() {
		BufferedImage boardImage = game.getBoard().displayBoard();
		boardPanel.updateBoardImage(boardImage);
		boardPanel_2.updateBoardImage(boardImage);
		boardOverviewFrame.updateBoardImage(boardImage);
	}
	
	private void updateGameTurnPanel() {
		txtPhase.setText(game.getGameState().getPhaseName());
		txtPhase_2.setText(game.getGameState().getPhaseName());
		if (selectedField != null) {
			txtSelectedfield.setText(selectedField.getName());
		}
		else {
			txtSelectedfield.setText("");
		}
		if (game.getGameState().equals(GameState.PLAN)) {
			txtActiveplayer.setText("Alle (Planungsphase)");
			txtActiveplayer_2.setText("Alle (Planungsphase)");
		}
		else {
			User activePlayer = game.getTurnManager().getPlayerOrder().getActivePlayer();
			if (activePlayer != null) {
				txtActiveplayer.setText(activePlayer.getUsername());
				txtActiveplayer_2.setText(activePlayer.getUsername());
			}
			else {
				txtActiveplayer.setText("");
				txtActiveplayer_2.setText("");
			}			
		}
		if (selectedField != null && selectedField.getCommand() != null) {
			txtCommand.setText(selectedField.getCommand().getName());
		}
		else {
			txtCommand.setText("");
		}
		if (game.getGameState() == GameState.ACT && selectedField != null && selectedField.getAffiliation() != null && 
				selectedField.getAffiliation().equals(game.getLocalUser())) {
			btnSpielzugAusfhren.setEnabled(true);
		}
		else {
			btnSpielzugAusfhren.setEnabled(false);
		}
		if (game.getGameState() == GameState.ACT && game.getPlayerOrder().isPlayersTurn(game.getLocalUser()) && 
				game.getFightManager().getCurrentFight() == null) {
			panel_heroes.getBtnEinsetzen().setEnabled(true);
			panel_heroes_2.getBtnEinsetzen().setEnabled(true);
		}
		else {
			panel_heroes.getBtnEinsetzen().setEnabled(false);
			panel_heroes_2.getBtnEinsetzen().setEnabled(false);
		}
	}
	
	private void updateTurnCards() {
		if (game.getGameTurnBonusManager().getBonuses() != null && game.getTurnManager().getTurn() < Game.getGameVariableStorage().getGameTurns()) {
			TurnBonus bonus = game.getGameTurnBonusManager().getUsersBonus(game.getLocalUser());
			TurnGoal goal = game.getTurnManager().getGameTurnGoalManager().getTurnGoal();
			panel_turn_bonus.setTurnBonus(bonus);
			panel_turn_goal.setTurnGoal(goal);
			panel_turn_bonus_2.setTurnBonus(bonus);
			panel_turn_goal_2.setTurnGoal(goal);
		}
	}
	
	private void updateHeroCards() {
		panel_heroes.updateHeroCards(game);
		panel_heroes_2.updateHeroCards(game);
	}
	
	private void updateTurnOrder() {
		orderPanel.updateTurnOrder(game);
		orderPanel_2.updateTurnOrder(game);
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
	}
	
	private void updatePoints() {
		pointPanel.updatePoints(game);
		pointPanel_2.updatePoints(game);
	}
	
	protected Game getGame() {
		return game;
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
	public HelpMenuFrame getHelpMenuFrame() {
		return helpMenuFrame;
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
}