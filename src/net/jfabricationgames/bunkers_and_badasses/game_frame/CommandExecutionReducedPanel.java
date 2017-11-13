package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.miginfocom.swing.MigLayout;

public class CommandExecutionReducedPanel extends CommandExecutionPanel {

	private static final long serialVersionUID = 2751862162221743375L;

	public CommandExecutionReducedPanel(TurnExecutionFrame turnExecutionFrame, Game gameRef) {
		super();
		this.turnExecutionFrame = turnExecutionFrame;
		this.game = gameRef;
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.GRAY);
		setLayout(new MigLayout("", "[175px:250px,grow][175px:175px,grow][500px:500px,grow]", "[][5px,grow][100px:n,grow]"));
		
		JLabel lblBefehlAusfhren = new JLabel("Befehl Ausf\u00FChren:");
		lblBefehlAusfhren.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblBefehlAusfhren, "cell 0 0 3 1,alignx center");
		
		JPanel panel_command_row_1 = new JPanel();
		panel_command_row_1.setBackground(Color.GRAY);
		add(panel_command_row_1, "cell 0 2,grow");
		panel_command_row_1.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
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
				turnExecutionFrame.showOtherBoardView();
			}
		});
		panel_command_1.add(btnbersicht, "cell 0 8 2 1,alignx center");
		btnbersicht.setBackground(Color.GRAY);
		
		btnBefehlAusfhren = new JButton("Befehl Ausf\u00FChren");
		btnBefehlAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executeCommand();
			}
		});
		btnBefehlAusfhren.setEnabled(false);
		btnBefehlAusfhren.setBackground(Color.GRAY);
		
		btnAusfhrungBeenden = new JButton("Ausf\u00FChrung Beenden");
		btnAusfhrungBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (game.getGameState().equals(GameState.ACT) && game.getPlayerOrder().isPlayersTurn(game.getLocalUser())) {
					ConfirmDialog confirm = new ConfirmDialog("Ausführung wirklich beenden?", turnExecutionFrame, 0);
					confirm.setLocationRelativeTo(turnExecutionFrame);
					confirm.setVisible(true);
				}
				else {
					btnAusfhrungBeenden.setEnabled(false);
				}
			}
		});
		btnAusfhrungBeenden.setEnabled(false);
		btnAusfhrungBeenden.setBackground(Color.GRAY);
		
		JPanel panel_command_row_2 = new JPanel();
		panel_command_row_2.setBackground(Color.GRAY);
		add(panel_command_row_2, "cell 1 2,grow");
		panel_command_row_2.setLayout(new MigLayout("", "[grow]", "[grow][3px:n:3px][grow]"));
		
		JPanel panel_command_3 = new JPanel();
		panel_command_row_2.add(panel_command_3, "cell 0 0,grow");
		panel_command_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_3.setBackground(Color.GRAY);
		panel_command_3.setLayout(new MigLayout("", "[grow][][40px:50px][grow]", "[][5px][][][]"));
		
		JLabel lblMarschBefehl = new JLabel("Marsch / Rekrutierung:");
		lblMarschBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblMarschBefehl, "cell 1 0 2 1,alignx center");
		
		JLabel lblTruppennormal_1 = new JLabel("Truppen (Normal):");
		lblTruppennormal_1.setToolTipText("<html>\r\nMarschbefehl:<br>\r\nNormale Truppen die bei diesem <br>\r\nMarsch bewegt werden sollen.<br>\r\n<br>\r\nRekrutierungsbefehl:<br>\r\nNormale Truppen die neu Rekrutiert<br>\r\nwerden sollen.\r\n</html>");
		lblTruppennormal_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblTruppennormal_1, "cell 1 2");
		
		spinnerNormalTroops = new JSpinner();
		spinnerNormalTroops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateKosts();
			}
		});
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
		spinnerBadassTroops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateKosts();
			}
		});
		spinnerBadassTroops.setToolTipText("<html>\r\nMarschbefehl:<br>\r\nBadasses die bei diesem Marsch<br>\r\nbewegt werden sollen.<br>\r\n<br>\r\nRekrutierungsbefehl:<br>\r\nTruppen die von normalen Truppen<br>\r\nzu Badasses aufger\u00FCstet werden sollen.\r\n</html>");
		spinnerBadassTroops.setEnabled(false);
		spinnerBadassTroops.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spinnerBadassTroops.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinnerBadassTroops, "cell 2 3,growx");
		
		JLabel lblAufrstungen = new JLabel("Aufr\u00FCstungen:");
		lblAufrstungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_3.add(lblAufrstungen, "cell 1 4");
		
		spinnerAufrstungen = new JSpinner();
		spinnerAufrstungen.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateKosts();
			}
		});
		spinnerAufrstungen.setEnabled(false);
		spinnerAufrstungen.setBackground(Color.LIGHT_GRAY);
		panel_command_3.add(spinnerAufrstungen, "cell 2 4,growx");
		
		JPanel panel_command_6 = new JPanel();
		panel_command_row_2.add(panel_command_6, "cell 0 2,grow");
		panel_command_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_6.setBackground(Color.GRAY);
		panel_command_6.setLayout(new MigLayout("", "[grow][grow]", "[][5px][][]"));
		
		JLabel lblResourcenGewinnung = new JLabel("Resourcen Gewinnung:");
		lblResourcenGewinnung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_6.add(lblResourcenGewinnung, "cell 0 0 2 1,alignx center");
		
		JPanel panel_command_row_3 = new JPanel();
		panel_command_row_3.setBackground(Color.GRAY);
		add(panel_command_row_3, "cell 2 2,grow");
		panel_command_row_3.setLayout(new MigLayout("", "[100px:150px,grow][100px:150px,grow]", "[grow][3px:n:3px][grow]"));
		
		JPanel panel_command_2 = new JPanel();
		panel_command_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_row_3.add(panel_command_2, "cell 0 0,grow");
		panel_command_2.setBackground(Color.GRAY);
		panel_command_2.setLayout(new MigLayout("", "[grow]", "[][5px][]"));
		
		JLabel lblZiel = new JLabel("Ziel:");
		panel_command_2.add(lblZiel, "cell 0 0,alignx center");
		lblZiel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JScrollPane scrollPane_target_field = new JScrollPane();
		panel_command_2.add(scrollPane_target_field, "cell 0 2,grow");
		
		list_target_field = new JList<Field>(fieldTargetModel);
		list_target_field.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateSpinners();
			}
		});
		list_target_field.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_target_field.setBackground(Color.LIGHT_GRAY);
		scrollPane_target_field.setViewportView(list_target_field);
		
		JPanel panel_command_5 = new JPanel();
		panel_command_row_3.add(panel_command_5, "cell 1 0,grow");
		panel_command_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_5.setBackground(Color.GRAY);
		panel_command_5.setLayout(new MigLayout("", "[grow][][grow][][][grow]", "[][5px][][][5px][][][]"));
		
		JLabel lblAufbau = new JLabel("Aufbau:");
		lblAufbau.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblAufbau, "cell 0 0 6 1,alignx center");
		
		JLabel lblGebude_2 = new JLabel("Geb\u00E4ude:");
		lblGebude_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblGebude_2, "cell 0 5 3 1");
		
		JLabel lblKosten = new JLabel("Kosten:");
		lblKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(lblKosten, "cell 0 7 2 1");
		
		JPanel panel_command_7 = new JPanel();
		panel_command_row_3.add(panel_command_7, "cell 0 2 2 1,grow");
		panel_command_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_command_7.setBackground(Color.GRAY);
		panel_command_7.setLayout(new MigLayout("", "[grow][][5px:n:5px][][grow]", "[grow]"));
		panel_command_7.add(btnBefehlAusfhren, "cell 1 0,alignx center");
		panel_command_7.add(btnAusfhrungBeenden, "cell 3 0,alignx center");
		
		ButtonGroup group_building = new ButtonGroup();
		
		rdbtnAufbauen = new JRadioButton("Aufbauen");
		rdbtnAufbauen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateKosts();
			}
		});
		rdbtnAufbauen.setSelected(true);
		rdbtnAufbauen.setEnabled(false);
		rdbtnAufbauen.setBackground(Color.GRAY);
		rdbtnAufbauen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		group_building.add(rdbtnAufbauen);
		
		rdbtnAufrsten = new JRadioButton("Aufr\u00FCsten");
		rdbtnAufrsten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateKosts();
			}
		});
		rdbtnAufrsten.setEnabled(false);
		rdbtnAufrsten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAufrsten.setBackground(Color.GRAY);
		group_building.add(rdbtnAufrsten);
		
		rdbtnAbreien = new JRadioButton("Abrei\u00DFen");
		rdbtnAbreien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateKosts();
			}
		});
		rdbtnAbreien.setEnabled(false);
		rdbtnAbreien.setBackground(Color.GRAY);
		rdbtnAbreien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		group_building.add(rdbtnAbreien);
		
		panel_command_5.add(rdbtnAufbauen, "cell 1 2 3 1");
		panel_command_5.add(rdbtnAufrsten, "cell 4 2");
		panel_command_5.add(rdbtnAbreien, "cell 1 3 4 1,alignx center");
		
		list_building = new JComboBox<Building>(buildingModel);
		list_building.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateKosts();
			}
		});
		list_building.setBackground(Color.LIGHT_GRAY);
		list_building.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_5.add(list_building, "cell 0 6 6 1,grow");
		
		txtKosten = new JTextField();
		txtKosten.setBackground(Color.LIGHT_GRAY);
		txtKosten.setEditable(false);
		txtKosten.setColumns(10);
		panel_command_5.add(txtKosten, "cell 2 7 4 1,growx");
		
		ButtonGroup resource_group = new ButtonGroup();
		
		rdbtnCredits = new JRadioButton("Credits");
		rdbtnCredits.setSelected(true);
		rdbtnCredits.setEnabled(false);
		rdbtnCredits.setBackground(Color.GRAY);
		rdbtnCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resource_group.add(rdbtnCredits);
		
		rdbtnMunition = new JRadioButton("Munition");
		rdbtnMunition.setEnabled(false);
		rdbtnMunition.setBackground(Color.GRAY);
		rdbtnMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resource_group.add(rdbtnMunition);
		
		rdbtnEridium = new JRadioButton("Eridium");
		rdbtnEridium.setEnabled(false);
		rdbtnEridium.setBackground(Color.GRAY);
		rdbtnEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resource_group.add(rdbtnEridium);
		
		panel_command_6.add(rdbtnCredits, "cell 0 2,alignx center");
		panel_command_6.add(rdbtnMunition, "cell 1 2,alignx center");
		panel_command_6.add(rdbtnEridium, "cell 0 3 2 1,alignx center");
	}
}