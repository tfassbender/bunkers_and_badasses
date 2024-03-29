package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;
import com.jfabricationgames.toolbox.properties.event.PropertiesWindowListener;

import net.jfabricationgames.bunkers_and_badasses.game.BunkersAndBadassesClientInterpreter;
import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameTurnManager;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfile;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_character.troop.Bandit;
import net.jfabricationgames.bunkers_and_badasses.game_communication.PreGameDataMessage;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardPanel;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCardPanel;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JRadioButton;

public class PreGameSelectionFrame extends JFrame implements TurnBonusCardSelectionListener, BoardPanelListener, ConfirmDialogListener {
	
	private static final long serialVersionUID = 3805066039018442763L;
	
	private static final String SKILL_SELECTION_PANEL = "skill_selection";
	private static final String TROOP_POSITIONING_PANEL = "troop_positioning";
	private static final String BONUS_SELECTION_PANEL = "bonus_selection";
	
	private static final int SELECTION_TYPE_BASE = 1;
	private static final int SELECTION_TYPE_FIELDS = 2;
	private static final int SELECTION_TYPE_BONUS = 3;
	
	private JPanel contentPane;
	private JPanel panel_turn_goals;
	
	private BoardPanel boardPanel;
	
	private BoardOverviewFrame boardOverview;
	
	private int selectionType = 0;
	private int fieldSelectionIndex = 0;
	
	private Field selectedBaseField;
	private final Field[] selectedStartFields = new Field[3];
	private final int[] startTroops = new int[] {1, 1, 1};
	private final int startingTroops;
	private int troopsLeft;
	
	private TurnBonus selectedBonus;
	
	private boolean usersTurn = false;
	
	private TurnGoalTurnBonusDialog turnDialog;
	
	private Game game;
	private JPanel panel_turn_bonuses;
	private JPanel panel_player_order;
	private JList<String> list_skill_profiles;
	private JTextArea txtrSkillProfile;
	private JPanel panel_6;
	private JTextField txtBase;
	private JTextField txtPlayerturn;
	private JTextField txtTroopsLeft;
	private JTextField txtPosition_1;
	private JTextField txtPosition_2;
	private JTextField txtPosition_3;
	private JButton btnStartTruppenBesttigen;
	private JButton btnBasisBesttigen;
	private JSpinner spinner_troops;
	private JSpinner spinner_troops_1;
	private JSpinner spinner_troops_2;
	private JButton btnWeiter_1;
	private JPanel panel_selectable_turn_bonuses;
	private JTextField txtBonus;
	private JTextArea txtrChosen;
	private JPanel panel_turn_goals_inner;
	private JTextArea txtrBonusDescription;
	private JButton btnAusgewhltenBonusBesttigen;
	private JButton btnSpielStarten;
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
	private JRadioButton rdbtnPosition1;
	private JRadioButton rdbtnPosition2;
	private JRadioButton rdbtnPosition3;
	
	public PreGameSelectionFrame(Game game) {
		addWindowListener(new PropertiesWindowListener(propsFile, PropertiesWindowListener.WINDOW_CLOSING_EVENT));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				new ConfirmDialog("Spiel wirklich beenden?", PreGameSelectionFrame.this, 0).setVisible(true);
			}
		});
		this.game = game;
		//Add a reference to this frame to the client interpreter
		((BunkersAndBadassesClientInterpreter) game.getClient().getClientInterpreter()).setPreGameSelectionFrame(this);
		((BunkersAndBadassesClientInterpreter) game.getClient().getClientInterpreter()).setGame(game);
		
		startingTroops = Game.getGameVariableStorage().getStartTroops();
		
		turnDialog = new TurnGoalTurnBonusDialog(game, false, false);
		
		setTitle("Spiel Vorbereitung - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PreGameSelectionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
		setMinimumSize(new Dimension(1100, 600));
		setLocationRelativeTo(null);
		
		propsFile.alignWindow();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDialog = new JMenu("Dialog");
		menuBar.add(mnDialog);
		
		JMenuItem mntmChatDialog = new JMenuItem("Chat Dialog");
		mntmChatDialog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmChatDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.getGameFrame().showChatDialog();
			}
		});
		mnDialog.add(mntmChatDialog);
		
		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);
		
		JMenuItem mntmHilfe = new JMenuItem("Hilfe Menü");
		mntmHilfe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmHilfe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.getGameFrame().getHelpMenuFrame().setVisible(true);
				game.getGameFrame().getHelpMenuFrame().requestFocus();
			}
		});
		mnHilfe.add(mntmHilfe);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[100px:n,grow]", "[grow]"));
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		contentPane.add(panel_6, "cell 0 0,grow");
		panel_6.setLayout(new CardLayout(0, 0));
		
		JPanel panel_troop_positioning = new JPanel();
		panel_troop_positioning.setBackground(Color.GRAY);
		panel_6.add(panel_troop_positioning, TROOP_POSITIONING_PANEL);
		panel_troop_positioning.setLayout(new MigLayout("", "[800px,grow][:300px:300px,grow]", "[grow]"));
		
		JPanel panel_skill_selection = new JPanel();
		panel_6.add(panel_skill_selection, SKILL_SELECTION_PANEL);
		panel_skill_selection.setBackground(Color.GRAY);
		panel_skill_selection.setLayout(new MigLayout("", "[350px,grow][350px,grow][500px,grow]", "[grow]"));
		
		boardPanel = new BoardPanel();
		boardPanel.addBoardPanelListener(this);
		panel_troop_positioning.add(boardPanel, "cell 0 0,grow");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel_troop_positioning.add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[][][10px][30px][30px,grow][grow][][grow]", "[][30px][][][30px][][][][30px][][5px][][5px][][][][][10px][][grow][]"));
		
		JLabel lblStartpositionen = new JLabel("Startpositionen:");
		lblStartpositionen.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblStartpositionen, "cell 0 0 8 1,alignx center");
		
		JLabel lblAktuellerSpieler = new JLabel("Am Zug:");
		lblAktuellerSpieler.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblAktuellerSpieler, "cell 0 2 8 1");
		
		txtPlayerturn = new JTextField();
		txtPlayerturn.setBackground(Color.LIGHT_GRAY);
		txtPlayerturn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPlayerturn.setEditable(false);
		panel.add(txtPlayerturn, "cell 0 3 8 1,growx");
		txtPlayerturn.setColumns(10);
		
		JLabel lblBasisPosition = new JLabel("Basis Position:");
		lblBasisPosition.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblBasisPosition, "cell 0 5 8 1");
		
		txtBase = new JTextField();
		txtBase.setBackground(Color.LIGHT_GRAY);
		txtBase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtBase.setEditable(false);
		panel.add(txtBase, "cell 0 6 8 1,growx");
		txtBase.setColumns(10);
		
		btnBasisBesttigen = new JButton("Basis Best\u00E4tigen");
		btnBasisBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmBase();
			}
		});
		btnBasisBesttigen.setEnabled(false);
		btnBasisBesttigen.setBackground(Color.GRAY);
		panel.add(btnBasisBesttigen, "cell 0 7 8 1");
		
		JLabel lblStartTruppen = new JLabel("Start Truppen:");
		lblStartTruppen.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblStartTruppen, "cell 0 9 8 1");
		
		JLabel lblTruppenbrig = new JLabel("Truppen \u00DCbrig:");
		lblTruppenbrig.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblTruppenbrig, "cell 0 11 2 1,alignx trailing");
		
		txtTroopsLeft = new JTextField();
		txtTroopsLeft.setBackground(Color.LIGHT_GRAY);
		txtTroopsLeft.setEditable(false);
		txtTroopsLeft.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(txtTroopsLeft, "cell 3 11,growx");
		txtTroopsLeft.setColumns(10);
		
		JLabel lblTruppen = new JLabel("Truppen:");
		lblTruppen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblTruppen, "cell 5 11");
		
		JLabel lblPosition = new JLabel("Position 1:");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblPosition, "cell 0 13");
		
		txtPosition_1 = new JTextField();
		txtPosition_1.setBackground(Color.LIGHT_GRAY);
		txtPosition_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPosition_1.setEditable(false);
		panel.add(txtPosition_1, "cell 1 13 4 1,growx");
		txtPosition_1.setColumns(10);
		
		spinner_troops = new JSpinner();
		spinner_troops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				checkSpinnerState(spinner_troops, 0);
			}
		});
		spinner_troops.setModel(new SpinnerNumberModel(1, 1, startingTroops, 1));
		spinner_troops.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(spinner_troops, "cell 5 13,growx");
		
		ButtonGroup groupFields = new ButtonGroup();
		
		rdbtnPosition1 = new JRadioButton("");
		rdbtnPosition1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnPosition1.isSelected()) {
					fieldSelectionIndex = 0;
				}
			}
		});
		groupFields.add(rdbtnPosition1);
		rdbtnPosition1.setBackground(Color.GRAY);
		panel.add(rdbtnPosition1, "cell 6 13");
		
		JLabel lblPosition_1 = new JLabel("Position 2:");
		lblPosition_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblPosition_1, "cell 0 14");
		
		txtPosition_2 = new JTextField();
		txtPosition_2.setBackground(Color.LIGHT_GRAY);
		txtPosition_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPosition_2.setEditable(false);
		panel.add(txtPosition_2, "cell 1 14 4 1,growx");
		txtPosition_2.setColumns(10);
		
		spinner_troops_1 = new JSpinner();
		spinner_troops_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				checkSpinnerState(spinner_troops_1, 1);
			}
		});
		spinner_troops_1.setModel(new SpinnerNumberModel(1, 1, startingTroops, 1));
		spinner_troops_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(spinner_troops_1, "cell 5 14,growx");
		
		rdbtnPosition2 = new JRadioButton("");
		rdbtnPosition2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnPosition2.isSelected()) {
					fieldSelectionIndex = 1;
				}
			}
		});
		groupFields.add(rdbtnPosition2);
		rdbtnPosition2.setBackground(Color.GRAY);
		panel.add(rdbtnPosition2, "cell 6 14");
		
		JLabel lblPosition_2 = new JLabel("Position 3:");
		lblPosition_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblPosition_2, "cell 0 15");
		
		txtPosition_3 = new JTextField();
		txtPosition_3.setBackground(Color.LIGHT_GRAY);
		txtPosition_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPosition_3.setEditable(false);
		panel.add(txtPosition_3, "cell 1 15 4 1,growx");
		txtPosition_3.setColumns(10);
		
		spinner_troops_2 = new JSpinner();
		spinner_troops_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				checkSpinnerState(spinner_troops_2, 2);
			}
		});
		spinner_troops_2.setModel(new SpinnerNumberModel(1, 1, startingTroops, 1));
		spinner_troops_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(spinner_troops_2, "cell 5 15,growx");
		
		btnStartTruppenBesttigen = new JButton("Start Truppen Best\u00E4tigen");
		btnStartTruppenBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmStartTroops();
			}
		});
		
		rdbtnPosition3 = new JRadioButton("");
		rdbtnPosition3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnPosition3.isSelected()) {
					fieldSelectionIndex = 2;
				}
			}
		});
		groupFields.add(rdbtnPosition3);
		rdbtnPosition3.setBackground(Color.GRAY);
		panel.add(rdbtnPosition3, "cell 6 15");
		btnStartTruppenBesttigen.setEnabled(false);
		btnStartTruppenBesttigen.setBackground(Color.GRAY);
		panel.add(btnStartTruppenBesttigen, "cell 0 16 5 1");
		
		JButton btnSpielfeldbersicht = new JButton("Spielfeld \u00DCbersicht");
		btnSpielfeldbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boardPanel.showOtherView();
			}
		});
		
		JButton btnSpielrundenAnzeigen = new JButton("Spielrunden Anzeigen");
		btnSpielrundenAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				turnDialog.setVisible(true);
			}
		});
		btnSpielrundenAnzeigen.setBackground(Color.GRAY);
		panel.add(btnSpielrundenAnzeigen, "cell 0 18 8 1");
		btnSpielfeldbersicht.setBackground(Color.GRAY);
		panel.add(btnSpielfeldbersicht, "flowx,cell 0 20 4 1");
		
		btnWeiter_1 = new JButton("Weiter");
		btnWeiter_1.setEnabled(false);
		btnWeiter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startTurnBonusSelection();
			}
		});
		btnWeiter_1.setBackground(Color.GRAY);
		panel.add(btnWeiter_1, "cell 4 20 4 1,alignx right");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_skill_selection.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblSpielRunden = new JLabel("Spiel Runden:");
		lblSpielRunden.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_1.add(lblSpielRunden, "cell 0 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		panel_1.add(scrollPane, "cell 0 1,grow");
		
		panel_turn_goals = new JPanel();
		panel_turn_goals.setBackground(Color.GRAY);
		scrollPane.setViewportView(panel_turn_goals);
		panel_turn_goals.setLayout(new MigLayout("", "[75px]", "[150px]"));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_skill_selection.add(panel_4, "cell 1 0,grow");
		panel_4.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblRundenBoni = new JLabel("Runden Boni:");
		lblRundenBoni.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_4.add(lblRundenBoni, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(20);
		panel_4.add(scrollPane_1, "cell 0 1,grow");
		
		panel_turn_bonuses = new JPanel();
		panel_turn_bonuses.setBackground(Color.GRAY);
		scrollPane_1.setViewportView(panel_turn_bonuses);
		panel_turn_bonuses.setLayout(new MigLayout("", "[75px][grow]", "[150px]"));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_skill_selection.add(panel_2, "cell 2 0,grow");
		panel_2.setLayout(new MigLayout("", "[grow]", "[300px,grow][300px,grow][]"));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_2.add(panel_3, "cell 0 0,grow");
		panel_3.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblSpielerReihenfolge = new JLabel("Reihenfolge:");
		lblSpielerReihenfolge.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_3.add(lblSpielerReihenfolge, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_3.add(scrollPane_2, "cell 0 1,grow");
		
		panel_player_order = new JPanel();
		panel_player_order.setBackground(Color.GRAY);
		scrollPane_2.setViewportView(panel_player_order);
		panel_player_order.setLayout(new MigLayout("", "[50px][grow][30px]", "[30px]"));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_2.add(panel_5, "cell 0 1,grow");
		panel_5.setLayout(new MigLayout("", "[150px,grow][250px,grow]", "[][10px][grow]"));
		
		JLabel lblSkillProfilAuswhlen = new JLabel("Skill Profil Ausw\u00E4hlen:");
		lblSkillProfilAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_5.add(lblSkillProfilAuswhlen, "cell 0 0 2 1,alignx center");
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_5.add(scrollPane_3, "cell 0 2,grow");
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		list_skill_profiles = new JList<String>(model);
		list_skill_profiles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				describeSkillProfile(list_skill_profiles.getSelectedIndex());
			}
		});
		list_skill_profiles.setBackground(Color.LIGHT_GRAY);
		scrollPane_3.setViewportView(list_skill_profiles);
		model.addElement("Profil 1");
		model.addElement("Profil 2");
		model.addElement("Profil 3");
		model.addElement("Profil 4");
		model.addElement("Profil 5");
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel_5.add(scrollPane_4, "cell 1 2,grow");
		
		txtrSkillProfile = new JTextArea();
		txtrSkillProfile.setBackground(Color.LIGHT_GRAY);
		txtrSkillProfile.setEditable(false);
		scrollPane_4.setViewportView(txtrSkillProfile);
		
		JButton btnWeiter = new JButton("Weiter");
		btnWeiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//select the skill profile
				selectProfile();
				startTroopPositioning();
				//collect the starting resources after the skill profile was chosen
				/*game.getResourceManager().collectGameStartResources(game.getLocalUser());
				game.getSkillProfileManager().collectSkillResources(game.getLocalUser());*/
			}
		});
		btnWeiter.setBackground(Color.GRAY);
		panel_2.add(btnWeiter, "cell 0 2,alignx center");
		
		list_skill_profiles.setSelectedIndex(0);
		
		JPanel panel_turn_bonus_selection = new JPanel();
		panel_turn_bonus_selection.setBackground(Color.GRAY);
		panel_6.add(panel_turn_bonus_selection, BONUS_SELECTION_PANEL);
		panel_turn_bonus_selection.setLayout(new MigLayout("", "[300px:300px,grow][300px:350px,grow][:450px:500px,grow]", "[grow]"));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.GRAY);
		panel_turn_bonus_selection.add(panel_11, "cell 0 0,grow");
		panel_11.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblRundenZiele = new JLabel("Runden Ziele:");
		lblRundenZiele.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_11.add(lblRundenZiele, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.getVerticalScrollBar().setUnitIncrement(20);
		panel_11.add(scrollPane_7, "cell 0 1,grow");
		
		panel_turn_goals_inner = new JPanel();
		panel_turn_goals_inner.setBackground(Color.GRAY);
		scrollPane_7.setViewportView(panel_turn_goals_inner);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.GRAY);
		panel_turn_bonus_selection.add(panel_7, "cell 1 0,grow");
		panel_7.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblRundenBoni_1 = new JLabel("Runden Boni:");
		lblRundenBoni_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_7.add(lblRundenBoni_1, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.getVerticalScrollBar().setUnitIncrement(20);
		panel_7.add(scrollPane_5, "cell 0 1,grow");
		
		panel_selectable_turn_bonuses = new JPanel();
		panel_selectable_turn_bonuses.setBackground(Color.GRAY);
		scrollPane_5.setViewportView(panel_selectable_turn_bonuses);
		panel_selectable_turn_bonuses.setLayout(new MigLayout("", "[]", "[]"));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_turn_bonus_selection.add(panel_9, "cell 2 0,grow");
		panel_9.setLayout(new MigLayout("", "[][150px][grow]", "[][grow][][][100px][][][][grow][grow]"));
		
		JLabel lblAusgewhlteBoni = new JLabel("Ausgew\u00E4hlte Boni:");
		lblAusgewhlteBoni.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_9.add(lblAusgewhlteBoni, "cell 0 0 3 1,alignx center");
		
		JLabel lblAusgewhlterBonus = new JLabel("Ausgew\u00E4hlter Bonus:");
		lblAusgewhlterBonus.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_9.add(lblAusgewhlterBonus, "cell 0 2,alignx trailing");
		
		txtBonus = new JTextField();
		txtBonus.setBackground(Color.LIGHT_GRAY);
		txtBonus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtBonus.setEditable(false);
		panel_9.add(txtBonus, "cell 1 2 2 1,growx");
		txtBonus.setColumns(10);
		
		JLabel lblBeschreibung = new JLabel("Beschreibung:");
		lblBeschreibung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_9.add(lblBeschreibung, "cell 0 3 3 1");
		
		txtrBonusDescription = new JTextArea();
		txtrBonusDescription.setWrapStyleWord(true);
		txtrBonusDescription.setLineWrap(true);
		txtrBonusDescription.setBackground(Color.LIGHT_GRAY);
		txtrBonusDescription.setEditable(false);
		panel_9.add(txtrBonusDescription, "cell 0 4 2 1,grow");
		
		btnAusgewhltenBonusBesttigen = new JButton("Ausgew\u00E4hlten Bonus Best\u00E4tigen");
		btnAusgewhltenBonusBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmBonus();
			}
		});
		btnAusgewhltenBonusBesttigen.setEnabled(false);
		btnAusgewhltenBonusBesttigen.setBackground(Color.GRAY);
		panel_9.add(btnAusgewhltenBonusBesttigen, "cell 0 5 3 1");
		
		JLabel lblBereitsGewhlt = new JLabel("Bereits Gew\u00E4hlt:");
		lblBereitsGewhlt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_9.add(lblBereitsGewhlt, "cell 0 7");
		
		JScrollPane scrollPane_6 = new JScrollPane();
		panel_9.add(scrollPane_6, "cell 0 8 2 1,grow");
		
		txtrChosen = new JTextArea();
		txtrChosen.setBackground(Color.LIGHT_GRAY);
		txtrChosen.setEditable(false);
		scrollPane_6.setViewportView(txtrChosen);
		
		btnSpielStarten = new JButton("Spiel Starten");
		btnSpielStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startGame();
			}
		});
		
		JButton btnSpielfeldbersichtAnzeigen = new JButton("Spielfeld Übersicht Anzeigen");
		btnSpielfeldbersichtAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardOverview.setVisible(true);
				boardOverview.requestFocus();
			}
		});
		btnSpielfeldbersichtAnzeigen.setBackground(Color.GRAY);
		panel_9.add(btnSpielfeldbersichtAnzeigen, "cell 0 9 2 1,alignx left,aligny bottom");
		btnSpielStarten.setEnabled(false);
		btnSpielStarten.setBackground(Color.GRAY);
		panel_9.add(btnSpielStarten, "cell 2 9,alignx right,aligny bottom");
		

		addGameTurns(panel_turn_goals);
		addGameTurns(panel_turn_goals_inner);
		addTurnBonuses(panel_turn_bonuses, false);
		addTurnBonuses(panel_selectable_turn_bonuses, true);
		addPlayers(panel_player_order);
		
		updateBoardImage();
		
		//start the pre-game-selections with the skill selection
		startSkillSelection();
	}
	
	@Override
	public void receiveBoardMouseClick(MouseEvent event, boolean doubleClick) {
		selectField();
	}
	
	@Override
	public void turnBonusCardSelected(TurnBonus bonus) {
		selectedBonus = bonus;
		txtBonus.setText(bonus.getName());
		txtrBonusDescription.setText(bonus.getDescriptionNoHtml());
	}
	
	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			System.exit(0);
		}
	}
	
	private void addGameTurns(JPanel panel) {
		panel.removeAll();
		int turns = GameTurnManager.getNumTurns();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < turns; i++) {
			sb.append("[150px]");
		}
		panel.setLayout(new MigLayout("", "[75px][grow]", sb.toString()));
		Font font = new Font("Tahoma", Font.BOLD, 18);
		for (int i = 0; i < turns; i++) {
			JLabel label = new JLabel(Integer.toString(i+1));
			label.setFont(font);
			panel.add(label, "cell 0 " + i + ",alignx center");
			TurnGoalCardPanel imagePanel = new TurnGoalCardPanel(game.getTurnManager().getGameTurnGoalManager().getTurnGoal(i));
			panel.add(imagePanel, "cell 1 " + i + ",grow");
		}
	}
	private void addTurnBonuses(JPanel panel, boolean selectable) {
		panel.removeAll();
		List<TurnBonus> bonusCards = game.getGameTurnBonusManager().getBonuses();
		int bonuses = bonusCards.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bonuses; i++) {
			sb.append("[150px]");
		}
		panel.setLayout(new MigLayout("", "[grow]", sb.toString()));
		for (int i = 0; i < bonuses; i++) {
			TurnBonusCardPanel imagePanel = new TurnBonusCardPanel(bonusCards.get(i));
			if (selectable) {
				imagePanel.setSelectionListener(this);
				imagePanel.setChangeColorOnFocusEvent(true);
			}
			panel.add(imagePanel, "cell 0 " + i + ",grow");
		}
	}
	private void addPlayers(JPanel panel) {
		panel.removeAll();
		int players = game.getPlayers().size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < players; i++) {
			sb.append("[30px]");
		}
		panel.setLayout(new MigLayout("", "[50][grow][30]", sb.toString()));
		Font font = new Font("Tahoma", Font.BOLD, 14);
		User[] order = game.getPlayerOrder().getOrder();
		for (int i = 0; i < players; i++) {
			JLabel label = new JLabel(Integer.toString(i+1));
			label.setFont(font);
			panel.add(label, "cell 0 " + i + ",alignx center");
			JLabel label_2 = new JLabel(order[i].getUsername());
			label_2.setFont(font);
			panel.add(label_2, "cell 1 " + i);
			JLabel label_3 = new JLabel("\u25CF");
			label_3.setForeground(game.getColorManager().getColor(order[i]).getColor());
			panel.add(label_3, "cell 2 " + i + ",alignx center");
		}
	}
	
	/**
	 * Receive the data from the other clients and add it to the current field.
	 */
	public void receivePreGameData(PreGameDataMessage message) {
		int index = -1;
		User[] order = game.getPlayerOrder().getOrder();
		Field field;
		for (int i = 0; i < order.length; i++) {
			if (message.getUser().equals(order[i])) {
				index = i;
			}
		}
		//System.out.println("index: " + index + " message_data: " + message.getData());		
		switch (message.getData()) {
			case PreGameDataMessage.DATA_SKILL_PROFILE:
				game.getSkillProfileManager().setSelectedProfile(message.getUser(), message.getSelectedProfile());
				//collect the starting and skill resources of the player
				//game.getResourceManager().collectGameStartResources(message.getUser());
				//game.getSkillProfileManager().collectSkillResources(message.getUser());
				break;
			case PreGameDataMessage.DATA_BASE_POSITION:
				//set the affiliation and the building on the local board
				field = game.getBoard().getFieldByName(message.getBasePosition().getName()); 
				field.setAffiliation(message.getUser());
				field.setBuilding(new ArschgaulsPalace());
				if (index == 0) {
					//every user has chosen his start position -> go on to the troop positions
					//the next selection type is set when the user is set
					setPlayersTurn(order[0]);
				}
				else {
					//let the next user choose his base position
					setPlayersTurn(order[index-1]);
				}
				break;
			case PreGameDataMessage.DATA_STARTING_POSITION:
				//set the troops on the local board
				for (int i = 0; i < message.getStartingTroopPositions().length; i++) {
					if (message.getStartingTroopPositions()[i] != null) {
						field = game.getBoard().getFieldByName(message.getStartingTroopPositions()[i].getName());
						field.setAffiliation(message.getUser());
						for (int j = 0; j < message.getStartingTroops()[i]; j++) {
							field.getTroops().add(new Bandit());
						}
					}
				}
				if (index == order.length-1) {
					//all players have chosen their starting troops
					//choose the game turn bonuses next
					if (message.getNeutralTroops() != null) {
						game.getBoard().addNeutralTroops(message.getNeutralTroops());
					}
					else {
						System.err.println("Error: no neutral troops found");
					}
					btnWeiter_1.setEnabled(true);
					setPlayersTurn(order[index]);//last player chooses the bonus first
				}
				else {
					//let the next use choose his starting troops
					setPlayersTurn(order[index+1]);
				}
				break;
			case PreGameDataMessage.DATA_TURN_BONUS:
				//select the bonuses for the users
				message.getSelectedBonus().loadImage();//load the image that was not sent with the serialized stream
				game.getGameTurnBonusManager().chooseFirstTurnBonus(message.getUser(), message.getSelectedBonus());
				txtrChosen.append(message.getSelectedBonus().getName() + "\n");
				if (index == 0) {
					//all players have chosen their bonuses
					//game can be started now
					btnSpielStarten.setEnabled(true);
				}
				else {
					setPlayersTurn(order[index-1]);
				}
				break;
		}
		updateBoardImage();
	}
	
	/**
	 * Start the game frame after the troops are positioned.
	 */
	private void startGame() {
		game.startGame();
		game.getGameFrame().setVisible(true);
		game.getGameFrame().requestFocus();
		dispose();
	}
	
	/**
	 * Check whether the spinners have values that are valid.
	 * 
	 * @param spinner
	 * 		The spinner that was changed.
	 * 
	 * @param position
	 * 		The number of this spinner.
	 */
	private void checkSpinnerState(JSpinner spinner, int position) {
		int val = (int) spinner.getValue();
		if (startingTroops - startTroops[0] - startTroops[1] - startTroops[2] - val + startTroops[position] < 0) {
			val = startingTroops - startTroops[0] - startTroops[1] - startTroops[2] + startTroops[position];
			spinner.setValue(val);//change the value and recall this method
		}
		else {
			startTroops[position] = val;
		}
		updateStartTroops();
	}
	
	/**
	 * Add a description of the skill profile to the text area.
	 */
	private void describeSkillProfile(int profile) {
		txtrSkillProfile.setText(game.getSkillProfileManager().getSkillProfiles()[list_skill_profiles.getSelectedIndex()].describe());
		txtrSkillProfile.setCaretPosition(0);
	}
	
	/**
	 * Select the field underneath the mouse and add it as base or troop position.
	 */
	private void selectField() {
		if (usersTurn) {
			Field field = game.getBoard().getFieldAtMousePosition();
			boolean fieldSelectable = true;
			if (field != null) {
				if (selectionType == SELECTION_TYPE_BASE) {
					selectedBaseField = field;
					txtBase.setText(field.getName());
				}
				else if (selectionType == SELECTION_TYPE_FIELDS && field != null) {
					fieldSelectable = selectedBaseField.equals(field);
					for (Field neighbour : selectedBaseField.getNeighbours()) {
						fieldSelectable |= field.getName().equals(neighbour.getName());
					}
					if (fieldSelectable) {
						/*selectedStartFields[2] = selectedStartFields[1];
						selectedStartFields[1] = selectedStartFields[0];
						selectedStartFields[0] = field;
						troopsLeft += startTroops[2];
						startTroops[2] = startTroops[1];
						startTroops[1] = startTroops[0];
						startTroops[0] = 1;*/

						selectedStartFields[fieldSelectionIndex] = field;
						startTroops[fieldSelectionIndex] = 1;
						fieldSelectionIndex++;
						fieldSelectionIndex %= 3;
						
						updateStartTroops();
						updateFieldSelectionButtons();
					}
				}
			}
		}
	}
	/**
	 * Update some panels and text fields.
	 */
	private void updateStartTroops() {
		if (selectedStartFields[0] != null) {
			txtPosition_1.setText(selectedStartFields[0].getName());			
		}
		if (selectedStartFields[1] != null) {
			txtPosition_2.setText(selectedStartFields[1].getName());			
		}
		if (selectedStartFields[2] != null) {
			txtPosition_3.setText(selectedStartFields[2].getName());			
		}
		spinner_troops.setValue(startTroops[0]);
		spinner_troops_1.setValue(startTroops[1]);
		spinner_troops_2.setValue(startTroops[2]);
		troopsLeft = startingTroops - (startTroops[0] + startTroops[1] + startTroops[2]);
		txtTroopsLeft.setText(Integer.toString(troopsLeft));
	}
	
	private void updateFieldSelectionButtons() {
		if (fieldSelectionIndex == 0) {
			rdbtnPosition1.setSelected(true);
		}
		else if (fieldSelectionIndex == 1) {
			rdbtnPosition2.setSelected(true);
		}
		else if (fieldSelectionIndex == 2) {
			rdbtnPosition3.setSelected(true);
		}
	}
	
	/**
	 * Confirm the position of the base and send an update to all users.
	 */
	private void confirmBase() {
		if (selectedBaseField != null && selectedBaseField.getBuilding() instanceof EmptyBuilding) {
			boolean fieldSelectable = true;
			for (Field neighbour : selectedBaseField.getNeighbours()) {
				fieldSelectable &= neighbour.getBuilding() instanceof EmptyBuilding;
			}
			fieldSelectable &= selectedBaseField.getBuilding() instanceof EmptyBuilding;
			if (fieldSelectable) {
				selectedBaseField.setAffiliation(game.getLocalUser());
				selectedBaseField.setBuilding(new ArschgaulsPalace());
				//send an update to all users
				PreGameDataMessage message = new PreGameDataMessage();
				message.setData(PreGameDataMessage.DATA_BASE_POSITION);
				message.setBasePosition(selectedBaseField);
				message.setUser(game.getLocalUser());
				game.getClient().sendMessage(message);
				//update the frame functions
				nextPlayersTurn(game.getLocalUser(), PreGameDataMessage.DATA_BASE_POSITION);
				updateBoardImage();				
			}
			else {
				new ErrorDialog("Du kannst deine Basis nicht auf einem Feld aufbauen, dass direkt an die Basis eines anderen Spielers angrenzt.\n\nEs wird schon früh genug Schießereien geben.\nNur keine Sorge.").setVisible(true);
			}
		}
		else if (selectedBaseField == null) {
			new ErrorDialog("Du solltest ein Feld auswählen wenn du deine Basis aufbauen willst.").setVisible(true);
		}
		else if (!(selectedBaseField.getBuilding() instanceof EmptyBuilding)) {
			new ErrorDialog("Du kannst nicht deine Basis auf einem Feld aufbauen auf dem schon eine Basis steht.\n\nAußer du willst versuchen sie zu stapeln...\nIch muss zugeben das würde ich gerne sehen.").setVisible(true);
		}
	}
	/**
	 * Confirm the position of the start troops and send an update to all users.
	 */
	private void confirmStartTroops() {
		if (selectedStartFields[0] != null && startTroops[0] + startTroops[1] + startTroops[2] == startingTroops) {
			//check whether the chosen start fields are empty
			boolean fieldsEmpty = true;
			for (Field field : selectedStartFields) {
				fieldsEmpty &= field != null && (field.getAffiliation() == null || field.getAffiliation().equals(game.getLocalUser()));
			}
			if (fieldsEmpty) {
				//the user chose at least one field and the six starting troops
				if (selectedStartFields[0] != null && startTroops[0] > 0) {
					selectedStartFields[0].setAffiliation(game.getLocalUser());
					for (int i = 0; i < startTroops[0]; i++) {
						selectedStartFields[0].getTroops().add(new Bandit());
					}
				}
				if (selectedStartFields[1] != null && startTroops[1] > 0) {
					selectedStartFields[1].setAffiliation(game.getLocalUser());
					for (int i = 0; i < startTroops[1]; i++) {
						selectedStartFields[1].getTroops().add(new Bandit());
					}
				}
				if (selectedStartFields[2] != null && startTroops[2] > 0) {
					selectedStartFields[2].setAffiliation(game.getLocalUser());
					for (int i = 0; i < startTroops[2]; i++) {
						selectedStartFields[2].getTroops().add(new Bandit());
					}
				}
				//send an update to all users
				PreGameDataMessage message = new PreGameDataMessage();
				message.setData(PreGameDataMessage.DATA_STARTING_POSITION);
				message.setStartingTroopPositions(selectedStartFields);
				message.setStartingTroops(startTroops);
				message.setUser(game.getLocalUser());
				User[] order = game.getPlayerOrder().getOrder();
				if (game.getLocalUser().equals(order[order.length-1])) {
					//last user to choose the start troops -> add the neutral troops
					Map<Field, int[]> neutralTroops = game.getBoard().addNeutralTroops();
					message.setNeutralTroops(neutralTroops);
				}
				game.getClient().sendMessage(message);
				//update the frame functions
				nextPlayersTurn(game.getLocalUser(), PreGameDataMessage.DATA_STARTING_POSITION);
				updateBoardImage();
			}
			else {
				new ErrorDialog("Du kannst kein Startfeld aussuchen, auf dem sich schon ein gegner Befindet.\n\nKaffeekränzchen sind hier unangebracht!").setVisible(true);
			}
		}
		else {
			new ErrorDialog("Du musst alle deine Starttruppen auf dem Spielfeld verteilen.").setVisible(true);
		}
	}
	/**
	 * Confirm the selected turn bonus and send an update to all users.
	 */
	private void confirmBonus() {
		if (selectedBonus == null) {
			new ErrorDialog("Du must einen Bonus auswählen bevor du ihn bestätigen kannst.").setVisible(true);
		}
		else if (!game.getGameTurnBonusManager().isTurnBonusChoosable(selectedBonus)) {
			new ErrorDialog("Du solltest einen Bonus auswählen, den dir noch kein anderer Spieler weggeschnappt hat.\n\nNatürlich nachdem Du ihn dafür zur Sau gemacht hast, dass er einfach so deinen Bonus nimmt.");
		}
		else {
			game.getGameTurnBonusManager().chooseFirstTurnBonus(game.getLocalUser(), selectedBonus);
			addTurnBonuses(panel_selectable_turn_bonuses, true);
			turnDialog.update();
			PreGameDataMessage message = new PreGameDataMessage();
			message.setData(PreGameDataMessage.DATA_TURN_BONUS);
			message.setUser(game.getLocalUser());
			message.setSelectedBonus(selectedBonus);
			game.getClient().sendMessage(message);
			//update the frame functions
			txtrChosen.append(message.getSelectedBonus().getName() + "\n");
			nextPlayersTurn(game.getLocalUser(), PreGameDataMessage.DATA_TURN_BONUS);
			updateBoardImage();
		}
	}
	
	private void nextPlayersTurn(User lastUser, int data) {
		int index = -1;
		User[] order = game.getPlayerOrder().getOrder();
		for (int i = 0; i < order.length; i++) {
			if (lastUser.equals(order[i])) {
				index = i;
			}
		}
		switch (data) {
			case PreGameDataMessage.DATA_BASE_POSITION:
				if (index == 0) {
					//every user has chosen his start position -> go on to the troop positions
					//the next selection type is set when the user is set
					setPlayersTurn(order[0]);
				}
				else {
					//let the next user choose his base position
					setPlayersTurn(order[index-1]);
				}
				break;
			case PreGameDataMessage.DATA_STARTING_POSITION:
				if (index == order.length-1) {
					//all players have chosen their starting troops
					btnWeiter_1.setEnabled(true);
					setPlayersTurn(order[index]);//last player chooses the bonus first
					//choose the game turn bonuses next
				}
				else {
					//let the next use choose his starting troops
					setPlayersTurn(order[index+1]);
				}
				break;
			case PreGameDataMessage.DATA_TURN_BONUS:
				if (index == 0) {
					//all players have chosen their bonuses
					//game can be started now
					btnAusgewhltenBonusBesttigen.setEnabled(false);
					btnSpielStarten.setEnabled(true);
				}
				else {
					setPlayersTurn(order[index-1]);
				}
				break;
		}
	}
	
	private void updateBoardImage() {
		BufferedImage board = game.getBoard().displayBoard();
		boardPanel.updateBoardImage(board);
	}
	
	/**
	 * Let the next player make his turn.
	 * If it's not the local player all buttons are disabled.
	 * Otherwise the right buttons for the case are enabled.
	 */
	private void setPlayersTurn(User user) {
		if (user.equals(game.getLocalUser())) {
			if (selectionType == 0) {
				selectionType = SELECTION_TYPE_BASE;
				btnBasisBesttigen.setEnabled(true);
			}
			else if (selectionType == SELECTION_TYPE_BASE) {
				selectionType = SELECTION_TYPE_FIELDS;
				btnBasisBesttigen.setEnabled(false);
				btnStartTruppenBesttigen.setEnabled(true);
			}
			else if (selectionType == SELECTION_TYPE_FIELDS) {
				selectionType = SELECTION_TYPE_BONUS;
				btnStartTruppenBesttigen.setEnabled(false);
				btnAusgewhltenBonusBesttigen.setEnabled(true);
			}
			else if (selectionType == SELECTION_TYPE_BONUS) {
				btnAusgewhltenBonusBesttigen.setEnabled(false);
			}
			usersTurn = true;
		}
		else {
			btnBasisBesttigen.setEnabled(false);
			btnStartTruppenBesttigen.setEnabled(false);
			btnAusgewhltenBonusBesttigen.setEnabled(false);
			usersTurn = false;
		}
		txtPlayerturn.setText(user.getUsername());
	}
	
	/**
	 * Select a skill profile.
	 */
	private void selectProfile() {
		//select the skill profile
		SkillProfileManager manager = game.getSkillProfileManager();
		SkillProfile profile = manager.getSkillProfiles()[list_skill_profiles.getSelectedIndex()];
		manager.setSelectedProfile(game.getLocalUser(), profile);
		//send a message to the other users
		PreGameDataMessage message = new PreGameDataMessage();
		message.setData(PreGameDataMessage.DATA_SKILL_PROFILE);
		message.setUser(game.getLocalUser());
		message.setSelectedProfile(profile);
		game.getClient().sendMessage(message);
	}
	/**
	 * Change to the troop positioning panel and enable the buttons for the first user.
	 */
	private void startTroopPositioning() {
		CardLayout layout = (CardLayout) panel_6.getLayout();
		layout.show(panel_6, TROOP_POSITIONING_PANEL);
		User[] playerOrder = game.getPlayerOrder().getOrder();
		if (!usersTurn) {
			setPlayersTurn(playerOrder[playerOrder.length-1]);			
		}
	}
	/**
	 * Change to the turn bonus selection panel.
	 */
	private void startTurnBonusSelection() {
		//update the board in the overview frame
		boardOverview = new BoardOverviewFrame(game.getBoard());
		CardLayout layout = (CardLayout) panel_6.getLayout();
		layout.show(panel_6, BONUS_SELECTION_PANEL);
	}
	/**
	 * Start the skill selection.
	 */
	private void startSkillSelection() {
		CardLayout layout = (CardLayout) panel_6.getLayout();
		layout.show(panel_6, SKILL_SELECTION_PANEL);
	}
}