package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.UserResource;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class AdditionalCommandFrame extends JFrame {
	
	private static final long serialVersionUID = 8435788787429898787L;
	
	private JPanel contentPane;
	
	private TurnPlaningFrame callingFrame;
	private ResourceInfoPanel resourcePanel;
	private Game game;
	
	private JPanel panel_1;
	private JLabel lblBefehle;
	private JScrollPane scrollPane;
	private JList<Command> list;
	private DefaultListModel<Command> command_list_model = new DefaultListModel<Command>();
	private JLabel lblKosten;
	private JTextField txtKosten;
	private JButton btnKaufen;
	
	public AdditionalCommandFrame(TurnPlaningFrame callingFrame, Game game, Command... commands) {
		this.callingFrame = callingFrame;
		this.game = game;
		
		setTitle("Bunkers and Badasses - Zus\u00E4tzliche Befehle Kaufen");
		setResizable(false);
		setBounds(100, 100, 600, 300);
		setLocationRelativeTo(callingFrame);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow][300px]", "[][10px][grow]"));
		
		JLabel lblZustzlicheBefehleKaufen = new JLabel("Zus\u00E4tzliche Befehle Kaufen:");
		lblZustzlicheBefehleKaufen.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblZustzlicheBefehleKaufen, "cell 0 0 2 1,alignx center");
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new MigLayout("", "[][grow][]", "[][grow][]"));
		
		lblBefehle = new JLabel("Befehle:");
		lblBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblBefehle, "cell 0 0 3 1,alignx center");
		
		scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 1 3 1,grow");
		
		list = new JList<Command>(command_list_model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.LIGHT_GRAY);
		list.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(list);
		for (Command command : commands) {
			command_list_model.addElement(command);
		}
		
		lblKosten = new JLabel("Kosten:");
		lblKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblKosten, "cell 0 2,alignx trailing");
		
		txtKosten = new JTextField();
		txtKosten.setHorizontalAlignment(SwingConstants.CENTER);
		txtKosten.setText(Game.getGameVariableStorage().getAdditionalCommandCosts() + " Eridium");
		txtKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtKosten.setBackground(Color.LIGHT_GRAY);
		txtKosten.setEditable(false);
		panel_1.add(txtKosten, "cell 1 2,growx");
		txtKosten.setColumns(10);
		
		btnKaufen = new JButton("Kaufen");
		btnKaufen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buyCommand(list.getSelectedValue());
			}
		});
		btnKaufen.setBackground(Color.GRAY);
		panel_1.add(btnKaufen, "cell 2 2");
		
		resourcePanel = new ResourceInfoPanel();
		panel.add(resourcePanel, "cell 1 2,grow");
	}
	
	public void update() {
		resourcePanel.updateResources(game, game.getLocalUser());
	}
	
	private void buyCommand(Command command) {
		UserResource resource = game.getPlanManager().getCurrentResource();
		if (command == null) {
			new ErrorDialog("Kein Befehl ausgewählt.\n\nDu musst schon einen Befehl aussuchen um ihn zu Kaufen.").setVisible(true);
		}
		else if (resource.getEridium() < Game.getGameVariableStorage().getAdditionalCommandCosts()) {
			new ErrorDialog("Nicht genug Eridium vorhanden.\n\nAnschreiben lassen geht leider nicht.").setVisible(true);
		}
		else {
			//resource.addEridium(-Game.getGameVariableStorage().getAdditionalCommandCosts());
			resource.payAdditionalCommand();
			game.getPlanManager().addCommand(command.getIdentifier());
			callingFrame.update();
			dispose();
		}
	}
}