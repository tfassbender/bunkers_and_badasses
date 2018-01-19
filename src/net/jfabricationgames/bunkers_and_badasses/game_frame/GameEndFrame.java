package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameTurnManager;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager.UserPoints;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardPanel;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCardPanel;
import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameEndFrame extends JFrame implements ConfirmDialogListener {
	
	private static final long serialVersionUID = 653068952076793123L;
	
	private JPanel contentPane;
	private JTextField txtWinner;
	
	private Game game;
	
	private JFGClient client;
	
	private List<UserPoints> userPoints;
	
	public GameEndFrame(Game game, JFGClient client) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new ConfirmDialog("Zum Main Menü zurückkehren?", GameEndFrame.this, 0).setVisible(true);//confirm type is unused here
			}
		});
		this.game = game;
		this.client = client;
		this.userPoints = game.getPointManager().getSortedPointList();
		
		if (game.getStartingPlayer().equals(game.getLocalUser())) {
			//add the final statistic values
			game.getStatisticManager().addEndValues(game);
			//store the final game in the database if the local player is the starting player
			Game.getGameStore().storeGame(game, true);
		}
		
		setTitle("Spiel Ende - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameEndFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1250, 800);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		ImagePanel panel = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/torgue_explosion_sound.png"));
		panel.setAdaptSizeKeepProportion(true);
		panel.setPrimaryProportion(ImagePanel.PRIMARY_PROPORTION_HEIGHT);
		panel.setCentered(true);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[:200px:300px,grow][600px,grow][:200px:300px,grow]", "[][50px,grow][200px,grow][300px,grow][125px,grow][]"));
		
		JLabel lblBunkersAndBadasses = new JLabel("Bunkers and Badasses");
		lblBunkersAndBadasses.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblBunkersAndBadasses, "cell 0 0 3 1,alignx center");
		
		ImagePanel panel_image_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("login/moxxi_1.png"));
		panel_image_1.setAdaptSizeKeepProportion(true);
		panel_image_1.setCentered(true);
		panel_image_1.setBackground(new Color(0, 0, 0, 0));
		panel.add(panel_image_1, "cell 0 2 1 2,grow");
		
		JPanel panel_game_data = new JPanel();
		panel_game_data.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_game_data.setBackground(new Color(0, 0, 0, 0));
		panel.add(panel_game_data, "cell 1 1 1 4,grow");
		panel_game_data.setLayout(new CardLayout(0, 0));
		
		JPanel panel_results = new JPanel();
		panel_results.setBackground(new Color(0, 0, 0, 0));
		panel_game_data.add(panel_results, "panel_results");
		panel_results.setLayout(new MigLayout("", "[grow]", "[][100px:100px:150px][grow][200px]"));
		
		JLabel lblErgebnisse = new JLabel("Ergebnisse:");
		lblErgebnisse.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_results.add(lblErgebnisse, "cell 0 0,alignx center");
		
		JPanel panel_winner = new JPanel();
		panel_winner.setBackground(new Color(0, 0, 0, 0));
		panel_results.add(panel_winner, "cell 0 1,grow");
		panel_winner.setLayout(new MigLayout("", "[200px,grow][200px,grow][200px,grow]", "[grow][][][grow]"));
		
		JLabel lblUltimativenSuperbadassAller = new JLabel("DER ULTIMATIVE SUPER-BADASS ALLER BADASSES IST:");
		lblUltimativenSuperbadassAller.setFont(new Font("Tahoma", Font.BOLD, 22));
		panel_winner.add(lblUltimativenSuperbadassAller, "cell 0 1 3 1,alignx center");
		
		StringBuilder winner = new StringBuilder();
		winner.append(userPoints.get(0).getUser().getUsername());
		for (int i = 1; i < userPoints.size(); i++) {
			if (userPoints.get(i).getPoints() == userPoints.get(0).getPoints()) {
				//two or more players have the same score
				winner.append(", ");
				winner.append(userPoints.get(i).getUser().getUsername());
			}
		}
		txtWinner = new JTextField(winner.toString());
		txtWinner.setForeground(Color.BLACK);
		txtWinner.setHorizontalAlignment(SwingConstants.CENTER);
		txtWinner.setBackground(Color.LIGHT_GRAY);
		txtWinner.setEditable(false);
		txtWinner.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_winner.add(txtWinner, "cell 1 2,growx");
		txtWinner.setColumns(10);
		
		JPanel panel_points = new JPanel();
		panel_points.setBackground(new Color(0, 0, 0, 100));
		panel_results.add(panel_points, "cell 0 2,grow");
		panel_points.setLayout(new MigLayout("", "[grow,center]", "[][grow]"));
		
		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_points.add(lblPunkte, "cell 0 0");
		
		JScrollPane scrollPane_points = new JScrollPane();
		scrollPane_points.setBackground(new Color(0, 0, 0, 0));
		panel_points.add(scrollPane_points, "cell 0 1,grow");
		
		JPanel panel_players_points_header = new JPanel();
		panel_players_points_header.setBackground(new Color(0, 0, 0, 0));
		scrollPane_points.setColumnHeaderView(panel_players_points_header);
		panel_players_points_header.setLayout(new MigLayout("", "[175px:n:175px][75px:n:75px][100px:n:100px][grow]", "[]"));
		
		JLabel lblPlatz = new JLabel("Platz");
		panel_players_points_header.add(lblPlatz, "cell 1 0,alignx center");
		lblPlatz.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblPunkte_1 = new JLabel("Punkte");
		panel_players_points_header.add(lblPunkte_1, "cell 2 0,alignx center");
		lblPunkte_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblSpieler = new JLabel("Spieler");
		panel_players_points_header.add(lblSpieler, "cell 3 0");
		lblSpieler.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		ImagePanel panel_players_points = new ImagePanel();//new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/torgue_explosion_sound_2.png"));
		panel_players_points.setAdaptSizeKeepProportion(true);
		panel_players_points.setCentered(true);
		scrollPane_points.setViewportView(panel_players_points);
		panel_players_points.setBackground(new Color(0, 0, 0, 0));
		addPlayersPoints(panel_players_points);
		
		ImagePanel panel_image_3 = new ImagePanel();//new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/mr_torgue_2.png"));
		panel_image_3.setCentered(true);
		panel_image_3.setAdaptSizeKeepProportion(true);
		panel_image_3.setBackground(new Color(0, 0, 0, 1));
		panel_results.add(panel_image_3, "cell 0 3,grow");
		
		JPanel panel_board = new JPanel();
		panel_board.setBackground(new Color(0, 0, 0, 0));
		panel_game_data.add(panel_board, "panel_board");
		panel_board.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		JLabel lblDasSpielfeld = new JLabel("Das Spielfeld");
		lblDasSpielfeld.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_board.add(lblDasSpielfeld, "cell 0 0,alignx center");
		
		BoardPanel panel_game_board = new BoardPanel();
		panel_game_board.setBackground(new Color(0, 0, 0, 0));
		panel_board.add(panel_game_board, "cell 0 1,grow");
		panel_game_board.updateBoardImage(game.getBoard().displayBoard());
		
		JButton btnbersicht = new JButton("Übersicht");
		btnbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_game_board.showOtherView();
			}
		});
		btnbersicht.setBackground(new Color(0, 0, 0, 0));
		panel_board.add(btnbersicht, "cell 0 2,alignx center");
		
		JPanel panel_turns = new JPanel();
		panel_turns.setBackground(new Color(0, 0, 0, 0));
		panel_game_data.add(panel_turns, "panel_turns");
		panel_turns.setLayout(new MigLayout("", "[200px,grow][200px,grow]", "[][grow]"));
		
		JLabel lblGespielteRunden = new JLabel("Gespielte Runden");
		lblGespielteRunden.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_turns.add(lblGespielteRunden, "cell 0 0,alignx center");
		
		JLabel lblRundenboni = new JLabel("Rundenboni");
		lblRundenboni.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_turns.add(lblRundenboni, "cell 1 0,alignx center");
		
		JScrollPane scrollPane_turns = new JScrollPane();
		scrollPane_turns.setBackground(new Color(0, 0, 0, 0));
		scrollPane_turns.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane_turns.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				repaint();
			}
		});
		panel_turns.add(scrollPane_turns, "cell 0 1,grow");
		
		JPanel panel_turn_goals = new JPanel();
		panel_turn_goals.setBackground(new Color(0, 0, 0, 0));
		scrollPane_turns.setViewportView(panel_turn_goals);
		addGameTurns(panel_turn_goals);
		
		JScrollPane scrollPane_bonuses = new JScrollPane();
		scrollPane_bonuses.setBackground(new Color(0, 0, 0, 0));
		scrollPane_bonuses.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane_bonuses.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				repaint();
			}
		});
		panel_turns.add(scrollPane_bonuses, "cell 1 1,grow");
		
		JPanel panel_turn_bonuses = new JPanel();
		panel_turn_bonuses.setBackground(new Color(0, 0, 0, 0));
		scrollPane_bonuses.setViewportView(panel_turn_bonuses);
		addTurnBonuses(panel_turn_bonuses);
		
		ImagePanel panel_image_2 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/claptrap_4.png"));
		panel_image_2.setCentered(true);
		panel_image_2.setAdaptSizeKeepProportion(true);
		panel_image_2.setBackground(new Color(0, 0, 0, 0));
		panel.add(panel_image_2, "cell 2 3,grow");
		
		JPanel panel_buttons = new JPanel();
		panel_buttons.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_buttons.setBackground(new Color(0, 0, 0, 0));
		panel.add(panel_buttons, "cell 1 5,grow");
		panel_buttons.setLayout(new MigLayout("", "[grow][][][][][][grow]", "[]"));
		
		JButton btnErgebnisse = new JButton("Ergebnisse");
		btnErgebnisse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout layout = (CardLayout) panel_game_data.getLayout();
				layout.show(panel_game_data, "panel_results");
				repaint();
			}
		});
		btnErgebnisse.setBackground(new Color(0, 0, 0, 0));
		panel_buttons.add(btnErgebnisse, "cell 1 0");
		
		JButton btnSpielfeld = new JButton("Spielfeld");
		btnSpielfeld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) panel_game_data.getLayout();
				layout.show(panel_game_data, "panel_board");
				repaint();
			}
		});
		btnSpielfeld.setBackground(new Color(0, 0, 0, 0));
		panel_buttons.add(btnSpielfeld, "cell 2 0");
		
		JButton btnRunden = new JButton("Runden");
		btnRunden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) panel_game_data.getLayout();
				layout.show(panel_game_data, "panel_turns");
				repaint();
			}
		});
		btnRunden.setBackground(new Color(0, 0, 0, 0));
		panel_buttons.add(btnRunden, "cell 3 0");
		
		JButton btnStatistiken = new JButton("Statistiken");
		btnStatistiken.setBackground(new Color(0, 0, 0, 0));
		btnStatistiken.setEnabled(false);
		panel_buttons.add(btnStatistiken, "cell 4 0");
		
		JButton btnZurckZumHauptmen = new JButton("Zurück zum Hauptmenü");
		btnZurckZumHauptmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConfirmDialog("Zum Main Menü zurückkehren?", GameEndFrame.this, 0).setVisible(true);//confirm type is unused here
			}
		});
		btnZurckZumHauptmen.setBackground(new Color(0, 0, 0, 0));
		panel_buttons.add(btnZurckZumHauptmen, "cell 5 0");
	}
	
	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			backToMainMenu();
		}
	}
	
	private void backToMainMenu() {
		new MainMenuFrame(client).setVisible(true);
		dispose();
	}
	
	private void addPlayersPoints(JPanel panel) {
		//set the layout for the panel
		String layout = "";
		for (int i = 0; i < userPoints.size(); i++) {
			layout += "[25px]";
		}
		panel.setLayout(new MigLayout("", "[175px:n:175px][75px:n:75px,center][100px:n:100px,center][grow]", layout));
		//add the components to the panel
		panel.removeAll();
		Font font = new Font("Tahoma", Font.BOLD, 14);
		for (int i = 0; i < userPoints.size(); i++) {
			JLabel rank = new JLabel(Integer.toString(i+1));
			JLabel points = new JLabel(Integer.toString(userPoints.get(i).getPoints()));
			JLabel player = new JLabel(userPoints.get(i).getUser().getUsername());
			rank.setFont(font);
			points.setFont(font);
			player.setFont(font);
			panel.add(rank, "cell 1 " + i);
			panel.add(points, "cell 2 " + i);
			panel.add(player, "cell 3 " + i);
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
	private void addTurnBonuses(JPanel panel) {
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
			panel.add(imagePanel, "cell 0 " + i + ",grow");
		}
	}
}