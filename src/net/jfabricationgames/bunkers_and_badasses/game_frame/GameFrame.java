package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.chat.ChatClient;
import net.jfabricationgames.bunkers_and_badasses.chat.ChatPanel;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_communication.GameLogMessage;
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
	private ListModel<GameLogMessage> logListModel = new DefaultListModel<GameLogMessage>();
	
	public static void main(String[] args) {
		new GameFrame(null).setVisible(true);
	}
	
	public GameFrame(JFGClient client) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 700);
		setMinimumSize(new Dimension(1250, 700));
		
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
		mnDialog.add(mntmPlanungsDialogffnen);
		
		JMenuItem mntmSpielbersicht = new JMenuItem("Spiel\u00FCbersicht");
		mnDialog.add(mntmSpielbersicht);
		
		JMenuItem mntmGebietsbersicht = new JMenuItem("Gebiets\u00FCbersicht");
		mnDialog.add(mntmGebietsbersicht);
		
		JMenuItem mntmInfoDialogffnen = new JMenuItem("Allgemein Info");
		mnDialog.add(mntmInfoDialogffnen);
		
		JMenuItem mntmTruppenInfoDialog = new JMenuItem("Truppen Info");
		mnDialog.add(mntmTruppenInfoDialog);
		
		JMenuItem mntmResourcenInfoDialog = new JMenuItem("Resourcen Info");
		mnDialog.add(mntmResourcenInfoDialog);
		
		JMenuItem mntmHeldenInfoDialog = new JMenuItem("Helden Info");
		mnDialog.add(mntmHeldenInfoDialog);
		
		JMenuItem mntmChatDialog = new JMenuItem("Chat Dialog");
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
		
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		chatClient = new ChatClient(client);
		chatPanel = new ChatPanel(chatClient);
		chatPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		chatClient.setChatPanel(chatPanel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[900px,grow][:350px:400px,grow]", "[500px,grow][:200px:200px,grow]"));
		
		JPanel panel_board_capture = new JPanel();
		panel_board_capture.setBackground(Color.GRAY);
		panel.add(panel_board_capture, "cell 0 0,grow");
		panel_board_capture.setLayout(new MigLayout("", "[971px,grow]", "[460px,grow]"));
		
		JScrollPane scrollPane_board = new JScrollPane();
		panel_board_capture.add(scrollPane_board, "cell 0 0,grow");
		
		JPanel panel_board = new JPanel();
		panel_board.setBackground(Color.GRAY);
		scrollPane_board.setViewportView(panel_board);
		
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
		
		JList list_log = new JList();
		list_log.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_log.setBackground(Color.LIGHT_GRAY);
		scrollPane_log.setViewportView(list_log);
		
		JPanel panel_field = new JPanel();
		panel_field.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field.setBackground(Color.GRAY);
		panel_side_bar.add(panel_field, "cell 0 2,grow");
		panel_field.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][][5px][][50px,grow]"));
		
		JLabel lblFeldbersicht = new JLabel("Feld \u00DCbersicht:");
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblSpieler, "cell 0 2");
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblNormaleTruppen, "cell 2 2");
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblBefehl_1, "cell 0 3");
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblBadassTruppen, "cell 2 3");
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblGebude, "cell 0 4");
		
		JLabel lblNachbarn = new JLabel("Nachbarn:");
		lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblNachbarn, "cell 0 6 4 1,alignx center");
		
		JScrollPane scrollPane_neighbours = new JScrollPane();
		panel_field.add(scrollPane_neighbours, "cell 0 7 4 1,grow");
		
		JList list_neighbours = new JList();
		list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_neighbours.setBackground(Color.LIGHT_GRAY);
		scrollPane_neighbours.setViewportView(list_neighbours);
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel.add(panel_low_bar, "cell 0 1 2 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[300px,grow][300px,grow][:200px:250px,grow][:200px:250px,grow][:200px:200px,grow]", "[100px,grow]"));
		
		JPanel panel_turn = new JPanel();
		panel_turn.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_turn.setBackground(Color.GRAY);
		panel_low_bar.add(panel_turn, "cell 0 0,grow");
		panel_turn.setLayout(new MigLayout("", "[grow][grow]", "[][grow][][10px][][][][][grow]"));
		
		JLabel lblSpielzug = new JLabel("Spielzug:");
		lblSpielzug.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_turn.add(lblSpielzug, "cell 0 0 2 1,alignx center");
		
		JButton btnSpielfeldbersicht = new JButton("Spielfeld \u00DCbersicht");
		btnSpielfeldbersicht.setBackground(Color.GRAY);
		panel_turn.add(btnSpielfeldbersicht, "cell 0 2,alignx center");
		
		JButton btnSpielzugAusfhren = new JButton("Spielzug Ausf\u00FChren");
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
		lblbrigeResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblbrigeResourcen, "cell 1 2");
		
		JLabel lblErhaltnchsteRunde = new JLabel("Erhalt:");
		lblErhaltnchsteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblErhaltnchsteRunde, "cell 2 2");
		
		JLabel lblVerbrauchletzteRunde = new JLabel("Verbrauch:");
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
		
		JList list_heroes = new JList();
		list_heroes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_heroes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_heroes.setBackground(Color.LIGHT_GRAY);
		scrollPane_heroes.setViewportView(list_heroes);
		
		JButton btnbersicht = new JButton("\u00DCbersicht");
		btnbersicht.setBackground(Color.GRAY);
		panel_heroes.add(btnbersicht, "cell 0 3,alignx center");
		
		JButton btnEinsetzen = new JButton("Einsetzen");
		btnEinsetzen.setBackground(Color.GRAY);
		panel_heroes.add(btnEinsetzen, "cell 1 3,alignx center");
		
		JPanel panel_points = new JPanel();
		panel_points.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_points.setBackground(Color.GRAY);
		panel_low_bar.add(panel_points, "cell 3 0,grow");
		panel_points.setLayout(new MigLayout("", "[][5px][grow]", "[][5px][grow][][]"));
		
		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_points.add(lblPunkte, "cell 0 0 3 1,alignx center");
		
		JScrollPane scrollPane_points = new JScrollPane();
		panel_points.add(scrollPane_points, "cell 0 2 3 1,grow");
		
		JList list_points = new JList();
		list_points.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_points.setBackground(Color.LIGHT_GRAY);
		scrollPane_points.setViewportView(list_points);
		
		JLabel lblDeinePunkte = new JLabel("Deine Punkte:");
		lblDeinePunkte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblDeinePunkte, "cell 0 3");
		
		JLabel lblPlayerpoints = new JLabel("");
		lblPlayerpoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblPlayerpoints, "cell 2 3");
		
		JLabel lblDeinePosition = new JLabel("Deine Position:");
		lblDeinePosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblDeinePosition, "cell 0 4");
		
		JLabel lblPlayerposition = new JLabel("");
		lblPlayerposition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblPlayerposition, "cell 2 4");
		
		JPanel panel_logo_capture = new JPanel();
		panel_logo_capture.setBackground(Color.GRAY);
		panel_low_bar.add(panel_logo_capture, "cell 4 0,grow");
		panel_logo_capture.setLayout(new MigLayout("", "[grow]", "[grow][:200px:200px,grow]"));
		
		ImagePanel panel_logo = new ImagePanel(imageLoader.loadImage("others/logo_1.png"));
		panel_logo.setCentered(true);
		panel_logo.setAdaptSizeKeepProportion(true);
		panel_logo.setBackground(Color.GRAY);
		panel_logo_capture.add(panel_logo, "cell 0 1,grow");
	}
}