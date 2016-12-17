package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.Command;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.Building;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class TurnPlaningFrame extends JFrame {
	
	private static final long serialVersionUID = -4935074259881358736L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtField;
	private JTextField txtCurrcommand;
	private JTextField txtKosts;
	private JTextField txtberflle;
	private JTextField txtRckzge;
	private JTextField txtMrsche;
	private JTextField txtAufbauten;
	private JTextField txtRekrutierungen;
	private JTextField txtResourcen;
	
	private JTextField txtSpieler;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtTruppennormal;
	private JTextField txtTruppenbadass;
	
	private ListModel<Field> fieldNoCommandListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldCommandListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldNeighboursListModel = new DefaultListModel<Field>();
	private ListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	private ListModel<FieldBuilding> buildingsAllListModel = new DefaultListModel<FieldBuilding>();
	private ListModel<User> userListModel = new DefaultListModel<User>();
	
	private ComboBoxModel<Command> commandBoxModel = new DefaultComboBoxModel<Command>();
	
	private boolean fieldOverview = false;
	private JPanel panel_board_capture;
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";
	private JTextField txtFeld;
	private JTextField txtRegion;
	
	public TurnPlaningFrame() {
		setTitle("Bunkers and Badasses - Zug Planung");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnPlaningFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1150, 700);
		setMinimumSize(new Dimension(1150, 700));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[450px,grow][:600px:800px,grow]", "[300px,grow][:400px:400px,grow]"));
			
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
		panel_side_bar.setLayout(new MigLayout("", "[200px,grow][200px,grow][200px,grow]", "[250px,grow][150px,grow][300px,grow]"));
		
		JPanel panel_fields_no_command = new JPanel();
		panel_fields_no_command.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_no_command.setBackground(Color.GRAY);
		panel_side_bar.add(panel_fields_no_command, "cell 0 0 1 2,grow");
		panel_fields_no_command.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblGebieteOhneBefehl = new JLabel("Gebiete ohne Befehl:");
		lblGebieteOhneBefehl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_no_command.add(lblGebieteOhneBefehl, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_no_command = new JScrollPane();
		panel_fields_no_command.add(scrollPane_fields_no_command, "cell 0 2,grow");
		
		JList<Field> list_fields_no_command = new JList<Field>(fieldNoCommandListModel);
		list_fields_no_command.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_no_command.setToolTipText("<html>\r\nDiese Gebiete haben noch <br>\r\nkeinen Befehl erhalten.\r\n</html>");
		list_fields_no_command.setBackground(Color.LIGHT_GRAY);
		list_fields_no_command.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_fields_no_command.setViewportView(list_fields_no_command);
		
		JPanel panel_fields_command = new JPanel();
		panel_fields_command.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_command.setBackground(Color.GRAY);
		panel_side_bar.add(panel_fields_command, "cell 1 0 1 2,grow");
		panel_fields_command.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblGebieteMitBefehl = new JLabel("Gebiete mit Befehl:");
		lblGebieteMitBefehl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_command.add(lblGebieteMitBefehl, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_command = new JScrollPane();
		panel_fields_command.add(scrollPane_fields_command, "cell 0 2,grow");
		
		JList<Field> list_fields_command = new JList<Field>(fieldCommandListModel);
		list_fields_command.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_command.setToolTipText("<html>\r\nDiese Gebiete haben bereits <br>\r\neinen Befehl erhalten\r\n</html>");
		list_fields_command.setBackground(Color.LIGHT_GRAY);
		list_fields_command.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_fields_command.setViewportView(list_fields_command);
		
		JPanel panel_fields_all = new JPanel();
		panel_fields_all.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_all.setBackground(Color.GRAY);
		panel_side_bar.add(panel_fields_all, "cell 2 0,grow");
		panel_fields_all.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAlleGebiete = new JLabel("Alle Gebiete:");
		lblAlleGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_all.add(lblAlleGebiete, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_all = new JScrollPane();
		panel_fields_all.add(scrollPane_fields_all, "cell 0 2,grow");
		
		JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
		list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_fields_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_fields_all.setViewportView(list_fields_all);
		
		JPanel panel_buildings = new JPanel();
		panel_side_bar.add(panel_buildings, "cell 2 1 1 2,grow");
		panel_buildings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_buildings.setBackground(Color.GRAY);
		panel_buildings.setLayout(new MigLayout("", "[grow]", "[][5px][][grow][5px][][grow]"));
		
		JLabel lblGebude_1 = new JLabel("Geb\u00E4ude:");
		lblGebude_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_buildings.add(lblGebude_1, "cell 0 0,alignx center");
		
		JLabel lblDeineGebude = new JLabel("Deine Geb\u00E4ude:");
		lblDeineGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_buildings.add(lblDeineGebude, "cell 0 2");
		
		JScrollPane scrollPane_buildings_player = new JScrollPane();
		panel_buildings.add(scrollPane_buildings_player, "cell 0 3,grow");
		
		JList<FieldBuilding> list_buildings_player = new JList<FieldBuilding>(buildingsPlayerListModel);
		list_buildings_player.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_buildings_player.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_buildings_player.setBackground(Color.LIGHT_GRAY);
		scrollPane_buildings_player.setViewportView(list_buildings_player);
		
		JLabel lblAlleGebude = new JLabel("Alle Geb\u00E4ude:");
		lblAlleGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_buildings.add(lblAlleGebude, "cell 0 5");
		
		JScrollPane scrollPane_buildings_all = new JScrollPane();
		panel_buildings.add(scrollPane_buildings_all, "cell 0 6,grow");
		
		JList<FieldBuilding> list_buildings_all = new JList<FieldBuilding>(buildingsAllListModel);
		list_buildings_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_buildings_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_buildings_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_buildings_all.setViewportView(list_buildings_all);
		
		JPanel panel_field_info = new JPanel();
		panel_field_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_field_info.setBackground(Color.GRAY);
		panel_side_bar.add(panel_field_info, "cell 0 2 2 1,grow");
		panel_field_info.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][][][][5px][][50px,grow]"));
		
		JLabel lblFeldbersicht = new JLabel("Feld \u00DCbersicht:");
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field_info.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblFeld_1 = new JLabel("Feld:");
		lblFeld_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblFeld_1, "cell 0 2,alignx trailing");
		
		txtFeld = new JTextField();
		txtFeld.setBackground(Color.LIGHT_GRAY);
		txtFeld.setEditable(false);
		txtFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(txtFeld, "cell 1 2 3 1,growx");
		txtFeld.setColumns(10);
		
		JLabel lblRegion = new JLabel("Region:");
		lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblRegion, "cell 0 3,alignx trailing");
		
		txtRegion = new JTextField();
		txtRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtRegion.setBackground(Color.LIGHT_GRAY);
		txtRegion.setEditable(false);
		panel_field_info.add(txtRegion, "cell 1 3 3 1,growx");
		txtRegion.setColumns(10);
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblSpieler, "cell 0 4,alignx trailing");
		
		txtSpieler = new JTextField();
		txtSpieler.setBackground(Color.LIGHT_GRAY);
		txtSpieler.setEditable(false);
		txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(txtSpieler, "cell 1 4 3 1,growx");
		txtSpieler.setColumns(10);
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblBefehl_1, "cell 0 5,alignx trailing");
		
		txtBefehl = new JTextField();
		txtBefehl.setBackground(Color.LIGHT_GRAY);
		txtBefehl.setEditable(false);
		txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(txtBefehl, "cell 1 5,growx");
		txtBefehl.setColumns(10);
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblNormaleTruppen, "cell 2 5,alignx trailing");
		
		txtTruppennormal = new JTextField();
		txtTruppennormal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppennormal.setBackground(Color.LIGHT_GRAY);
		txtTruppennormal.setEditable(false);
		txtTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(txtTruppennormal, "cell 3 5,growx");
		txtTruppennormal.setColumns(10);
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblGebude, "cell 0 6,alignx trailing");
		
		txtGebude = new JTextField();
		txtGebude.setBackground(Color.LIGHT_GRAY);
		txtGebude.setEditable(false);
		txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(txtGebude, "cell 1 6,growx");
		txtGebude.setColumns(10);
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblBadassTruppen, "cell 2 6,alignx trailing");
		
		txtTruppenbadass = new JTextField();
		txtTruppenbadass.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenbadass.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass.setEditable(false);
		txtTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(txtTruppenbadass, "cell 3 6,growx");
		txtTruppenbadass.setColumns(10);
		
		JLabel lblNachbarn = new JLabel("Nachbarn:");
		lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field_info.add(lblNachbarn, "cell 0 8 4 1,alignx center");
		
		JScrollPane scrollPane_neighbours = new JScrollPane();
		panel_field_info.add(scrollPane_neighbours, "cell 0 9 4 1,grow");
		
		JList<Field> list_neighbours = new JList<Field>(fieldNeighboursListModel);
		list_neighbours.setToolTipText("<html>\r\nBenachbarte Felder\r\n</html>");
		list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_neighbours.setBackground(Color.LIGHT_GRAY);
		scrollPane_neighbours.setViewportView(list_neighbours);
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel.add(panel_low_bar, "cell 0 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[350px,grow][75px,grow][100px,grow][50px,grow]", "[grow][100px:n,grow]"));
		
		JPanel panel_command = new JPanel();
		panel_command.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_command.setBackground(Color.GRAY);
		panel_low_bar.add(panel_command, "cell 0 0 2 1,grow");
		panel_command.setLayout(new MigLayout("", "[][grow][fill]", "[][5px][grow][grow][grow][grow][grow]"));
		
		JLabel lblBefehle = new JLabel("Befehle:");
		lblBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_command.add(lblBefehle, "cell 0 0 3 1,alignx center");
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblFeld, "cell 0 2,alignx trailing");
		
		txtField = new JTextField();
		txtField.setEditable(false);
		txtField.setBackground(Color.LIGHT_GRAY);
		txtField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(txtField, "cell 1 2,growx");
		txtField.setColumns(10);
		
		JButton btnbersicht = new JButton("\u00DCbersicht");
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
		btnbersicht.setToolTipText("<html>\r\nZwichen einer \u00DCbersicht \u00FCber das <br>\r\ngesammte Spielfeld und einer kleineren <br>\r\ndetailierteren Sicht wechseln\r\n</html>");
		btnbersicht.setBackground(Color.GRAY);
		panel_command.add(btnbersicht, "cell 2 2");
		
		JLabel lblBefehl = new JLabel("Aktueller Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblBefehl, "cell 0 3,alignx trailing");
		
		txtCurrcommand = new JTextField();
		txtCurrcommand.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCurrcommand.setBackground(Color.LIGHT_GRAY);
		txtCurrcommand.setEditable(false);
		panel_command.add(txtCurrcommand, "cell 1 3,growx");
		txtCurrcommand.setColumns(10);
		
		JButton btnLschen = new JButton("L\u00F6schen");
		btnLschen.setToolTipText("<html>\r\nDen bestehenden Befehl f\u00FCr <br>\r\ndas ausgew\u00E4hlte Feld entfernen\r\n</html>");
		btnLschen.setBackground(Color.GRAY);
		panel_command.add(btnLschen, "cell 2 3");
		
		JLabel lblNeuerBefehl = new JLabel("Neuer Befehl:");
		lblNeuerBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblNeuerBefehl, "cell 0 4,alignx trailing");
		
		JComboBox<Command> comboBox = new JComboBox<Command>(commandBoxModel);
		comboBox.setBackground(Color.GRAY);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(comboBox, "cell 1 4,growx");
		
		JButton btnHinzufgen = new JButton("Hinzuf\u00FCgen");
		btnHinzufgen.setToolTipText("<html>\r\nDen ausgew\u00E4hlten Befehl dem <br>\r\nausgew\u00E4hlten Feld zuweisen\r\n</html>");
		btnHinzufgen.setBackground(Color.GRAY);
		panel_command.add(btnHinzufgen, "cell 2 4");
		
		JLabel lblKosten = new JLabel("Kosten:");
		lblKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblKosten, "cell 0 5,alignx trailing");
		
		txtKosts = new JTextField();
		txtKosts.setToolTipText("<html>\r\nDie Kosten die ein Befehl auf <br>\r\ndem ausgew\u00E4hlten Feld verursacht\r\n</html>");
		txtKosts.setEditable(false);
		txtKosts.setBackground(Color.LIGHT_GRAY);
		txtKosts.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(txtKosts, "cell 1 5 2 1,growx");
		txtKosts.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(Color.GRAY);
		panel_low_bar.add(panel_1, "cell 2 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow][][25px:40px][grow]", "[][5px][][][][][][]"));
		
		JLabel lblbrigeBefehle = new JLabel("\u00DCbrige Befehle:");
		lblbrigeBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblbrigeBefehle, "cell 1 0 3 1,alignx center");
		
		JLabel lblberflle = new JLabel("\u00DCberf\u00E4lle:");
		lblberflle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblberflle, "cell 1 2,alignx trailing");
		
		txtberflle = new JTextField();
		txtberflle.setHorizontalAlignment(SwingConstants.CENTER);
		txtberflle.setBackground(Color.LIGHT_GRAY);
		txtberflle.setEditable(false);
		txtberflle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(txtberflle, "cell 2 2,growx");
		txtberflle.setColumns(10);
		
		JLabel lblRckzge = new JLabel("R\u00FCckz\u00FCge:");
		lblRckzge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblRckzge, "cell 1 3,alignx trailing");
		
		txtRckzge = new JTextField();
		txtRckzge.setHorizontalAlignment(SwingConstants.CENTER);
		txtRckzge.setBackground(Color.LIGHT_GRAY);
		txtRckzge.setEditable(false);
		txtRckzge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(txtRckzge, "cell 2 3,growx");
		txtRckzge.setColumns(10);
		
		JLabel lblMrsche = new JLabel("M\u00E4rsche:");
		lblMrsche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblMrsche, "cell 1 4,alignx trailing");
		
		txtMrsche = new JTextField();
		txtMrsche.setHorizontalAlignment(SwingConstants.CENTER);
		txtMrsche.setBackground(Color.LIGHT_GRAY);
		txtMrsche.setEditable(false);
		txtMrsche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(txtMrsche, "cell 2 4,growx");
		txtMrsche.setColumns(10);
		
		JLabel lblAufbauten = new JLabel("Aufbauten:");
		lblAufbauten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblAufbauten, "cell 1 5,alignx trailing");
		
		txtAufbauten = new JTextField();
		txtAufbauten.setHorizontalAlignment(SwingConstants.CENTER);
		txtAufbauten.setBackground(Color.LIGHT_GRAY);
		txtAufbauten.setEditable(false);
		txtAufbauten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(txtAufbauten, "cell 2 5,growx");
		txtAufbauten.setColumns(10);
		
		JLabel lblRekrutierungen = new JLabel("Rekrutierungen:");
		lblRekrutierungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblRekrutierungen, "cell 1 6,alignx trailing");
		
		txtRekrutierungen = new JTextField();
		txtRekrutierungen.setHorizontalAlignment(SwingConstants.CENTER);
		txtRekrutierungen.setBackground(Color.LIGHT_GRAY);
		txtRekrutierungen.setEditable(false);
		txtRekrutierungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(txtRekrutierungen, "cell 2 6,growx");
		txtRekrutierungen.setColumns(10);
		
		JLabel lblResourcen = new JLabel("Resourcen:");
		lblResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblResourcen, "cell 1 7,alignx trailing");
		
		txtResourcen = new JTextField();
		txtResourcen.setHorizontalAlignment(SwingConstants.CENTER);
		txtResourcen.setBackground(Color.LIGHT_GRAY);
		txtResourcen.setEditable(false);
		txtResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(txtResourcen, "cell 2 7,growx");
		txtResourcen.setColumns(10);
		
		ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/claptrap_1.png"));
		panel_image.setToolTipText("Claptrap: Interplanetarer Ninja Assasine");
		panel_image.setRemoveIfToSmall(true);
		panel_image.setAdaptSizeKeepProportion(true);
		panel_image.setCentered(true);
		panel_image.setImageMinimumSize(new int[] {75, 150});
		panel_image.setBackground(Color.GRAY);
		panel_low_bar.add(panel_image, "cell 3 0,grow");
		
		JPanel panel_resources = new JPanel();
		panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_resources.setBackground(Color.GRAY);
		panel_low_bar.add(panel_resources, "cell 0 1,grow");
		panel_resources.setLayout(new MigLayout("", "[right][grow,center][grow,center][grow,center]", "[][grow][][grow][grow][grow][grow]"));
		
		JLabel lblResourcen_headline = new JLabel("Resourcen:");
		lblResourcen_headline.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_resources.add(lblResourcen_headline, "cell 0 0 4 1,alignx center");
		
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
		
		JPanel panel_order = new JPanel();
		panel_low_bar.add(panel_order, "cell 1 1 3 1,grow");
		panel_order.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order.setBackground(Color.GRAY);
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
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDialog = new JMenu("Dialog");
		menuBar.add(mnDialog);
		
		JMenuItem menuItem = new JMenuItem("Zug Ausf\u00FChren");
		mnDialog.add(menuItem);
		
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
	}
	
	public class FieldBuilding {
		
		private Field field;
		private Building building;
		
		public FieldBuilding(Field field, Building building) {
			this.field = field;
			this.building = building;
		}
		
		public String toString() {
			return field.getName() + ": " + building.getName();
		}
	}
}