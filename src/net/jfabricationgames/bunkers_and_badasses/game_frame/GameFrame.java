package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatDialog;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatPanel;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager.UserPoints;
import net.jfabricationgames.bunkers_and_badasses.game.UserColor;
import net.jfabricationgames.bunkers_and_badasses.game.UserColorManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameLogMessage;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 2516173588531625786L;
	
	private JPanel contentPane;

	private ChatClient chatClient;
	private ChatPanel chatPanel;
	private JFGClient client;
	
	private JTextField txtPhase;
	private JTextField txtActiveplayer;
	private JTextField txtSelectedfield;
	private JTextField txtCommand;
	
	private static ImageLoader imageLoader;

	private ListModel<Hero> heroesListModel = new DefaultListModel<Hero>();
	private ListModel<Field> fieldListModel = new DefaultListModel<Field>();
	private ListModel<User> userListModel = new DefaultListModel<User>();
	private ListModel<GameLogMessage> logListModel = new DefaultListModel<GameLogMessage>();
	private ListModel<UserPoints> pointListModel = new DefaultListModel<UserPoints>();
	
	private boolean fieldOverview = false;
	private JPanel panel_board_capture;
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";

	private JTextField txtFeld;
	private JTextField txtRegion;
	private JTextField txtSpieler;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtTruppennormal;
	private JTextField txtTruppenbadass;
	
	private JTextField txtYourPoints;
	private JTextField txtYourPosition;
	private JPanel panel_scroll_board;
	private JPanel panel_board_overview;
	private JScrollPane scrollPane_board;
	
	public static void main(String[] args) {
		new GameFrame(null, null).setVisible(true);
	}
	
	public GameFrame(JFGClient client, Game game) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 750);
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
				//TODO delete after tests
				new TurnPlaningFrame().setVisible(true);
			}
		});
		mnDialog.add(mntmPlanungsDialogffnen);
		
		JMenuItem mntmZugAusfhren = new JMenuItem("Zug Ausf\u00FChren");
		mntmZugAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new TurnExecutionFrame().setVisible(true);
			}
		});
		mnDialog.add(mntmZugAusfhren);
		
		JMenuItem mntmSpielbersicht = new JMenuItem("Spiel\u00FCbersicht");
		mntmSpielbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new GameOverviewFrame().setVisible(true);
			}
		});
		mnDialog.add(mntmSpielbersicht);
		
		JMenuItem mntmGebietsbersicht = new JMenuItem("Gebiets\u00FCbersicht");
		mntmGebietsbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new FieldOverviewDialog().setVisible(true);
			}
		});
		mnDialog.add(mntmGebietsbersicht);
		
		JMenuItem mntmTruppenInfoDialog = new JMenuItem("Truppen Info");
		mntmTruppenInfoDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new TroopInfoDialog().setVisible(true);
			}
		});
		mnDialog.add(mntmTruppenInfoDialog);
		
		JMenuItem mntmResourcenInfoDialog = new JMenuItem("Resourcen Info");
		mntmResourcenInfoDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new ResourceInfoDialog().setVisible(true);
			}
		});
		mnDialog.add(mntmResourcenInfoDialog);
		
		JMenuItem mntmHeldenInfoDialog = new JMenuItem("Helden Info");
		mntmHeldenInfoDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new SelectHeroCardDialog(false).setVisible(true);
			}
		});
		mnDialog.add(mntmHeldenInfoDialog);
		
		JMenuItem mntmChatDialog = new JMenuItem("Chat Dialog");
		mntmChatDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new ChatDialog(chatClient, GameFrame.this).setVisible(true);
			}
		});
		mnDialog.add(mntmChatDialog);
		
		JMenuItem mntmkamfAusfhrung = new JMenuItem("(Kamf Ausf\u00FChrung)");
		mntmkamfAusfhrung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new FightExecutionFrame().setVisible(true);
			}
		});
		mnDialog.add(mntmkamfAusfhrung);
		
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
		
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		chatClient = new ChatClient(client);
		chatPanel = new ChatPanel(chatClient);
		chatPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		chatClient.addChatPanel(chatPanel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[900px,grow][:350px:400px,grow]", "[500px,grow][:200px:200px,grow]"));
		
		panel_board_capture = new JPanel();
		panel_board_capture.setBackground(Color.GRAY);
		panel.add(panel_board_capture, "cell 0 0,grow");
		panel_board_capture.setLayout(new CardLayout(0, 0));
		
		JPanel panel_scroll_board_capture = new JPanel();
		panel_scroll_board_capture.setBackground(Color.GRAY);
		panel_board_capture.add(panel_scroll_board_capture, SCROLL_BOARD);
		panel_scroll_board_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		scrollPane_board = new JScrollPane();
		panel_scroll_board_capture.add(scrollPane_board, "cell 0 0,grow");
		
		panel_scroll_board = new JPanel();
		panel_scroll_board.setBackground(Color.GRAY);
		scrollPane_board.setViewportView(panel_scroll_board);
		
		JPanel panel_board_overview_capture = new JPanel();
		panel_board_overview_capture.setBackground(Color.GRAY);
		panel_board_capture.add(panel_board_overview_capture, OVERVIEW_BOARD);
		panel_board_overview_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		panel_board_overview = new JPanel();
		panel_board_overview.setBackground(Color.GRAY);
		panel_board_overview_capture.add(panel_board_overview, "cell 0 0,grow");
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel.add(panel_side_bar, "cell 1 0,grow");
		panel_side_bar.setLayout(new MigLayout("", "[grow]", "[grow][::250px,grow][::250px,grow]"));
		
		chatPanel.setBackground(Color.GRAY);
		panel_side_bar.add(chatPanel, "cell 0 0,grow");
		
		JPanel panel_log = new JPanel();
		panel_log.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_log.setBackground(Color.GRAY);
		panel_side_bar.add(panel_log, "cell 0 1,grow");
		panel_log.setLayout(new MigLayout("", "[grow]", "[][25px,grow][50px,grow]"));
		
		JLabel lblSpielLog = new JLabel("Spiel Log:");
		lblSpielLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_log.add(lblSpielLog, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_log_detail = new JScrollPane();
		panel_log.add(scrollPane_log_detail, "cell 0 1,grow");
		
		JTextArea txtrLogdetail = new JTextArea();
		txtrLogdetail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrLogdetail.setBackground(Color.LIGHT_GRAY);
		scrollPane_log_detail.setViewportView(txtrLogdetail);
		
		JScrollPane scrollPane_log = new JScrollPane();
		panel_log.add(scrollPane_log, "cell 0 2,grow");
		
		JList<GameLogMessage> list_log = new JList<GameLogMessage>(logListModel);
		list_log.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_log.setToolTipText("<html>\r\n\u00DCbersicht \u00FCber die letzten Aktionen\r\n</html>");
		list_log.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_log.setBackground(Color.LIGHT_GRAY);
		scrollPane_log.setViewportView(list_log);
		
		JPanel panel_field = new JPanel();
		panel_field.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field.setBackground(Color.GRAY);
		panel_side_bar.add(panel_field, "cell 0 2,grow");
		panel_field.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][][][][5px][][50px,grow]"));
		
		JLabel lblFeldbersicht = new JLabel("Feld \u00DCbersicht:");
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblFeld, "cell 0 2,alignx trailing");
		
		txtFeld = new JTextField();
		txtFeld.setEditable(false);
		txtFeld.setBackground(Color.LIGHT_GRAY);
		txtFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtFeld, "cell 1 2 3 1,growx");
		txtFeld.setColumns(10);
		
		JLabel lblRegion = new JLabel("Region:");
		lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblRegion, "cell 0 3,alignx trailing");
		
		txtRegion = new JTextField();
		txtRegion.setEditable(false);
		txtRegion.setBackground(Color.LIGHT_GRAY);
		txtRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtRegion, "cell 1 3 3 1,growx");
		txtRegion.setColumns(10);
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblSpieler, "cell 0 4,alignx trailing");
		
		txtSpieler = new JTextField();
		txtSpieler.setBackground(Color.LIGHT_GRAY);
		txtSpieler.setEditable(false);
		txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtSpieler, "cell 1 4 3 1,growx");
		txtSpieler.setColumns(10);
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblBefehl_1, "cell 0 5,alignx trailing");
		
		txtBefehl = new JTextField();
		txtBefehl.setBackground(Color.LIGHT_GRAY);
		txtBefehl.setEditable(false);
		txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtBefehl, "cell 1 5,growx");
		txtBefehl.setColumns(10);
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblNormaleTruppen, "cell 2 5,alignx trailing");
		
		txtTruppennormal = new JTextField();
		txtTruppennormal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppennormal.setBackground(Color.LIGHT_GRAY);
		txtTruppennormal.setEditable(false);
		txtTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtTruppennormal, "cell 3 5,growx");
		txtTruppennormal.setColumns(10);
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblGebude, "cell 0 6,alignx trailing");
		
		txtGebude = new JTextField();
		txtGebude.setBackground(Color.LIGHT_GRAY);
		txtGebude.setEditable(false);
		txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtGebude, "cell 1 6,growx");
		txtGebude.setColumns(10);
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblBadassTruppen, "cell 2 6,alignx trailing");
		
		txtTruppenbadass = new JTextField();
		txtTruppenbadass.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenbadass.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass.setEditable(false);
		txtTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtTruppenbadass, "cell 3 6,growx");
		txtTruppenbadass.setColumns(10);
		
		JLabel lblNachbarn = new JLabel("Nachbarn:");
		lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblNachbarn, "cell 0 8 4 1,alignx center");
		
		JScrollPane scrollPane_neighbours = new JScrollPane();
		panel_field.add(scrollPane_neighbours, "cell 0 9 4 1,grow");
		
		JList<Field> list_neighbours = new JList<Field>(fieldListModel);
		list_neighbours.setToolTipText("<html>\r\nBenachbarte Felder\r\n</html>");
		list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_neighbours.setBackground(Color.LIGHT_GRAY);
		scrollPane_neighbours.setViewportView(list_neighbours);
		
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
				CardLayout layout = (CardLayout) panel_board_capture.getLayout();
				if (fieldOverview) {
					layout.show(panel_board_capture, SCROLL_BOARD);
				}
				else {
					layout.show(panel_board_capture, OVERVIEW_BOARD);
				}
				fieldOverview = !fieldOverview;
			}
		});
		btnSpielfeldbersicht.setToolTipText("<html>\r\nZwichen einer \u00DCbersicht \u00FCber das <br>\r\ngesammte Spielfeld und einer kleineren <br>\r\ndetailierteren Sicht wechseln\r\n</html>");
		btnSpielfeldbersicht.setBackground(Color.GRAY);
		panel_turn.add(btnSpielfeldbersicht, "cell 0 2,alignx center");
		
		JButton btnSpielzugAusfhren = new JButton("Spielzug Ausf\u00FChren");
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
		
		JPanel panel_resources = new JPanel();
		panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_resources.setBackground(Color.GRAY);
		panel_low_bar.add(panel_resources, "cell 1 0,grow");
		panel_resources.setLayout(new MigLayout("", "[right][grow,center][grow,center][grow,center]", "[][grow][][grow][grow][grow][grow]"));
		
		JLabel lblResourcen = new JLabel("Resourcen:");
		lblResourcen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_resources.add(lblResourcen, "cell 0 0 4 1,alignx center");
		
		JLabel lblbrigeResourcen = new JLabel("\u00DCbrig:");
		lblbrigeResourcen.setToolTipText("Im Moment vorhandene Resourcen");
		lblbrigeResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblbrigeResourcen, "cell 1 2");
		
		JLabel lblErhaltnchsteRunde = new JLabel("Erhalt:");
		lblErhaltnchsteRunde.setToolTipText("<html>\r\nMomentaner Erhalt an Resourcen zu<br>\r\nBeginn der n\u00E4chsten Runde. (Resourcen<br>\r\nGewinnungsbefehle nicht ber\u00FCcksichtigt)<br>\r\n</html>");
		lblErhaltnchsteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblErhaltnchsteRunde, "cell 2 2");
		
		JLabel lblVerbrauchletzteRunde = new JLabel("Verbrauch:");
		lblVerbrauchletzteRunde.setToolTipText("<html>\r\nVerbrauchte Resourcen (f\u00FCr <br>\r\nBefehle) in der letzten Runde\r\n</html>");
		lblVerbrauchletzteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblVerbrauchletzteRunde, "cell 3 2");
		
		JLabel labelCredits = new JLabel("Credits:");
		labelCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(labelCredits, "cell 0 3");
		
		JLabel lblLC = new JLabel("");
		panel_resources.add(lblLC, "cell 1 3");
		
		JLabel lblGC = new JLabel("");
		panel_resources.add(lblGC, "cell 2 3");
		
		JLabel lblUC = new JLabel("");
		panel_resources.add(lblUC, "cell 3 3");
		
		JLabel labelMunition = new JLabel("Munition:");
		labelMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(labelMunition, "cell 0 4");
		
		JLabel lblLA = new JLabel("");
		panel_resources.add(lblLA, "cell 1 4");
		
		JLabel lblGA = new JLabel("");
		panel_resources.add(lblGA, "cell 2 4");
		
		JLabel lblUA = new JLabel("");
		panel_resources.add(lblUA, "cell 3 4");
		
		JLabel labelEridium = new JLabel("Eridium:");
		labelEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(labelEridium, "cell 0 5");
		
		JLabel lblLE = new JLabel("");
		panel_resources.add(lblLE, "cell 1 5");
		
		JLabel lblGE = new JLabel("");
		panel_resources.add(lblGE, "cell 2 5");
		
		JLabel lblUE = new JLabel("");
		panel_resources.add(lblUE, "cell 3 5");
		
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
		btnbersicht.setToolTipText("<html>\r\n\u00DCbersicht \u00FCber die vorhandenen <br>\r\nHelden Karten\r\n</html>");
		btnbersicht.setBackground(Color.GRAY);
		panel_heroes.add(btnbersicht, "cell 0 3,alignx center");
		
		JButton btnEinsetzen = new JButton("Einsetzen");
		btnEinsetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO delete after tests
				new SelectHeroCardDialog(true).setVisible(true);
			}
		});
		btnEinsetzen.setToolTipText("<html>\r\nEine der vorhandenen Helden Karten<br>\r\n(deren Spezialfunktion) einsetzen\r\n</html>");
		btnEinsetzen.setBackground(Color.GRAY);
		panel_heroes.add(btnEinsetzen, "cell 1 3,alignx center");
		
		JPanel panel_order = new JPanel();
		panel_order.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order.setBackground(Color.GRAY);
		panel_low_bar.add(panel_order, "cell 3 0,grow");
		panel_order.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblReihenfolge = new JLabel("Reihenfolge:");
		lblReihenfolge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_order.add(lblReihenfolge, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_order = new JScrollPane();
		panel_order.add(scrollPane_order, "cell 0 2,grow");
		
		JList<User> list_order = new JList<User>(userListModel);
		list_order.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_order.setToolTipText("<html>\r\nDie Reihenfolge der Spieler <br>\r\n(F\u00FCr diese Runde)\r\n</html>");
		scrollPane_order.setViewportView(list_order);
		list_order.setBackground(Color.LIGHT_GRAY);
		list_order.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JPanel panel_points = new JPanel();
		panel_points.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_points.setBackground(Color.GRAY);
		panel_low_bar.add(panel_points, "cell 4 0,grow");
		panel_points.setLayout(new MigLayout("", "[][5px][grow]", "[][5px][grow][][]"));
		
		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_points.add(lblPunkte, "cell 0 0 3 1,alignx center");
		
		JScrollPane scrollPane_points = new JScrollPane();
		panel_points.add(scrollPane_points, "cell 0 2 3 1,grow");
		
		JList<UserPoints> list_points = new JList<UserPoints>(pointListModel);
		list_points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_points.setToolTipText("<html>\r\nDie Siegpunkte aller Spieler\r\n</html>");
		list_points.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_points.setBackground(Color.LIGHT_GRAY);
		scrollPane_points.setViewportView(list_points);
		
		JLabel lblDeinePunkte = new JLabel("Deine Punkte:");
		lblDeinePunkte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblDeinePunkte, "cell 0 3");
		
		txtYourPoints = new JTextField();
		txtYourPoints.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourPoints.setBackground(Color.LIGHT_GRAY);
		txtYourPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYourPoints.setEditable(false);
		panel_points.add(txtYourPoints, "cell 2 3,growx");
		txtYourPoints.setColumns(10);
		
		JLabel lblDeinePosition = new JLabel("Deine Position:");
		lblDeinePosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblDeinePosition, "cell 0 4");
		
		txtYourPosition = new JTextField();
		txtYourPosition.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourPosition.setBackground(Color.LIGHT_GRAY);
		txtYourPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYourPosition.setEditable(false);
		panel_points.add(txtYourPosition, "cell 2 4,growx");
		txtYourPosition.setColumns(10);
		
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
		
		JPanel panel_colors = new JPanel();
		panel_colors.setBackground(Color.GRAY);
		scrollPane_colors.setViewportView(panel_colors);
		
		//TODO delete comment after tests; while testing game will be null
		//addPlayerColors(panel_colors, game.getPlayers(), game.getColorManager());
		
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
			g.drawOval(0, 0, getWidth(), getHeight());//draw an oval of the users color on the panel
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
		
		for (int i = 0; i < players.size(); i++) {
			//create a label for the players name and a panel for his color
			JLabel lblName = new JLabel(players.get(i).getUsername());
			UserColorPanel colorPanel = new UserColorPanel(colorManager.getUserColors().get(players.get(i)));
			colorPanel.setBackground(Color.GRAY);
			
			//add the components to the panel
			panel_colors.add(lblName, "cell 0 " + i);
			panel_colors.add(colorPanel, "cell 1 " + i + ",grow");
		}
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
}