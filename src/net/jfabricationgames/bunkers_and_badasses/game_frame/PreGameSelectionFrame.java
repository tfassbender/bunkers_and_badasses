package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameTurnManager;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardPanel;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCardPanel;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class PreGameSelectionFrame extends JFrame {
	
	private static final long serialVersionUID = 3805066039018442763L;
	
	private static final String SKILL_SELECTION_PANEL = "skill_selection";
	private static final String TROOP_POSITIONING_PANEL = "troop_positioning";
	
	private JPanel contentPane;
	private JPanel panel_turn_goals;
	
	private boolean fieldOverview = false;
	private JPanel panel_board_capture;
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";
	private JPanel panel_scroll_board;
	private JPanel panel_board_overview;
	private JScrollPane scrollPane_board;
	
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
	
	public PreGameSelectionFrame(Game game) {
		this.game = game;
		setTitle("Bunkers and Badasses - Spiel Start");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PreGameSelectionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
		setMinimumSize(new Dimension(1100, 600));
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[100px:n,grow][grow]", "[grow]"));
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		contentPane.add(panel_6, "cell 0 0,grow");
		panel_6.setLayout(new CardLayout(0, 0));
		
		JPanel panel_troop_positioning = new JPanel();
		panel_troop_positioning.setBackground(Color.GRAY);
		panel_6.add(panel_troop_positioning, SKILL_SELECTION_PANEL);
		panel_troop_positioning.setLayout(new MigLayout("", "[800px,grow][:300px:300px,grow]", "[grow]"));
		
		JPanel panel_skill_selection = new JPanel();
		panel_6.add(panel_skill_selection, TROOP_POSITIONING_PANEL);
		panel_skill_selection.setBackground(Color.GRAY);
		panel_skill_selection.setLayout(new MigLayout("", "[350px,grow][350px,grow][500px,grow]", "[grow]"));
		
		panel_board_capture = new JPanel();
		panel_board_capture.setBackground(Color.GRAY);
		panel_troop_positioning.add(panel_board_capture, "cell 0 0,grow");
		panel_board_capture.setLayout(new CardLayout(0, 0));
		
		JPanel panel_scroll_board_capture = new JPanel();
		panel_scroll_board_capture.setBackground(Color.GRAY);
		panel_board_capture.add(panel_scroll_board_capture, SCROLL_BOARD);
		panel_scroll_board_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		scrollPane_board = new JScrollPane();
		panel_scroll_board_capture.add(scrollPane_board, "cell 0 0,grow");
		
		panel_scroll_board = new JPanel();
		panel_scroll_board.setBackground(Color.GRAY);
		scrollPane_board.setViewportView(panel_scroll_board);
		
		JPanel panel_board_overview_capture = new JPanel();
		panel_board_overview_capture.setBackground(Color.GRAY);
		panel_board_capture.add(panel_board_overview_capture, OVERVIEW_BOARD);
		panel_board_overview_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		panel_board_overview = new JPanel();
		panel_board_overview.setBackground(Color.GRAY);
		panel_board_overview_capture.add(panel_board_overview, "cell 0 0,grow");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel_troop_positioning.add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[][][10px][30px][30px,grow][grow]", "[][30px][][][30px][][][][30px][][5px][][5px][][][][][grow][]"));
		
		JLabel lblStartpositionen = new JLabel("Startpositionen:");
		lblStartpositionen.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblStartpositionen, "cell 0 0 6 1,alignx center");
		
		JLabel lblAktuellerSpieler = new JLabel("Am Zug:");
		lblAktuellerSpieler.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblAktuellerSpieler, "cell 0 2 6 1");
		
		txtPlayerturn = new JTextField();
		txtPlayerturn.setBackground(Color.LIGHT_GRAY);
		txtPlayerturn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPlayerturn.setEditable(false);
		panel.add(txtPlayerturn, "cell 0 3 6 1,growx");
		txtPlayerturn.setColumns(10);
		
		JLabel lblBasisPosition = new JLabel("Basis Position:");
		lblBasisPosition.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblBasisPosition, "cell 0 5 6 1");
		
		txtBase = new JTextField();
		txtBase.setBackground(Color.LIGHT_GRAY);
		txtBase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtBase.setEditable(false);
		panel.add(txtBase, "cell 0 6 6 1,growx");
		txtBase.setColumns(10);
		
		JButton btnBasisBesttigen = new JButton("Basis Best\u00E4tigen");
		btnBasisBesttigen.setBackground(Color.GRAY);
		panel.add(btnBasisBesttigen, "cell 0 7 6 1");
		
		JLabel lblStartTruppen = new JLabel("Start Truppen:");
		lblStartTruppen.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblStartTruppen, "cell 0 9 6 1");
		
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
		
		JSpinner spinner_troops_1 = new JSpinner();
		spinner_troops_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(spinner_troops_1, "cell 5 13,growx");
		
		JLabel lblPosition_1 = new JLabel("Position 2:");
		lblPosition_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblPosition_1, "cell 0 14");
		
		txtPosition_2 = new JTextField();
		txtPosition_2.setBackground(Color.LIGHT_GRAY);
		txtPosition_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPosition_2.setEditable(false);
		panel.add(txtPosition_2, "cell 1 14 4 1,growx");
		txtPosition_2.setColumns(10);
		
		JSpinner spinner_troops_2 = new JSpinner();
		spinner_troops_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(spinner_troops_2, "cell 5 14,growx");
		
		JLabel lblPosition_2 = new JLabel("Position 3:");
		lblPosition_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblPosition_2, "cell 0 15");
		
		txtPosition_3 = new JTextField();
		txtPosition_3.setBackground(Color.LIGHT_GRAY);
		txtPosition_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPosition_3.setEditable(false);
		panel.add(txtPosition_3, "cell 1 15 4 1,growx");
		txtPosition_3.setColumns(10);
		
		JSpinner spinner_troops_3 = new JSpinner();
		spinner_troops_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(spinner_troops_3, "cell 5 15,growx");
		
		JButton btnStartTruppenBesttigen = new JButton("Start Truppen Best\u00E4tigen");
		btnStartTruppenBesttigen.setBackground(Color.GRAY);
		panel.add(btnStartTruppenBesttigen, "cell 0 16 6 1");
		
		JButton btnSpielfeldbersicht = new JButton("Spielfeld \u00DCbersicht");
		btnSpielfeldbersicht.setBackground(Color.GRAY);
		panel.add(btnSpielfeldbersicht, "cell 0 18 6 1");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_skill_selection.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblSpielRunden = new JLabel("Spiel Runden:");
		lblSpielRunden.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_1.add(lblSpielRunden, "cell 0 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
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
				selectProfile();
				startTroopPositioning();
			}
		});
		btnWeiter.setBackground(Color.GRAY);
		panel_2.add(btnWeiter, "cell 0 2,alignx center");
		
		list_skill_profiles.setSelectedIndex(0);
		
		addGameTurns(panel_turn_goals);
		addTurnBonuses(panel_turn_bonuses);
		addPlayers(panel_player_order);
	}
	
	private void addGameTurns(JPanel panel) {
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
	private void addTurnBonuses(JPanel panel) {
		int bonuses = game.getPlayers().size() + 3;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bonuses; i++) {
			sb.append("[150px]");
		}
		panel.setLayout(new MigLayout("", "[grow]", sb.toString()));
		List<TurnBonus> bonusCards = game.getGameTurnBonusManager().getBonuses();
		for (int i = 0; i < bonuses; i++) {
			TurnBonusCardPanel imagePanel = new TurnBonusCardPanel(bonusCards.get(i));
			panel.add(imagePanel, "cell 0 " + i + ",grow");
		}
	}
	private void addPlayers(JPanel panel) {
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
	
	private void describeSkillProfile(int profile) {
		txtrSkillProfile.setText(game.getSkillProfileManager().getSkillProfiles()[list_skill_profiles.getSelectedIndex()].describe());
	}
	
	/**
	 * Select a skill profile.
	 */
	private void selectProfile() {
		SkillProfileManager manager = game.getSkillProfileManager();
		manager.setSelectedProfile(game.getLocalUser(), manager.getSkillProfiles()[list_skill_profiles.getSelectedIndex()]);
	}
	private void startTroopPositioning() {
		CardLayout layout = (CardLayout) panel_6.getLayout();
		layout.show(panel_6, "");
	}
}