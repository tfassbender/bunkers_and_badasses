package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero.ExecutionComponent;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero.ExecutionData;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class HeroEffectExecutionFrame extends JFrame {
	
	private static final long serialVersionUID = 1451467567039315736L;
	
	private JPanel contentPane;
	
	private Hero currentHero;
	private ExecutionData executionData;
	
	private Game game;
	
	private DefaultListModel<Field> startFieldListModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> targetFieldsListModel = new DefaultListModel<Field>();
	
	private DefaultComboBoxModel<Command> commandComboBoxModel = new DefaultComboBoxModel<Command>();
	
	private JList<Field> listStartField;
	private JList<Field> listTargetField;
	private JSpinner spinnerStartFieldNormalTroops;
	private JSpinner spinnerStartFieldBadassTroops;
	private JSpinner spinnerTargetFieldNormalTroops;
	private JSpinner spinnerTargetFieldBadassTroops;
	private JComboBox<Command> comboBoxCommands;
	private JButton btnAuswahlZurcksetsen;
	private JButton btnBefehlAusfhren;
	private JTextArea textAreaTargetFields;
	
	public HeroEffectExecutionFrame(Game game) {
		this.game = game;
		
		setTitle("Helden Effekt - Bunkers And Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(HeroEffectExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[200px,grow][200px][grow]", "[][200px,grow][][150px]"));
		
		JLabel lblStartFeld = new JLabel("Start Feld:");
		lblStartFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblStartFeld, "cell 0 0");
		
		JLabel lblZielFeld = new JLabel("Ziel Feld:");
		lblZielFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblZielFeld, "cell 1 0");
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 1,grow");
		
		listStartField = new JList<Field>(startFieldListModel);
		listStartField.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				executionData.setStartField(listStartField.getSelectedValue());
				requestExecutionData();
			}
		});
		listStartField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listStartField.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(listStartField);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "cell 1 1,grow");
		
		listTargetField = new JList<Field>(targetFieldsListModel);
		listTargetField.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				executionData.setTargetFields(listTargetField.getSelectedValuesList());
				executionData.getTargetFields().forEach(field -> createEntry(field));
				updateTargetFieldsText();
				requestExecutionData();
			}
		});
		listTargetField.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(listTargetField);
		
		JPanel panel_selection = new JPanel();
		panel_selection.setBackground(Color.GRAY);
		panel.add(panel_selection, "cell 2 1 1 3,grow");
		panel_selection.setLayout(new MigLayout("", "[][50px][grow]", "[grow][][][10px][][][10px][][][5px][]"));
		
		ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/heros.png"));
		panel_image.setCentered(true);
		panel_image.setAdaptSizeKeepProportion(true);
		panel_image.setBackground(Color.GRAY);
		panel_selection.add(panel_image, "cell 0 0 2 1,grow");
		
		JLabel label = new JLabel("Start Feld - Normale Truppen:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_selection.add(label, "cell 0 1");
		
		spinnerStartFieldNormalTroops = new JSpinner();
		spinnerStartFieldNormalTroops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				executionData.setStartFieldNormalTroops((Integer) spinnerStartFieldNormalTroops.getValue());
				requestExecutionData();
			}
		});
		panel_selection.add(spinnerStartFieldNormalTroops, "cell 1 1,growx");
		
		JLabel lblStartFeld_1 = new JLabel("Start Feld - Badass Truppen:");
		lblStartFeld_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_selection.add(lblStartFeld_1, "cell 0 2");
		
		spinnerStartFieldBadassTroops = new JSpinner();
		spinnerStartFieldBadassTroops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				executionData.setStartFieldBadassTroops((Integer) spinnerStartFieldBadassTroops.getValue());
				requestExecutionData();
			}
		});
		panel_selection.add(spinnerStartFieldBadassTroops, "cell 1 2,growx");
		
		JLabel lblZielFeld_1 = new JLabel("Ziel Feld - Normale Truppen:");
		lblZielFeld_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_selection.add(lblZielFeld_1, "cell 0 4");
		
		spinnerTargetFieldNormalTroops = new JSpinner();
		spinnerTargetFieldNormalTroops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = (Integer) spinnerTargetFieldNormalTroops.getValue();
				listTargetField.getSelectedValuesList().forEach(field -> executionData.getTargetFieldsNormalTroops().put(field, value));
				updateTargetFieldsText();
				requestExecutionData();
			}
		});
		panel_selection.add(spinnerTargetFieldNormalTroops, "cell 1 4,growx");
		
		JLabel lblZielFeld_2 = new JLabel("Ziel Feld - Badass Truppen:");
		lblZielFeld_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_selection.add(lblZielFeld_2, "cell 0 5");
		
		spinnerTargetFieldBadassTroops = new JSpinner();
		spinnerTargetFieldBadassTroops.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = (Integer) spinnerTargetFieldBadassTroops.getValue();
				listTargetField.getSelectedValuesList().forEach(field -> executionData.getTargetFieldsBadassTroops().put(field, value));
				updateTargetFieldsText();
				requestExecutionData();
			}
		});
		panel_selection.add(spinnerTargetFieldBadassTroops, "cell 1 5,growx");
		
		JLabel lblBefehl = new JLabel("Befehl:");
		lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_selection.add(lblBefehl, "cell 0 7 2 1");
		
		comboBoxCommands = new JComboBox<Command>(commandComboBoxModel);
		comboBoxCommands.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executionData.setCommand((Command) comboBoxCommands.getSelectedItem());
				requestExecutionData();
			}
		});
		panel_selection.add(comboBoxCommands, "cell 0 8,growx");
		
		JPanel panel_buttons = new JPanel();
		panel_buttons.setBackground(Color.GRAY);
		panel_selection.add(panel_buttons, "cell 0 10 3 1,grow");
		panel_buttons.setLayout(new BorderLayout(0, 0));
		
		btnBefehlAusfhren = new JButton("Befehl Ausf\u00FChren");
		btnBefehlAusfhren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				execute();
			}
		});
		panel_buttons.add(btnBefehlAusfhren, BorderLayout.EAST);
		btnBefehlAusfhren.setBackground(Color.GRAY);
		
		btnAuswahlZurcksetsen = new JButton("Auswahl Zur\u00FCcksetsen");
		btnAuswahlZurcksetsen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executionData = null;
				requestExecutionData();
			}
		});
		btnAuswahlZurcksetsen.setBackground(Color.GRAY);
		panel_buttons.add(btnAuswahlZurcksetsen, BorderLayout.WEST);
		
		JLabel lblAusgewhlt = new JLabel("Ausgew\u00E4hlt:");
		lblAusgewhlt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblAusgewhlt, "cell 0 2 2 1");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2, "cell 0 3 2 1,grow");
		
		textAreaTargetFields = new JTextArea();
		textAreaTargetFields.setEditable(false);
		textAreaTargetFields.setBackground(Color.LIGHT_GRAY);
		textAreaTargetFields.setWrapStyleWord(true);
		textAreaTargetFields.setLineWrap(true);
		scrollPane_2.setViewportView(textAreaTargetFields);
	}
	
	private void createEntry(Field field) {
		//if no value is in the maps, initialize them with 0
		if (executionData.getTargetFieldsNormalTroops().get(field) == null) {
			executionData.getTargetFieldsNormalTroops().put(field, 0);			
		}
		if (executionData.getTargetFieldsBadassTroops().get(field) == null) {
			executionData.getTargetFieldsBadassTroops().put(field, 0);
		}
	}
	
	private void updateTargetFieldsText() {
		textAreaTargetFields.setText("");
		Consumer<Field> displayValues = new Consumer<Field>() {
			@Override
			public void accept(Field field) {
				Integer normalTroops = executionData.getTargetFieldsNormalTroops().get(field);
				Integer badassTroops = executionData.getTargetFieldsBadassTroops().get(field);
				StringBuilder sb = new StringBuilder();
				boolean normalTroopsPresent = normalTroops != null && normalTroops.intValue() > 0;
				boolean badassTroopsPresent = badassTroops != null && badassTroops.intValue() > 0;
				if (normalTroopsPresent || badassTroopsPresent) {
					sb.append(field.getName());
					sb.append(":\n");
					if (normalTroopsPresent) {
						sb.append("Normale Truppen: ");
						sb.append(normalTroops.toString());
						sb.append('\n');
					}
					if (badassTroopsPresent) {
						sb.append("Badass Truppen: ");
						sb.append(badassTroops.toString());
						sb.append('\n');
					}
					sb.append('\n');
				}
				textAreaTargetFields.append(sb.toString());
			}
		};
		executionData.getPossibleTargetFields().forEach(displayValues);
	}
	
	private void disableAll() {
		listStartField.setEnabled(false);
		listTargetField.setEnabled(false);
		spinnerStartFieldNormalTroops.setEnabled(false);
		spinnerStartFieldBadassTroops.setEnabled(false);
		spinnerTargetFieldNormalTroops.setEnabled(false);
		spinnerTargetFieldBadassTroops.setEnabled(false);
		comboBoxCommands.setEnabled(false);
		btnAuswahlZurcksetsen.setEnabled(false);
		btnBefehlAusfhren.setEnabled(false);
	}
	public void initializeComponents(List<ExecutionComponent> components) {
		disableAll();
		if (currentHero != null) {
			if (components.contains(ExecutionComponent.FIELD_START)) {
				listStartField.setEnabled(true);
			}
			if (components.contains(ExecutionComponent.FIELD_TARGET)) {
				listTargetField.setEnabled(true);
			}
			if (components.contains(ExecutionComponent.SPINNER_NUMBER_NORMAL)) {
				spinnerStartFieldNormalTroops.setEnabled(true);
			}
			if (components.contains(ExecutionComponent.SPINNER_NUMBER_BADASS)) {
				spinnerStartFieldBadassTroops.setEnabled(true);
			}
			if (components.contains(ExecutionComponent.SPINNER_NUMBER_PER_FIELD_NORMAL)) {
				spinnerTargetFieldNormalTroops.setEnabled(true);
			}
			if (components.contains(ExecutionComponent.SPINNER_NUMBER_PER_FIELD_BADASS)) {
				spinnerTargetFieldBadassTroops.setEnabled(true);
			}
			if (components.contains(ExecutionComponent.SELECTION_COMMAND)) {
				comboBoxCommands.setEnabled(true);
			}
			if (!components.isEmpty()) {
				btnAuswahlZurcksetsen.setEnabled(true);
				btnBefehlAusfhren.setEnabled(true);
			}			
		}
	}
	public void requestExecutionData() {
		if (currentHero != null) {
			executionData = currentHero.getExecutionData(executionData);
			//update the lists
			startFieldListModel.removeAllElements();
			targetFieldsListModel.removeAllElements();
			executionData.getPossibleStartFields().forEach(field -> startFieldListModel.addElement(field));
			executionData.getPossibleTargetFields().forEach(field -> targetFieldsListModel.addElement(field));
			listStartField.setSelectedValue(executionData.getStartField(), true);
			int[] selectedIndices = executionData.getTargetFields().stream().map(targetFieldsListModel::indexOf).mapToInt(i->i).toArray();
			listTargetField.setSelectedIndices(selectedIndices);
			//update the spinners
			spinnerStartFieldNormalTroops.setModel(executionData.getStartFieldNormalTroopsModel());
			spinnerStartFieldBadassTroops.setModel(executionData.getStartFieldBadassTroopsModel());
			spinnerTargetFieldNormalTroops.setModel(executionData.getTargetFieldNormalTroopsModel());
			spinnerTargetFieldBadassTroops.setModel(executionData.getTargetFieldBadassTroopsModel());
			//update the combo box
			commandComboBoxModel.removeAllElements();
			executionData.getPossibleCommands().forEach(command -> commandComboBoxModel.addElement(command));
			commandComboBoxModel.setSelectedItem(executionData.getCommand());
		}
	}
	private void execute() {
		if (currentHero != null) {
			if (currentHero.execute(executionData)) {
				//remove the hero from the players hand
				game.getHeroCardManager().heroCardUsed(currentHero, game.getLocalUser());
				//remove the current hero
				currentHero = null;
				executionData = null;
				disableAll();
				dispose();
			}
			//if the execution fails the specific error message is shown by the heros
		}
	}
	
	public void startHeroEffectExecution(Hero hero) {
		this.currentHero = hero;
		executionData = null;
		setVisible(true);
		initializeComponents(hero.getExecutionComponentsNeeded());
		requestExecutionData();
	}
}