package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class TargetFieldSelectionDialog extends JDialog {
	
	private static final long serialVersionUID = 483050552559369915L;
	
	private JPanel contentPane;
	private JTextField txtNormalTroopsLeft;
	private JTextField txtBadassTroopsLeft;
	private JSpinner spinnerNormal;
	private JSpinner spinnerBadass;
	
	private DefaultListModel<Field> targetFieldListModel = new DefaultListModel<Field>();
	
	private Map<Field, int[]> troops = new HashMap<Field, int[]>();
	
	private List<Field> targets;
	
	private Field startField;
	
	private int normalTroopsLeft;
	private int badassTroopsLeft;
	private int normalTroopsStart;
	private int badassTroopsStart;
	
	private TurnExecutionFrame turnExecutionFrame;
	private Game game;
	
	public TargetFieldSelectionDialog(TurnExecutionFrame callingFrame, Game game, Field startField, List<Field> targets, int normalTroops, int badassTroops) {
		this.turnExecutionFrame = callingFrame;
		this.game = game;
		this.startField = startField;
		this.targets = targets;
		this.normalTroopsLeft = normalTroops;
		this.badassTroopsLeft = badassTroops;
		this.normalTroopsStart = normalTroops;
		this.badassTroopsStart = badassTroops;
		
		setModal(true);
		setResizable(false);
		setTitle("Bunkers and Badasses - Ziel Auswahl");
		setIconImage(Toolkit.getDefaultToolkit().getImage(TargetFieldSelectionDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][15px][][30px][5px][][30px][grow]", "[][15px][][10px][grow]"));
		
		JLabel lblTruppenbewegungZiele = new JLabel("Truppenbewegung - Ziele Ausw\u00E4hlen:");
		lblTruppenbewegungZiele.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblTruppenbewegungZiele, "cell 0 0 8 1,alignx center");
		
		JLabel lblTruppen = new JLabel("Truppen \u00DCbrig:");
		lblTruppen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblTruppen, "cell 0 2");
		
		JLabel lblNormal = new JLabel("Normal:");
		lblNormal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblNormal, "cell 2 2,alignx trailing");
		
		txtNormalTroopsLeft = new JTextField();
		txtNormalTroopsLeft.setBackground(Color.LIGHT_GRAY);
		txtNormalTroopsLeft.setEnabled(false);
		panel.add(txtNormalTroopsLeft, "cell 3 2,growx");
		txtNormalTroopsLeft.setColumns(10);
		
		JLabel lblBadasses = new JLabel("Badasses:");
		lblBadasses.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblBadasses, "cell 5 2,alignx trailing");
		
		txtBadassTroopsLeft = new JTextField();
		txtBadassTroopsLeft.setBackground(Color.LIGHT_GRAY);
		txtBadassTroopsLeft.setEnabled(false);
		panel.add(txtBadassTroopsLeft, "cell 6 2,growx");
		txtBadassTroopsLeft.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, "cell 0 4 8 1,grow");
		panel_1.setLayout(new MigLayout("", "[200px][grow][][40px][grow]", "[][10px,grow][][][10px][][][grow]"));
		
		JLabel lblZielFelder = new JLabel("Ziel Felder:");
		panel_1.add(lblZielFelder, "cell 0 0,alignx center");
		lblZielFelder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblTruppen_1 = new JLabel("Truppen:");
		lblTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblTruppen_1, "cell 1 0 4 1,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 1 1 7,grow");
		
		JList<Field> list = new JList<Field>(targetFieldListModel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateSpinners(list.getSelectedValue());
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(list);
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblNormaleTruppen, "cell 2 2");
		
		spinnerNormal = new JSpinner();
		spinnerNormal.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Field field = list.getSelectedValue();
				int[] currentTroops = troops.get(field);
				currentTroops[0] = (int) spinnerNormal.getValue();
				troops.put(field, currentTroops);
				countTroopsLeft();
			}
		});
		spinnerNormal.setBackground(Color.LIGHT_GRAY);
		spinnerNormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(spinnerNormal, "cell 3 2,growx");
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblBadassTruppen, "cell 2 3");
		
		spinnerBadass = new JSpinner();
		spinnerBadass.setBackground(Color.LIGHT_GRAY);
		spinnerBadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(spinnerBadass, "cell 3 3,growx");
		
		JButton btnAuswahlZurcksetzen = new JButton("Auswahl Zur\u00FCcksetzen");
		btnAuswahlZurcksetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
		});
		btnAuswahlZurcksetzen.setBackground(Color.GRAY);
		panel_1.add(btnAuswahlZurcksetzen, "cell 2 5 2 1,alignx center");
		
		JButton btnBefehlAbschicken = new JButton("Befehl Abschicken");
		btnBefehlAbschicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeMoves();
			}
		});
		btnBefehlAbschicken.setBackground(Color.GRAY);
		panel_1.add(btnBefehlAbschicken, "cell 2 6 2 1,alignx center");
		
		addTargets(targets);
		initTroopMap(targets);
		initSpinners(normalTroops, badassTroops);
	}
	
	private void executeMoves() {
		if (normalTroopsLeft == normalTroopsStart && badassTroopsLeft == badassTroopsStart) {
			new ErrorDialog("Du hast keine Truppenbewegungen ausgew�hlt.\n\nDu solltest mindestens ein Ziel mit Truppen aussuchen.\nOder du l�sst deine Truppen einfach im Kreis laufen.\nDas kann auch lustig sein.").setVisible(true);
		}
		else {
			for (Field field : targets) {
				int[] movedTroops = troops.get(field);
				if (movedTroops[0] + movedTroops[1] > 0) {
					if (field.getAffiliation() != null && !field.getAffiliation().equals(game.getLocalUser())) {
						//TODO start a fight
					}
					else {
						game.getBoard().moveTroops(startField, field, movedTroops[0], movedTroops[1]);
					}
				}
			}
			turnExecutionFrame.setFieldSelectionSucessfull(true);
			dispose();
		}
	}
	
	private void resetAll() {
		initTroopMap(targets);
		normalTroopsLeft = normalTroopsStart;
		badassTroopsLeft = badassTroopsStart;
		initSpinners(normalTroopsStart, badassTroopsStart);
	}
	
	private void countTroopsLeft() {
		int normalTroops = 0;
		int badassTroops = 0;
		for (Field field : troops.keySet()) {
			int[] troopNum = troops.get(field);
			normalTroops += troopNum[0];
			badassTroops += troopNum[1];
		}
		normalTroopsLeft = normalTroopsStart - normalTroops;
		badassTroopsLeft = badassTroopsStart - badassTroops;
	}
	
	private void addTargets(List<Field> targets) {
		for (Field field : targets) {
			targetFieldListModel.addElement(field);
		}
	}
	
	private void initTroopMap(List<Field> targets) {
		for (Field field : targets) {
			troops.put(field, new int[2]);
		}
	}
	
	private void initSpinners(int normalTroops, int badassTroops) {
		spinnerNormal.setModel(new SpinnerNumberModel(0, 0, normalTroops, 1));
		spinnerBadass.setModel(new SpinnerNumberModel(0, 0, badassTroops, 1));
	}
	private void updateSpinners(Field field) {
		int[] currentTroops = troops.get(field);
		spinnerNormal.setModel(new SpinnerNumberModel(currentTroops[0], 0, normalTroopsLeft, 1));
		spinnerBadass.setModel(new SpinnerNumberModel(currentTroops[1], 0, badassTroopsLeft, 1));
	}
}