package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.FightManager;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class SupportRequestFrame extends JFrame {
	
	private static final long serialVersionUID = -702644470055482751L;
	
	private static User localPlayer;
	
	private Fight fight;
	private Field supportField;
	private FightManager manager;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtAttacked;
	private JTextField txtSupport;
	private JTextField txtPoweratk;
	private JTextField txtPowerdef;
	private JTextField txtAttacker;
	private JTextField txtDefender;
	private JTextField txtSupporttroups;
	private JRadioButton rdbtnAngreifer;
	private JRadioButton rdbtnVerteidiger;
	
	public SupportRequestFrame(Fight fight, Field supportField, FightManager manager) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.fight = fight;
		this.supportField = supportField;
		this.manager = manager;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SupportRequestFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Unterst\u00FCtzungs Anfrage");
		setBounds(100, 100, 400, 500);
		setMinimumSize(new Dimension(400, 500));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][10px][][][][][][]"));
		
		JLabel lblUntersttzungsAnfrage = new JLabel("Unterst\u00FCtzungs Anfrage:");
		lblUntersttzungsAnfrage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblUntersttzungsAnfrage, "cell 0 0,alignx center");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new MigLayout("", "[grow][][150px][grow]", "[][]"));
		
		JLabel lblKampfUm = new JLabel("Umk\u00E4mpftes Gebiet:");
		panel_1.add(lblKampfUm, "cell 1 0,alignx trailing");
		lblKampfUm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtAttacked = new JTextField();
		panel_1.add(txtAttacked, "cell 2 0,growx");
		txtAttacked.setEditable(false);
		txtAttacked.setBackground(Color.LIGHT_GRAY);
		txtAttacked.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAttacked.setColumns(10);
		
		JLabel lblUntersttzendesGebiet = new JLabel("Unterst\u00FCtzendes Gebiet:");
		panel_1.add(lblUntersttzendesGebiet, "cell 1 1,alignx trailing");
		lblUntersttzendesGebiet.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtSupport = new JTextField();
		panel_1.add(txtSupport, "cell 2 1,growx");
		txtSupport.setEditable(false);
		txtSupport.setBackground(Color.LIGHT_GRAY);
		txtSupport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSupport.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel.add(panel_4, "cell 0 3,grow");
		panel_4.setLayout(new MigLayout("", "[grow][][150px][grow]", "[10px][][]"));
		
		JLabel lblAngreifer_1 = new JLabel("Angreifer:");
		panel_4.add(lblAngreifer_1, "cell 1 1,alignx trailing");
		lblAngreifer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtAttacker = new JTextField();
		panel_4.add(txtAttacker, "cell 2 1,growx");
		txtAttacker.setEditable(false);
		txtAttacker.setBackground(Color.LIGHT_GRAY);
		txtAttacker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAttacker.setColumns(10);
		
		JLabel lblVerteidiger_1 = new JLabel("Verteidiger:");
		panel_4.add(lblVerteidiger_1, "cell 1 2,alignx trailing");
		lblVerteidiger_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtDefender = new JTextField();
		panel_4.add(txtDefender, "cell 2 2,growx");
		txtDefender.setEditable(false);
		txtDefender.setBackground(Color.LIGHT_GRAY);
		txtDefender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDefender.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel.add(panel_2, "cell 0 4,grow");
		panel_2.setLayout(new MigLayout("", "[grow][][25px][10px][][25px][grow]", "[10px][][5px][]"));
		
		JLabel lblMomentaneKampfstrken = new JLabel("Momentane Kampfst\u00E4rken:");
		panel_2.add(lblMomentaneKampfstrken, "cell 0 1 7 1,alignx center");
		lblMomentaneKampfstrken.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblAngreifer = new JLabel("Angreifer:");
		panel_2.add(lblAngreifer, "cell 1 3");
		lblAngreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtPoweratk = new JTextField();
		panel_2.add(txtPoweratk, "cell 2 3");
		txtPoweratk.setHorizontalAlignment(SwingConstants.CENTER);
		txtPoweratk.setEditable(false);
		txtPoweratk.setBackground(Color.LIGHT_GRAY);
		txtPoweratk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPoweratk.setColumns(10);
		
		JLabel lblVerteidiger = new JLabel("Verteidiger:");
		panel_2.add(lblVerteidiger, "cell 4 3");
		lblVerteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtPowerdef = new JTextField();
		panel_2.add(txtPowerdef, "cell 5 3");
		txtPowerdef.setHorizontalAlignment(SwingConstants.CENTER);
		txtPowerdef.setEditable(false);
		txtPowerdef.setBackground(Color.LIGHT_GRAY);
		txtPowerdef.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPowerdef.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel.add(panel_3, "cell 0 5,grow");
		panel_3.setLayout(new MigLayout("", "[grow][][25px][grow]", "[10px][]"));
		
		JLabel lblTruppenImUntersttzenden = new JLabel("Truppenst\u00E4rke im Unterst\u00FCtzenden Gebiet:");
		panel_3.add(lblTruppenImUntersttzenden, "cell 1 1");
		lblTruppenImUntersttzenden.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtSupporttroups = new JTextField();
		panel_3.add(txtSupporttroups, "cell 2 1,growx");
		txtSupporttroups.setHorizontalAlignment(SwingConstants.CENTER);
		txtSupporttroups.setEditable(false);
		txtSupporttroups.setBackground(Color.LIGHT_GRAY);
		txtSupporttroups.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSupporttroups.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel.add(panel_5, "cell 0 6,grow");
		panel_5.setLayout(new MigLayout("", "[grow][][][grow]", "[10px][][5px][]"));
		
		JLabel lblUntersttzen = new JLabel("Unterst\u00FCtzen:");
		panel_5.add(lblUntersttzen, "cell 0 1 4 1,alignx center");
		lblUntersttzen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ButtonGroup group = new ButtonGroup();
		
		rdbtnAngreifer = new JRadioButton("Angreifer");
		panel_5.add(rdbtnAngreifer, "cell 1 3");
		rdbtnAngreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAngreifer.setBackground(Color.GRAY);
		group.add(rdbtnAngreifer);
		
		rdbtnVerteidiger = new JRadioButton("Verteidiger");
		panel_5.add(rdbtnVerteidiger, "cell 2 3");
		rdbtnVerteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnVerteidiger.setBackground(Color.GRAY);
		group.add(rdbtnVerteidiger);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		panel.add(panel_6, "cell 0 7,grow");
		panel_6.setLayout(new MigLayout("", "[grow][][][grow]", "[10px][]"));
		
		JButton btnUntersttzen = new JButton("Unterst\u00FCtzen");
		btnUntersttzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				support();
			}
		});
		panel_6.add(btnUntersttzen, "cell 1 1");
		btnUntersttzen.setBackground(Color.GRAY);
		
		JButton btnUntersttzungVerweigern = new JButton("Unterst\u00FCtzung verweigern");
		btnUntersttzungVerweigern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reject();
			}
		});
		panel_6.add(btnUntersttzungVerweigern, "cell 2 1");
		btnUntersttzungVerweigern.setBackground(Color.GRAY);
		
		updateRequest(fight, supportField);
	}
	
	private void support() {
		if (!rdbtnAngreifer.isSelected() && !rdbtnVerteidiger.isSelected()) {
			new ErrorDialog("Du musst auch aussuchen wen Du unterst�tzen willst.\n\nEinfach auf alles ballern was sich bewegt gillt leider nicht.").setVisible(true);
		}
		if (rdbtnAngreifer.isSelected()) {
			fight.getAttackSupporters().add(supportField);
		}
		else {
			fight.getDefenceSupporters().add(supportField);
		}
		manager.update();
	}
	private void reject() {
		fight.getSupportRejections().add(supportField);
		manager.update();
	}
	
	private void updateRequest(Fight fight, Field support) {
		txtAttacked.setText(fight.getDefendingField().getName());
		txtSupport.setText(support.getName());
		txtAttacker.setText(fight.getAttackingPlayer().getUsername());
		txtDefender.setText(fight.getDefendingPlayer().getUsername());
		txtPoweratk.setText(Integer.toString(fight.getAttackingStrength()));
		txtPowerdef.setText(Integer.toString(fight.getDefendingStrength()));
		txtSupporttroups.setText(Integer.toString(support.getTroopStrength()));
		if (fight.getDefendingPlayer().equals(localPlayer)) {
			rdbtnAngreifer.setEnabled(false);
		}
		else if (fight.getAttackingPlayer().equals(localPlayer)) {
			rdbtnVerteidiger.setEnabled(false);
		}
	}
	
	public static User getLocalPlayer() {
		return localPlayer;
	}
	public static void setLocalPlayer(User localPlayer) {
		SupportRequestFrame.localPlayer = localPlayer;
	}
}