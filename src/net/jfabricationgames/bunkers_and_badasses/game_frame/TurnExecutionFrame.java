package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
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
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game.UserResourceManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.CrazyEarlsBlackMarket;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MarcusGunshop;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MoxxisTavern;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MoxxisUnderdome;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.RolandsTurret;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ScootersCatchARide;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TannisResearchStation;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TinyTinasMine;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TorguesBadassDome;
import net.jfabricationgames.bunkers_and_badasses.game_command.BuildCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.CollectCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_command.MarchCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RaidCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RecruitCommand;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class TurnExecutionFrame extends JFrame implements BoardPanelListener, ConfirmDialogListener, TurnBonusSelectionListener {
	
	private static final long serialVersionUID = 245242421914033099L;
	
	private JPanel contentPane;
	
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	private PlayerOrderPanel orderPanel;
	private PointPanel pointPanel;
	
	private Game game;
	private Field selectedField;
	
	private BoardPanel boardPanel;
	
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldTargetModel = new DefaultListModel<Field>();
	private DefaultListModel<Building> buildingModel = new DefaultListModel<Building>();
	private DefaultListModel<FieldCommand> fieldCommandModel = new DefaultListModel<FieldCommand>();
	
	private JTextField txtFeld_1;
	private JTextField txtBefehl;
	private JTextField txtTruppenn;
	private JTextField txtGebude;
	private JTextField txtTruppenb;
	private JButton btnBefehlAusfhren;
	private JRadioButton rdbtnCredits;
	private JRadioButton rdbtnMunition;
	private JRadioButton rdbtnEridium;
	private JRadioButton rdbtnAufbauen;
	private JRadioButton rdbtnAufrsten;
	private JRadioButton rdbtnAbreien;
	private JSpinner spinnerNormalTroops;
	private JSpinner spinnerBadassTroops;
	
	private static Building[] buildables;
	private JList<Field> list_target_field;
	private JList<Building> list_building;
	private JSpinner spinnerAufrstungen;
	
	private boolean fieldSelectionSucessfull;
	private JButton btnAusfhrungBeenden;

	static {
		buildables = new Building[9];
		buildables[0] = new CrazyEarlsBlackMarket();
		buildables[1] = new MarcusGunshop();
		buildables[2] = new MoxxisTavern();
		buildables[3] = new MoxxisUnderdome();
		buildables[4] = new RolandsTurret();
		buildables[5] = new ScootersCatchARide();
		buildables[6] = new TannisResearchStation();
		buildables[7] = new TinyTinasMine();
		buildables[8] = new TorguesBadassDome();
	}
	
	public TurnExecutionFrame(Game game) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Zug Ausf\u00FChrung");
		setBounds(100, 100, 1300, 775);
		setMinimumSize(new Dimension(1300, 775));
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[650px,grow][:550px:800px,grow]", "[400px,grow][:400px:400px,grow]"));
		
		boardPanel = new BoardPanel();
		boardPanel.addBoardPanelListener(this);
		panel.add(boardPanel, "cell 0 0,grow");
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel.add(panel_side_bar, "cell 1 0 1 2,grow");
		panel_side_bar.setLayout(new MigLayout("", "[250px,grow][200px,grow][200px,grow]", "[300px,grow][:100px:100px,grow][:100px:100px,grow][:200px:300px,grow]"));
		
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
		list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_fields_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_fields_all.setViewportView(list_fields_all);

		fieldPanel = new FieldDescriptionPanel("Feld �bersicht", true);
		panel_side_bar.add(fieldPanel, "cell 1 0 2 1,grow");
		
		JPanel panel_executable_commands = new JPanel();
		panel_executable_commands.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_executable_commands.setBackground(Color.GRAY);
		panel_side_bar.add(panel_executable_commands, "cell 0 2 1 2,grow");
		panel_executable_commands.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAusfhrbareBefehle = new JLabel("Ausf\u00FChrbare Befehle:");
		lblAusfhrbareBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_executable_commands.add(lblAusfhrbareBefehle, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_executable_commands = new JScrollPane();
		panel_executable_commands.add(scrollPane_executable_commands, "cell 0 2,grow");
		
		JList<FieldCommand> list_executable_commands = new JList<FieldCommand>(fieldCommandModel);
		list_executable_commands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_executable_commands.setBackground(Color.LIGHT_GRAY);
		list_executable_commands.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_executable_commands.setViewportView(list_executable_commands);
		
		resourcePanel = new ResourceInfoPanel();
		panel_side_bar.add(resourcePanel, "cell 1 1 2 2,grow");

		orderPanel = new PlayerOrderPanel();
		panel_side_bar.add(orderPanel, "cell 1 3,grow");
		
		pointPanel = new PointPanel();
		panel_side_bar.add(pointPanel, "cell 2 3,grow");
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel.add(panel_low_bar, "cell 0 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_execution = new JPanel();
		panel_execution.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_execution.setBackground(Color.GRAY);
		panel_low_bar.add(panel_execution, "cell 0 0,grow");
		panel_execution.setLayout(new MigLayout("", "[300px,grow][300px,grow][300px,grow]", "[][5px,grow][100px:n,grow]"));
		
		JLabel lblBefehlAusfhren = new JLabel("Befehl Ausf\u00FChren:");
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
		
		txtFeld_1 = new JTextField();
		txtFeld_1.setEditable(false);
		txtFeld_1.setBackground(Color.LIGHT_GRAY);
		txtFeld_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_1.add(txtFeld_1, "cell 1 2,growx");
		txtFeld_1.setColumns(10);
		
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
				boardPanel.showOtherView();
			}
		});
		panel_command_1.add(btnbersicht, "cell 0 8 2 1,alignx center");
		btnbersicht.setBackground(Color.GRAY);
		
		JPanel panel_command_7 = new JPanel();
		panel_command_row_1.add(panel_command_7, "cell 0 2,grow");
		panel_command_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_7.setBackground(Color.GRAY);
		panel_command_7.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		btnBefehlAusfhren = new JButton("Befehl Ausf\u00FChren");
		btnBefehlAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executeCommand();
			}
		});
		btnBefehlAusfhren.setEnabled(false);
		btnBefehlAusfhren.setBackground(Color.GRAY);
		panel_command_7.add(btnBefehlAusfhren, "cell 0 0,alignx center,aligny center");
		
		btnAusfhrungBeenden = new JButton("Ausf\u00FChrung Beenden");
		btnAusfhrungBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ConfirmDialog("Ausf�hrung wirklich beenden?", TurnExecutionFrame.this, 0).setVisible(true);
			}
		});
		btnAusfhrungBeenden.setEnabled(false);
		panel_command_7.add(btnAusfhrungBeenden, "cell 0 1,alignx center");
		btnAusfhrungBeenden.setBackground(Color.GRAY);
		
		JPanel panel_command_row_2 = new JPanel();
		panel_command_row_2.setBackground(Color.GRAY);
		panel_execution.add(panel_command_row_2, "cell 1 2,grow");
		panel_command_row_2.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px,grow][5px,grow]"));
		
		JPanel panel_command_2 = new JPanel();
		panel_command_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_2.add(panel_command_2, "cell 0 0,grow");
		panel_command_2.setBackground(Color.GRAY);
		panel_command_2.setLayout(new MigLayout("", "[grow]", "[][5px][]"));
		
		JLabel lblZiel = new JLabel("Ziel:");
		panel_command_2.add(lblZiel, "cell 0 0,alignx center");
		lblZiel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JScrollPane scrollPane_target_field = new JScrollPane();
		panel_command_2.add(scrollPane_target_field, "cell 0 2,grow");
		
		list_target_field = new JList<Field>(fieldTargetModel);
		list_target_field.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_target_field.setBackground(Color.LIGHT_GRAY);
		scrollPane_target_field.setViewportView(list_target_field);
		
		JPanel panel_command_3 = new JPanel();
		panel_command_row_2.add(panel_command_3, "cell 0 2,grow");
		panel_command_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_3.setBackground(Color.GRAY);
		panel_command_3.setLayout(new MigLayout("", "[grow][][50px][grow]", "[][5px][][][]"));
		
		JLabel lblMarschBefehl = new JLabel("Marsch / Rekrutierung:");
		lblMarschBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblMarschBefehl, "cell 1 0 2 1,alignx center");
		
		JLabel lblTruppennormal_1 = new JLabel("Truppen (Normal):");
		lblTruppennormal_1.setToolTipText("<html>\r\nMarschbefehl:<br>\r\nNormale Truppen die bei diesem <br>\r\nMarsch bewegt werden sollen.<br>\r\n<br>\r\nRekrutierungsbefehl:<br>\r\nNormale Truppen die neu Rekrutiert<br>\r\nwerden sollen.\r\n</html>");
		lblTruppennormal_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblTruppennormal_1, "cell 1 2");
		
		spinnerNormalTroops = new JSpinner();
		spinnerNormalTroops.setToolTipText("<html>\r\nMarschbefehl:<br>\r\nNormale Truppen die bei diesem <br>\r\nMarsch bewegt werden sollen.<br>\r\n<br>\r\nRekrutierungsbefehl:<br>\r\nNormale Truppen die neu Rekrutiert<br>\r\nwerden sollen.\r\n</html>");
		spinnerNormalTroops.setEnabled(false);
		spinnerNormalTroops.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spinnerNormalTroops.setForeground(Color.LIGHT_GRAY);
		spinnerNormalTroops.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinnerNormalTroops, "cell 2 2,growx");
		
		JLabel lblTruppenbadass_1 = new JLabel("Truppen (Badass):");
		lblTruppenbadass_1.setToolTipText("<html>\r\nMarschbefehl:<br>\r\nBadasses die bei diesem Marsch<br>\r\nbewegt werden sollen.<br>\r\n<br>\r\nRekrutierungsbefehl:<br>\r\nTruppen die von normalen Truppen<br>\r\nzu Badasses aufger\u00FCstet werden sollen.\r\n</html>");
		lblTruppenbadass_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblTruppenbadass_1, "cell 1 3");
		
		spinnerBadassTroops = new JSpinner();
		spinnerBadassTroops.setToolTipText("<html>\r\nMarschbefehl:<br>\r\nBadasses die bei diesem Marsch<br>\r\nbewegt werden sollen.<br>\r\n<br>\r\nRekrutierungsbefehl:<br>\r\nTruppen die von normalen Truppen<br>\r\nzu Badasses aufger\u00FCstet werden sollen.\r\n</html>");
		spinnerBadassTroops.setEnabled(false);
		spinnerBadassTroops.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spinnerBadassTroops.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinnerBadassTroops, "cell 2 3,growx");
		
		JLabel lblAufrstungen = new JLabel("Aufr\u00FCstungen:");
		lblAufrstungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblAufrstungen, "cell 1 4");
		
		spinnerAufrstungen = new JSpinner();
		spinnerAufrstungen.setEnabled(false);
		spinnerAufrstungen.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinnerAufrstungen, "cell 2 4,growx");
		
		JPanel panel_command_row_3 = new JPanel();
		panel_command_row_3.setBackground(Color.GRAY);
		panel_execution.add(panel_command_row_3, "cell 2 2,grow");
		panel_command_row_3.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px,grow][100px:n,grow]"));
		
		JPanel panel_command_5 = new JPanel();
		panel_command_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_3.add(panel_command_5, "cell 0 0,grow");
		panel_command_5.setBackground(Color.GRAY);
		panel_command_5.setLayout(new MigLayout("", "[grow][grow][][][grow]", "[][5px][][][5px][][100px,grow]"));
		
		JLabel lblAufbau = new JLabel("Aufbau:");
		lblAufbau.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblAufbau, "cell 0 0 5 1,alignx center");
		
		ButtonGroup group_building = new ButtonGroup();
		
		rdbtnAufbauen = new JRadioButton("Aufbauen");
		rdbtnAufbauen.setSelected(true);
		rdbtnAufbauen.setEnabled(false);
		rdbtnAufbauen.setBackground(Color.GRAY);
		rdbtnAufbauen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		group_building.add(rdbtnAufbauen);
		panel_command_5.add(rdbtnAufbauen, "cell 1 2 2 1");
		
		rdbtnAufrsten = new JRadioButton("Aufr\u00FCsten");
		rdbtnAufrsten.setEnabled(false);
		rdbtnAufrsten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAufrsten.setBackground(Color.GRAY);
		group_building.add(rdbtnAufrsten);
		panel_command_5.add(rdbtnAufrsten, "cell 3 2");
		
		rdbtnAbreien = new JRadioButton("Abrei\u00DFen");
		rdbtnAbreien.setEnabled(false);
		rdbtnAbreien.setBackground(Color.GRAY);
		rdbtnAbreien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		group_building.add(rdbtnAbreien);
		panel_command_5.add(rdbtnAbreien, "cell 1 3 3 1,alignx center");
		
		JLabel lblGebude_2 = new JLabel("Geb\u00E4ude:");
		lblGebude_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblGebude_2, "cell 0 5 2 1");
		
		JScrollPane scrollPane_building = new JScrollPane();
		panel_command_5.add(scrollPane_building, "cell 0 6 5 1,grow");
		
		list_building = new JList<Building>(buildingModel);
		list_building.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_building.setBackground(Color.LIGHT_GRAY);
		list_building.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_building.setViewportView(list_building);
		
		JPanel panel_command_6 = new JPanel();
		panel_command_row_3.add(panel_command_6, "cell 0 2,grow");
		panel_command_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_6.setBackground(Color.GRAY);
		panel_command_6.setLayout(new MigLayout("", "[grow][grow]", "[][5px][][]"));
		
		JLabel lblResourcenGewinnung = new JLabel("Resourcen Gewinnung:");
		lblResourcenGewinnung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(lblResourcenGewinnung, "cell 0 0 2 1,alignx center");
		
		ButtonGroup resource_group = new ButtonGroup();
		
		rdbtnCredits = new JRadioButton("Credits");
		rdbtnCredits.setSelected(true);
		rdbtnCredits.setEnabled(false);
		rdbtnCredits.setBackground(Color.GRAY);
		rdbtnCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resource_group.add(rdbtnCredits);
		panel_command_6.add(rdbtnCredits, "cell 0 2,alignx center");
		
		rdbtnMunition = new JRadioButton("Munition");
		rdbtnMunition.setEnabled(false);
		rdbtnMunition.setBackground(Color.GRAY);
		rdbtnMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resource_group.add(rdbtnMunition);
		panel_command_6.add(rdbtnMunition, "cell 1 2,alignx center");
		
		rdbtnEridium = new JRadioButton("Eridium");
		rdbtnEridium.setEnabled(false);
		rdbtnEridium.setBackground(Color.GRAY);
		rdbtnEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resource_group.add(rdbtnEridium);
		panel_command_6.add(rdbtnEridium, "cell 0 3 2 1,alignx center");
	}
	
	@Override
	public void receiveBoardMouseClick(MouseEvent event) {
		selectedField = game.getBoard().getFieldAtMousePosition();
		updateField();
	}
	
	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			game.setState(GameState.SELECT_BONUS);
			TurnGoalTurnBonusDialog dialog = game.getGameFrame().getTurnGoalTurnBonusDialog();
			dialog.showPanel(TurnGoalTurnBonusDialog.TURN_BONUS_PANEL);
			dialog.setTurnBonusSelectable(true, this);
			dialog.setVisible(true);
			dialog.requestFocus();
		}
	}
	
	@Override
	public void receiveTurnBonusSelection(TurnBonus selectedBonus) {
		game.getGameTurnBonusManager().chooseTurnBonus(game.getLocalUser(), selectedBonus);
		game.setState(GameState.WAIT);
		game.getGameFrame().getTurnGoalTurnBonusDialog().setTurnBonusSelectable(false, null);
		game.getTurnExecutionManager().commit();
	}
	
	private List<Field> findPossibleMovingTargets(Field field) {
		List<Field> possibleTargets = new ArrayList<Field>();
		List<Field> newTargets;
		possibleTargets.add(field);
		for (int i = 0; i < field.getBuilding().getMoveDistance(); i++) {
			newTargets = new ArrayList<Field>();
			for (Field start : possibleTargets) {
				if (start.getAffiliation().equals(game.getLocalUser())) {
					for (Field neighbour : start.getNeighbours()) {
						newTargets.add(neighbour);
					}
				}
			}
			possibleTargets.addAll(newTargets);
		}
		return possibleTargets;
	}
	
	public void update() {
		updateResources();
		updatePlayerOrder();
		updatePointPanel();
		updateField();
		updateFieldList();
		updateFieldCommandList();
	}
	
	private void updateFieldList() {
		fieldAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
		}
	}
	
	private void updateFieldCommandList() {
		fieldCommandModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (field.getCommand() != null) {
				fieldCommandModel.addElement(new FieldCommand(field, field.getCommand()));
			}
		}
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
	}
	
	private void updatePlayerOrder() {
		orderPanel.updateTurnOrder(game);
	}
	
	private void updatePointPanel() {
		pointPanel.updatePoints(game);
	}
	
	private void updateField() {
		fieldPanel.updateField(selectedField);
		disableAll();
		if (selectedField != null) {
			txtFeld_1.setText(selectedField.getName());
			if (selectedField.getCommand() != null) {
				txtBefehl.setText(selectedField.getCommand().getName());
			}
			else {
				txtBefehl.setText("-----");
			}
			txtTruppenn.setText(Integer.toString(selectedField.getNormalTroops()));
			txtTruppenb.setText(Integer.toString(selectedField.getBadassTroops()));
			txtGebude.setText(selectedField.getBuilding().getName());
			enableComponents(selectedField);
		}
		else {
			txtFeld_1.setText("");
			txtBefehl.setText("");
			txtTruppenn.setText("");
			txtTruppenb.setText("");
			txtGebude.setText("");
		}
		repaint();
	}
	
	private void disableAll() {
		btnBefehlAusfhren.setEnabled(false);
		btnAusfhrungBeenden.setEnabled(false);
		rdbtnCredits.setEnabled(false);
		rdbtnMunition.setEnabled(false);
		rdbtnEridium.setEnabled(false);
		rdbtnAufbauen.setEnabled(false);
		rdbtnAufrsten.setEnabled(false);
		rdbtnAbreien.setEnabled(false);
		spinnerNormalTroops.setEnabled(false);
		spinnerBadassTroops.setEnabled(false);
		spinnerAufrstungen.setEnabled(false);
		fieldTargetModel.removeAllElements();
		buildingModel.removeAllElements();
	}
	
	/**
	 * Enable the components depending on the command on the chosen field
	 */
	private void enableComponents(Field field) {
		if (field.getAffiliation().equals(game.getLocalUser())) {
			Command command = field.getCommand();
			if (command.isExecutable() && game.getPlayerOrder().isPlayersTurn(game.getLocalUser())) {
				btnBefehlAusfhren.setEnabled(true);
				btnAusfhrungBeenden.setEnabled(true);
			}
			if (command instanceof BuildCommand) {
				if (field.getBuilding() instanceof EmptyBuilding) {
					rdbtnAufbauen.setEnabled(true);
					for (Building building : buildables) {
						buildingModel.addElement(building);
					}
				}
				else {
					rdbtnAbreien.setEnabled(true);
					if (field.getBuilding().isExtendable()) {
						rdbtnAufrsten.setEnabled(true);
					}
				}
			}
			else if (command instanceof CollectCommand) {
				rdbtnCredits.setEnabled(true);
				rdbtnMunition.setEnabled(true);
				rdbtnEridium.setEnabled(true);
			}
			else if (command instanceof MarchCommand) {
				for (Field target : findPossibleMovingTargets(field)) {
					fieldTargetModel.addElement(target);
				}
				spinnerNormalTroops.setEnabled(true);
				spinnerBadassTroops.setEnabled(true);
				spinnerNormalTroops.setModel(new SpinnerNumberModel(0, 0, field.getNormalTroops(), 1));
				spinnerBadassTroops.setModel(new SpinnerNumberModel(0, 0, field.getBadassTroops(), 1));
			}
			else if (command instanceof RaidCommand) {
				for (Field target : field.getNeighbours()) {
					if (target.getAffiliation().equals(game.getLocalUser())) {
						if (target.getCommand() != null && target.getCommand().isRemovable()) {
							fieldTargetModel.addElement(target);
						}
					}
				}
			}
			else if (command instanceof RecruitCommand) {
				spinnerNormalTroops.setEnabled(true);
				spinnerNormalTroops.setModel(new SpinnerNumberModel(0, 0, field.getBuilding().getRecruitableTroops(), 1));
				boolean badassesRecruitable = false;
				for (Field boardField : game.getBoard().getFields()) {
					badassesRecruitable |= boardField.getBuilding().isBadassTroopsRecruitable() && boardField.getAffiliation().equals(game.getLocalUser());
				}
				if (badassesRecruitable) {
					spinnerBadassTroops.setEnabled(true);
					spinnerAufrstungen.setEnabled(true);
					spinnerBadassTroops.setModel(new SpinnerNumberModel(0, 0, field.getBuilding().getRecruitableTroops()/2, 1));
					spinnerAufrstungen.setModel(new SpinnerNumberModel(0, 0, Math.min(field.getNormalTroops(), field.getBuilding().getRecruitableTroops()), 1));
				}
			}
		}
	}
	
	private void executeCommand() {
		boolean commandExecuted = false;
		if (selectedField.getAffiliation().equals(game.getLocalUser())) {
			Command command = selectedField.getCommand();
			UserResourceManager resourceManager = game.getResourceManager();
			if (command.isExecutable()) {
				if (command instanceof BuildCommand) {
					if (rdbtnAufbauen.isSelected()) {
						if (selectedField.getBuilding() instanceof EmptyBuilding) {
							Building building = list_building.getSelectedValue();
							if (building != null) {
								try {
									resourceManager.payBuilding(building, game.getLocalUser());
									selectedField.setBuilding(building.newInstance());
									commandExecuted = true;
								}
								catch (ResourceException re) {
									new ErrorDialog("Du hast nicht genug Resourcen um das Geb�ude zu bezahlen.\n\nAnschreiben lassen geht hier leider nicht.").setVisible(true);
								}
							}
							else {
								new ErrorDialog("Du musst ein Geb�ude ausw�hlen um es aufzubauen.\n\nOder glaubst du etwa, dass deine Truppen aus Architekten bestehen?!\nDas w�rde die Existenz von Gehirnzellen bedingen!").setVisible(true);
							}
						}
						else {
							new ErrorDialog("Du kannst nicht zwei Geb�ude auf ein Feld bauen.\n\nAu�er du willst versuchen sie zu stapeln...\nIch muss zugeben das w�rde ich gerne sehen.").setVisible(true);
						}
					}
					else if (rdbtnAufrsten.isSelected()) {
						Building building = selectedField.getBuilding();
						if (building != null && building.isExtendable()) {
							try {
								resourceManager.payBuildingUpgrade(building, game.getLocalUser());
								building.extend();
								commandExecuted = true;
							}
							catch (ResourceException re) {
								new ErrorDialog("Du hast nicht genug Resourcen um das Geb�ude zu bezahlen.\n\nAnschreiben lassen geht hier leider nicht.").setVisible(true);
							}
						}
						else {
							new ErrorDialog("Dieses Geb�ude kann man nicht aufr�sten.\n\nBau einfach ein paar mehr davon. Ist doch genug Platz da.\nAu�er Du spielst zu schlecht...").setVisible(true);
						}
					}
					else if (rdbtnAbreien.isSelected()) {
						Building building = selectedField.getBuilding();
						if (building == null || building instanceof EmptyBuilding) {
							new ErrorDialog("Du musst ein Geb�ude aufbauen bevor Du es einrei�en kannst.\n\nErst die Arbeit, dann das Vergn�gen...").setVisible(true);
						}
						else if (building instanceof ArschgaulsPalace) {
							new ErrorDialog("Du kannst doch nicht Arschgauls Palast abrei�en!!!\nWas soll der Schei�?!");
						}
						else {
							selectedField.setBuilding(new EmptyBuilding());
							commandExecuted = true;
						}
					}
				}
				else if (command instanceof CollectCommand) {
					int collectionType = 0;
					if (rdbtnCredits.isSelected()) {
						collectionType = 1;
					}
					else if (rdbtnMunition.isSelected()) {
						collectionType = 2;
					}
					else if (rdbtnEridium.isSelected()) {
						collectionType = 3;
					}
					if (collectionType == 0) {
						new ErrorDialog("Du musst schon aussuchen was deine Truppen suchen sollen.\n\nOder Du l�sst sie einfach ein paar h�bsche Steine suchen. Ist ja Dein Geld...").setVisible(true);
					}
					else {
						resourceManager.collectCommandResources(game.getLocalUser());
						commandExecuted = true;
					}
				}
				else if (command instanceof MarchCommand) {
					int[] targets = list_target_field.getSelectedIndices();
					int normalTroops = (Integer) spinnerNormalTroops.getValue();
					int badassTroops = (Integer) spinnerBadassTroops.getValue();
					int troops = normalTroops + badassTroops;
					if (targets.length > 1) {
						int enemyFields = 0;
						User affiliation;
						for (int i : targets) {
							affiliation = fieldTargetModel.getElementAt(i).getAffiliation();
							if (affiliation != null && !affiliation.equals(game.getLocalUser())) {
								enemyFields++;
							}
						}
						if (enemyFields > 1) {
							new ErrorDialog("Du kannst nicht in einem Zug zwei Gegner angreifen.\n\nLass den armen Schweinen doch auch mal ne Chance.").setVisible(true);
						}
						else if (troops <= 0) {
							new ErrorDialog("Du solltest auch Truppen aussuchen wenn du willst dass die sich bewegen.\n\nDas ist keine freiwillige Aktion hier.").setVisible(true);
						}
						/*else if (troops < targets.length) {
							new ErrorDialog("Das sind mehr Felder als du Truppen hast.\n\nDeine Truppen k�nnen sich nicht Zweiteilen.\nNaja k�nnen sie schon aber dannach sind sie meistens ein wenig... tot...").setVisible(true);
						}*/
						else {
							List<Field> targetFields = new ArrayList<Field>(targets.length);
							for (int i : targets) {
								targetFields.add(fieldTargetModel.getElementAt(i));
							}
							fieldSelectionSucessfull = false;
							new TargetFieldSelectionDialog(this, game, selectedField, targetFields, normalTroops, badassTroops).setVisible(true);
							commandExecuted = fieldSelectionSucessfull;
						}
					}
					else if (targets.length == 1) {
						Field targetField = fieldTargetModel.getElementAt(targets[0]);
						if (troops > 0) {
							if (targetField.getAffiliation().equals(game.getLocalUser()) || targetField.getDefenceStrength() == 0) {
								game.getBoard().moveTroops(selectedField, targetField, normalTroops, badassTroops);
								commandExecuted = true;
							}
							else {
								game.getFightManager().startFight(selectedField, targetField, normalTroops, badassTroops);
								commandExecuted = true;
							}
						}
						else {
							new ErrorDialog("Du solltest auch Truppen aussuchen wenn du willst dass die sich bewegen.\n\nDas ist keine freiwillige Aktion hier.").setVisible(true);
						}
					}
					else {
						new ErrorDialog("Du solltest ein Ziel aussuchen wenn du willst das deine Truppen sich bewegen.\n\nOder Du l�sst sie einfach im Kreis rennen. Das kann auch lustig sein.\nNicht unbedingt zielf�hrend... aber lustig.").setVisible(true);
					}
				}
				else if (command instanceof RaidCommand) {
					int[] targets = list_target_field.getSelectedIndices();
					if (targets.length > 1) {
						new ErrorDialog("Du kannst nicht in einem Zug mehrere Felder �berfallen.\n\nLass den Anderen auch noch was �brig.").setVisible(true);
					}
					else if (targets.length == 0) {
						new ErrorDialog("Du musst schon ein Ziel aussuchen das deine Leute �berfallen k�nnen.\n\nOder Du �berf�llst dich einfach selbst.\nDas schl�gt auch die Zeit tot.").setVisible(true);
					}
					else {
						Field target = fieldTargetModel.getElementAt(targets[0]);
						Command selectedCommand = target.getCommand();
						if (selectedCommand == null) {
							new ErrorDialog("Du kannst keinen Befehl entfernen wo garkein Befehl ist.\n\nDu k�nntest sie aber nur so zum Spa� �berfallen...").setVisible(true);
						}
						else if (!selectedCommand.isRemovable()) {
							new ErrorDialog("Diesen Befehl kannst Du nicht entferenen.\n\nDie Anderen sollen doch auch noch ihren Spa� haben.").setVisible(true);
						}
						else {
							if (target.getCommand() instanceof CollectCommand) {
								resourceManager.collectCommandResources(game.getLocalUser());
							}
							target.setCommand(null);
							commandExecuted = true;
						}
					}
				}
				else if (command instanceof RecruitCommand) {
					int normalTroops = (Integer) spinnerNormalTroops.getValue();
					int badassTroops = (Integer) spinnerBadassTroops.getValue();
					int upgrades = (Integer) spinnerAufrstungen.getValue();
					int troops = normalTroops + badassTroops*2 + upgrades;
					if (troops <= 0) {
						new ErrorDialog("Du willst also 0 Truppen Rekrutieren?!\n\nBist Du sicher, dass du das Spielprinzip verstanden hast?").setVisible(true);
					}
					else if (troops > selectedField.getBuilding().getRecruitableTroops()) {
						new ErrorDialog("Du kannst nicht so viele Truppen auf einmal rekrutieren.\n\nDiese Selbstmordkandidaten wachsen nunmal nicht an B�umen.").setVisible(true);
					}
					else {
						if (upgrades <= selectedField.getNormalTroops()) {
							try {
								resourceManager.payRecroutedTroops(normalTroops, badassTroops, upgrades, game.getLocalUser());
								selectedField.addNormalTroops(normalTroops);
								selectedField.addBadassTroops(badassTroops);
								selectedField.removeNormalTroops(upgrades);
								selectedField.addBadassTroops(upgrades);
							}
							catch (ResourceException re) {
								new ErrorDialog("Du hast nicht genug Resourcen um die Truppen zu bezahlen.\n\nAnschreiben lassen geht hier leider nicht.").setVisible(true);
							}
						}
						else {
							new ErrorDialog("Du hast nicht genug Truppen um sie aufzur�sten.\n\nAber vielleicht gibst du die Waffen einfach ein paar Skags...\nOder r�stest die Truppen deiner Gegner auf...").setVisible(true);
						}
					}
				}
			}
		}
		if (commandExecuted) {
			selectedField.setCommand(null);
			game.getTurnExecutionManager().commit();
		}
		update();
	}
	
	public void setSelectedField(Field field) {
		selectedField = field;
		updateField();
	}
	public void setFieldSelectionSucessfull(boolean fieldSelectionSucessfull) {
		this.fieldSelectionSucessfull = fieldSelectionSucessfull;
	}
}