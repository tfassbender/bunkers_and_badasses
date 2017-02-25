package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Command;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.miginfocom.swing.MigLayout;

public class TurnExecutionFrame extends JFrame {
	
	private static final long serialVersionUID = 245242421914033099L;
	
	private JPanel contentPane;
	
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	private PlayerOrderPanel orderPanel;
	
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldTargetModel = new DefaultListModel<Field>();
	private ListModel<Building> buildingModel = new DefaultListModel<Building>();
	private ListModel<FieldCommand> fieldCommandModel = new DefaultListModel<FieldCommand>();
	
	private JTextField txtFeld_1;
	private JTextField txtBefehl;
	private JTextField txtTruppenn;
	private JTextField txtTruppenb;
	private JTextField txtGebude;
	private JTextField txtVerluste;
	
	private boolean fieldOverview = false;
	private JPanel panel_board_capture;
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
		panel.setLayout(new MigLayout("", "[650px,grow][:550px:800px,grow]", "[350px,grow][:400px:400px,grow]"));
		
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
		panel_side_bar.setLayout(new MigLayout("", "[250px,grow][350px,grow]", "[300px,grow][:100px:100px,grow][:100px:100px,grow][:200px:300px,grow]"));
		
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

		fieldPanel = new FieldDescriptionPanel("Feld Übersicht", true);
		panel_side_bar.add(fieldPanel, "cell 1 0,grow");
		
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
		panel_side_bar.add(resourcePanel, "cell 1 1 1 2,grow");

		orderPanel = new PlayerOrderPanel();
		panel_side_bar.add(orderPanel, "cell 1 3,grow");
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel.add(panel_low_bar, "cell 0 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel_execution = new JPanel();
		panel_execution.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_execution.setBackground(Color.GRAY);
		panel_low_bar.add(panel_execution, "cell 0 0,grow");
		panel_execution.setLayout(new MigLayout("", "[300px,grow][200px,grow][100px,grow]", "[][5px,grow][100px:n,grow]"));
		
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
		
		JScrollPane scrollPane_target_field = new JScrollPane();
		panel_command_2.add(scrollPane_target_field, "cell 0 2,grow");
		
		JList<Field> list_target_field = new JList<Field>(fieldTargetModel);
		list_target_field.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_target_field.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_target_field.setBackground(Color.LIGHT_GRAY);
		scrollPane_target_field.setViewportView(list_target_field);
		
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
		txtVerluste.setHorizontalAlignment(SwingConstants.CENTER);
		txtVerluste.setEditable(false);
		txtVerluste.setBackground(Color.LIGHT_GRAY);
		txtVerluste.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_4.add(txtVerluste, "cell 2 2,growx");
		txtVerluste.setColumns(10);
		
		JPanel panel_command_6 = new JPanel();
		panel_command_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_2.add(panel_command_6, "cell 0 4,grow");
		panel_command_6.setBackground(Color.GRAY);
		panel_command_6.setLayout(new MigLayout("", "[grow][grow]", "[][5px][][]"));
		
		JLabel lblResourcenGewinnung = new JLabel("Resourcen Gewinnung:");
		lblResourcenGewinnung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(lblResourcenGewinnung, "cell 0 0 2 1,alignx center");
		
		JRadioButton rdbtnCredits = new JRadioButton("Credits");
		rdbtnCredits.setBackground(Color.GRAY);
		rdbtnCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(rdbtnCredits, "cell 0 2,alignx center");
		
		JRadioButton rdbtnMunition = new JRadioButton("Munition");
		rdbtnMunition.setBackground(Color.GRAY);
		rdbtnMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(rdbtnMunition, "cell 1 2,alignx center");
		
		JRadioButton rdbtnEridium = new JRadioButton("Eridium");
		rdbtnEridium.setBackground(Color.GRAY);
		rdbtnEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(rdbtnEridium, "cell 0 3 2 1,alignx center");
		
		JPanel panel_command_row_3 = new JPanel();
		panel_command_row_3.setBackground(Color.GRAY);
		panel_execution.add(panel_command_row_3, "cell 2 2,grow");
		panel_command_row_3.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px,grow][100px:n,grow]"));
		
		JPanel panel_command_5 = new JPanel();
		panel_command_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_3.add(panel_command_5, "cell 0 0,grow");
		panel_command_5.setBackground(Color.GRAY);
		panel_command_5.setLayout(new MigLayout("", "[grow][grow][][][grow]", "[][5px][][][5px][][grow]"));
		
		JLabel lblAufbau = new JLabel("Aufbau:");
		lblAufbau.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblAufbau, "cell 0 0 5 1,alignx center");
		
		JRadioButton rdbtnAufbauen = new JRadioButton("Aufbauen");
		rdbtnAufbauen.setBackground(Color.GRAY);
		rdbtnAufbauen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(rdbtnAufbauen, "cell 1 2 2 1");
		
		JRadioButton rdbtnAufrsten = new JRadioButton("Aufr\u00FCsten");
		rdbtnAufrsten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAufrsten.setBackground(Color.GRAY);
		panel_command_5.add(rdbtnAufrsten, "cell 3 2");
		
		JRadioButton rdbtnAbreien = new JRadioButton("Abrei\u00DFen");
		rdbtnAbreien.setBackground(Color.GRAY);
		rdbtnAbreien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(rdbtnAbreien, "cell 1 3 3 1,alignx center");
		
		JLabel lblGebude_2 = new JLabel("Geb\u00E4ude:");
		lblGebude_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblGebude_2, "cell 0 5 2 1");
		
		JScrollPane scrollPane_building = new JScrollPane();
		panel_command_5.add(scrollPane_building, "cell 0 6 5 1,grow");
		
		JList<Building> list_building = new JList<Building>(buildingModel);
		list_building.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_building.setBackground(Color.LIGHT_GRAY);
		list_building.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_building.setViewportView(list_building);
		
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
	
	private class FieldCommand {
		
		private Field field;
		private Command command;
		
		public FieldCommand(Field field, Command command) {
			this.field = field;
			this.command = command;
		}
		
		@Override
		public String toString() {
			return field.toString() + command.toString();
		}
		
		public Field getField() {
			return field;
		}
		public Command getCommand() {
			return command;
		}
	}
}