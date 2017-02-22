package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.miginfocom.swing.MigLayout;

public class TurnGoalTurnBonusDialog extends JDialog {
	
	private static final long serialVersionUID = 852713388137185228L;
	
	private static final String TURN_BONUS_PANEL = "turn_bonus";
	private static final String TURN_GOAL_PANEL = "turn_goal";
	
	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	
	public TurnGoalTurnBonusDialog(Game game) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnGoalTurnBonusDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Runden Ziele");
		setBounds(100, 100, 1000, 350);
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
				panel.add(panel_turn_goals, "name_13767850722345");
				panel_turn_goals.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
				{
					JLabel lblRundenZiele = new JLabel("Runden Ziele:");
					lblRundenZiele.setFont(new Font("Tahoma", Font.BOLD, 20));
					panel_turn_goals.add(lblRundenZiele, "cell 0 0,alignx center");
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					panel_turn_goals.add(scrollPane, "cell 0 1,grow");
					{
						JPanel panel_1 = new JPanel();
						panel_1.setBackground(Color.GRAY);
						scrollPane.setViewportView(panel_1);
						panel_1.setLayout(new MigLayout("", "[]", "[]"));
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
				panel.add(panel_turn_bonuses, "name_13772292203048");
				panel_turn_bonuses.setLayout(new MigLayout("", "[grow][grow]", "[][grow][]"));
				{
					JLabel lblRundenBoni = new JLabel("Runden Boni:");
					lblRundenBoni.setFont(new Font("Tahoma", Font.BOLD, 20));
					panel_turn_bonuses.add(lblRundenBoni, "cell 0 0 2 1,alignx center");
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					panel_turn_bonuses.add(scrollPane, "cell 0 1 2 1,grow");
					{
						JPanel panel_1 = new JPanel();
						panel_1.setBackground(Color.GRAY);
						scrollPane.setViewportView(panel_1);
						panel_1.setLayout(new MigLayout("", "[]", "[]"));
					}
				}
				{
					JButton btnBonusAuswhlen = new JButton("Bonus Ausw\u00E4hlen");
					btnBonusAuswhlen.setEnabled(false);
					btnBonusAuswhlen.setBackground(Color.GRAY);
					panel_turn_bonuses.add(btnBonusAuswhlen, "cell 0 2");
				}
				{
					JButton btnRundenZieleAnzeigen = new JButton("Runden Ziele Anzeigen");
					btnRundenZieleAnzeigen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							showPanel(TURN_GOAL_PANEL);
						}
					});
					btnRundenZieleAnzeigen.setBackground(Color.GRAY);
					panel_turn_bonuses.add(btnRundenZieleAnzeigen, "cell 1 2,alignx right");
				}
			}
		}
	}
	
	private void showPanel(String name) {
		CardLayout layout = (CardLayout) panel.getLayout();
		layout.show(panel, name);
	}
}