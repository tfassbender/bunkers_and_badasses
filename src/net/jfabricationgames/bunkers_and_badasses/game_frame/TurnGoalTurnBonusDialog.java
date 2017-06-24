package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameTurnManager;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardPanel;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusCardSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnGoalCardPanel;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class TurnGoalTurnBonusDialog extends JDialog implements TurnBonusCardSelectionListener {
	
	private static final long serialVersionUID = 852713388137185228L;
	
	public static final String TURN_BONUS_PANEL = "turn_bonus";
	public static final String TURN_GOAL_PANEL = "turn_goal";
	
	private Game game;
	private boolean selectable;
	
	private TurnBonus selectedTurnBonus;
	
	private TurnBonusSelectionListener listener;
	
	private DefaultListModel<User> model;
	
	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	private JPanel panel_user_bonus;
	private JPanel panel_turn_goal_list;
	private JPanel panel_choosable_bonuses;
	private JButton btnBonusAuswhlen;
	
	public TurnGoalTurnBonusDialog(Game game, boolean selectable, boolean turnBonusChangeEnabled) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		this.selectable = selectable;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnGoalTurnBonusDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Runden Ziele / Boni - Bunkers and Badasses");
		setBounds(100, 100, 1000, 300);
		setMinimumSize(new Dimension(1000, 300));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			panel = new JPanel();
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new CardLayout(0, 0));
			{
				JPanel panel_turn_goals = new JPanel();
				panel_turn_goals.setBackground(Color.GRAY);
				panel.add(panel_turn_goals, "turn_goal");
				panel_turn_goals.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
				{
					JLabel lblRundenZiele = new JLabel("Runden Ziele:");
					lblRundenZiele.setFont(new Font("Tahoma", Font.BOLD, 20));
					panel_turn_goals.add(lblRundenZiele, "cell 0 0,alignx center");
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
					panel_turn_goals.add(scrollPane, "cell 0 1,grow");
					{
						panel_turn_goal_list = new JPanel();
						panel_turn_goal_list.setBackground(Color.GRAY);
						scrollPane.setViewportView(panel_turn_goal_list);
						panel_turn_goal_list.setLayout(new MigLayout("", "[]", "[]"));
					}
				}
				{
					JButton btnRundenBoniAnzeigen = new JButton("Runden Boni Anzeigen");
					btnRundenBoniAnzeigen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							showPanel(TURN_BONUS_PANEL);
						}
					});
					btnRundenBoniAnzeigen.setBackground(Color.GRAY);
					panel_turn_goals.add(btnRundenBoniAnzeigen, "cell 0 2,alignx right");
				}
			}
			{
				JPanel panel_turn_bonuses = new JPanel();
				panel_turn_bonuses.setBackground(Color.GRAY);
				panel.add(panel_turn_bonuses, "turn_bonus");
				panel_turn_bonuses.setLayout(new MigLayout("", "[600px,grow][10px:10px][200px,grow][300px,grow]", "[][][grow][]"));
				{
					JLabel lblRundenBoni = new JLabel("Runden Boni:");
					lblRundenBoni.setFont(new Font("Tahoma", Font.BOLD, 20));
					panel_turn_bonuses.add(lblRundenBoni, "cell 0 0 4 1,alignx center");
				}
				{
					JLabel lblWhlbareBoni = new JLabel("W\u00E4hlbare Boni:");
					lblWhlbareBoni.setFont(new Font("Tahoma", Font.BOLD, 16));
					panel_turn_bonuses.add(lblWhlbareBoni, "cell 0 1,alignx center");
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
					panel_turn_bonuses.add(scrollPane, "cell 0 2,grow");
					{
						panel_choosable_bonuses = new JPanel();
						panel_choosable_bonuses.setBackground(Color.GRAY);
						scrollPane.setViewportView(panel_choosable_bonuses);
						panel_choosable_bonuses.setLayout(new MigLayout("", "[]", "[]"));
					}
				}
				{
					JLabel lblBoniDerSpieler = new JLabel("Boni der Spieler:");
					lblBoniDerSpieler.setFont(new Font("Tahoma", Font.BOLD, 16));
					panel_turn_bonuses.add(lblBoniDerSpieler, "cell 2 1 2 1,alignx center");
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					panel_turn_bonuses.add(scrollPane, "cell 2 2,grow");
					{
						model = new DefaultListModel<User>();
						JList<User> list = new JList<User>(model);
						list.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								User user = list.getSelectedValue();
								showUsersBonus(user);
							}
						});
						list.setBackground(Color.LIGHT_GRAY);
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						list.setFont(new Font("Tahoma", Font.PLAIN, 12));
						scrollPane.setViewportView(list);
					}
				}
				{
					panel_user_bonus = new JPanel();
					panel_user_bonus.setBackground(Color.GRAY);
					panel_turn_bonuses.add(panel_user_bonus, "cell 3 2,grow");
					panel_user_bonus.setLayout(new BorderLayout(0, 0));
				}
				{
					btnBonusAuswhlen = new JButton("Bonus Ausw\u00E4hlen");
					btnBonusAuswhlen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							chooseNewTurnBonus();
						}
					});
					btnBonusAuswhlen.setEnabled(turnBonusChangeEnabled);
					btnBonusAuswhlen.setBackground(Color.GRAY);
					panel_turn_bonuses.add(btnBonusAuswhlen, "cell 0 3");
				}
				{
					JButton btnRundenZieleAnzeigen = new JButton("Runden Ziele Anzeigen");
					btnRundenZieleAnzeigen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							showPanel(TURN_GOAL_PANEL);
						}
					});
					btnRundenZieleAnzeigen.setBackground(Color.GRAY);
					panel_turn_bonuses.add(btnRundenZieleAnzeigen, "cell 3 3,alignx right");
				}
			}
		}
		
		addTurnGoals();
		addTurnBonuses();
		addUsers();
	}
	
	@Override
	public void turnBonusCardSelected(TurnBonus bonus) {
		selectedTurnBonus = bonus;
	}
	
	public void showPanel(String name) {
		CardLayout layout = (CardLayout) panel.getLayout();
		layout.show(panel, name);
	}
	
	private void addTurnGoals() {
		panel_turn_goal_list.removeAll();
		int turns = GameTurnManager.getNumTurns();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < turns; i++) {
			sb.append("[200px]");
		}
		panel_turn_goal_list.setLayout(new MigLayout("", sb.toString(), "[30px][grow]"));
		Font font = new Font("Tahoma", Font.BOLD, 18);
		for (int i = 0; i < turns; i++) {
			JLabel label = new JLabel(Integer.toString(i+1));
			label.setFont(font);
			panel_turn_goal_list.add(label, "cell " + i + " 0,alignx center");
			TurnGoalCardPanel imagePanel = new TurnGoalCardPanel();
			if (game.getTurnManager().getGameTurnGoalManager().turnGoalsAvialable()) {
				imagePanel.setTurnGoal(game.getTurnManager().getGameTurnGoalManager().getTurnGoal(i));
			}
			panel_turn_goal_list.add(imagePanel, "cell " + i + " 1,grow");
		}
	}
	private void addTurnBonuses() {
		panel_choosable_bonuses.removeAll();
		List<TurnBonus> bonusCards = game.getGameTurnBonusManager().getBonuses();
		if (bonusCards != null) {
			int bonuses = bonusCards.size();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bonuses; i++) {
				sb.append("[200px]");
			}
			panel_choosable_bonuses.setLayout(new MigLayout("", sb.toString(), "[grow]"));
			for (int i = 0; i < bonuses; i++) {
				TurnBonusCardPanel imagePanel = new TurnBonusCardPanel(bonusCards.get(i));
				if (selectable) {
					imagePanel.setSelectionListener(this);
					imagePanel.setChangeColorOnFocusEvent(true);
				}
				panel_choosable_bonuses.add(imagePanel, "cell " + i + " 0,grow");
			}
		}
	}
	private void addUsers() {
		model.removeAllElements();
		for (User user : game.getPlayers()) {
			model.addElement(user);
		}
		repaint();
	}
	
	/**
	 * Choose a turn bonus for the user and change it in the games manager.
	 */
	private void chooseNewTurnBonus() {
		if (selectedTurnBonus != null) {
			listener.receiveTurnBonusSelection(selectedTurnBonus);
			setVisible(false);
		}
	}
	
	/**
	 * Show the users bonus on the panel.
	 */
	private void showUsersBonus(User user) {
		panel_user_bonus.removeAll();
		panel_user_bonus.add(new TurnBonusCardPanel(game.getGameTurnBonusManager().getUsersBonus(user)), BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	/**
	 * Update all shown elements.
	 */
	public void update() {
		addTurnGoals();
		addTurnBonuses();
		revalidate();
		repaint();
	}
	
	public void setTurnBonusSelectable(boolean selectable, TurnBonusSelectionListener listener) {
		if (selectable && listener == null) {
			throw new IllegalArgumentException("Can't enable selection without listener.");
		}
		this.selectable = selectable;
		this.listener = listener;
		btnBonusAuswhlen.setEnabled(selectable);
	}
	
	public boolean isTurnBonusSelectable() {
		return selectable;
	}
	
	public boolean isTurnBonusChangeEnabled() {
		return btnBonusAuswhlen.isEnabled();
	}
	public void setTurnBonusChangeEnabled(boolean enabled) {
		btnBonusAuswhlen.setEnabled(enabled);
	}
}