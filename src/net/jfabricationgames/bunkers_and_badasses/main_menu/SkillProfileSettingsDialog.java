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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_frame.GameFrame;
import net.miginfocom.swing.MigLayout;

public class SkillProfileSettingsDialog extends JDialog {
	
	private static final long serialVersionUID = -6176020670400001990L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textField;
	
	private int startSkillPoints;
	
	private SkillTreeNode root;
	
	private Map<JCheckBox, SkillTreeNode> skillMap;
	private Map<String, SkillTreeNode> nameMap;
	
	public SkillProfileSettingsDialog(MainMenuFrame callingFrame) {
		setTitle("Bunkers and Badasses - Skill-Profil");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1100, 550);
		setMinimumSize(new Dimension(1100, 550));
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		startSkillPoints = 25;//TODO better load from database
		
		root = new SkillTreeNode(0, null);
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
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxEridium.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium, "cell 3 3 2 1");
					e1 = new SkillTreeNode(1, chckbxEridium);
					skillMap.put(chckbxEridium, e1);
				}
				{
					JCheckBox chckbxCredits = new JCheckBox("Credits 1");
					chckbxCredits.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxCredits.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits, "cell 10 3");
					c1 = new SkillTreeNode(1, chckbxCredits);
					skillMap.put(chckbxCredits, c1);
				}
				{
					JCheckBox chckbxPunkte = new JCheckBox("Punkte 1");
					chckbxPunkte.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxPunkte.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte, "cell 15 3");
					p1 = new SkillTreeNode(1, chckbxPunkte);
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
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxEridium_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium_1, "cell 3 5 2 1");
					e2 = new SkillTreeNode(2, chckbxEridium_1);
					skillMap.put(chckbxEridium_1, e2);
				}
				{
					JCheckBox chckbxEridiumGebude = new JCheckBox("E. Geb\u00E4ude 1");
					chckbxEridiumGebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxEridiumGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude, "cell 5 5 2 1");
					eb1 = new SkillTreeNode(2, chckbxEridiumGebude);
					skillMap.put(chckbxEridiumGebude, eb1);
				}
				{
					JCheckBox chckbxCGebude = new JCheckBox("C. Geb\u00E4ude 1");
					chckbxCGebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxCGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude, "cell 8 5 2 1");
					cb1 = new SkillTreeNode(2, chckbxCGebude);
					skillMap.put(chckbxCGebude, cb1);
				}
				{
					JCheckBox chckbxCredits_1 = new JCheckBox("Credits 2");
					chckbxCredits_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxCredits_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits_1, "cell 10 5");
					c2 = new SkillTreeNode(2, chckbxCredits_1);
					skillMap.put(chckbxCredits_1, c2);
				}
				{
					JCheckBox chckbxMunition = new JCheckBox("Munition 1");
					chckbxMunition.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxMunition.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition, "cell 11 5");
					a1 = new SkillTreeNode(2, chckbxMunition);
					skillMap.put(chckbxMunition, a1);
				}
				{
					JCheckBox chckbxPunkte_1 = new JCheckBox("Punkte 2");
					chckbxPunkte_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxPunkte_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_1, "cell 15 5");
					p2 = new SkillTreeNode(2, chckbxPunkte_1);
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
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxHeld.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld, "cell 2 7");
					h1 = new SkillTreeNode(3, chckbxHeld);
					skillMap.put(chckbxHeld, h1);
				}
				{
					JCheckBox chckbxEridium_2 = new JCheckBox("Eridium 3");
					chckbxEridium_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxEridium_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium_2, "cell 3 7 2 1");
					e3 = new SkillTreeNode(3, chckbxEridium_2);
					skillMap.put(chckbxEridium_2, e3);
				}
				{
					JCheckBox chckbxEridiumGebude_1 = new JCheckBox("E. Geb\u00E4ude 2");
					chckbxEridiumGebude_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxEridiumGebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude_1, "cell 5 7 2 1");
					eb2 = new SkillTreeNode(3, chckbxEridiumGebude_1);
					skillMap.put(chckbxEridiumGebude_1, eb2);
				}
				{
					JCheckBox chckbxCGebude_1 = new JCheckBox("C. Geb\u00E4ude 2");
					chckbxCGebude_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxCGebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude_1, "cell 8 7 2 1");
					cb2 = new SkillTreeNode(3, chckbxCGebude_1);
					skillMap.put(chckbxCGebude_1, cb2);
				}
				{
					JCheckBox chckbxCredits_2 = new JCheckBox("Credits 3");
					chckbxCredits_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxCredits_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits_2, "cell 10 7");
					c3 = new SkillTreeNode(3, chckbxCredits_2);
					skillMap.put(chckbxCredits_2, c3);
				}
				{
					JCheckBox chckbxMunition_1 = new JCheckBox("Munition 2");
					chckbxMunition_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxMunition_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition_1, "cell 11 7");
					a2 = new SkillTreeNode(3, chckbxMunition_1);
					skillMap.put(chckbxMunition_1, a2);
				}
				{
					JCheckBox chckbxMGebude = new JCheckBox("M. Geb\u00E4ude 1");
					chckbxMGebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxMGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxMGebude, "cell 12 7 2 1");
					ab1 = new SkillTreeNode(3, chckbxMGebude);
					skillMap.put(chckbxMGebude, ab1);
				}
				{
					JCheckBox chckbxPunkte_2 = new JCheckBox("Punkte 3");
					chckbxPunkte_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxPunkte_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_2, "cell 15 7");
					p3 = new SkillTreeNode(3, chckbxPunkte_2);
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
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxHeld_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld_1, "cell 2 9");
					h2 = new SkillTreeNode(4, chckbxHeld_1);
					skillMap.put(chckbxHeld_1, h2);
				}
				{
					JCheckBox chckbxEridiumGebude_2 = new JCheckBox("E. Geb\u00E4ude 3");
					chckbxEridiumGebude_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxEridiumGebude_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude_2, "cell 5 9 2 1");
					eb3 = new SkillTreeNode(4, chckbxEridiumGebude_2);
					skillMap.put(chckbxEridiumGebude_2, eb3);
				}
				{
					JCheckBox chckbxCGebude_2 = new JCheckBox("C. Geb\u00E4ude 3");
					chckbxCGebude_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxCGebude_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude_2, "cell 8 9 2 1");
					cb3 = new SkillTreeNode(4, chckbxCGebude_2);
					skillMap.put(chckbxCGebude_2, cb3);
				}
				{
					JCheckBox chckbxMunition_2 = new JCheckBox("Munition 3");
					chckbxMunition_2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxMunition_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition_2, "cell 11 9");
					a3 = new SkillTreeNode(4, chckbxMunition_2);
					skillMap.put(chckbxMunition_2, a3);
				}
				{
					JCheckBox chckbxMgebude = new JCheckBox("M. Geb\u00E4ude 2");
					chckbxMgebude.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxMgebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxMgebude, "cell 12 9 2 1");
					ab2 = new SkillTreeNode(4, chckbxMgebude);
					skillMap.put(chckbxMgebude, ab2);
				}
				{
					JCheckBox chckbxPunkte_3 = new JCheckBox("Punkte 4");
					chckbxPunkte_3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxPunkte_3.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_3, "cell 15 9");
					p4 = new SkillTreeNode(4, chckbxPunkte_3);
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
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxHeld_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld_2, "cell 2 11");
					h3 = new SkillTreeNode(5, chckbxHeld_2);
					skillMap.put(chckbxHeld_2, h3);
				}
				{
					JCheckBox chckbxMgebude_1 = new JCheckBox("M. Geb\u00E4ude 3");
					chckbxMgebude_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxMgebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxMgebude_1, "cell 12 11 2 1");
					ab3 = new SkillTreeNode(5, chckbxMgebude_1);
					skillMap.put(chckbxMgebude_1, ab3);
				}
				{
					JCheckBox chckbxPunkte_4 = new JCheckBox("Punkte 5");
					chckbxPunkte_4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							skillMap.get(e.getSource()).setSelected(false);
							enableSkills();
						}
					});
					chckbxPunkte_4.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_4, "cell 15 11");
					p5 = new SkillTreeNode(5, chckbxPunkte_4);
					skillMap.put(chckbxPunkte_4, p5);
				}
			}
			{
				JPanel panel_info = new JPanel();
				panel_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				panel_info.setBackground(Color.GRAY);
				panel.add(panel_info, "cell 3 2,grow");
				panel_info.setLayout(new MigLayout("", "[][grow]", "[][10px][][10px][][grow]"));
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
					JButton btnHilfe = new JButton("Hilfe");
					btnHilfe.setBackground(Color.GRAY);
					panel_info.add(btnHilfe, "cell 0 4 2 1,alignx center");
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
		
		enableSkills();
	}
	
	
	private void submitSkills() {
		//TODO submit the skill points to the database
	}
	
	private void enableSkills() {
		int skillPointsLeft = getSkillPointsLeft();
		enableSkills(root);
		enableSkillsByPoints(skillPointsLeft);
		textField.setText(Integer.toString(skillPointsLeft));
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
			node.getCheck().setEnabled(true);
		}
		else {
			node.getCheck().setEnabled(false);
			node.getCheck().setSelected(false);
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
		
		public SkillTreeNode(int cost, JCheckBox check) {
			this.cost = cost;
			this.check = check;
			selected = false;
			prev = new ArrayList<SkillTreeNode>();
			next = new ArrayList<SkillTreeNode>();
		}
		
		private boolean selected;
		
		private int cost;
		
		private JCheckBox check;
		
		private List<SkillTreeNode> prev;
		private List<SkillTreeNode> next;

		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		
		public void setEnabled(boolean enabled) {
			check.setEnabled(enabled);
		}
		
		public int getCost() {
			return cost;
		}
		
		public JCheckBox getCheck() {
			return check;
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