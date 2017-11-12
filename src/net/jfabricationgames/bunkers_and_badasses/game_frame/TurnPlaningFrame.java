package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;
import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;
import com.jfabricationgames.toolbox.properties.event.PropertiesWindowListener;

import net.jfabricationgames.bunkers_and_badasses.error.CommandException;
import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game.UserPlanManager;
import net.jfabricationgames.bunkers_and_badasses.game.UserResource;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_command.BuildCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.CollectCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.jfabricationgames.bunkers_and_badasses.game_command.DefendCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.MarchCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RaidCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RecruitCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.RetreatCommand;
import net.jfabricationgames.bunkers_and_badasses.game_command.SupportCommand;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;

public class TurnPlaningFrame extends JFrame implements BoardPanelListener, ConfirmDialogListener {
	
	private static final long serialVersionUID = -4935074259881358736L;
	
	private final JPanel contentPanel = new JPanel();
	
	private Game game;
	
	private Field selectedField;
	
	private AdditionalCommandFrame additionalCommandFrame;
	
	private ResourceInfoPanel resourcePanel;
	private ResourceInfoPanel resourcePanel_2;
	private FieldDescriptionPanel fieldPanel;
	private PlayerOrderPanel orderPanel;
	private PlayerOrderPanel orderPanel_2;
	private BoardPanel boardPanel;
	
	private JTextField txtField;
	private JTextField txtCurrcommand;
	private JTextField txtKosts;
	private JTextField txtberflle;
	private JTextField txtRckzge;
	private JTextField txtMrsche;
	private JTextField txtAufbauten;
	private JTextField txtRekrutierungen;
	private JTextField txtResourcen;

	private JTextField txtField_2;
	private JTextField txtCurrcommand_2;
	private JTextField txtKosts_2;
	private JTextField txtberflle_2;
	private JTextField txtRckzge_2;
	private JTextField txtMrsche_2;
	private JTextField txtAufbauten_2;
	private JTextField txtRekrutierungen_2;
	private JTextField txtResourcen_2;
	
	private Command commandRaid;
	private Command commandRetreat;
	private Command commandMarch;
	private Command commandBuild;
	private Command commandRecruit;
	private Command commandMine;
	private Command commandSupport;
	private Command commandDefend;
	
	private DefaultListModel<Field> fieldNoCommandListModel = new DefaultListModel<Field>();
	private DefaultListModel<FieldCommand> fieldCommandListModel = new DefaultListModel<FieldCommand>();
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	private DefaultListModel<FieldBuilding> buildingsAllListModel = new DefaultListModel<FieldBuilding>();
	
	private DefaultComboBoxModel<Command> commandBoxModel = new DefaultComboBoxModel<Command>();
	private JButton btnLschen;
	private JButton btnHinzufgen;
	private JTextField txtSupport;
	private JTextField txtVerteidigung;
	private JButton btnAlleBefehleBesttigen;
	private JButton btnZustzlicheBefehle;
	private JComboBox<Command> comboBox;
	
	private JButton btnLschen_2;
	private JButton btnHinzufgen_2;
	private JTextField txtSupport_2;
	private JTextField txtVerteidigung_2;
	private JButton btnAlleBefehleBesttigen_2;
	private JButton btnZustzlicheBefehle_2;
	private JComboBox<Command> comboBox_2;
	private BoardPanel boardPanel_2;
	
	private static final String REDUCED_INFO_VIEW = "reduced_info";
	private static final String COMPLETE_INFO_VIEW = "complete_info";
	private JPanel panel_game_type;
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
	public TurnPlaningFrame(Game game) {
		addWindowListener(new PropertiesWindowListener(propsFile, PropertiesWindowListener.WINDOW_CLOSING_EVENT));
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setTitle("Zug Planung - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnPlaningFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1300, 800);
		setMinimumSize(new Dimension(1000, 600));
		
		propsFile.alignWindow();
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		panel_game_type = new JPanel();
		contentPanel.add(panel_game_type, "cell 0 0,grow");
		panel_game_type.setLayout(new CardLayout(0, 0));
		
		JPanel panel_reduced_info_set = new JPanel();
		panel_reduced_info_set.setBackground(Color.GRAY);
		panel_game_type.add(panel_reduced_info_set, REDUCED_INFO_VIEW);
		panel_reduced_info_set.setLayout(new MigLayout("", "[800px,grow][:450px:450px,grow]", "[500px,grow][:250px:260px,grow]"));
		
		boardPanel_2 = new BoardPanel();
		boardPanel_2.addBoardPanelListener(this);
		panel_reduced_info_set.add(boardPanel_2, "cell 0 0,grow");
		
		JPanel panel_side_bar_2 = new JPanel();
		panel_side_bar_2.setBackground(Color.GRAY);
		panel_reduced_info_set.add(panel_side_bar_2, "cell 1 0 1 2,grow");
		panel_side_bar_2.setLayout(new MigLayout("", "[:250px:250px,grow][:250px:250px]", "[300px,grow][:100px:300px,grow]"));
		
		JPanel panel_fields_no_command_2 = new JPanel();
		panel_fields_no_command_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_no_command_2.setBackground(Color.GRAY);
		panel_side_bar_2.add(panel_fields_no_command_2, "cell 0 0,grow");
		panel_fields_no_command_2.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblGebieteOhneBefehl_2 = new JLabel("Gebiete ohne Befehl:");
		lblGebieteOhneBefehl_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_no_command_2.add(lblGebieteOhneBefehl_2, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_no_command_2 = new JScrollPane();
		panel_fields_no_command_2.add(scrollPane_fields_no_command_2, "cell 0 2,grow");
		
		JList<Field> list_fields_no_command_2 = new JList<Field>(fieldNoCommandListModel);
		list_fields_no_command_2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedField = list_fields_no_command_2.getSelectedValue();
				updateField();
			}
		});
		list_fields_no_command_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_no_command_2.setToolTipText("<html>\r\nDiese Gebiete haben noch <br>\r\nkeinen Befehl erhalten.\r\n</html>");
		list_fields_no_command_2.setBackground(Color.LIGHT_GRAY);
		list_fields_no_command_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_fields_no_command_2.setViewportView(list_fields_no_command_2);
		
		JPanel panel_fields_command_2 = new JPanel();
		panel_side_bar_2.add(panel_fields_command_2, "cell 1 0,grow");
		panel_fields_command_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_command_2.setBackground(Color.GRAY);
		panel_fields_command_2.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblGebieteMitBefehl_2 = new JLabel("Gebiete mit Befehl:");
		lblGebieteMitBefehl_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_command_2.add(lblGebieteMitBefehl_2, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_command_2 = new JScrollPane();
		panel_fields_command_2.add(scrollPane_fields_command_2, "cell 0 2,grow");
		
		JList<FieldCommand> list_fields_command_2 = new JList<FieldCommand>(fieldCommandListModel);
		list_fields_command_2.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedField = list_fields_command_2.getSelectedValue().getField();
				updateField();
			}
		});
		list_fields_command_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_command_2.setToolTipText("<html>\r\nDiese Gebiete haben bereits <br>\r\neinen Befehl erhalten\r\n</html>");
		list_fields_command_2.setBackground(Color.LIGHT_GRAY);
		list_fields_command_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_fields_command_2.setViewportView(list_fields_command_2);
		
		resourcePanel_2 = new ResourceInfoPanel();
		panel_side_bar_2.add(resourcePanel_2, "cell 0 1 2 1,grow");
		
		JPanel panel_low_bar_2 = new JPanel();
		panel_low_bar_2.setBackground(Color.GRAY);
		panel_reduced_info_set.add(panel_low_bar_2, "cell 0 1,grow");
		panel_low_bar_2.setLayout(new MigLayout("", "[:400px:500px,grow][:150px:200px,grow][:200px:250px,grow][grow]", "[grow]"));
		
		JPanel panel_command_2 = new JPanel();
		panel_command_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_command_2.setBackground(Color.GRAY);
		panel_low_bar_2.add(panel_command_2, "cell 0 0,grow");
		panel_command_2.setLayout(new MigLayout("", "[][grow][][fill]", "[][5px,grow][][::35px,grow][::35px,grow][::35px,grow][::35px,grow][5px,grow][grow]"));
		
		JLabel lblBefehle_2 = new JLabel("Befehle:");
		lblBefehle_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_command_2.add(lblBefehle_2, "cell 0 0 4 1,alignx center");
		
		JButton btnMehrEinblenden = new JButton("Mehr Einblenden");
		btnMehrEinblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showExtendedInfo();
			}
		});
		btnMehrEinblenden.setBackground(Color.GRAY);
		panel_command_2.add(btnMehrEinblenden, "cell 3 2,alignx center");
		
		JLabel lblFeld_2 = new JLabel("Feld:");
		lblFeld_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(lblFeld_2, "cell 0 3,alignx trailing");
		
		JButton btnbersicht_2 = new JButton("\u00DCbersicht");
		btnbersicht_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel_2.showOtherView();
			}
		});
		
		txtField_2 = new JTextField();
		txtField_2.setEditable(false);
		txtField_2.setBackground(Color.LIGHT_GRAY);
		txtField_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(txtField_2, "cell 1 3 2 1,growx");
		txtField_2.setColumns(10);
		btnbersicht_2.setToolTipText("<html>\r\nZwichen einer \u00DCbersicht \u00FCber das <br>\r\ngesammte Spielfeld und einer kleineren <br>\r\ndetailierteren Sicht wechseln\r\n</html>");
		btnbersicht_2.setBackground(Color.GRAY);
		panel_command_2.add(btnbersicht_2, "cell 3 3");
		
		JLabel lblBefehl_2 = new JLabel("Aktueller Befehl:");
		lblBefehl_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(lblBefehl_2, "cell 0 4,alignx trailing");
		
		txtCurrcommand_2 = new JTextField();
		txtCurrcommand_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCurrcommand_2.setBackground(Color.LIGHT_GRAY);
		txtCurrcommand_2.setEditable(false);
		panel_command_2.add(txtCurrcommand_2, "cell 1 4 2 1,growx");
		txtCurrcommand_2.setColumns(10);
		
		btnLschen_2 = new JButton("L\u00F6schen");
		btnLschen_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteCommand();
			}
		});
		btnLschen_2.setToolTipText("<html>\r\nDen bestehenden Befehl f\u00FCr <br>\r\ndas ausgew\u00E4hlte Feld entfernen\r\n</html>");
		btnLschen_2.setBackground(Color.GRAY);
		panel_command_2.add(btnLschen_2, "cell 3 4");
		
		JLabel lblNeuerBefehl_2 = new JLabel("Neuer Befehl:");
		lblNeuerBefehl_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(lblNeuerBefehl_2, "cell 0 5,alignx trailing");
		
		comboBox_2 = new JComboBox<Command>(commandBoxModel);
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateField();
			}
		});
		comboBox_2.setBackground(Color.GRAY);
		comboBox_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(comboBox_2, "cell 1 5 2 1,growx");
		
		btnHinzufgen_2 = new JButton("Hinzuf\u00FCgen");
		btnHinzufgen_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCommand((Command) comboBox_2.getSelectedItem());
			}
		});
		btnHinzufgen_2.setToolTipText("<html>\r\nDen ausgew\u00E4hlten Befehl dem <br>\r\nausgew\u00E4hlten Feld zuweisen\r\n</html>");
		btnHinzufgen_2.setBackground(Color.GRAY);
		panel_command_2.add(btnHinzufgen_2, "cell 3 5");
		
		JLabel lblKosten_2 = new JLabel("Kosten:");
		lblKosten_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(lblKosten_2, "cell 0 6,alignx trailing");
		
		txtKosts_2 = new JTextField();
		txtKosts_2.setToolTipText("<html>\r\nDie Kosten die ein Befehl auf <br>\r\ndem ausgew\u00E4hlten Feld verursacht\r\n</html>");
		txtKosts_2.setEditable(false);
		txtKosts_2.setBackground(Color.LIGHT_GRAY);
		txtKosts_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command_2.add(txtKosts_2, "cell 1 6 3 1,growx");
		txtKosts_2.setColumns(10);
		
		btnAlleBefehleBesttigen_2 = new JButton("Alle Befehle Best\u00E4tigen");
		btnAlleBefehleBesttigen_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfirmDialog confirmDialog = new ConfirmDialog("Zug-Planung wirklich beenden?", TurnPlaningFrame.this, 0);
				confirmDialog.setLocationRelativeTo(TurnPlaningFrame.this);
				confirmDialog.setVisible(true);
			}
		});
		
		btnZustzlicheBefehle_2 = new JButton("Zus\u00E4tzliche Befehle Kaufen");
		btnZustzlicheBefehle_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				additionalCommandFrame.setVisible(true);
				additionalCommandFrame.requestFocus();
				additionalCommandFrame.update();
			}
		});
		btnZustzlicheBefehle_2.setBackground(Color.GRAY);
		panel_command_2.add(btnZustzlicheBefehle_2, "cell 0 8 2 1");
		btnAlleBefehleBesttigen_2.setToolTipText("<html>\r\nAlle aktuellen Befehle best\u00E4tigen<br>\r\nund die Planugsphase beenden.\r\n</html>");
		btnAlleBefehleBesttigen_2.setBackground(Color.GRAY);
		panel_command_2.add(btnAlleBefehleBesttigen_2, "cell 2 8 2 1,alignx right");
		
		JPanel panel_commands_left_2 = new JPanel();
		panel_commands_left_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_commands_left_2.setBackground(Color.GRAY);
		panel_low_bar_2.add(panel_commands_left_2, "cell 1 0,grow");
		panel_commands_left_2.setLayout(new MigLayout("", "[grow][][25px:40px:40px,grow][grow]", "[][5px][][][][][][][][]"));
		
		JLabel lblbrigeBefehle_2 = new JLabel("\u00DCbrige Befehle:");
		lblbrigeBefehle_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_commands_left_2.add(lblbrigeBefehle_2, "cell 0 0 4 1,alignx center");
		
		JLabel lblberflle_2 = new JLabel("\u00DCberf\u00E4lle:");
		lblberflle_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblberflle_2, "cell 1 2,alignx trailing");
		
		txtberflle_2 = new JTextField();
		txtberflle_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtberflle_2.setBackground(Color.LIGHT_GRAY);
		txtberflle_2.setEditable(false);
		txtberflle_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtberflle_2, "cell 2 2,growx");
		txtberflle_2.setColumns(10);
		
		JLabel lblRckzge_2 = new JLabel("R\u00FCckz\u00FCge:");
		lblRckzge_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblRckzge_2, "cell 1 3,alignx trailing");
		
		txtRckzge_2 = new JTextField();
		txtRckzge_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtRckzge_2.setBackground(Color.LIGHT_GRAY);
		txtRckzge_2.setEditable(false);
		txtRckzge_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtRckzge_2, "cell 2 3,growx");
		txtRckzge_2.setColumns(10);
		
		JLabel lblMrsche_2 = new JLabel("M\u00E4rsche:");
		lblMrsche_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblMrsche_2, "cell 1 4,alignx trailing");
		
		txtMrsche_2 = new JTextField();
		txtMrsche_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtMrsche_2.setBackground(Color.LIGHT_GRAY);
		txtMrsche_2.setEditable(false);
		txtMrsche_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtMrsche_2, "cell 2 4,growx");
		txtMrsche_2.setColumns(10);
		
		JLabel lblAufbauten_2 = new JLabel("Aufbauten:");
		lblAufbauten_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblAufbauten_2, "cell 1 5,alignx trailing");
		
		txtAufbauten_2 = new JTextField();
		txtAufbauten_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtAufbauten_2.setBackground(Color.LIGHT_GRAY);
		txtAufbauten_2.setEditable(false);
		txtAufbauten_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtAufbauten_2, "cell 2 5,growx");
		txtAufbauten_2.setColumns(10);
		
		JLabel lblRekrutierungen_2 = new JLabel("Rekrutierungen:");
		lblRekrutierungen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblRekrutierungen_2, "cell 1 6,alignx trailing");
		
		txtRekrutierungen_2 = new JTextField();
		txtRekrutierungen_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtRekrutierungen_2.setBackground(Color.LIGHT_GRAY);
		txtRekrutierungen_2.setEditable(false);
		txtRekrutierungen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtRekrutierungen_2, "cell 2 6,growx");
		txtRekrutierungen_2.setColumns(10);
		
		JLabel lblResourcen_2 = new JLabel("Resourcen:");
		lblResourcen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblResourcen_2, "cell 1 7,alignx trailing");
		
		txtResourcen_2 = new JTextField();
		txtResourcen_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtResourcen_2.setBackground(Color.LIGHT_GRAY);
		txtResourcen_2.setEditable(false);
		txtResourcen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtResourcen_2, "cell 2 7,growx");
		txtResourcen_2.setColumns(10);
		
		JLabel lblUntersttzungen_2 = new JLabel("Unterst\u00FCtzungen:");
		lblUntersttzungen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblUntersttzungen_2, "cell 1 8,alignx trailing");
		
		txtSupport_2 = new JTextField();
		txtSupport_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtSupport_2.setEditable(false);
		txtSupport_2.setBackground(Color.LIGHT_GRAY);
		txtSupport_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(txtSupport_2, "cell 2 8,growx");
		txtSupport_2.setColumns(10);
		
		JLabel lblVerteidigungen_2 = new JLabel("Verteidigungen:");
		lblVerteidigungen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left_2.add(lblVerteidigungen_2, "cell 1 9,alignx trailing");
		
		txtVerteidigung_2 = new JTextField();
		txtVerteidigung_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtVerteidigung_2.setEditable(false);
		txtVerteidigung_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtVerteidigung_2.setBackground(Color.LIGHT_GRAY);
		panel_commands_left_2.add(txtVerteidigung_2, "cell 2 9,growx");
		txtVerteidigung_2.setColumns(10);
		
		orderPanel_2 = new PlayerOrderPanel();
		panel_low_bar_2.add(orderPanel_2, "cell 2 0,grow");
		
		ImagePanel panel_image_2 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/claptrap_1.png"));
		panel_image_2.setBackground(Color.GRAY);
		panel_image_2.setToolTipText("Claptrap: Interplanetarer Ninja Assasine");
		panel_image_2.setRemoveIfToSmall(true);
		panel_image_2.setAdaptSizeKeepProportion(true);
		panel_image_2.setCentered(true);
		panel_image_2.setImageMinimumSize(new int[] {75, 150});
		panel_low_bar_2.add(panel_image_2, "cell 3 0,grow");
		
		JPanel panel_complete_info_set = new JPanel();
		panel_game_type.add(panel_complete_info_set, COMPLETE_INFO_VIEW);
		panel_complete_info_set.setBackground(Color.GRAY);
		panel_complete_info_set.setLayout(new MigLayout("", "[450px,grow][:600px:800px,grow]", "[300px,grow][:400px:400px,grow]"));
		
		boardPanel = new BoardPanel();
		boardPanel.addBoardPanelListener(this);
		panel_complete_info_set.add(boardPanel, "cell 0 0,grow");
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel_complete_info_set.add(panel_side_bar, "cell 1 0 1 2,grow");
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
		list_fields_no_command.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedField = list_fields_no_command.getSelectedValue();
				updateField();
			}
		});
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
		
		JList<FieldCommand> list_fields_command = new JList<FieldCommand>(fieldCommandListModel);
		list_fields_command.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedField = list_fields_command.getSelectedValue().getField();
				updateField();
			}
		});
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
		list_fields_all.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedField = list_fields_all.getSelectedValue();
				updateField();
			}
		});
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
		list_buildings_player.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedField = list_buildings_player.getSelectedValue().getField();
				updateField();
			}
		});
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
		list_buildings_all.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedField = list_buildings_all.getSelectedValue().getField();
				updateField();
			}
		});
		list_buildings_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_buildings_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_buildings_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_buildings_all.setViewportView(list_buildings_all);

		fieldPanel = new FieldDescriptionPanel("Feld Übersicht", true);
		panel_side_bar.add(fieldPanel, "cell 0 2 2 1,grow");
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel_complete_info_set.add(panel_low_bar, "cell 0 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[350px,grow][75px,grow][100px,grow][50px,grow]", "[grow][100px:n,grow]"));
		
		JPanel panel_command = new JPanel();
		panel_command.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_command.setBackground(Color.GRAY);
		panel_low_bar.add(panel_command, "cell 0 0 2 1,grow");
		panel_command.setLayout(new MigLayout("", "[][grow][][fill]", "[][5px,grow][][::35px,grow][::35px,grow][::35px,grow][::35px,grow][5px,grow][grow]"));
		
		JLabel lblBefehle = new JLabel("Befehle:");
		lblBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_command.add(lblBefehle, "cell 0 0 4 1,alignx center");
		
		JButton btnWenigerEinblenden = new JButton("Weniger Einblenden");
		btnWenigerEinblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showReducedInfo();
			}
		});
		btnWenigerEinblenden.setBackground(Color.GRAY);
		panel_command.add(btnWenigerEinblenden, "cell 3 2");
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblFeld, "cell 0 3,alignx trailing");
		
		txtField = new JTextField();
		txtField.setEditable(false);
		txtField.setBackground(Color.LIGHT_GRAY);
		txtField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(txtField, "cell 1 3 2 1,growx");
		txtField.setColumns(10);
		
		JButton btnbersicht = new JButton("\u00DCbersicht");
		btnbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel.showOtherView();
			}
		});
		btnbersicht.setToolTipText("<html>\r\nZwichen einer \u00DCbersicht \u00FCber das <br>\r\ngesammte Spielfeld und einer kleineren <br>\r\ndetailierteren Sicht wechseln\r\n</html>");
		btnbersicht.setBackground(Color.GRAY);
		panel_command.add(btnbersicht, "cell 3 3");
		
		JLabel lblBefehl = new JLabel("Aktueller Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblBefehl, "cell 0 4,alignx trailing");
		
		txtCurrcommand = new JTextField();
		txtCurrcommand.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCurrcommand.setBackground(Color.LIGHT_GRAY);
		txtCurrcommand.setEditable(false);
		panel_command.add(txtCurrcommand, "cell 1 4 2 1,growx");
		txtCurrcommand.setColumns(10);
		
		btnLschen = new JButton("L\u00F6schen");
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteCommand();
			}
		});
		btnLschen.setToolTipText("<html>\r\nDen bestehenden Befehl f\u00FCr <br>\r\ndas ausgew\u00E4hlte Feld entfernen\r\n</html>");
		btnLschen.setBackground(Color.GRAY);
		panel_command.add(btnLschen, "cell 3 4");
		
		JLabel lblNeuerBefehl = new JLabel("Neuer Befehl:");
		lblNeuerBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblNeuerBefehl, "cell 0 5,alignx trailing");
		
		comboBox = new JComboBox<Command>(commandBoxModel);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateField();
			}
		});
		comboBox.setBackground(Color.GRAY);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(comboBox, "cell 1 5 2 1,growx");
		
		btnHinzufgen = new JButton("Hinzuf\u00FCgen");
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCommand((Command) comboBox.getSelectedItem());
			}
		});
		btnHinzufgen.setToolTipText("<html>\r\nDen ausgew\u00E4hlten Befehl dem <br>\r\nausgew\u00E4hlten Feld zuweisen\r\n</html>");
		btnHinzufgen.setBackground(Color.GRAY);
		panel_command.add(btnHinzufgen, "cell 3 5");
		
		JLabel lblKosten = new JLabel("Kosten:");
		lblKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(lblKosten, "cell 0 6,alignx trailing");
		
		txtKosts = new JTextField();
		txtKosts.setToolTipText("<html>\r\nDie Kosten die ein Befehl auf <br>\r\ndem ausgew\u00E4hlten Feld verursacht\r\n</html>");
		txtKosts.setEditable(false);
		txtKosts.setBackground(Color.LIGHT_GRAY);
		txtKosts.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_command.add(txtKosts, "cell 1 6 3 1,growx");
		txtKosts.setColumns(10);
		
		btnAlleBefehleBesttigen = new JButton("Alle Befehle Best\u00E4tigen");
		btnAlleBefehleBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfirmDialog confirmDialog = new ConfirmDialog("Zug-Planung wirklich beenden?", TurnPlaningFrame.this, 0);
				confirmDialog.setLocationRelativeTo(TurnPlaningFrame.this);
				confirmDialog.setVisible(true);
			}
		});
		
		btnZustzlicheBefehle = new JButton("Zus\u00E4tzliche Befehle Kaufen");
		btnZustzlicheBefehle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				additionalCommandFrame.setVisible(true);
				additionalCommandFrame.requestFocus();
				additionalCommandFrame.update();
			}
		});
		btnZustzlicheBefehle.setBackground(Color.GRAY);
		panel_command.add(btnZustzlicheBefehle, "cell 0 8 2 1");
		btnAlleBefehleBesttigen.setToolTipText("<html>\r\nAlle aktuellen Befehle best\u00E4tigen<br>\r\nund die Planugsphase beenden.\r\n</html>");
		btnAlleBefehleBesttigen.setBackground(Color.GRAY);
		panel_command.add(btnAlleBefehleBesttigen, "cell 2 8 2 1,alignx right");
		
		JPanel panel_commands_left = new JPanel();
		panel_commands_left.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_commands_left.setBackground(Color.GRAY);
		panel_low_bar.add(panel_commands_left, "cell 2 0,grow");
		panel_commands_left.setLayout(new MigLayout("", "[grow][][25px:40px,grow][grow]", "[][5px][][][][][][][][]"));
		
		JLabel lblbrigeBefehle = new JLabel("\u00DCbrige Befehle:");
		lblbrigeBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_commands_left.add(lblbrigeBefehle, "cell 0 0 4 1,alignx center");
		
		JLabel lblberflle = new JLabel("\u00DCberf\u00E4lle:");
		lblberflle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblberflle, "cell 1 2,alignx trailing");
		
		txtberflle = new JTextField();
		txtberflle.setHorizontalAlignment(SwingConstants.CENTER);
		txtberflle.setBackground(Color.LIGHT_GRAY);
		txtberflle.setEditable(false);
		txtberflle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtberflle, "cell 2 2,growx");
		txtberflle.setColumns(10);
		
		JLabel lblRckzge = new JLabel("R\u00FCckz\u00FCge:");
		lblRckzge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblRckzge, "cell 1 3,alignx trailing");
		
		txtRckzge = new JTextField();
		txtRckzge.setHorizontalAlignment(SwingConstants.CENTER);
		txtRckzge.setBackground(Color.LIGHT_GRAY);
		txtRckzge.setEditable(false);
		txtRckzge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtRckzge, "cell 2 3,growx");
		txtRckzge.setColumns(10);
		
		JLabel lblMrsche = new JLabel("M\u00E4rsche:");
		lblMrsche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblMrsche, "cell 1 4,alignx trailing");
		
		txtMrsche = new JTextField();
		txtMrsche.setHorizontalAlignment(SwingConstants.CENTER);
		txtMrsche.setBackground(Color.LIGHT_GRAY);
		txtMrsche.setEditable(false);
		txtMrsche.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtMrsche, "cell 2 4,growx");
		txtMrsche.setColumns(10);
		
		JLabel lblAufbauten = new JLabel("Aufbauten:");
		lblAufbauten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblAufbauten, "cell 1 5,alignx trailing");
		
		txtAufbauten = new JTextField();
		txtAufbauten.setHorizontalAlignment(SwingConstants.CENTER);
		txtAufbauten.setBackground(Color.LIGHT_GRAY);
		txtAufbauten.setEditable(false);
		txtAufbauten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtAufbauten, "cell 2 5,growx");
		txtAufbauten.setColumns(10);
		
		JLabel lblRekrutierungen = new JLabel("Rekrutierungen:");
		lblRekrutierungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblRekrutierungen, "cell 1 6,alignx trailing");
		
		txtRekrutierungen = new JTextField();
		txtRekrutierungen.setHorizontalAlignment(SwingConstants.CENTER);
		txtRekrutierungen.setBackground(Color.LIGHT_GRAY);
		txtRekrutierungen.setEditable(false);
		txtRekrutierungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtRekrutierungen, "cell 2 6,growx");
		txtRekrutierungen.setColumns(10);
		
		JLabel lblResourcen = new JLabel("Resourcen:");
		lblResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblResourcen, "cell 1 7,alignx trailing");
		
		txtResourcen = new JTextField();
		txtResourcen.setHorizontalAlignment(SwingConstants.CENTER);
		txtResourcen.setBackground(Color.LIGHT_GRAY);
		txtResourcen.setEditable(false);
		txtResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtResourcen, "cell 2 7,growx");
		txtResourcen.setColumns(10);
		
		JLabel lblUntersttzungen = new JLabel("Unterst\u00FCtzungen:");
		lblUntersttzungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblUntersttzungen, "cell 1 8,alignx trailing");
		
		txtSupport = new JTextField();
		txtSupport.setHorizontalAlignment(SwingConstants.CENTER);
		txtSupport.setEditable(false);
		txtSupport.setBackground(Color.LIGHT_GRAY);
		txtSupport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(txtSupport, "cell 2 8,growx");
		txtSupport.setColumns(10);
		
		JLabel lblVerteidigungen = new JLabel("Verteidigungen:");
		lblVerteidigungen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_commands_left.add(lblVerteidigungen, "cell 1 9,alignx trailing");
		
		txtVerteidigung = new JTextField();
		txtVerteidigung.setHorizontalAlignment(SwingConstants.CENTER);
		txtVerteidigung.setEditable(false);
		txtVerteidigung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtVerteidigung.setBackground(Color.LIGHT_GRAY);
		panel_commands_left.add(txtVerteidigung, "cell 2 9,growx");
		txtVerteidigung.setColumns(10);
		
		ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/claptrap_1.png"));
		panel_image.setToolTipText("Claptrap: Interplanetarer Ninja Assasine");
		panel_image.setRemoveIfToSmall(true);
		panel_image.setAdaptSizeKeepProportion(true);
		panel_image.setCentered(true);
		panel_image.setImageMinimumSize(new int[] {75, 150});
		panel_image.setBackground(Color.GRAY);
		panel_low_bar.add(panel_image, "cell 3 0,grow");
		
		resourcePanel = new ResourceInfoPanel();
		panel_low_bar.add(resourcePanel, "cell 0 1,grow");

		orderPanel = new PlayerOrderPanel();
		panel_low_bar.add(orderPanel, "cell 1 1 3 1,grow");
		
		showReducedInfo();
		initCommands();
		update();
	}

	@Override
	public void receiveBoardMouseClick(MouseEvent event, boolean doubleClick) {
		selectedField = game.getBoard().getFieldAtMousePosition();
		updateField();
	}
	
	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			game.getPlanManager().commit();
			//game.setState(GameState.ACT);//set by the plan manager
			game.getGameFrame().update();
			disableAll();
		}
	}
	
	/**
	 * Delete the command from the current field
	 */
	private void deleteCommand() {
		if (selectedField != null && selectedField.getAffiliation() != null && selectedField.getAffiliation().equals(game.getLocalUser()) && selectedField.getCommand() != null) {
			game.getPlanManager().deleteCommand(selectedField);
			updateBoard();
			updateResources();
			updateFieldLists();
			updateCommandList();
		}
	}
	/**
	 * Add a command to the current field
	 */
	private void addCommand(Command command) {
		if (selectedField != null && selectedField.getAffiliation() != null &&  selectedField.getAffiliation().equals(game.getLocalUser()) && selectedField.getCommand() == null && command != null) {
			try {
				game.getPlanManager().addCommand(selectedField, command);
				updateBoard();
				updateResources();
				updateFieldLists();
				updateCommandList();
			}
			catch (CommandException ce) {
				ce.printStackTrace();
				new ErrorDialog(ce.getErrorText()).setVisible(true);
			}
		}
	}
	
	/**
	 * Initialize all commands once to not create new every time they are needed.
	 */
	private void initCommands() {
		commandRaid = new RaidCommand();
		commandRetreat = new RetreatCommand();
		commandMarch = new MarchCommand();
		commandBuild = new BuildCommand();
		commandRecruit = new RecruitCommand();
		commandMine = new CollectCommand();
		commandSupport = new SupportCommand();
		commandDefend = new DefendCommand();
		additionalCommandFrame = new AdditionalCommandFrame(this, game, commandRaid, commandRetreat, commandMarch, 
				commandBuild, commandRecruit, commandMine, commandSupport, commandDefend);
	}
	
	public void update() {
		disableAll();
		updateBoard();
		updateCommandList();
		updateField();
		updateResources();
		updatePlayerOrder();
		updateFieldLists();
		updateBuildings();
	}
	
	private void disableAll() {
		btnAlleBefehleBesttigen.setEnabled(false);
		btnZustzlicheBefehle.setEnabled(false);
		btnLschen.setEnabled(false);
		btnHinzufgen.setEnabled(false);
		btnAlleBefehleBesttigen_2.setEnabled(false);
		btnZustzlicheBefehle_2.setEnabled(false);
		btnLschen_2.setEnabled(false);
		btnHinzufgen_2.setEnabled(false);
	}
	
	private void showReducedInfo() {
		CardLayout layout = (CardLayout) panel_game_type.getLayout();
		layout.show(panel_game_type, REDUCED_INFO_VIEW);
	}
	private void showExtendedInfo() {
		CardLayout layout = (CardLayout) panel_game_type.getLayout();
		layout.show(panel_game_type, COMPLETE_INFO_VIEW);
	}
	
	private void updateBoard() {
		boardPanel.updateBoardImage(game.getBoard().displayBoard());
		boardPanel_2.updateBoardImage(game.getBoard().displayBoard());
	}
	
	private void updateField() {
		fieldPanel.updateField(selectedField);
		if (selectedField != null) {
			txtField.setText(selectedField.getName());
			txtField_2.setText(selectedField.getName());
			if (selectedField.getAffiliation() != null && selectedField.getAffiliation().equals(game.getLocalUser())) {
				if (selectedField.getCommand() != null) {
					txtCurrcommand.setText(selectedField.getCommand().getName());
					txtCurrcommand_2.setText(selectedField.getCommand().getName());
				}
				else {
					txtCurrcommand.setText("-----");
					txtCurrcommand_2.setText("-----");
				}
				Command command = (Command) comboBox.getSelectedItem();
				int costsCredits = 0;
				int costsAmmo = 0;
				if (command != null) {
					costsCredits = UserResource.getCreditsForCommand(command, selectedField);
					costsAmmo = UserResource.getAmmoForCommand(command, selectedField);
				}
				txtKosts.setText(costsCredits + " Credits, " + costsAmmo + " Munition");
				if (game.getGameState().equals(GameState.PLAN)) {
					if (selectedField.getCommand() != null) {
						btnLschen.setEnabled(true);
					}
					if (command != null && command.isExecutableOnField(selectedField)) {
						btnHinzufgen.setEnabled(true);						
					}
					else {
						btnHinzufgen.setEnabled(false);
					}
				}
				else {
					btnLschen.setEnabled(false);
					btnHinzufgen.setEnabled(false);
				}
				Command command_2 = (Command) comboBox_2.getSelectedItem();
				if (command_2 != null) {
					costsCredits = UserResource.getCreditsForCommand(command_2, selectedField);
					costsAmmo = UserResource.getAmmoForCommand(command_2, selectedField);
				}
				txtKosts_2.setText(costsCredits + " Credits, " + costsAmmo + " Munition");
				if (game.getGameState().equals(GameState.PLAN)) {
					if (selectedField.getCommand() != null) {
						btnLschen_2.setEnabled(true);
					}
					if (command_2 != null && command_2.isExecutableOnField(selectedField)) {
						btnHinzufgen_2.setEnabled(true);						
					}
					else {
						btnHinzufgen_2.setEnabled(false);
					}
				}
				else {
					btnLschen_2.setEnabled(false);
					btnHinzufgen_2.setEnabled(false);
				}
			}
			else {
				btnLschen.setEnabled(false);
				btnHinzufgen.setEnabled(false);
				txtCurrcommand.setText("");
				txtKosts.setText("");
				btnLschen_2.setEnabled(false);
				btnHinzufgen_2.setEnabled(false);
				txtCurrcommand_2.setText("");
				txtKosts_2.setText("");
			}
		}
		else {
			txtField.setText("");
			txtCurrcommand.setText("");
			txtKosts.setText("");
			btnLschen.setEnabled(false);
			btnHinzufgen.setEnabled(false);
			txtField_2.setText("");
			txtCurrcommand_2.setText("");
			txtKosts_2.setText("");
			btnLschen_2.setEnabled(false);
			btnHinzufgen_2.setEnabled(false);
		}
		if (game.getGameState().equals(GameState.PLAN)) {
			btnAlleBefehleBesttigen.setEnabled(true);
			btnZustzlicheBefehle.setEnabled(true);
			btnAlleBefehleBesttigen_2.setEnabled(true);
			btnZustzlicheBefehle_2.setEnabled(true);
		}
	}
	
	private void updateFieldLists() {
		fieldNoCommandListModel.removeAllElements();
		fieldCommandListModel.removeAllElements();
		fieldAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
			if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
				if (field.getCommand() != null) {
					fieldCommandListModel.addElement(new FieldCommand(field, field.getCommand()));
				}
				else if (field.isCommandPlaceable()) {
					fieldNoCommandListModel.addElement(field);
				}
			}
		}
	}
	
	private void updateBuildings() {
		buildingsPlayerListModel.removeAllElements();
		buildingsAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (!(field.getBuilding() instanceof EmptyBuilding)) {
				buildingsAllListModel.addElement(new FieldBuilding(field, field.getBuilding()));
				if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
					buildingsPlayerListModel.addElement(new FieldBuilding(field, field.getBuilding()));
				}
			}
		}
	}
	
	/**
	 * Update the command drop-down list and the text fields 
	 */
	private void updateCommandList() {
		int raid = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_RAID);
		int retreat = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_RETREAT);
		int march = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_MARCH);
		int build = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_BUILD);
		int recruit = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_RECRUIT);
		int mine = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_COLLECT);
		int support = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_SUPPORT);
		int defend = game.getPlanManager().getCommandsLeft(UserPlanManager.COMMAND_DEFEND);
		int index = comboBox.getSelectedIndex();
		commandBoxModel.removeAllElements();
		txtberflle.setText(Integer.toString(raid));
		txtRckzge.setText(Integer.toString(retreat));
		txtMrsche.setText(Integer.toString(march));
		txtAufbauten.setText(Integer.toString(build));
		txtRekrutierungen.setText(Integer.toString(recruit));
		txtResourcen.setText(Integer.toString(mine));
		txtSupport.setText(Integer.toString(support));
		txtVerteidigung.setText(Integer.toString(defend));
		txtberflle_2.setText(Integer.toString(raid));
		txtRckzge_2.setText(Integer.toString(retreat));
		txtMrsche_2.setText(Integer.toString(march));
		txtAufbauten_2.setText(Integer.toString(build));
		txtRekrutierungen_2.setText(Integer.toString(recruit));
		txtResourcen_2.setText(Integer.toString(mine));
		txtSupport_2.setText(Integer.toString(support));
		txtVerteidigung_2.setText(Integer.toString(defend));
		if (raid > 0) {
			commandBoxModel.addElement(commandRaid);
		}
		if (retreat > 0) {
			commandBoxModel.addElement(commandRetreat);
		}
		if (march > 0) {
			commandBoxModel.addElement(commandMarch);
		}
		if (build > 0) {
			commandBoxModel.addElement(commandBuild);
		}
		if (recruit > 0) {
			commandBoxModel.addElement(commandRecruit);
		}
		if (mine > 0) {
			commandBoxModel.addElement(commandMine);
		}
		if (support > 0) {
			commandBoxModel.addElement(commandSupport);
		}
		if (defend > 0) {
			commandBoxModel.addElement(commandDefend);
		}
		if (commandBoxModel.getSize() > index) {
			comboBox.setSelectedIndex(index);
		}
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
		resourcePanel_2.updateResources(game, game.getLocalUser());
	}
	
	private void updatePlayerOrder() {
		orderPanel.updateTurnOrder(game);
		orderPanel_2.updateTurnOrder(game);
	}
	
	public void setSelectedField(Field field) {
		this.selectedField = field;
		updateField();
	}
}