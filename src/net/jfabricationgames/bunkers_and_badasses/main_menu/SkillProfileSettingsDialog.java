package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfile;
import net.jfabricationgames.bunkers_and_badasses.game.SkillProfileManager;
import net.jfabricationgames.bunkers_and_badasses.game_communication.SkillProfileTransferMessage;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameFrame;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class SkillProfileSettingsDialog extends JFrame {
	
	private static final long serialVersionUID = -6176020670400001990L;
	
	private static final int SKILL_ERIDIUM = 1;
	private static final int SKILL_CREDITS = 2;
	private static final int SKILL_AMMO = 3;
	private static final int SKILL_ERIDIUM_BUILDING = 4;
	private static final int SKILL_CREDITS_BUILDING = 5;
	private static final int SKILL_AMMO_BUILDING = 6;
	private static final int SKILL_HERO = 7;
	private static final int SKILL_POINTS = 8;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textField;
	
	private int startSkillPoints;
	
	private int selectedSkillIndex;
	
	private SkillTreeNode root;
	
	private Map<JCheckBox, SkillTreeNode> skillMap;
	private Map<String, SkillTreeNode> nameMap;
	
	private JFGClient client;
	
	private SkillProfileManager profileManager;
	
	public SkillProfileSettingsDialog(MainMenuFrame callingFrame, JFGClient client, SkillProfileManager profileManager) {
		setTitle("Bunkers and Badasses - Skill-Profil");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1100, 550);
		setMinimumSize(new Dimension(1100, 550));
		setLocationRelativeTo(callingFrame);
		
		this.client = client;
		this.profileManager = profileManager;
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		startSkillPoints = Game.getGameVariableStorage().getSkillPoints();
		
		root = new SkillTreeNode(0, new JCheckBox(), 0, 0);//the checkbox is a dummy just to avoid nullpointers
		root.setEnabled(true);
		root.setSelected(true);
		
		skillMap = new HashMap<JCheckBox, SkillTreeNode>();
		nameMap = new HashMap<String, SkillTreeNode>();
		
		//create all skill tree nodes to combine them
		SkillTreeNode e1;
		SkillTreeNode e2;
		SkillTreeNode e3;
		SkillTreeNode eb1;
		SkillTreeNode eb2;
		SkillTreeNode eb3;
		SkillTreeNode h1;
		SkillTreeNode h2;
		SkillTreeNode h3;
		SkillTreeNode c1;
		SkillTreeNode c2;
		SkillTreeNode c3;
		SkillTreeNode cb1;
		SkillTreeNode cb2;
		SkillTreeNode cb3;
		SkillTreeNode a1;
		SkillTreeNode a2;
		SkillTreeNode a3;
		SkillTreeNode ab1;
		SkillTreeNode ab2;
		SkillTreeNode ab3;
		SkillTreeNode p1;
		SkillTreeNode p2;
		SkillTreeNode p3;
		SkillTreeNode p4;
		SkillTreeNode p5;
		
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[200px,grow][250px,grow][250px,grow][150px,grow]", "[][10px:n:10px][200px,grow][300px,grow][grow]"));
			{
				JLabel lblSkills = new JLabel("Skill Profil");
				lblSkills.setFont(new Font("Tahoma", Font.PLAIN, 24));
				panel.add(lblSkills, "cell 0 0 4 1,alignx center");
			}
			{
				JPanel panel_skills = new JPanel();
				panel_skills.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				panel_skills.setBackground(Color.GRAY);
				panel.add(panel_skills, "cell 0 2 3 2,grow");
				panel_skills.setLayout(new MigLayout("", "[center][10px][center][center][center][center][center][grow][:60px:60px,center][center][center][center][center][center][grow][center][10px]", "[][][5px][][25px][][25px][][25px][][25px][]"));
				{
					JLabel lblEridium = new JLabel("Eridium");
					lblEridium.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblEridium, "cell 2 0 5 1");
				}
				{
					JLabel lblCreditsammo = new JLabel("Credits/Munition");
					lblCreditsammo.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblCreditsammo, "cell 8 0 6 1,alignx center");
				}
				{
					JLabel lblPunkte = new JLabel("Punkte");
					lblPunkte.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblPunkte, "cell 15 0,alignx center");
				}
				{
					JLabel lblLevel = new JLabel("Level");
					lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblLevel, "cell 0 1");
				}
				{
					JLabel label = new JLabel("1");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 3");
				}
				{
					JCheckBox chckbxEridium = new JCheckBox("Eridium 1");
					chckbxEridium.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxEridium.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium, "cell 3 3 2 1");
					e1 = new SkillTreeNode(1, chckbxEridium, SKILL_ERIDIUM, 1);
					skillMap.put(chckbxEridium, e1);
				}
				{
					JCheckBox chckbxCredits = new JCheckBox("Credits 1");
					chckbxCredits.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxCredits.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits, "cell 10 3");
					c1 = new SkillTreeNode(1, chckbxCredits, SKILL_CREDITS, 1);
					skillMap.put(chckbxCredits, c1);
				}
				{
					JCheckBox chckbxPunkte = new JCheckBox("Punkte 1");
					chckbxPunkte.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxPunkte.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte, "cell 15 3");
					p1 = new SkillTreeNode(1, chckbxPunkte, SKILL_POINTS, 1);
					skillMap.put(chckbxPunkte, p1);
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 3 4 2 1");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dr.png")));
					panel_skills.add(label, "cell 5 4,alignx right");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dl.png")));
					panel_skills.add(label, "cell 9 4,alignx right");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 10 4");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dr.png")));
					panel_skills.add(label, "cell 11 4,alignx left");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 15 4");
				}
				{
					JLabel label = new JLabel("2");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 5");
				}
				{
					JCheckBox chckbxEridium_1 = new JCheckBox("Eridium 2");
					chckbxEridium_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxEridium_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium_1, "cell 3 5 2 1");
					e2 = new SkillTreeNode(2, chckbxEridium_1, SKILL_ERIDIUM, 2);
					skillMap.put(chckbxEridium_1, e2);
				}
				{
					JCheckBox chckbxEridiumGebude = new JCheckBox("E. Geb\u00E4ude 1");
					chckbxEridiumGebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxEridiumGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude, "cell 5 5 2 1");
					eb1 = new SkillTreeNode(2, chckbxEridiumGebude, SKILL_ERIDIUM_BUILDING, 1);
					skillMap.put(chckbxEridiumGebude, eb1);
				}
				{
					JCheckBox chckbxCGebude = new JCheckBox("C. Geb\u00E4ude 1");
					chckbxCGebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxCGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude, "cell 8 5 2 1");
					cb1 = new SkillTreeNode(2, chckbxCGebude, SKILL_CREDITS_BUILDING, 1);
					skillMap.put(chckbxCGebude, cb1);
				}
				{
					JCheckBox chckbxCredits_1 = new JCheckBox("Credits 2");
					chckbxCredits_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxCredits_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits_1, "cell 10 5");
					c2 = new SkillTreeNode(2, chckbxCredits_1, SKILL_CREDITS, 2);
					skillMap.put(chckbxCredits_1, c2);
				}
				{
					JCheckBox chckbxMunition = new JCheckBox("Munition 1");
					chckbxMunition.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxMunition.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition, "cell 11 5");
					a1 = new SkillTreeNode(2, chckbxMunition, SKILL_AMMO, 1);
					skillMap.put(chckbxMunition, a1);
				}
				{
					JCheckBox chckbxPunkte_1 = new JCheckBox("Punkte 2");
					chckbxPunkte_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxPunkte_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_1, "cell 15 5");
					p2 = new SkillTreeNode(2, chckbxPunkte_1, SKILL_POINTS, 2);
					skillMap.put(chckbxPunkte_1, p2);
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dl.png")));
					panel_skills.add(label, "cell 2 6,alignx right");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 3 6 2 1");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dr.png")));
					panel_skills.add(label, "cell 5 6");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 6 6");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 8 6");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dl.png")));
					panel_skills.add(label, "cell 9 6,alignx right");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 10 6");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 11 6");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dr.png")));
					panel_skills.add(label, "cell 12 6 2 1,alignx left");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 15 6");
				}
				{
					JLabel label = new JLabel("3");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 7");
				}
				{
					JCheckBox chckbxHeld = new JCheckBox("Held 1");
					chckbxHeld.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxHeld.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld, "cell 2 7");
					h1 = new SkillTreeNode(3, chckbxHeld, SKILL_HERO, 1);
					skillMap.put(chckbxHeld, h1);
				}
				{
					JCheckBox chckbxEridium_2 = new JCheckBox("Eridium 3");
					chckbxEridium_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxEridium_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium_2, "cell 3 7 2 1");
					e3 = new SkillTreeNode(3, chckbxEridium_2, SKILL_ERIDIUM, 3);
					skillMap.put(chckbxEridium_2, e3);
				}
				{
					JCheckBox chckbxEridiumGebude_1 = new JCheckBox("E. Geb\u00E4ude 2");
					chckbxEridiumGebude_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxEridiumGebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude_1, "cell 5 7 2 1");
					eb2 = new SkillTreeNode(3, chckbxEridiumGebude_1, SKILL_ERIDIUM_BUILDING, 2);
					skillMap.put(chckbxEridiumGebude_1, eb2);
				}
				{
					JCheckBox chckbxCGebude_1 = new JCheckBox("C. Geb\u00E4ude 2");
					chckbxCGebude_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxCGebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude_1, "cell 8 7 2 1");
					cb2 = new SkillTreeNode(3, chckbxCGebude_1, SKILL_CREDITS_BUILDING, 2);
					skillMap.put(chckbxCGebude_1, cb2);
				}
				{
					JCheckBox chckbxCredits_2 = new JCheckBox("Credits 3");
					chckbxCredits_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxCredits_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits_2, "cell 10 7");
					c3 = new SkillTreeNode(3, chckbxCredits_2, SKILL_CREDITS, 3);
					skillMap.put(chckbxCredits_2, c3);
				}
				{
					JCheckBox chckbxMunition_1 = new JCheckBox("Munition 2");
					chckbxMunition_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxMunition_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition_1, "cell 11 7");
					a2 = new SkillTreeNode(3, chckbxMunition_1, SKILL_AMMO, 2);
					skillMap.put(chckbxMunition_1, a2);
				}
				{
					JCheckBox chckbxMGebude = new JCheckBox("M. Geb\u00E4ude 1");
					chckbxMGebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxMGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxMGebude, "cell 12 7 2 1");
					ab1 = new SkillTreeNode(3, chckbxMGebude, SKILL_AMMO_BUILDING, 1);
					skillMap.put(chckbxMGebude, ab1);
				}
				{
					JCheckBox chckbxPunkte_2 = new JCheckBox("Punkte 3");
					chckbxPunkte_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxPunkte_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_2, "cell 15 7");
					p3 = new SkillTreeNode(3, chckbxPunkte_2, SKILL_POINTS, 3);
					skillMap.put(chckbxPunkte_2, p3);
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 2 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dl.png")));
					panel_skills.add(label, "cell 3 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dr.png")));
					panel_skills.add(label, "cell 4 8,alignx right");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 6 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 8 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dl.png")));
					panel_skills.add(label, "cell 10 8,alignx left");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 11 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_dr.png")));
					panel_skills.add(label, "cell 12 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 13 8");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 15 8");
				}
				{
					JLabel label = new JLabel("4");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 9");
				}
				{
					JCheckBox chckbxHeld_1 = new JCheckBox("Held 2");
					chckbxHeld_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxHeld_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld_1, "cell 2 9");
					h2 = new SkillTreeNode(4, chckbxHeld_1, SKILL_HERO, 2);
					skillMap.put(chckbxHeld_1, h2);
				}
				{
					JCheckBox chckbxEridiumGebude_2 = new JCheckBox("E. Geb\u00E4ude 3");
					chckbxEridiumGebude_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxEridiumGebude_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude_2, "cell 5 9 2 1");
					eb3 = new SkillTreeNode(4, chckbxEridiumGebude_2, SKILL_ERIDIUM_BUILDING, 3);
					skillMap.put(chckbxEridiumGebude_2, eb3);
				}
				{
					JCheckBox chckbxCGebude_2 = new JCheckBox("C. Geb\u00E4ude 3");
					chckbxCGebude_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxCGebude_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude_2, "cell 8 9 2 1");
					cb3 = new SkillTreeNode(4, chckbxCGebude_2, SKILL_CREDITS_BUILDING, 3);
					skillMap.put(chckbxCGebude_2, cb3);
				}
				{
					JCheckBox chckbxMunition_2 = new JCheckBox("Munition 3");
					chckbxMunition_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxMunition_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition_2, "cell 11 9");
					a3 = new SkillTreeNode(4, chckbxMunition_2, SKILL_AMMO, 3);
					skillMap.put(chckbxMunition_2, a3);
				}
				{
					JCheckBox chckbxMgebude = new JCheckBox("M. Geb\u00E4ude 2");
					chckbxMgebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxMgebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxMgebude, "cell 12 9 2 1");
					ab2 = new SkillTreeNode(4, chckbxMgebude, SKILL_AMMO_BUILDING, 2);
					skillMap.put(chckbxMgebude, ab2);
				}
				{
					JCheckBox chckbxPunkte_3 = new JCheckBox("Punkte 4");
					chckbxPunkte_3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxPunkte_3.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_3, "cell 15 9");
					p4 = new SkillTreeNode(4, chckbxPunkte_3, SKILL_POINTS, 4);
					skillMap.put(chckbxPunkte_3, p4);
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 2 10");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 13 10");
				}
				{
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/main_menu/line_d.png")));
					panel_skills.add(label, "cell 15 10");
				}
				{
					JLabel label = new JLabel("5");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 11");
				}
				{
					JCheckBox chckbxHeld_2 = new JCheckBox("Held 3");
					chckbxHeld_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxHeld_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld_2, "cell 2 11");
					h3 = new SkillTreeNode(5, chckbxHeld_2, SKILL_HERO, 3);
					skillMap.put(chckbxHeld_2, h3);
				}
				{
					JCheckBox chckbxMgebude_1 = new JCheckBox("M. Geb\u00E4ude 3");
					chckbxMgebude_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxMgebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxMgebude_1, "cell 12 11 2 1");
					ab3 = new SkillTreeNode(5, chckbxMgebude_1, SKILL_AMMO_BUILDING, 3);
					skillMap.put(chckbxMgebude_1, ab3);
				}
				{
					JCheckBox chckbxPunkte_4 = new JCheckBox("Punkte 5");
					chckbxPunkte_4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).changeSelected();
							enableSkills();
						}
					});
					chckbxPunkte_4.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_4, "cell 15 11");
					p5 = new SkillTreeNode(5, chckbxPunkte_4, SKILL_POINTS, 5);
					skillMap.put(chckbxPunkte_4, p5);
				}
			}
			{
				JPanel panel_info = new JPanel();
				panel_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				panel_info.setBackground(Color.GRAY);
				panel.add(panel_info, "cell 3 2,grow");
				panel_info.setLayout(new MigLayout("", "[][grow]", "[][10px][][10px][][10px][][grow]"));
				{
					JLabel lblInfo = new JLabel("Info:");
					lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_info.add(lblInfo, "cell 0 0 2 1,alignx center");
				}
				{
					JLabel lblSkillpunktebrig = new JLabel("Skillpunkte \u00DCbrig:");
					lblSkillpunktebrig.setFont(new Font("Tahoma", Font.PLAIN, 14));
					panel_info.add(lblSkillpunktebrig, "cell 0 2,alignx trailing");
				}
				{
					textField = new JTextField();
					textField.setEditable(false);
					textField.setBackground(Color.LIGHT_GRAY);
					textField.setHorizontalAlignment(SwingConstants.CENTER);
					panel_info.add(textField, "cell 1 2,growx");
					textField.setColumns(10);
				}
				{
					JLabel lblSkillProfilNummer = new JLabel("Skill Profil Nummer:");
					lblSkillProfilNummer.setFont(new Font("Tahoma", Font.PLAIN, 14));
					panel_info.add(lblSkillProfilNummer, "cell 0 4");
				}
				{
					JSpinner spinner = new JSpinner();
					spinner.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							selectedSkillIndex = (Integer) spinner.getValue();
							selectedSkillIndex -= 1;//set first index to 0
							setSelectedProfile(selectedSkillIndex);
						}
					});
					spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
					panel_info.add(spinner, "cell 1 4,growx");
				}
				{
					JButton btnHilfe = new JButton("Hilfe");
					btnHilfe.setBackground(Color.GRAY);
					panel_info.add(btnHilfe, "cell 0 6 2 1,alignx center");
				}
			}
			{
				ImagePanel panel_img = new ImagePanel(GameFrame.getImageLoader().loadImage("main_menu/angel_1.png"));
				panel_img.setAdaptSizeKeepProportion(true);
				panel_img.setCentered(true);
				panel_img.setBackground(Color.GRAY);
				panel.add(panel_img, "cell 3 3,grow");
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBackground(Color.GRAY);
				panel.add(panel_1, "cell 0 4 4 1,grow");
				panel_1.setLayout(new MigLayout("", "[grow][][][grow]", "[]"));
				{
					JButton btnOk = new JButton("Ok");
					btnOk.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							submitSkills();
							dispose();
						}
					});
					panel_1.add(btnOk, "cell 1 0");
					btnOk.setBackground(Color.GRAY);
				}
				{
					JButton btnAbbrechen = new JButton("Abbrechen");
					btnAbbrechen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					panel_1.add(btnAbbrechen, "cell 2 0");
					btnAbbrechen.setBackground(Color.GRAY);
				}
			}
		}
		
		//put the skill tree nodes in a map
		nameMap.put("e1", e1);
		nameMap.put("e2", e2);
		nameMap.put("e3", e3);
		nameMap.put("eb1", eb1);
		nameMap.put("eb2", eb2);
		nameMap.put("eb3", eb3);
		nameMap.put("h1", h1);
		nameMap.put("h2", h2);
		nameMap.put("h3", h3);
		nameMap.put("c1", c1);
		nameMap.put("c2", c2);
		nameMap.put("c3", c3);
		nameMap.put("cb1", cb1);
		nameMap.put("cb2", cb2);
		nameMap.put("cb3", cb3);
		nameMap.put("a1", a1);
		nameMap.put("a2", a2);
		nameMap.put("a3", a3);
		nameMap.put("ab1", ab1);
		nameMap.put("ab2", ab2);
		nameMap.put("ab3", ab3);
		nameMap.put("p1", p1);
		nameMap.put("p2", p2);
		nameMap.put("p3", p3);
		nameMap.put("p4", p4);
		nameMap.put("p5", p5);
		
		//build the tree
		//add roots next
		root.addNext(e1);
		root.addNext(c1);
		root.addNext(p1);
		//line 1 next
		e1.addNext(e2);
		e1.addNext(eb1);
		c1.addNext(cb1);
		c1.addNext(c2);
		c1.addNext(a1);
		p1.addNext(p2);
		//line 2 next
		e2.addNext(h1);
		e2.addNext(e3);
		e2.addNext(eb2);
		eb1.addNext(eb2);
		cb1.addNext(cb2);
		c2.addNext(cb2);
		c2.addNext(c3);
		a1.addNext(a2);
		a1.addNext(ab1);
		p2.addNext(p3);
		//line 3 next
		h1.addNext(h2);
		e3.addNext(h2);
		e3.addNext(eb3);
		eb2.addNext(eb3);
		cb2.addNext(cb3);
		c3.addNext(cb3);
		a2.addNext(a3);
		a2.addNext(ab2);
		ab1.addNext(ab2);
		p3.addNext(p4);
		//line 4 next
		h2.addNext(h3);
		ab2.addNext(ab3);
		p4.addNext(p5);
		
		//printNodes();
		
		enableSkills();
		
		selectedSkillIndex = 0;
		addSkills(profileManager.getSkillProfiles()[0]);
		
		//printNodes();
	}
	
	/*private void printNodes() {
		for (String name : nameMap.keySet()) {
			System.out.println(nameMap.get(name));
		}
	}*/
	/*private void printNodesTree(SkillTreeNode node) {
		System.out.println(node);
		for (SkillTreeNode next : node.next) {
			printNodes(next);
		}
		System.out.println("");
	}*/
	
	private void addSkills(SkillProfile profile) {
		addSkills(profile.getEridium(), profile.getCredits(), profile.getAmmo(), profile.getEridiumBuilding(), 
				profile.getCreditsBuilding(), profile.getAmmoBuilding(), profile.getHero(), profile.getPoints());
	}
	private void addSkills(int eridium, int credits, int ammo, int eridiumBuilding, int creditsBuilding, int ammoBuilding, int heros, int points) {
		List<SkillTreeNode> leafs = new ArrayList<SkillTreeNode>();
		leafs.add(nameMap.get("e" + eridium));
		leafs.add(nameMap.get("c" + credits));
		leafs.add(nameMap.get("a" + ammo));
		leafs.add(nameMap.get("eb" + eridiumBuilding));
		leafs.add(nameMap.get("cb" + creditsBuilding));
		leafs.add(nameMap.get("ab" + ammoBuilding));
		leafs.add(nameMap.get("h" + heros));
		leafs.add(nameMap.get("p" + points));
		for (SkillTreeNode node : leafs) {
			if (node != null) {
				selectPreviouse(node);
			}
		}
		enableSkills();
	}
	
	/**
	 * Reset the skills and set all to disabled and unselected
	 */
	private void resetSkills() {
		for (SkillTreeNode node : nameMap.values()) {
			node.setEnabled(false);
		}
	}
	
	private void setSelectedProfile(int profile) {
		resetSkills();
		addSkills(profileManager.getSkillProfiles()[profile]);
	}
	
	private void selectPreviouse(SkillTreeNode node) {
		node.setSelected(true);
		for (int i = 0; i < node.getPrev().size(); i++) {
			selectPreviouse(node.getPrev().get(i));
		}
	}
	
	private void submitSkills() {
		SkillProfileTransferMessage message = new SkillProfileTransferMessage();
		int[] skills = new int[9];
		for (SkillTreeNode node : skillMap.values()) {
			if (skills[node.getType()] < node.getLevel() && node.isSelected()) {
				skills[node.getType()] = node.getLevel();
			}
		}
		SkillProfile skillProfile = new SkillProfile();
		skillProfile.setEridium(skills[SKILL_ERIDIUM]);
		skillProfile.setCredits(skills[SKILL_CREDITS]);
		skillProfile.setAmmo(skills[SKILL_AMMO]);
		skillProfile.setEridiumBuilding(skills[SKILL_ERIDIUM_BUILDING]);
		skillProfile.setCreditsBuilding(skills[SKILL_CREDITS_BUILDING]);
		skillProfile.setAmmoBuilding(skills[SKILL_AMMO_BUILDING]);
		skillProfile.setHero(skills[SKILL_HERO]);
		skillProfile.setPoints(skills[SKILL_POINTS]);
		skillProfile.setId(profileManager.getSkillProfiles()[selectedSkillIndex].getId());
		message.setUpdate(skillProfile);
		message.setRequest(false);
		//send the message to the server
		client.sendMessage(message);
		//update in the local
		
	}
	
	private void enableSkills() {
		int skillPointsLeft = getSkillPointsLeft();
		enableSkills(root);
		enableSkillsByPoints(skillPointsLeft);
		textField.setText(Integer.toString(skillPointsLeft));
		revalidate();
		repaint();
	}
	
	/**
	 * Enable the skill check boxes recursively
	 * 
	 * @param node
	 * 		The recursive node
	 */
	private void enableSkills(SkillTreeNode node) {
		boolean enable = true;
		for (int i = 0; i < node.getPrev().size(); i++) {
			enable &= node.getPrev().get(i).isSelected();
		}
		if (enable) {
			node.setEnabled(true);
			//node.getCheck().setEnabled(true);
		}
		else {
			node.setEnabled(false);
			//node.getCheck().setEnabled(false);
			//node.getCheck().setSelected(false);
		}
		for (int i = 0; i < node.getNext().size(); i++) {
			enableSkills(node.getNext().get(i));
		}
	}
	private void enableSkillsByPoints(int skillPoints) {
		for (SkillTreeNode node : skillMap.values()) {
			if (node.getCost() > skillPoints && !node.isSelected()) {
				node.setEnabled(false);
			}
		}
	}
	private int getSkillPointsLeft() {
		int skillPoints = startSkillPoints;
		for (SkillTreeNode node : skillMap.values()) {
			if (node.isSelected()) {
				skillPoints -= node.getCost();
			}
		}
		return skillPoints;
	}
	
	private class SkillTreeNode {
		
		public SkillTreeNode(int cost, JCheckBox check, int type, int level) {
			this.cost = cost;
			this.check = check;
			this.type = type;
			this.level = level;
			selected = false;
			check.setSelected(false);
			prev = new ArrayList<SkillTreeNode>();
			next = new ArrayList<SkillTreeNode>();
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("SkillTreeNode[");
			switch (type) {
				case SKILL_ERIDIUM:
					sb.append("Eridium ");
					break;
				case SKILL_CREDITS:
					sb.append("Credits ");
					break;
				case SKILL_AMMO:
					sb.append("Ammo ");
					break;
				case SKILL_ERIDIUM_BUILDING:
					sb.append("Eridium_Building ");
					break;
				case SKILL_CREDITS_BUILDING:
					sb.append("Credits_Building ");
					break;
				case SKILL_AMMO_BUILDING:
					sb.append("Ammo_Building ");
					break;
				case SKILL_HERO:
					sb.append("Hero ");
					break;
				case SKILL_POINTS:
					sb.append("Points ");
					break;
			}
			sb.append(level);
			sb.append(" Cost:");
			sb.append(cost);
			sb.append(" Enabled: ");
			sb.append(isEnabled());
			sb.append(" Selected: ");
			sb.append(selected);
			sb.append("]");
			return sb.toString();
		}
		
		private boolean selected;
		
		private int cost;
		private int type;
		private int level;
		
		private JCheckBox check;
		
		private List<SkillTreeNode> prev;
		private List<SkillTreeNode> next;
		

		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			check.setSelected(selected);
			this.selected = selected;
		}
		
		public void changeSelected() {
			setSelected(!selected);
		}
		
		public void setEnabled(boolean enabled) {
			check.setEnabled(enabled);
			if (!enabled) {
				setSelected(false);
			}
		}
		
		public int getType() {
			return type;
		}
		public int getLevel() {
			return level;
		}
		public int getCost() {
			return cost;
		}
		
		public List<SkillTreeNode> getPrev() {
			return prev;
		}
		
		public List<SkillTreeNode> getNext() {
			return next;
		}
		public void addNext(SkillTreeNode node) {
			node.getPrev().add(this);
			next.add(node);
		}
	}
}