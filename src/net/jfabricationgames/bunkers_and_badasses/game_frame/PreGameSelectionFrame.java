package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameTurnManager;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreGameSelectionFrame extends JFrame {
	
	private static final long serialVersionUID = 3805066039018442763L;
	
	private JPanel contentPane;
	private JPanel panel_turn_goals;
	
	private Game game;
	private JPanel panel_turn_bonuses;
	private JPanel panel_player_order;
	
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
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		contentPane.add(panel_6, "cell 0 0,grow");
		panel_6.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_6.add(panel, "name_14012148608106");
		panel.setBackground(Color.GRAY);
		panel.setLayout(new MigLayout("", "[350px,grow][350px,grow][500px,grow]", "[grow]"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, "cell 0 0,grow");
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
		panel.add(panel_4, "cell 1 0,grow");
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
		panel.add(panel_2, "cell 2 0,grow");
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
		JList<String> list_skill_profiles = new JList<String>(model);
		list_skill_profiles.setBackground(Color.LIGHT_GRAY);
		scrollPane_3.setViewportView(list_skill_profiles);
		model.addElement("Profil 1");
		model.addElement("Profil 2");
		model.addElement("Profil 3");
		model.addElement("Profil 4");
		model.addElement("Profil 5");
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel_5.add(scrollPane_4, "cell 1 2,grow");
		
		JTextArea txtrSkillProfile = new JTextArea();
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
			ImagePanel imagePanel = new ImagePanel(game.getTurnManager().getGameTurnGoalManager().getTurnGoal(i).getImage());
			imagePanel.setCentered(true);
			imagePanel.setAdaptSizeKeepProportion(true);
			imagePanel.setBackground(Color.GRAY);
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
			ImagePanel imagePanel = new ImagePanel(bonusCards.get(i).getImage());
			imagePanel.setCentered(true);
			imagePanel.setAdaptSizeKeepProportion(true);
			imagePanel.setBackground(Color.GRAY);
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
	
	/**
	 * Select a skill profile.
	 */
	private void selectProfile() {
		//TODO
	}
	private void startTroopPositioning() {
		//TODO
	}
}