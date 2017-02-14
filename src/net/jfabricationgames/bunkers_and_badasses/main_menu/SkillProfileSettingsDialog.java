package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.miginfocom.swing.MigLayout;

public class SkillProfileSettingsDialog extends JDialog {
	
	private static final long serialVersionUID = -6176020670400001990L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textField;
	
	private int startSkillPoints;
	private int skillPointsLeft;
	
	private SkillTreeNode root;
	
	private Map<JCheckBox, SkillTreeNode> skillMap;
	private Map<String, SkillTreeNode> nameMap;
	
	public SkillProfileSettingsDialog() {
		setTitle("Bunkers and Badasses - Skill-Profil");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SkillProfileSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1000, 550);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		startSkillPoints = 25;//TODO better load from database
		skillPointsLeft = startSkillPoints;
		
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
				panel_skills.setLayout(new MigLayout("", "[center][10px][center][center][center][grow][center][center][center][center][grow][center][10px]", "[][][5px][][25px][][25px][][25px][][25px][]"));
				{
					JLabel lblEridium = new JLabel("Eridium");
					lblEridium.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblEridium, "cell 2 0 3 1");
				}
				{
					JLabel lblCreditsammo = new JLabel("Credits/Munition");
					lblCreditsammo.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblCreditsammo, "cell 6 0 4 1,alignx center");
				}
				{
					JLabel lblPunkte = new JLabel("Punkte");
					lblPunkte.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(lblPunkte, "cell 11 0,alignx center");
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
					chckbxEridium.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium, "cell 3 3");
					e1 = new SkillTreeNode(1, chckbxEridium);
					skillMap.put(chckbxEridium, e1);
				}
				{
					JCheckBox chckbxCredits = new JCheckBox("Credits 1");
					chckbxCredits.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits, "cell 7 3");
					c1 = new SkillTreeNode(1, chckbxCredits);
					skillMap.put(chckbxCredits, c1);
				}
				{
					JCheckBox chckbxPunkte = new JCheckBox("Punkte 1");
					chckbxPunkte.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte, "cell 11 3");
					p1 = new SkillTreeNode(1, chckbxPunkte);
					skillMap.put(chckbxPunkte, p1);
				}
				{
					JLabel label = new JLabel("2");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 5");
				}
				{
					JCheckBox chckbxEridium_1 = new JCheckBox("Eridium 2");
					chckbxEridium_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium_1, "cell 3 5");
					e2 = new SkillTreeNode(2, chckbxEridium_1);
					skillMap.put(chckbxEridium_1, e2);
				}
				{
					JCheckBox chckbxEridiumGebude = new JCheckBox("E. Geb\u00E4ude 1");
					chckbxEridiumGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude, "cell 4 5");
					eb1 = new SkillTreeNode(2, chckbxEridiumGebude);
					skillMap.put(chckbxEridiumGebude, eb1);
				}
				{
					JCheckBox chckbxCGebude = new JCheckBox("C. Geb\u00E4ude 1");
					chckbxCGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude, "cell 6 5");
					cb1 = new SkillTreeNode(2, chckbxCGebude);
					skillMap.put(chckbxCGebude, cb1);
				}
				{
					JCheckBox chckbxCredits_1 = new JCheckBox("Credits 2");
					chckbxCredits_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits_1, "cell 7 5");
					c2 = new SkillTreeNode(2, chckbxCredits_1);
					skillMap.put(chckbxCredits_1, c2);
				}
				{
					JCheckBox chckbxMunition = new JCheckBox("Munition 1");
					chckbxMunition.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition, "cell 8 5");
					a1 = new SkillTreeNode(2, chckbxMunition);
					skillMap.put(chckbxMunition, a1);
				}
				{
					JCheckBox chckbxPunkte_1 = new JCheckBox("Punkte 2");
					chckbxPunkte_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_1, "cell 11 5");
					p2 = new SkillTreeNode(2, chckbxPunkte_1);
					skillMap.put(chckbxPunkte_1, p2);
				}
				{
					JLabel label = new JLabel("3");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 7");
				}
				{
					JCheckBox chckbxHeld = new JCheckBox("Held 1");
					chckbxHeld.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld, "cell 2 7");
					h1 = new SkillTreeNode(3, chckbxHeld);
					skillMap.put(chckbxHeld, h1);
				}
				{
					JCheckBox chckbxEridium_2 = new JCheckBox("Eridium 3");
					chckbxEridium_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridium_2, "cell 3 7");
					e3 = new SkillTreeNode(3, chckbxEridium_2);
					skillMap.put(chckbxEridium_2, e3);
				}
				{
					JCheckBox chckbxEridiumGebude_1 = new JCheckBox("E. Geb\u00E4ude 2");
					chckbxEridiumGebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude_1, "cell 4 7");
					eb2 = new SkillTreeNode(3, chckbxEridiumGebude_1);
					skillMap.put(chckbxEridiumGebude_1, eb2);
				}
				{
					JCheckBox chckbxCGebude_1 = new JCheckBox("C. Geb\u00E4ude 2");
					chckbxCGebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude_1, "cell 6 7");
					cb2 = new SkillTreeNode(3, chckbxCGebude_1);
					skillMap.put(chckbxCGebude_1, cb2);
				}
				{
					JCheckBox chckbxCredits_2 = new JCheckBox("Credits 3");
					chckbxCredits_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxCredits_2, "cell 7 7");
					c3 = new SkillTreeNode(3, chckbxCredits_2);
					skillMap.put(chckbxCredits_2, c3);
				}
				{
					JCheckBox chckbxMunition_1 = new JCheckBox("Munition 2");
					chckbxMunition_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition_1, "cell 8 7");
					a2 = new SkillTreeNode(3, chckbxMunition_1);
					skillMap.put(chckbxMunition_1, a2);
				}
				{
					JCheckBox chckbxMGebude = new JCheckBox("M. Geb\u00E4ude 1");
					chckbxMGebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxMGebude, "cell 9 7");
					ab1 = new SkillTreeNode(3, chckbxMGebude);
					skillMap.put(chckbxMGebude, ab1);
				}
				{
					JCheckBox chckbxPunkte_2 = new JCheckBox("Punkte 3");
					chckbxPunkte_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_2, "cell 11 7");
					p3 = new SkillTreeNode(3, chckbxPunkte_2);
					skillMap.put(chckbxPunkte_2, p3);
				}
				{
					JLabel label = new JLabel("4");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 9");
				}
				{
					JCheckBox chckbxHeld_1 = new JCheckBox("Held 2");
					chckbxHeld_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld_1, "cell 2 9");
					h2 = new SkillTreeNode(4, chckbxHeld_1);
					skillMap.put(chckbxHeld_1, h2);
				}
				{
					JCheckBox chckbxEridiumGebude_2 = new JCheckBox("E. Geb\u00E4ude 3");
					chckbxEridiumGebude_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxEridiumGebude_2, "cell 4 9");
					eb3 = new SkillTreeNode(4, chckbxEridiumGebude_2);
					skillMap.put(chckbxEridiumGebude_2, eb3);
				}
				{
					JCheckBox chckbxCGebude_2 = new JCheckBox("C. Geb\u00E4ude 3");
					chckbxCGebude_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxCGebude_2, "cell 6 9");
					cb3 = new SkillTreeNode(4, chckbxCGebude_2);
					skillMap.put(chckbxCGebude_2, cb3);
				}
				{
					JCheckBox chckbxMunition_2 = new JCheckBox("Munition 3");
					chckbxMunition_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxMunition_2, "cell 8 9");
					a3 = new SkillTreeNode(4, chckbxMunition_2);
					skillMap.put(chckbxMunition_2, a3);
				}
				{
					JCheckBox chckbxMgebude = new JCheckBox("M. Geb\u00E4ude 2");
					chckbxMgebude.setBackground(Color.GRAY);
					panel_skills.add(chckbxMgebude, "cell 9 9");
					ab2 = new SkillTreeNode(4, chckbxMgebude);
					skillMap.put(chckbxMgebude, ab2);
				}
				{
					JCheckBox chckbxPunkte_3 = new JCheckBox("Punkte 4");
					chckbxPunkte_3.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_3, "cell 11 9");
					p4 = new SkillTreeNode(4, chckbxPunkte_3);
					skillMap.put(chckbxPunkte_3, p4);
				}
				{
					JLabel label = new JLabel("5");
					label.setFont(new Font("Tahoma", Font.PLAIN, 16));
					panel_skills.add(label, "cell 0 11");
				}
				{
					JCheckBox chckbxHeld_2 = new JCheckBox("Held 3");
					chckbxHeld_2.setBackground(Color.GRAY);
					panel_skills.add(chckbxHeld_2, "cell 2 11");
					h3 = new SkillTreeNode(5, chckbxHeld_2);
					skillMap.put(chckbxHeld_2, h3);
				}
				{
					JCheckBox chckbxMgebude_1 = new JCheckBox("M. Geb\u00E4ude 3");
					chckbxMgebude_1.setBackground(Color.GRAY);
					panel_skills.add(chckbxMgebude_1, "cell 9 11");
					ab3 = new SkillTreeNode(5, chckbxMgebude_1);
					skillMap.put(chckbxMgebude_1, ab3);
				}
				{
					JCheckBox chckbxPunkte_4 = new JCheckBox("Punkte 5");
					chckbxPunkte_4.setBackground(Color.GRAY);
					panel_skills.add(chckbxPunkte_4, "cell 11 11");
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
				ImagePanel panel_img = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("main_menu/angel_1.png"));
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
					panel_1.add(btnOk, "cell 1 0");
					btnOk.setBackground(Color.GRAY);
				}
				{
					JButton btnAbbrechen = new JButton("Abbrechen");
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
		//line 1 previous
		e1.addPrev(root);
		c1.addPrev(root);
		p1.addPrev(root);
		//line 1 next
		e1.addNext(e2);
		e1.addNext(eb1);
		c1.addNext(cb1);
		c1.addNext(c2);
		c1.addNext(a1);
		p1.addNext(p2);
		//line 2 previous
		e2.addPrev(e1);
		eb1.addPrev(e1);
		cb1.addPrev(c1);
		c2.addPrev(c1);
		a1.addPrev(c1);
		p2.addPrev(p1);
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
		//line 3 previous
		h1.addPrev(e2);
		e3.addPrev(e2);
		eb2.addPrev(e2);
		eb2.addPrev(eb1);
		cb2.addPrev(cb1);
		cb2.addPrev(c2);
		c3.addPrev(c2);
		a2.addPrev(a1);
		ab1.addPrev(a1);
		p3.addPrev(p2);
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
		//line 4 previous
		h2.addPrev(h1);
		h2.addPrev(e3);
		eb3.addPrev(e3);
		eb3.addPrev(eb2);
		cb3.addPrev(cb2);
		cb3.addPrev(c3);
		a3.addPrev(a2);
		ab2.addPrev(a2);
		ab2.addPrev(ab1);
		p4.addPrev(p3);
		//line 4 next
		h2.addNext(h3);
		ab2.addNext(ab3);
		p4.addNext(p5);
		//line 5 previous
		h3.addPrev(h2);
		ab3.addPrev(ab2);
		p5.addPrev(p4);
	}
	
	private class SkillTreeNode {
		
		public SkillTreeNode(int cost, JCheckBox check) {
			this.cost = cost;
			this.check = check;
			enabled = false;
			selected = false;
			prev = new ArrayList<SkillTreeNode>();
			next = new ArrayList<SkillTreeNode>();
		}
		
		private boolean enabled;
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
		
		public boolean isEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
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
		public void addPrev(SkillTreeNode node) {
			prev.add(node);
		}
		
		public List<SkillTreeNode> getNext() {
			return next;
		}
		public void addNext(SkillTreeNode node) {
			next.add(node);
		}
	}
}