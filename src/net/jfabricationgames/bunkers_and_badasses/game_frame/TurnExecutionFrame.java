package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Command;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TurnExecutionFrame extends JFrame {
	
	private static final long serialVersionUID = 245242421914033099L;
	
	private JPanel contentPane;
	
	private ListModel<Field> fieldNoCommandListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldCommandListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldNeighboursListModel = new DefaultListModel<Field>();
	private ListModel<User> userListModel = new DefaultListModel<User>();
	
	private ComboBoxModel<Command> commandBoxModel = new DefaultComboBoxModel<Command>();
	
	private JTextField txtFeld;
	private JTextField txtBefehl;
	private JTextField txtTruppenn;
	private JTextField txtTruppenb;
	private JTextField txtGebude;
	private JTextField txtVerluste;
	private JPanel panel_board_capture;
	
	private boolean fieldOverview = false;
	
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";
	
	public TurnExecutionFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Zug Ausf\u00FChrung");
		setBounds(100, 100, 1250, 700);
		setMinimumSize(new Dimension(1250, 700));
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[700px,grow][:500px:800px,grow]", "[350px,grow][:400px:400px,grow]"));
		
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
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel.add(panel_side_bar, "cell 1 0 1 2,grow");
		panel_side_bar.setLayout(new MigLayout("", "[grow][grow]", "[300px,grow][:100px:100px,grow][:100px:100px,grow][:200px:300px,grow]"));
		
		JPanel panel_fields_all = new JPanel();
		panel_fields_all.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_all.setBackground(Color.GRAY);
		panel_side_bar.add(panel_fields_all, "cell 0 0 1 2,grow");
		panel_fields_all.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAlleGebiete = new JLabel("Alle Gebiete:");
		lblAlleGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_all.add(lblAlleGebiete, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_all = new JScrollPane();
		panel_fields_all.add(scrollPane_fields_all, "cell 0 2,grow");
		
		JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
		list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_fields_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_fields_all.setViewportView(list_fields_all);
		
		JPanel panel_field_info = new JPanel();
		panel_field_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field_info.setBackground(Color.GRAY);
		panel_side_bar.add(panel_field_info, "cell 1 0,grow");
		panel_field_info.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][][5px][][50px,grow]"));
		
		JLabel lblFeldbersicht = new JLabel("Feld \u00DCbersicht:");
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field_info.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblSpieler, "cell 0 2");
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblNormaleTruppen, "cell 2 2");
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblBefehl_1, "cell 0 3");
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblBadassTruppen, "cell 2 3");
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblGebude, "cell 0 4");
		
		JLabel lblNachbarn = new JLabel("Nachbarn:");
		lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblNachbarn, "cell 0 6 4 1,alignx center");
		
		JScrollPane scrollPane_neighbours = new JScrollPane();
		panel_field_info.add(scrollPane_neighbours, "cell 0 7 4 1,grow");
		
		JList<Field> list_neighbours = new JList<Field>(fieldNeighboursListModel);
		list_neighbours.setToolTipText("<html>\r\nBenachbarte Felder\r\n</html>");
		list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_neighbours.setBackground(Color.LIGHT_GRAY);
		scrollPane_neighbours.setViewportView(list_neighbours);
		
		JPanel panel_executable_commands = new JPanel();
		panel_executable_commands.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_executable_commands.setBackground(Color.GRAY);
		panel_side_bar.add(panel_executable_commands, "cell 0 2 1 2,grow");
		panel_executable_commands.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAusfhrbareBefehle = new JLabel("Ausf\u00FChrbare Befehle:");
		lblAusfhrbareBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_executable_commands.add(lblAusfhrbareBefehle, "cell 0 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_executable_commands.add(scrollPane, "cell 0 2,grow");
		
		JList list = new JList();
		list.setBackground(Color.LIGHT_GRAY);
		list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(list);
		
		JPanel panel_resources = new JPanel();
		panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_resources.setBackground(Color.GRAY);
		panel_side_bar.add(panel_resources, "cell 1 1 1 2,grow");
		panel_resources.setLayout(new MigLayout("", "[right][grow,center][grow,center][grow,center]", "[][grow][][grow][grow][grow][grow]"));
		
		JLabel lblResourcen_headline = new JLabel("Resourcen:");
		lblResourcen_headline.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_resources.add(lblResourcen_headline, "cell 0 0 4 1,alignx center");
		
		JLabel lblbrigeResourcen = new JLabel("\u00DCbrig:");
		lblbrigeResourcen.setToolTipText("Im Moment vorhandene Resourcen");
		lblbrigeResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblbrigeResourcen, "cell 1 2");
		
		JLabel lblErhaltnchsteRunde = new JLabel("Erhalt:");
		lblErhaltnchsteRunde.setToolTipText("<html>\r\nMomentaner Erhalt an Resourcen zu<br>\r\nBeginn der n\u00E4chsten Runde. (Resourcen<br>\r\nGewinnung nicht ber\u00FCcksichtigt)<br>\r\n</html>");
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
		
		JPanel panel_order = new JPanel();
		panel_side_bar.add(panel_order, "cell 1 3,grow");
		panel_order.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order.setBackground(Color.GRAY);
		panel_order.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblReihenfolge = new JLabel("Reihenfolge:");
		lblReihenfolge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_order.add(lblReihenfolge, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_order = new JScrollPane();
		panel_order.add(scrollPane_order, "cell 0 2,grow");
		
		JList<User> list_order = new JList<User>(userListModel);
		list_order.setToolTipText("<html>\r\nDie Reihenfolge der Spieler <br>\r\n(F\u00FCr diese Runde)\r\n</html>");
		scrollPane_order.setViewportView(list_order);
		list_order.setBackground(Color.LIGHT_GRAY);
		list_order.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel.add(panel_low_bar, "cell 0 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_execution = new JPanel();
		panel_execution.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_execution.setBackground(Color.GRAY);
		panel_low_bar.add(panel_execution, "cell 0 0,grow");
		panel_execution.setLayout(new MigLayout("", "[300px,grow][100px,grow][100px,grow]", "[][5px,grow][100px:n,grow]"));
		
		JLabel lblBefehlAusfhren = new JLabel("Befehl Ausf\u00FChren");
		lblBefehlAusfhren.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_execution.add(lblBefehlAusfhren, "cell 0 0 3 1,alignx center");
		
		JPanel panel_command_row_1 = new JPanel();
		panel_command_row_1.setBackground(Color.GRAY);
		panel_execution.add(panel_command_row_1, "cell 0 2,grow");
		panel_command_row_1.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px][grow]"));
		
		JPanel panel_command_1 = new JPanel();
		panel_command_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_1.add(panel_command_1, "cell 0 0,grow");
		panel_command_1.setBackground(Color.GRAY);
		panel_command_1.setLayout(new MigLayout("", "[][grow]", "[][5px][][][][][][5px][grow]"));
		
		JLabel lblbersicht = new JLabel("\u00DCbersicht:");
		lblbersicht.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(lblbersicht, "cell 0 0 2 1,alignx center");
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(lblFeld, "cell 0 2,alignx trailing");
		
		txtFeld = new JTextField();
		txtFeld.setEditable(false);
		txtFeld.setBackground(Color.LIGHT_GRAY);
		txtFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(txtFeld, "cell 1 2,growx");
		txtFeld.setColumns(10);
		
		JLabel lblBefehl = new JLabel("Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(lblBefehl, "cell 0 3,alignx trailing");
		
		txtBefehl = new JTextField();
		txtBefehl.setEditable(false);
		txtBefehl.setBackground(Color.LIGHT_GRAY);
		txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(txtBefehl, "cell 1 3,growx");
		txtBefehl.setColumns(10);
		
		JLabel lblTruppennormal = new JLabel("Truppen (Normal):");
		lblTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(lblTruppennormal, "cell 0 4,alignx trailing");
		
		txtTruppenn = new JTextField();
		txtTruppenn.setEditable(false);
		txtTruppenn.setBackground(Color.LIGHT_GRAY);
		txtTruppenn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(txtTruppenn, "cell 1 4,growx");
		txtTruppenn.setColumns(10);
		
		JLabel lblTruppenbadass = new JLabel("Truppen (Badass):");
		lblTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(lblTruppenbadass, "cell 0 5,alignx trailing");
		
		txtTruppenb = new JTextField();
		txtTruppenb.setEditable(false);
		txtTruppenb.setBackground(Color.LIGHT_GRAY);
		txtTruppenb.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(txtTruppenb, "cell 1 5,growx");
		txtTruppenb.setColumns(10);
		
		JLabel lblGebude_1 = new JLabel("Geb\u00E4ude:");
		lblGebude_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(lblGebude_1, "cell 0 6,alignx trailing");
		
		txtGebude = new JTextField();
		txtGebude.setEditable(false);
		txtGebude.setBackground(Color.LIGHT_GRAY);
		txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(txtGebude, "cell 1 6,growx");
		txtGebude.setColumns(10);
		
		JButton btnbersicht = new JButton("Spielfeld \u00DCbersicht");
		btnbersicht.addActionListener(new ActionListener() {
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
		panel_command_1.add(btnbersicht, "cell 0 8 2 1,alignx center");
		btnbersicht.setBackground(Color.GRAY);
		
		JPanel panel_command_7 = new JPanel();
		panel_command_row_1.add(panel_command_7, "cell 0 2,grow");
		panel_command_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_7.setBackground(Color.GRAY);
		panel_command_7.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JButton btnBefehlAusfhren = new JButton("Befehl Ausf\u00FChren");
		btnBefehlAusfhren.setBackground(Color.GRAY);
		panel_command_7.add(btnBefehlAusfhren, "cell 0 0,alignx center,aligny center");
		
		JPanel panel_command_row_2 = new JPanel();
		panel_command_row_2.setBackground(Color.GRAY);
		panel_execution.add(panel_command_row_2, "cell 1 2,grow");
		panel_command_row_2.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px,grow][][3px:n:3px,grow][5px,grow]"));
		
		JPanel panel_command_2 = new JPanel();
		panel_command_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_2.add(panel_command_2, "cell 0 0,grow");
		panel_command_2.setBackground(Color.GRAY);
		panel_command_2.setLayout(new MigLayout("", "[grow]", "[][5px][]"));
		
		JLabel lblZiel = new JLabel("Ziel:");
		panel_command_2.add(lblZiel, "cell 0 0,alignx center");
		lblZiel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JScrollPane scrollPane_target = new JScrollPane();
		panel_command_2.add(scrollPane_target, "cell 0 2,grow");
		
		JList list_1 = new JList();
		list_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_1.setBackground(Color.LIGHT_GRAY);
		scrollPane_target.setViewportView(list_1);
		
		JPanel panel_command_4 = new JPanel();
		panel_command_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_2.add(panel_command_4, "cell 0 2,grow");
		panel_command_4.setBackground(Color.GRAY);
		panel_command_4.setLayout(new MigLayout("", "[grow][][75px,grow][grow]", "[][5px][]"));
		
		JLabel lblRckzug = new JLabel("R\u00FCckzug:");
		lblRckzug.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_4.add(lblRckzug, "cell 1 0 2 1,alignx center");
		
		JLabel lblVerluste = new JLabel("Verluste:");
		lblVerluste.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_4.add(lblVerluste, "cell 1 2,alignx trailing");
		
		txtVerluste = new JTextField();
		txtVerluste.setEditable(false);
		txtVerluste.setBackground(Color.LIGHT_GRAY);
		txtVerluste.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_4.add(txtVerluste, "cell 2 2,growx");
		txtVerluste.setColumns(10);
		
		JPanel panel_command_6 = new JPanel();
		panel_command_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_2.add(panel_command_6, "cell 0 4,grow");
		panel_command_6.setBackground(Color.GRAY);
		panel_command_6.setLayout(new MigLayout("", "[grow][grow][grow]", "[][5px][]"));
		
		JLabel lblResourcenGewinnung = new JLabel("Resourcen Gewinnung:");
		lblResourcenGewinnung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(lblResourcenGewinnung, "cell 0 0 3 1,alignx center");
		
		JRadioButton rdbtnCredits = new JRadioButton("Credits");
		rdbtnCredits.setBackground(Color.GRAY);
		rdbtnCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(rdbtnCredits, "cell 0 2");
		
		JRadioButton rdbtnMunition = new JRadioButton("Munition");
		rdbtnMunition.setBackground(Color.GRAY);
		rdbtnMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(rdbtnMunition, "cell 1 2");
		
		JRadioButton rdbtnEridium = new JRadioButton("Eridium");
		rdbtnEridium.setBackground(Color.GRAY);
		rdbtnEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(rdbtnEridium, "cell 2 2");
		
		JPanel panel_command_row_3 = new JPanel();
		panel_command_row_3.setBackground(Color.GRAY);
		panel_execution.add(panel_command_row_3, "cell 2 2,grow");
		panel_command_row_3.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px,grow][100px:n,grow]"));
		
		JPanel panel_command_5 = new JPanel();
		panel_command_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_3.add(panel_command_5, "cell 0 0,grow");
		panel_command_5.setBackground(Color.GRAY);
		panel_command_5.setLayout(new MigLayout("", "[grow][grow][][][grow]", "[][5px][][5px][][grow]"));
		
		JLabel lblAufbau = new JLabel("Aufbau:");
		lblAufbau.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblAufbau, "cell 0 0 5 1,alignx center");
		
		JRadioButton rdbtnAufbauen = new JRadioButton("Aufbauen");
		rdbtnAufbauen.setBackground(Color.GRAY);
		rdbtnAufbauen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(rdbtnAufbauen, "cell 1 2 2 1");
		
		JRadioButton rdbtnAbreien = new JRadioButton("Abrei\u00DFen");
		rdbtnAbreien.setBackground(Color.GRAY);
		rdbtnAbreien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(rdbtnAbreien, "cell 3 2");
		
		JLabel lblGebude_2 = new JLabel("Geb\u00E4ude:");
		lblGebude_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblGebude_2, "cell 0 4 2 1");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_command_5.add(scrollPane_1, "cell 0 5 5 1,grow");
		
		JList list_2 = new JList();
		list_2.setBackground(Color.LIGHT_GRAY);
		list_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_1.setViewportView(list_2);
		
		JPanel panel_command_3 = new JPanel();
		panel_command_row_3.add(panel_command_3, "cell 0 2,grow");
		panel_command_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_3.setBackground(Color.GRAY);
		panel_command_3.setLayout(new MigLayout("", "[grow][][50px][grow]", "[][5px][][]"));
		
		JLabel lblMarschBefehl = new JLabel("Marsch Befehl:");
		lblMarschBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblMarschBefehl, "cell 1 0 2 1,alignx center");
		
		JLabel lblTruppennormal_1 = new JLabel("Truppen (Normal):");
		lblTruppennormal_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblTruppennormal_1, "cell 1 2");
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spinner.setForeground(Color.LIGHT_GRAY);
		spinner.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinner, "cell 2 2,growx");
		
		JLabel lblTruppenbadass_1 = new JLabel("Truppen (Badass):");
		lblTruppenbadass_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblTruppenbadass_1, "cell 1 3");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spinner_1.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinner_1, "cell 2 3,growx");
	}
}