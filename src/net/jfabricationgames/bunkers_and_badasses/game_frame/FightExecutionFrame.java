package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;
import javax.swing.ListSelectionModel;

public class FightExecutionFrame extends JFrame {
	
	private static final long serialVersionUID = -8826989423127239957L;
	
	private JPanel contentPane;
	private JTextField txtAngreifer;
	private JTextField txtVerteidiger;
	private JTextField txtTruppenangreifer;
	private JTextField txtTruppenverteidiger;
	private JTextField txtUntersttzerangreifer;
	private JTextField txtUntersttzerverteidiger;
	private JTextField txtAktiverspieler;
	private JTextField txtAngreifendesfeld;
	private JTextField txtVerteidigendesFeld;
	
	private JTextField txtSpieler_angreifer;
	private JTextField txtBefehl_angreifer;
	private JTextField txtGebude_angreifer;
	private JTextField txtTruppennormal_angreifer;
	private JTextField txtTruppenbadass_angreifer;
	
	private JTextField txtSpieler_defender;
	private JTextField txtBefehl_defender;
	private JTextField txtGebude_defender;
	private JTextField txtTruppennormal_defender;
	private JTextField txtTruppenbadass_defender;
	
	private ListModel<Field> fieldNeighboursSupportModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldDeniedSupportModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldAttackerSupportModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldDefenderSupportModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldRetreadModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldFallingSupportModel = new DefaultListModel<Field>();
	
	private ComboBoxModel<Field> fieldSelectionModel = new DefaultComboBoxModel<Field>();
	
	private JTextField txtHeroAttacker;
	private JTextField txtHerodefender;
	private JTextField txtHeroAttackerAtk;
	private JTextField txtHeroAttackerDef;
	private JTextField txtDieAngriffspunkteDes;
	private JTextField textField_1;
	private JTextField txtSieger;
	private JTextField txtVerlierer;
	private JTextField txtOverhead;
	private JTextField txtFallendetruppen;
	private JTextField txtFallendetruppenbis;
	private JTextField txtRckzugnach;
	private JTextField txtFallendetruppengesammt;
	private JTextField textField_2;
	private JTextField txtbrig;
	private JTextField txtFallendeTruppen;
	
	public FightExecutionFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FightExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Kampf Ausf\u00FChrung");
		setBounds(100, 100, 1200, 740);
		setMinimumSize(new Dimension(1200, 740));
		
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
		
		JMenuItem mntmZugAusfhren = new JMenuItem("Zug Ausf\u00FChren");
		mnDialog.add(mntmZugAusfhren);
		
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
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[400px,grow]", "[300px,grow][400px,grow]"));
		
		JPanel panel_top_bar = new JPanel();
		panel_top_bar.setBackground(Color.GRAY);
		panel.add(panel_top_bar, "cell 0 0,grow");
		panel_top_bar.setLayout(new MigLayout("", "[400px,grow][400px,grow][200px,grow][200px,grow]", "[grow][grow]"));
		
		JPanel panel_info = new JPanel();
		panel_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_info.setBackground(Color.GRAY);
		panel_top_bar.add(panel_info, "cell 0 0 1 2,grow");
		panel_info.setLayout(new MigLayout("", "[][grow][50px,grow][5px][grow][50px,grow]", "[][5px,grow][][][5px][][][5px][][][10px][][][grow]"));
		
		JLabel lblKampfInfo = new JLabel("Kampf Info:");
		lblKampfInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_info.add(lblKampfInfo, "cell 0 0 6 1,alignx center");
		
		JLabel lblAngreifer = new JLabel("Angreifer:");
		lblAngreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAngreifer, "cell 0 2,alignx trailing");
		
		txtAngreifer = new JTextField();
		txtAngreifer.setEditable(false);
		txtAngreifer.setBackground(Color.LIGHT_GRAY);
		txtAngreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtAngreifer, "cell 1 2 5 1,growx");
		txtAngreifer.setColumns(10);
		
		JLabel lblVerteidiger = new JLabel("Verteidiger:");
		lblVerteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblVerteidiger, "cell 0 3,alignx trailing");
		
		txtVerteidiger = new JTextField();
		txtVerteidiger.setEditable(false);
		txtVerteidiger.setBackground(Color.LIGHT_GRAY);
		txtVerteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtVerteidiger, "cell 1 3 5 1,growx");
		txtVerteidiger.setColumns(10);
		
		JLabel lblAngreifendesFeld = new JLabel("Angreifendes Feld:");
		lblAngreifendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAngreifendesFeld, "cell 0 5 2 1");
		
		txtAngreifendesfeld = new JTextField();
		txtAngreifendesfeld.setBackground(Color.LIGHT_GRAY);
		txtAngreifendesfeld.setEditable(false);
		txtAngreifendesfeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtAngreifendesfeld, "cell 2 5 4 1,growx");
		txtAngreifendesfeld.setColumns(10);
		
		JLabel lblVerteidigendesFeld = new JLabel("Verteidigendes Feld:");
		lblVerteidigendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblVerteidigendesFeld, "cell 0 6 2 1");
		
		txtVerteidigendesFeld = new JTextField();
		txtVerteidigendesFeld.setBackground(Color.LIGHT_GRAY);
		txtVerteidigendesFeld.setEditable(false);
		txtVerteidigendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtVerteidigendesFeld, "cell 2 6 4 1,growx");
		txtVerteidigendesFeld.setColumns(10);
		
		JLabel lblTruppen = new JLabel("Angreifer Kampfst\u00E4rke:");
		lblTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblTruppen, "cell 0 8 2 1,alignx trailing");
		
		txtTruppenangreifer = new JTextField();
		txtTruppenangreifer.setToolTipText("<html>\r\nDie momentane gesammte Kampfst\u00E4rke<br>\r\ndes angreifenden Spielers\r\n</html>");
		txtTruppenangreifer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenangreifer.setEditable(false);
		txtTruppenangreifer.setBackground(Color.LIGHT_GRAY);
		txtTruppenangreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtTruppenangreifer, "cell 2 8,growx");
		txtTruppenangreifer.setColumns(10);
		
		JLabel lblUntersttzung = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblUntersttzung, "cell 4 8,alignx trailing");
		
		txtUntersttzerangreifer = new JTextField();
		txtUntersttzerangreifer.setToolTipText("<html>\r\nDie Anzahl der unterst\u00FCtzenden Felder (f\u00FCr den Angreifer)\r\n</html>");
		txtUntersttzerangreifer.setHorizontalAlignment(SwingConstants.CENTER);
		txtUntersttzerangreifer.setEditable(false);
		txtUntersttzerangreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUntersttzerangreifer.setBackground(Color.LIGHT_GRAY);
		panel_info.add(txtUntersttzerangreifer, "cell 5 8,growx");
		txtUntersttzerangreifer.setColumns(10);
		
		JLabel lblTruppen_1 = new JLabel("Verteidiger Kampfst\u00E4rke:");
		lblTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblTruppen_1, "cell 0 9 2 1,alignx trailing");
		
		txtTruppenverteidiger = new JTextField();
		txtTruppenverteidiger.setToolTipText("<html>\r\nDie momentane gesammte Kampfst\u00E4rke<br>\r\ndes verteidigenden Spielers\r\n</html>");
		txtTruppenverteidiger.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenverteidiger.setEditable(false);
		txtTruppenverteidiger.setBackground(Color.LIGHT_GRAY);
		txtTruppenverteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtTruppenverteidiger, "cell 2 9,growx");
		txtTruppenverteidiger.setColumns(10);
		
		JLabel lblUntersttzer = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblUntersttzer, "cell 4 9,alignx trailing");
		
		txtUntersttzerverteidiger = new JTextField();
		txtUntersttzerverteidiger.setToolTipText("<html>\r\nDie Anzahl der unterst\u00FCtzenden Felder (f\u00FCr den Verteidiger)\r\n</html>");
		txtUntersttzerverteidiger.setHorizontalAlignment(SwingConstants.CENTER);
		txtUntersttzerverteidiger.setEditable(false);
		txtUntersttzerverteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUntersttzerverteidiger.setBackground(Color.LIGHT_GRAY);
		panel_info.add(txtUntersttzerverteidiger, "cell 5 9,growx");
		txtUntersttzerverteidiger.setColumns(10);
		
		JLabel lblAktiverSpieler = new JLabel("Aktiver Spieler:");
		lblAktiverSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAktiverSpieler, "cell 0 11,alignx trailing");
		
		txtAktiverspieler = new JTextField();
		txtAktiverspieler.setToolTipText("<html>\r\nDer Spieler der grade am zug ist <br>\r\nbzw. eine Aktion ausf\u00FChren muss\r\n</html>");
		txtAktiverspieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAktiverspieler.setBackground(Color.LIGHT_GRAY);
		txtAktiverspieler.setEditable(false);
		panel_info.add(txtAktiverspieler, "cell 1 11 5 1,growx");
		txtAktiverspieler.setColumns(10);
		
		JLabel lblAktion = new JLabel("Aktion:");
		lblAktion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAktion, "cell 0 12,alignx trailing");
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("<html>\r\nDie aktuell auszuf\u00FChrende Aktion\r\n</html>");
		textField_2.setEditable(false);
		textField_2.setBackground(Color.LIGHT_GRAY);
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(textField_2, "cell 1 12 5 1,growx");
		textField_2.setColumns(10);
		
		JPanel panel_field_attacker = new JPanel();
		panel_field_attacker.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field_attacker.setBackground(Color.GRAY);
		panel_top_bar.add(panel_field_attacker, "cell 1 0,grow");
		panel_field_attacker.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px,grow][][][][grow]"));
		
		JLabel lblFeldbersicht = new JLabel("Angreifer Feld \u00DCbersicht:");
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field_attacker.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(lblSpieler, "cell 0 2,alignx trailing");
		
		txtSpieler_angreifer = new JTextField();
		txtSpieler_angreifer.setBackground(Color.LIGHT_GRAY);
		txtSpieler_angreifer.setEditable(false);
		txtSpieler_angreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(txtSpieler_angreifer, "cell 1 2 3 1,growx");
		txtSpieler_angreifer.setColumns(10);
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(lblBefehl_1, "cell 0 3,alignx trailing");
		
		txtBefehl_angreifer = new JTextField();
		txtBefehl_angreifer.setBackground(Color.LIGHT_GRAY);
		txtBefehl_angreifer.setEditable(false);
		txtBefehl_angreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(txtBefehl_angreifer, "cell 1 3,growx");
		txtBefehl_angreifer.setColumns(10);
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(lblNormaleTruppen, "cell 2 3,alignx trailing");
		
		txtTruppennormal_angreifer = new JTextField();
		txtTruppennormal_angreifer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppennormal_angreifer.setBackground(Color.LIGHT_GRAY);
		txtTruppennormal_angreifer.setEditable(false);
		txtTruppennormal_angreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(txtTruppennormal_angreifer, "cell 3 3,growx");
		txtTruppennormal_angreifer.setColumns(10);
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(lblGebude, "cell 0 4,alignx trailing");
		
		txtGebude_angreifer = new JTextField();
		txtGebude_angreifer.setBackground(Color.LIGHT_GRAY);
		txtGebude_angreifer.setEditable(false);
		txtGebude_angreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(txtGebude_angreifer, "cell 1 4,growx");
		txtGebude_angreifer.setColumns(10);
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(lblBadassTruppen, "cell 2 4,alignx trailing");
		
		txtTruppenbadass_angreifer = new JTextField();
		txtTruppenbadass_angreifer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenbadass_angreifer.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass_angreifer.setEditable(false);
		txtTruppenbadass_angreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(txtTruppenbadass_angreifer, "cell 3 4,growx");
		txtTruppenbadass_angreifer.setColumns(10);
		
		JPanel panel_neighbours_support = new JPanel();
		panel_top_bar.add(panel_neighbours_support, "cell 2 0,grow");
		panel_neighbours_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_neighbours_support.setBackground(Color.GRAY);
		panel_neighbours_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblNachbarnMitUntersttzung = new JLabel("Nachbarn mit Unterst\u00FCtzung:");
		lblNachbarnMitUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_neighbours_support.add(lblNachbarnMitUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_neighbour_support = new JScrollPane();
		panel_neighbours_support.add(scrollPane_neighbour_support, "cell 0 2,grow");
		
		JList<Field> list_neighbour_support = new JList<Field>(fieldNeighboursSupportModel);
		list_neighbour_support.setToolTipText("<html>\r\nAlle Nachbarfelder die einen<br>\r\nUnterst\u00FCtzungsbefehl haben\r\n</html>");
		list_neighbour_support.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_neighbour_support.setEnabled(false);
		list_neighbour_support.setBackground(Color.LIGHT_GRAY);
		list_neighbour_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_neighbour_support.setViewportView(list_neighbour_support);
		
		JPanel panel_denied_support = new JPanel();
		panel_top_bar.add(panel_denied_support, "cell 3 0,grow");
		panel_denied_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_denied_support.setBackground(Color.GRAY);
		panel_denied_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblUntersttzungsAbsagen = new JLabel("Unterst\u00FCtzungs Absagen:");
		lblUntersttzungsAbsagen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_denied_support.add(lblUntersttzungsAbsagen, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_denied_support = new JScrollPane();
		panel_denied_support.add(scrollPane_denied_support, "cell 0 2,grow");
		
		JList<Field> list_denied_support = new JList<Field>(fieldDeniedSupportModel);
		list_denied_support.setToolTipText("<html>\r\nAlle Nachbarfelder die ihre <br>\r\nUnterst\u00FCtzung abgesagt haben\r\n</html>");
		list_denied_support.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_denied_support.setEnabled(false);
		list_denied_support.setBackground(Color.LIGHT_GRAY);
		list_denied_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_denied_support.setViewportView(list_denied_support);
		
		JPanel panel_field_defender = new JPanel();
		panel_field_defender.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field_defender.setBackground(Color.GRAY);
		panel_top_bar.add(panel_field_defender, "cell 1 1,grow");
		panel_field_defender.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px,grow][][][][grow]"));
		
		JLabel lblFelddefender = new JLabel("Verteidiger Feld \u00DCbersicht:");
		lblFelddefender.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field_defender.add(lblFelddefender, "cell 0 0 4 1,alignx center");
		
		JLabel lblSpieler2 = new JLabel("Spieler:");
		lblSpieler2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(lblSpieler2, "cell 0 2,alignx trailing");
		
		txtSpieler_defender = new JTextField();
		txtSpieler_defender.setBackground(Color.LIGHT_GRAY);
		txtSpieler_defender.setEditable(false);
		txtSpieler_defender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(txtSpieler_defender, "cell 1 2 3 1,growx");
		txtSpieler_defender.setColumns(10);
		
		JLabel lblBefehl = new JLabel("Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(lblBefehl, "cell 0 3,alignx trailing");
		
		txtBefehl_defender = new JTextField();
		txtBefehl_defender.setBackground(Color.LIGHT_GRAY);
		txtBefehl_defender.setEditable(false);
		txtBefehl_defender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(txtBefehl_defender, "cell 1 3,growx");
		txtBefehl_defender.setColumns(10);
		
		JLabel lblNormaleTruppen2 = new JLabel("Normale Truppen:");
		lblNormaleTruppen2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(lblNormaleTruppen2, "cell 2 3,alignx trailing");
		
		txtTruppennormal_defender = new JTextField();
		txtTruppennormal_defender.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppennormal_defender.setBackground(Color.LIGHT_GRAY);
		txtTruppennormal_defender.setEditable(false);
		txtTruppennormal_defender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(txtTruppennormal_defender, "cell 3 3,growx");
		txtTruppennormal_defender.setColumns(10);
		
		JLabel lblGebude2 = new JLabel("Geb\u00E4ude:");
		lblGebude2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(lblGebude2, "cell 0 4,alignx trailing");
		
		txtGebude_defender = new JTextField();
		txtGebude_defender.setBackground(Color.LIGHT_GRAY);
		txtGebude_defender.setEditable(false);
		txtGebude_defender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(txtGebude_defender, "cell 1 4,growx");
		txtGebude_defender.setColumns(10);
		
		JLabel lblBadassTruppen2 = new JLabel("Badass Truppen:");
		lblBadassTruppen2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(lblBadassTruppen2, "cell 2 4,alignx trailing");
		
		txtTruppenbadass_defender = new JTextField();
		txtTruppenbadass_defender.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenbadass_defender.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass_defender.setEditable(false);
		txtTruppenbadass_defender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(txtTruppenbadass_defender, "cell 3 4,growx");
		txtTruppenbadass_defender.setColumns(10);
		
		JPanel panel_attacker_support = new JPanel();
		panel_top_bar.add(panel_attacker_support, "cell 2 1,grow");
		panel_attacker_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_attacker_support.setBackground(Color.GRAY);
		panel_attacker_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAngreiferUntersttzung = new JLabel("Angreifer Unterst\u00FCtzung:");
		lblAngreiferUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_attacker_support.add(lblAngreiferUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_attacker_support = new JScrollPane();
		panel_attacker_support.add(scrollPane_attacker_support, "cell 0 2,grow");
		
		JList<Field> list_attacker_support = new JList<Field>(fieldAttackerSupportModel);
		list_attacker_support.setToolTipText("<html>\r\nAlle Nachbarfelder die den <br>\r\nAngreifer unterst\u00FCtzen\r\n</html>");
		list_attacker_support.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_attacker_support.setEnabled(false);
		list_attacker_support.setBackground(Color.LIGHT_GRAY);
		list_attacker_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_attacker_support.setViewportView(list_attacker_support);
		
		JPanel panel_defender_support = new JPanel();
		panel_top_bar.add(panel_defender_support, "cell 3 1,grow");
		panel_defender_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_defender_support.setBackground(Color.GRAY);
		panel_defender_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblVerteidigerUntersttzung = new JLabel("Verteidiger Unterst\u00FCtzung:");
		lblVerteidigerUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_defender_support.add(lblVerteidigerUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_defender_support = new JScrollPane();
		panel_defender_support.add(scrollPane_defender_support, "cell 0 2,grow");
		
		JList<Field> list_defender_support = new JList<Field>(fieldDefenderSupportModel);
		list_defender_support.setToolTipText("<html>\r\nAlle Nachbarfelder die den<br>\r\nVerteidiger unterst\u00FCtzen\r\n</html>");
		list_defender_support.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_defender_support.setEnabled(false);
		list_defender_support.setBackground(Color.LIGHT_GRAY);
		list_defender_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_defender_support.setViewportView(list_defender_support);
		
		JPanel panel_low_right_bar = new JPanel();
		panel_low_right_bar.setBackground(Color.GRAY);
		panel.add(panel_low_right_bar, "cell 0 1,grow");
		panel_low_right_bar.setLayout(new MigLayout("", "[:400px:500px,grow][200px,grow][200px,grow]", "[grow][grow]"));
		
		JPanel panel_heroes = new JPanel();
		panel_heroes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_heroes.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_heroes, "cell 0 0 1 2,grow");
		panel_heroes.setLayout(new MigLayout("", "[grow][grow]", "[][5px][][5px][grow]"));
		
		JLabel lblHelden = new JLabel("Helden:");
		lblHelden.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_heroes.add(lblHelden, "cell 0 0 2 1,alignx center");
		
		JLabel lblAngreifer_1 = new JLabel("Angreifer:");
		lblAngreifer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_heroes.add(lblAngreifer_1, "cell 0 2,alignx center");
		
		JLabel lblVerteidiger_1 = new JLabel("Verteidiger:");
		lblVerteidiger_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_heroes.add(lblVerteidiger_1, "cell 1 2,alignx center");
		
		JPanel panel_hero_attacker = new JPanel();
		panel_hero_attacker.setBackground(Color.GRAY);
		panel_heroes.add(panel_hero_attacker, "cell 0 4,grow");
		panel_hero_attacker.setLayout(new MigLayout("", "[][25px,grow][][25px,grow]", "[][5px][][5px][][5px][grow][]"));
		
		JLabel lblHeld = new JLabel("Held:");
		lblHeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(lblHeld, "cell 0 0,alignx trailing");
		
		txtHeroAttacker = new JTextField();
		txtHeroAttacker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeroAttacker.setBackground(Color.LIGHT_GRAY);
		txtHeroAttacker.setEditable(false);
		panel_hero_attacker.add(txtHeroAttacker, "cell 1 0 3 1,growx");
		txtHeroAttacker.setColumns(10);
		
		JLabel lblAtk = new JLabel("Atk.:");
		lblAtk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(lblAtk, "cell 0 2,alignx trailing");
		
		txtHeroAttackerAtk = new JTextField();
		txtHeroAttackerAtk.setToolTipText("<html>\r\nDie Angriffspunkte des Helden\r\n</html>");
		txtHeroAttackerAtk.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeroAttackerAtk.setBackground(Color.LIGHT_GRAY);
		txtHeroAttackerAtk.setEditable(false);
		txtHeroAttackerAtk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(txtHeroAttackerAtk, "cell 1 2,growx");
		txtHeroAttackerAtk.setColumns(10);
		
		JLabel lblVert = new JLabel("Vert.:");
		lblVert.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(lblVert, "cell 2 2,alignx trailing");
		
		txtHeroAttackerDef = new JTextField();
		txtHeroAttackerDef.setToolTipText("<html>\r\nDie Verteidugungspunkte des Helden\r\n</html>");
		txtHeroAttackerDef.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeroAttackerDef.setBackground(Color.LIGHT_GRAY);
		txtHeroAttackerDef.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeroAttackerDef.setEditable(false);
		panel_hero_attacker.add(txtHeroAttackerDef, "cell 3 2,growx");
		txtHeroAttackerDef.setColumns(10);
		
		JRadioButton rdbtnEffekt = new JRadioButton("Effekt");
		rdbtnEffekt.setForeground(Color.BLACK);
		rdbtnEffekt.setBackground(Color.GRAY);
		rdbtnEffekt.setEnabled(false);
		panel_hero_attacker.add(rdbtnEffekt, "cell 0 4 2 1,alignx center");
		
		JRadioButton rdbtnStrke = new JRadioButton("St\u00E4rke");
		rdbtnStrke.setForeground(Color.BLACK);
		rdbtnStrke.setBackground(Color.GRAY);
		rdbtnStrke.setEnabled(false);
		panel_hero_attacker.add(rdbtnStrke, "cell 2 4 2 1,alignx center");
		
		JScrollPane scrollPane_hero_effect_atk = new JScrollPane();
		panel_hero_attacker.add(scrollPane_hero_effect_atk, "cell 0 6 4 1,grow");
		
		JTextArea txtrHeroEffectAttacker = new JTextArea();
		txtrHeroEffectAttacker.setToolTipText("<html>\r\nDer Spezialeffekt des Helden\r\n</html>");
		txtrHeroEffectAttacker.setWrapStyleWord(true);
		txtrHeroEffectAttacker.setLineWrap(true);
		txtrHeroEffectAttacker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrHeroEffectAttacker.setBackground(Color.LIGHT_GRAY);
		scrollPane_hero_effect_atk.setViewportView(txtrHeroEffectAttacker);
		
		JButton btnHeldAuswhlen = new JButton("Held Ausw\u00E4hlen");
		btnHeldAuswhlen.setBackground(Color.GRAY);
		panel_hero_attacker.add(btnHeldAuswhlen, "cell 0 7 4 1,alignx center");
		
		JPanel panel_hero_defender = new JPanel();
		panel_hero_defender.setBackground(Color.GRAY);
		panel_heroes.add(panel_hero_defender, "cell 1 4,grow");
		panel_hero_defender.setLayout(new MigLayout("", "[][25px,grow][][25px,grow]", "[][5px][][5px][][5px][grow][]"));
		
		JLabel lblHeld_1 = new JLabel("Held:");
		lblHeld_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_defender.add(lblHeld_1, "cell 0 0,alignx trailing");
		
		txtHerodefender = new JTextField();
		txtHerodefender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHerodefender.setBackground(Color.LIGHT_GRAY);
		txtHerodefender.setEditable(false);
		panel_hero_defender.add(txtHerodefender, "cell 1 0 3 1,growx");
		txtHerodefender.setColumns(10);
		
		JLabel label = new JLabel("Atk.:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_defender.add(label, "cell 0 2,alignx trailing");
		
		txtDieAngriffspunkteDes = new JTextField();
		txtDieAngriffspunkteDes.setToolTipText("<html>\r\nDie Angriffspunkte des Helden\r\n</html>");
		txtDieAngriffspunkteDes.setHorizontalAlignment(SwingConstants.CENTER);
		txtDieAngriffspunkteDes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDieAngriffspunkteDes.setEditable(false);
		txtDieAngriffspunkteDes.setColumns(10);
		txtDieAngriffspunkteDes.setBackground(Color.LIGHT_GRAY);
		panel_hero_defender.add(txtDieAngriffspunkteDes, "cell 1 2,growx");
		
		JLabel label_1 = new JLabel("Vert.:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_defender.add(label_1, "cell 2 2,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("<html>\r\nDie Verteidugungspunkte des Helden\r\n</html>");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBackground(Color.LIGHT_GRAY);
		panel_hero_defender.add(textField_1, "cell 3 2,growx");
		
		JRadioButton radioButton = new JRadioButton("Effekt");
		radioButton.setForeground(Color.BLACK);
		radioButton.setEnabled(false);
		radioButton.setBackground(Color.GRAY);
		panel_hero_defender.add(radioButton, "cell 0 4 2 1,alignx center");
		
		JRadioButton radioButton_1 = new JRadioButton("St\u00E4rke");
		radioButton_1.setForeground(Color.BLACK);
		radioButton_1.setEnabled(false);
		radioButton_1.setBackground(Color.GRAY);
		panel_hero_defender.add(radioButton_1, "cell 2 4 2 1,alignx center");
		
		JScrollPane scrollPane_hero_effect_def = new JScrollPane();
		panel_hero_defender.add(scrollPane_hero_effect_def, "cell 0 6 4 1,grow");
		
		JTextArea textArea = new JTextArea();
		textArea.setToolTipText("<html>\r\nDer Spezialeffekt des Helden\r\n</html>");
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textArea.setBackground(Color.LIGHT_GRAY);
		scrollPane_hero_effect_def.setViewportView(textArea);
		
		JButton button = new JButton("Held Ausw\u00E4hlen");
		button.setBackground(Color.GRAY);
		panel_hero_defender.add(button, "cell 0 7 4 1,alignx center");
		
		JPanel panel_end = new JPanel();
		panel_end.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_end.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_end, "cell 1 0,grow");
		panel_end.setLayout(new MigLayout("", "[grow][][25px][25px:n:25px][][25px:n:25px][50px][grow]", "[][5px,grow][][][5px][][5px][][grow]"));
		
		JLabel lblKampfAusgang = new JLabel("Kampf Ausgang:");
		lblKampfAusgang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_end.add(lblKampfAusgang, "cell 1 0 6 1,alignx center");
		
		JLabel lblSieger = new JLabel("Sieger:");
		lblSieger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblSieger, "cell 1 2");
		
		txtSieger = new JTextField();
		txtSieger.setEditable(false);
		txtSieger.setBackground(Color.LIGHT_GRAY);
		txtSieger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtSieger, "cell 2 2 5 1,growx");
		txtSieger.setColumns(10);
		
		JLabel lblVerlierer = new JLabel("Verlierer:");
		lblVerlierer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblVerlierer, "cell 1 3");
		
		txtVerlierer = new JTextField();
		txtVerlierer.setEditable(false);
		txtVerlierer.setBackground(Color.LIGHT_GRAY);
		txtVerlierer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtVerlierer, "cell 2 3 5 1,growx");
		txtVerlierer.setColumns(10);
		
		JLabel lblOverhead = new JLabel("Overhead:");
		lblOverhead.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblOverhead, "cell 1 5");
		
		txtOverhead = new JTextField();
		txtOverhead.setHorizontalAlignment(SwingConstants.CENTER);
		txtOverhead.setEditable(false);
		txtOverhead.setBackground(Color.LIGHT_GRAY);
		txtOverhead.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtOverhead, "cell 2 5,growx");
		txtOverhead.setColumns(10);
		
		JLabel lblFallendeTruppen = new JLabel("Fallende Truppen:");
		lblFallendeTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblFallendeTruppen, "cell 1 7 2 1");
		
		txtFallendetruppen = new JTextField();
		txtFallendetruppen.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendetruppen.setEditable(false);
		txtFallendetruppen.setBackground(Color.LIGHT_GRAY);
		txtFallendetruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtFallendetruppen, "cell 3 7,growx");
		txtFallendetruppen.setColumns(10);
		
		JLabel lblBis = new JLabel("bis");
		lblBis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblBis, "cell 4 7,alignx center");
		
		txtFallendetruppenbis = new JTextField();
		txtFallendetruppenbis.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendetruppenbis.setEditable(false);
		txtFallendetruppenbis.setBackground(Color.LIGHT_GRAY);
		txtFallendetruppenbis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtFallendetruppenbis, "cell 5 7,growx");
		txtFallendetruppenbis.setColumns(10);
		
		JPanel panel_fallen_troups_looser = new JPanel();
		panel_fallen_troups_looser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fallen_troups_looser.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_fallen_troups_looser, "cell 2 0,grow");
		panel_fallen_troups_looser.setLayout(new MigLayout("", "[grow][][][50px][grow]", "[][5px,grow][][][][][][grow]"));
		
		JLabel lblFallendeTruppenAuswhlen = new JLabel("Fallende Truppen Ausw\u00E4hlen:");
		lblFallendeTruppenAuswhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fallen_troups_looser.add(lblFallendeTruppenAuswhlen, "cell 0 0 5 1,alignx center");
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblFeld, "cell 1 2,alignx trailing");
		
		JComboBox<Field> comboBox = new JComboBox<Field>(fieldSelectionModel);
		comboBox.setToolTipText("<html>\r\nFalls auf mehreren deiner Felder Truppen <br>\r\nfallen kannst du hier das Feld ausw\u00E4hlen\r\n</html>");
		comboBox.setBackground(Color.GRAY);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(comboBox, "cell 2 2 2 1,growx");
		
		JLabel lblFallendeTruppen_2 = new JLabel("Fallende Truppen:");
		lblFallendeTruppen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblFallendeTruppen_2, "cell 1 3 2 1");
		
		txtFallendeTruppen = new JTextField();
		txtFallendeTruppen.setToolTipText("<html>\r\nDie Anzahl an Truppen die auf diesem Feld fallen\r\n</html>");
		txtFallendeTruppen.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendeTruppen.setBackground(Color.LIGHT_GRAY);
		txtFallendeTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(txtFallendeTruppen, "cell 3 3,growx");
		txtFallendeTruppen.setColumns(10);
		
		JLabel lblNormaleTruppen_1 = new JLabel("Normale Truppen:");
		lblNormaleTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblNormaleTruppen_1, "cell 1 4 2 1");
		
		JSpinner spinner_normal_troups = new JSpinner();
		panel_fallen_troups_looser.add(spinner_normal_troups, "cell 3 4,growx");
		
		JLabel lblBadassTruppen_1 = new JLabel("Badass Truppen:");
		lblBadassTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblBadassTruppen_1, "cell 1 5 2 1");
		
		JSpinner spinner_badass_troups = new JSpinner();
		panel_fallen_troups_looser.add(spinner_badass_troups, "cell 3 5,growx");
		
		JPanel panel_fallen_troups_looser_1 = new JPanel();
		panel_fallen_troups_looser_1.setBackground(Color.GRAY);
		panel_fallen_troups_looser.add(panel_fallen_troups_looser_1, "cell 0 6 5 1,alignx center,growy");
		panel_fallen_troups_looser_1.setLayout(new MigLayout("", "[][25px][]", "[]"));
		
		JLabel lblInstgesammt = new JLabel("Instgesammt:");
		lblInstgesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser_1.add(lblInstgesammt, "cell 0 0");
		
		txtFallendetruppengesammt = new JTextField();
		txtFallendetruppengesammt.setToolTipText("<html>\r\nDie Anzahl an ausgew\u00E4hlten Truppen, <br>\r\ndie auf diesem Feld fallen (Normale <br>\r\nTruppen z\u00E4hlen einen Punkt, Badasses <br>\r\nz\u00E4hlen zwei)\r\n</html>");
		panel_fallen_troups_looser_1.add(txtFallendetruppengesammt, "cell 1 0");
		txtFallendetruppengesammt.setEditable(false);
		txtFallendetruppengesammt.setBackground(Color.LIGHT_GRAY);
		txtFallendetruppengesammt.setColumns(10);
		
		JButton btnBesttigen = new JButton("Best\u00E4tigen");
		btnBesttigen.setBackground(Color.GRAY);
		panel_fallen_troups_looser_1.add(btnBesttigen, "cell 2 0");
		
		JPanel panel_retread = new JPanel();
		panel_retread.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_retread.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_retread, "cell 1 1,grow");
		panel_retread.setLayout(new MigLayout("", "[][grow]", "[][5px][grow][][]"));
		
		JLabel lblRckzugsfeld = new JLabel("R\u00FCckzugsfeld:");
		lblRckzugsfeld.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_retread.add(lblRckzugsfeld, "cell 0 0 2 1,alignx center");
		
		JScrollPane scrollPane_rueckzug = new JScrollPane();
		panel_retread.add(scrollPane_rueckzug, "cell 0 2 2 1,grow");
		
		JList<Field> list_rueckzug = new JList<Field>(fieldRetreadModel);
		list_rueckzug.setToolTipText("<html>\r\nDas Feld in das sich der Verlierer zur\u00FCck <br>\r\nzieht (verliert der angreifer zieht er sich<br>\r\nimmer in das Feld zur\u00FCck aus dem er <br>\r\ngekommen ist)\r\n</html>");
		list_rueckzug.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_rueckzug.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_rueckzug.setBackground(Color.LIGHT_GRAY);
		scrollPane_rueckzug.setViewportView(list_rueckzug);
		
		JLabel lblRckzugNach = new JLabel("R\u00FCckzug nach:");
		lblRckzugNach.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_retread.add(lblRckzugNach, "cell 0 3,alignx trailing");
		
		txtRckzugnach = new JTextField();
		txtRckzugnach.setBackground(Color.LIGHT_GRAY);
		txtRckzugnach.setEditable(false);
		panel_retread.add(txtRckzugnach, "cell 1 3,growx");
		txtRckzugnach.setColumns(10);
		
		JButton btnAuswhlen = new JButton("Ausw\u00E4hlen");
		btnAuswhlen.setBackground(Color.GRAY);
		panel_retread.add(btnAuswhlen, "cell 0 4 2 1,alignx center");
		
		JPanel panel_fallen_troups = new JPanel();
		panel_fallen_troups.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fallen_troups.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_fallen_troups, "cell 2 1,grow");
		panel_fallen_troups.setLayout(new MigLayout("", "[grow][][50px:50px,fill][10px:n][grow][grow]", "[][5px,grow][][][100px][][5px][][grow]"));
		
		JLabel lblFallendeTruppen_1 = new JLabel("Fallende Truppen:");
		lblFallendeTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fallen_troups.add(lblFallendeTruppen_1, "cell 1 0 4 1,alignx center");
		
		JLabel lblGesammt = new JLabel("Gesammt:");
		lblGesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblGesammt, "cell 1 2");
		
		JSpinner spinner_fallende_truppen_gesammt = new JSpinner();
		spinner_fallende_truppen_gesammt.setToolTipText("<html>\r\nDie gesammte Anzahl an fallenden Truppen (auf <br>\r\nbeiden Seiten). Die maximal m\u00F6gliche Anzahl entspricht<br>\r\ndem Overhead, die minimal m\u00F6gliche Anzahl dem <br>\r\nhalben abgerundeten Overhead (genauere Beschreibung <br>\r\nin der Regel Hilfe)\r\n</html>");
		spinner_fallende_truppen_gesammt.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		spinner_fallende_truppen_gesammt.setForeground(Color.LIGHT_GRAY);
		spinner_fallende_truppen_gesammt.setBackground(Color.LIGHT_GRAY);
		panel_fallen_troups.add(spinner_fallende_truppen_gesammt, "cell 2 2");
		
		JLabel lblUntersttzendesFeld = new JLabel("Unterst\u00FCtzende Felder:");
		lblUntersttzendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblUntersttzendesFeld, "cell 4 2,alignx center");
		
		JLabel lblVerlierer_1 = new JLabel("Verlierer:");
		lblVerlierer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblVerlierer_1, "cell 1 3");
		
		JSpinner spinner_fallende_truppen_verlierer = new JSpinner();
		spinner_fallende_truppen_verlierer.setToolTipText("<html>\r\nDie Anzahl an Truppen die in dem Feld fallen, <br>\r\ndass den Kampf verloren hat (nicht die <br>\r\nUnterst\u00FCtzer). Mindestens die H\u00E4lfte der <br>\r\ninsgesammt fallenden Truppen muss in diesem<br>\r\nFeld fallen (genauere Beschreibung in der <br>\r\nRegel Hilfe)\r\n</html>");
		spinner_fallende_truppen_verlierer.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		spinner_fallende_truppen_verlierer.setForeground(Color.LIGHT_GRAY);
		spinner_fallende_truppen_verlierer.setBackground(Color.LIGHT_GRAY);
		panel_fallen_troups.add(spinner_fallende_truppen_verlierer, "cell 2 3");
		
		JScrollPane scrollPane_support_field = new JScrollPane();
		panel_fallen_troups.add(scrollPane_support_field, "cell 4 3 1 3,grow");
		
		JList<Field> list_support_field = new JList<Field>(fieldFallingSupportModel);
		list_support_field.setToolTipText("<html>\r\nDie Felder die den verlierer unterst\u00FCtzt <br>\r\nhaben und in denen Truppen fallen k\u00F6nnen\r\n</html>");
		list_support_field.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_support_field.setBackground(Color.LIGHT_GRAY);
		scrollPane_support_field.setViewportView(list_support_field);
		
		JLabel lblbrig = new JLabel("\u00DCbrig:");
		lblbrig.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblbrig, "cell 1 4");
		
		txtbrig = new JTextField();
		txtbrig.setEditable(false);
		txtbrig.setToolTipText("<html>\r\nDie Anzahl an fallenden Truppen, die noch <br>\r\nauf den Verlierer und die unterst\u00FCtzenden <br>\r\nFelder verteilt werden m\u00FCssen\r\n</html>");
		txtbrig.setHorizontalAlignment(SwingConstants.CENTER);
		txtbrig.setBackground(Color.LIGHT_GRAY);
		txtbrig.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(txtbrig, "cell 2 4,growx");
		txtbrig.setColumns(10);
		
		JLabel lblUntersttzer_1 = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblUntersttzer_1, "cell 1 5");
		
		JSpinner spinner_fallende_truppen_unterstuetzer = new JSpinner();
		spinner_fallende_truppen_unterstuetzer.setToolTipText("<html>\r\nDie Anzahl der fallenden Truppen in einem <br>\r\nder unterst\u00FCtzenden Felder (maximal die <br>\r\nh\u00E4lfte der Truppen in einem unterst\u00FCtzenden <br>\r\nFeld (abgerundet) kann fallen; genauere <br>\r\nBeschreibung in der Regel Hilfe)\r\n</html>");
		spinner_fallende_truppen_unterstuetzer.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		spinner_fallende_truppen_unterstuetzer.setForeground(Color.LIGHT_GRAY);
		spinner_fallende_truppen_unterstuetzer.setBackground(Color.LIGHT_GRAY);
		panel_fallen_troups.add(spinner_fallende_truppen_unterstuetzer, "cell 2 5");
		
		JButton btnBesttigen_1 = new JButton("Best\u00E4tigen");
		btnBesttigen_1.setBackground(Color.GRAY);
		panel_fallen_troups.add(btnBesttigen_1, "cell 1 7 4 1,alignx center");
	}
}