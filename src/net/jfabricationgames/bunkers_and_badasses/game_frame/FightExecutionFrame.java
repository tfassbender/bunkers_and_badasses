package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;

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
	
	private boolean fieldOverview = false;
	private JPanel panel_board_capture;
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";
	
	public FightExecutionFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FightExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Kampf Ausf\u00FChrung");
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[400px,grow][100px,grow][700px,grow]", "[300px,grow][300px,grow]"));
		
		panel_board_capture = new JPanel();
		panel_board_capture.setBackground(Color.GRAY);
		panel.add(panel_board_capture, "cell 0 0,grow");
		panel_board_capture.setLayout(new CardLayout(0, 0));
		
		JPanel panel_scroll_board_capture = new JPanel();
		panel_scroll_board_capture.setBackground(Color.GRAY);
		panel_board_capture.add(panel_scroll_board_capture, SCROLL_BOARD);
		panel_scroll_board_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane_board = new JScrollPane();
		panel_scroll_board_capture.add(scrollPane_board, "cell 0 0,grow");
		
		JPanel panel_scroll_board = new JPanel();
		panel_scroll_board.setBackground(Color.GRAY);
		scrollPane_board.setViewportView(panel_scroll_board);
		
		JPanel panel_board_overview_capture = new JPanel();
		panel_board_overview_capture.setBackground(Color.GRAY);
		panel_board_capture.add(panel_board_overview_capture, OVERVIEW_BOARD);
		panel_board_overview_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_board_overview = new JPanel();
		panel_board_overview.setBackground(Color.GRAY);
		panel_board_overview_capture.add(panel_board_overview, "cell 0 0,grow");
		
		JPanel panel_top_right_bar = new JPanel();
		panel_top_right_bar.setBackground(Color.GRAY);
		panel.add(panel_top_right_bar, "cell 1 0 2 1,grow");
		panel_top_right_bar.setLayout(new MigLayout("", "[300px,grow][300px,grow]", "[grow][grow]"));
		
		JPanel panel_info = new JPanel();
		panel_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_info.setBackground(Color.GRAY);
		panel_top_right_bar.add(panel_info, "cell 0 0 1 2,grow");
		panel_info.setLayout(new MigLayout("", "[][grow][50px,grow][5px][grow][50px,grow]", "[][5px][][][5px][][][5px][][][10px][][grow][]"));
		
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
		txtTruppenangreifer.setEditable(false);
		txtTruppenangreifer.setBackground(Color.LIGHT_GRAY);
		txtTruppenangreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtTruppenangreifer, "cell 2 8,growx");
		txtTruppenangreifer.setColumns(10);
		
		JLabel lblUntersttzung = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblUntersttzung, "cell 4 8,alignx trailing");
		
		txtUntersttzerangreifer = new JTextField();
		txtUntersttzerangreifer.setEditable(false);
		txtUntersttzerangreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUntersttzerangreifer.setBackground(Color.LIGHT_GRAY);
		panel_info.add(txtUntersttzerangreifer, "cell 5 8,growx");
		txtUntersttzerangreifer.setColumns(10);
		
		JLabel lblTruppen_1 = new JLabel("Verteidiger Kampfst\u00E4rke:");
		lblTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblTruppen_1, "cell 0 9 2 1,alignx trailing");
		
		txtTruppenverteidiger = new JTextField();
		txtTruppenverteidiger.setEditable(false);
		txtTruppenverteidiger.setBackground(Color.LIGHT_GRAY);
		txtTruppenverteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtTruppenverteidiger, "cell 2 9,growx");
		txtTruppenverteidiger.setColumns(10);
		
		JLabel lblUntersttzer = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblUntersttzer, "cell 4 9,alignx trailing");
		
		txtUntersttzerverteidiger = new JTextField();
		txtUntersttzerverteidiger.setEditable(false);
		txtUntersttzerverteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUntersttzerverteidiger.setBackground(Color.LIGHT_GRAY);
		panel_info.add(txtUntersttzerverteidiger, "cell 5 9,growx");
		txtUntersttzerverteidiger.setColumns(10);
		
		JLabel lblAktiverSpieler = new JLabel("Aktiver Spieler:");
		lblAktiverSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAktiverSpieler, "cell 0 11,alignx trailing");
		
		txtAktiverspieler = new JTextField();
		txtAktiverspieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAktiverspieler.setBackground(Color.LIGHT_GRAY);
		txtAktiverspieler.setEditable(false);
		panel_info.add(txtAktiverspieler, "cell 1 11 5 1,growx");
		txtAktiverspieler.setColumns(10);
		
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
		btnSpielfeldbersicht.setBackground(Color.GRAY);
		panel_info.add(btnSpielfeldbersicht, "cell 0 13 6 1");
		
		JPanel panel_field_attacker = new JPanel();
		panel_field_attacker.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field_attacker.setBackground(Color.GRAY);
		panel_top_right_bar.add(panel_field_attacker, "cell 1 0,grow");
		panel_field_attacker.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][]"));
		
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
		txtTruppenbadass_angreifer.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass_angreifer.setEditable(false);
		txtTruppenbadass_angreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_attacker.add(txtTruppenbadass_angreifer, "cell 3 4,growx");
		txtTruppenbadass_angreifer.setColumns(10);
		
		JPanel panel_field_defender = new JPanel();
		panel_field_defender.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field_defender.setBackground(Color.GRAY);
		panel_top_right_bar.add(panel_field_defender, "cell 1 1,grow");
		panel_field_defender.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][]"));
		
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
		txtTruppenbadass_defender.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass_defender.setEditable(false);
		txtTruppenbadass_defender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_defender.add(txtTruppenbadass_defender, "cell 3 4,growx");
		txtTruppenbadass_defender.setColumns(10);
		
		JPanel panel_low_left_bar = new JPanel();
		panel_low_left_bar.setBackground(Color.GRAY);
		panel.add(panel_low_left_bar, "cell 0 1 2 1,grow");
		panel_low_left_bar.setLayout(new MigLayout("", "[250px,grow][250px,grow]", "[grow][grow]"));
		
		JPanel panel_neighbours_support = new JPanel();
		panel_neighbours_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_neighbours_support.setBackground(Color.GRAY);
		panel_low_left_bar.add(panel_neighbours_support, "cell 0 0,grow");
		panel_neighbours_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblNachbarnMitUntersttzung = new JLabel("Nachbarn mit Unterst\u00FCtzung:");
		lblNachbarnMitUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_neighbours_support.add(lblNachbarnMitUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_neighbour_support = new JScrollPane();
		panel_neighbours_support.add(scrollPane_neighbour_support, "cell 0 2,grow");
		
		JList<Field> list_neighbour_support = new JList<Field>(fieldNeighboursSupportModel);
		list_neighbour_support.setBackground(Color.LIGHT_GRAY);
		list_neighbour_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_neighbour_support.setViewportView(list_neighbour_support);
		
		JPanel panel_denied_support = new JPanel();
		panel_denied_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_denied_support.setBackground(Color.GRAY);
		panel_low_left_bar.add(panel_denied_support, "cell 1 0,grow");
		panel_denied_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblUntersttzungsAbsagen = new JLabel("Unterst\u00FCtzungs Absagen:");
		lblUntersttzungsAbsagen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_denied_support.add(lblUntersttzungsAbsagen, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_denied_support = new JScrollPane();
		panel_denied_support.add(scrollPane_denied_support, "cell 0 2,grow");
		
		JList<Field> list_denied_support = new JList<Field>(fieldDeniedSupportModel);
		list_denied_support.setBackground(Color.LIGHT_GRAY);
		list_denied_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_denied_support.setViewportView(list_denied_support);
		
		JPanel panel_attacker_support = new JPanel();
		panel_attacker_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_attacker_support.setBackground(Color.GRAY);
		panel_low_left_bar.add(panel_attacker_support, "cell 0 1,grow");
		panel_attacker_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAngreiferUntersttzung = new JLabel("Angreifer Unterst\u00FCtzung:");
		lblAngreiferUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_attacker_support.add(lblAngreiferUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_attacker_support = new JScrollPane();
		panel_attacker_support.add(scrollPane_attacker_support, "cell 0 2,grow");
		
		JList<Field> list_attacker_support = new JList<Field>(fieldAttackerSupportModel);
		list_attacker_support.setBackground(Color.LIGHT_GRAY);
		list_attacker_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_attacker_support.setViewportView(list_attacker_support);
		
		JPanel panel_defender_support = new JPanel();
		panel_defender_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_defender_support.setBackground(Color.GRAY);
		panel_low_left_bar.add(panel_defender_support, "cell 1 1,grow");
		panel_defender_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblVerteidigerUntersttzung = new JLabel("Verteidiger Unterst\u00FCtzung:");
		lblVerteidigerUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_defender_support.add(lblVerteidigerUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_defender_support = new JScrollPane();
		panel_defender_support.add(scrollPane_defender_support, "cell 0 2,grow");
		
		JList<Field> list_defender_support = new JList<Field>(fieldDefenderSupportModel);
		list_defender_support.setBackground(Color.LIGHT_GRAY);
		list_defender_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_defender_support.setViewportView(list_defender_support);
		
		JPanel panel_low_right_bar = new JPanel();
		panel_low_right_bar.setBackground(Color.GRAY);
		panel.add(panel_low_right_bar, "cell 2 1,grow");
		panel_low_right_bar.setLayout(new MigLayout("", "[]", "[]"));
	}
}