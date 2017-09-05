package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;
import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;
import com.jfabricationgames.toolbox.properties.event.PropertiesWindowListener;

import net.jfabricationgames.bunkers_and_badasses.error.TurnOrderException;
import net.jfabricationgames.bunkers_and_badasses.game.ConfirmDialogListener;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonus;
import net.jfabricationgames.bunkers_and_badasses.game_turn_cards.TurnBonusSelectionListener;
import net.miginfocom.swing.MigLayout;

public class TurnExecutionFrame extends JFrame implements BoardPanelListener, ConfirmDialogListener, TurnBonusSelectionListener {
	
	private static final long serialVersionUID = 245242421914033099L;
	
	private JPanel contentPane;
	
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	private PlayerOrderPanel orderPanel;
	private PointPanel pointPanel;
	
	private Game game;
	private Field selectedField;
	
	private BoardPanel boardPanel;
	
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<FieldCommand> fieldCommandModel = new DefaultListModel<FieldCommand>();
	private DefaultListModel<FieldCommand> executableFieldCommandModel = new DefaultListModel<FieldCommand>();
	
	private CommandExecutionPanel panel_execution;
	private CommandExecutionPanel panel_execution_2;
	private BoardPanel boardPanel_2;
	private JPanel panel_game_type;
	
	private ResourceInfoPanel resourcePanel_2;
	private PointPanel pointPanel_2;
	private PlayerOrderPanel orderPanel_2;
	private FieldDescriptionPanel fieldPanel_2;
	private UserColorPanel userColorPanel;
	
	private static final String REDUCED_INFO_VIEW = "reduced_info";
	private static final String COMPLETE_INFO_VIEW = "complete_info";
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
	public TurnExecutionFrame(Game game) {
		addWindowListener(new PropertiesWindowListener(propsFile, PropertiesWindowListener.WINDOW_CLOSING_EVENT));
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TurnExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Zug Ausführung - Bunkers and Badasses");
		setBounds(100, 100, 1300, 800);
		setMinimumSize(new Dimension(1000, 600));
		
		propsFile.alignWindow();
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		panel_game_type = new JPanel();
		contentPane.add(panel_game_type, "cell 0 0,grow");
		panel_game_type.setLayout(new CardLayout(0, 0));
		
		JPanel panel_reduced_info_set = new JPanel();
		panel_game_type.add(panel_reduced_info_set, REDUCED_INFO_VIEW);
		panel_reduced_info_set.setBackground(Color.GRAY);
		panel_reduced_info_set.setLayout(new MigLayout("", "[700px,grow][:500px:600px,grow]", "[700px,grow][350px:n:375px,grow]"));
		
		boardPanel_2 = new BoardPanel();
		boardPanel_2.addBoardPanelListener(this);
		panel_reduced_info_set.add(boardPanel_2, "cell 0 0,grow");
		
		JPanel panel_side_bar_2 = new JPanel();
		panel_side_bar_2.setBackground(Color.GRAY);
		panel_reduced_info_set.add(panel_side_bar_2, "cell 1 0 1 2,grow");
		panel_side_bar_2.setLayout(new MigLayout("", "[grow][100px:n][50px:n][150px:n,grow]", "[300px,grow][][grow][200px,grow]"));
		
		fieldPanel_2 = new FieldDescriptionPanel("Feld Übersicht", true);
		panel_side_bar_2.add(fieldPanel_2, "cell 0 0 2 2,grow");
		
		orderPanel_2 = new PlayerOrderPanel();
		panel_side_bar_2.add(orderPanel_2, "cell 2 0 2 1,grow");
		
		JButton btnMehrEinblenden = new JButton("Mehr Einblenden");
		panel_side_bar_2.add(btnMehrEinblenden, "cell 2 1 2 1,growx");
		btnMehrEinblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showExtendedInfo();
			}
		});
		btnMehrEinblenden.setBackground(Color.GRAY);
		
		JPanel panel_executable_commands_2 = new JPanel();
		panel_side_bar_2.add(panel_executable_commands_2, "cell 0 2 1 2,grow");
		panel_executable_commands_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_executable_commands_2.setBackground(Color.GRAY);
		panel_executable_commands_2.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAusfhrbareBefehle_2 = new JLabel("Ausführbare Befehle:");
		lblAusfhrbareBefehle_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_executable_commands_2.add(lblAusfhrbareBefehle_2, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_executable_commands_2.add(scrollPane_1, "cell 0 2,grow");
		
		JList<FieldCommand> list_executable_commands_2 = new JList<FieldCommand>(executableFieldCommandModel);
		list_executable_commands_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_executable_commands_2.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(list_executable_commands_2);
		
		pointPanel_2 = new PointPanel();
		panel_side_bar_2.add(pointPanel_2, "cell 1 2 2 1,grow");
		
		userColorPanel = new UserColorPanel();
		userColorPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_side_bar_2.add(userColorPanel, "cell 3 2,grow");
		
		userColorPanel.addPlayerColors(game.getPlayers(), game.getColorManager());
		
		resourcePanel_2 = new ResourceInfoPanel();
		panel_side_bar_2.add(resourcePanel_2, "cell 1 3 3 1,grow");
		
		JPanel panel_low_bar_2 = new JPanel();
		panel_low_bar_2.setBackground(Color.GRAY);
		panel_reduced_info_set.add(panel_low_bar_2, "cell 0 1,grow");
		panel_low_bar_2.setLayout(new MigLayout("", "[:600px:1000px,grow][grow]", "[50px,grow][150px,grow]"));
		
		panel_execution_2 = new CommandExecutionPanel(this, game);
		panel_low_bar_2.add(panel_execution_2, "cell 0 0 1 2,grow");
		
		ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/mr_torgue_1.png"));
		panel_image.setImageMinimumSize(new int[] {200, 150});
		panel_image.setRemoveIfToSmall(true);
		panel_image.setCentered(true);
		panel_image.setAdaptSizeKeepProportion(true);
		panel_image.setBackground(Color.GRAY);
		panel_low_bar_2.add(panel_image, "cell 1 1,grow");
		
		JPanel panel_complete_info_set = new JPanel();
		panel_game_type.add(panel_complete_info_set, COMPLETE_INFO_VIEW);
		panel_complete_info_set.setBackground(Color.GRAY);
		panel_complete_info_set.setLayout(new MigLayout("", "[650px,grow][:550px:800px,grow]", "[400px,grow][:400px:400px,grow]"));
		
		boardPanel = new BoardPanel();
		boardPanel.addBoardPanelListener(this);
		panel_complete_info_set.add(boardPanel, "cell 0 0,grow");
		
		JPanel panel_side_bar = new JPanel();
		panel_side_bar.setBackground(Color.GRAY);
		panel_complete_info_set.add(panel_side_bar, "cell 1 0 1 2,grow");
		panel_side_bar.setLayout(new MigLayout("", "[250px,grow][200px,grow][200px,grow]", "[250px,grow][100px,grow][:150px:150px,grow][:50px:100px,grow][:200px:300px,grow][]"));
		
		JPanel panel_fields_all = new JPanel();
		panel_fields_all.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields_all.setBackground(Color.GRAY);
		panel_side_bar.add(panel_fields_all, "cell 0 0,grow");
		panel_fields_all.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAlleGebiete = new JLabel("Alle Gebiete:");
		lblAlleGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields_all.add(lblAlleGebiete, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_all = new JScrollPane();
		panel_fields_all.add(scrollPane_fields_all, "cell 0 2,grow");
		
		JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
		list_fields_all.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedField = list_fields_all.getSelectedValue();
				panel_execution.updateField();
				panel_execution_2.updateField();
			}
		});
		list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_fields_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_fields_all.setViewportView(list_fields_all);

		fieldPanel = new FieldDescriptionPanel("Feld Übersicht", true);
		panel_side_bar.add(fieldPanel, "cell 1 0 2 2,grow");
		
		JPanel panel_all_commands = new JPanel();
		panel_all_commands.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_all_commands.setBackground(Color.GRAY);
		panel_side_bar.add(panel_all_commands, "cell 0 1 1 2,grow");
		panel_all_commands.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAusfhrbareBefehle = new JLabel("Alle Befehle:");
		lblAusfhrbareBefehle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_all_commands.add(lblAusfhrbareBefehle, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_executable_commands = new JScrollPane();
		panel_all_commands.add(scrollPane_executable_commands, "cell 0 2,grow");
		
		JList<FieldCommand> list_commands = new JList<FieldCommand>(fieldCommandModel);
		list_commands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_commands.setBackground(Color.LIGHT_GRAY);
		list_commands.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_executable_commands.setViewportView(list_commands);
		
		resourcePanel = new ResourceInfoPanel();
		panel_side_bar.add(resourcePanel, "cell 1 2 2 2,grow");
		
		JPanel panel_executable_commands = new JPanel();
		panel_executable_commands.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_executable_commands.setBackground(Color.GRAY);
		panel_side_bar.add(panel_executable_commands, "cell 0 3 1 2,grow");
		panel_executable_commands.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAusfhrbareBefehle_1 = new JLabel("Ausführbare Befehle:");
		lblAusfhrbareBefehle_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_executable_commands.add(lblAusfhrbareBefehle_1, "cell 0 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_executable_commands.add(scrollPane, "cell 0 2,grow");
		
		JList<FieldCommand> list_executable_commands = new JList<FieldCommand>(executableFieldCommandModel);
		list_executable_commands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_executable_commands.setBackground(Color.LIGHT_GRAY);
		list_executable_commands.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(list_executable_commands);

		orderPanel = new PlayerOrderPanel();
		panel_side_bar.add(orderPanel, "cell 1 4 1 2,grow");
		
		pointPanel = new PointPanel();
		panel_side_bar.add(pointPanel, "cell 2 4 1 2,grow");
		
		JButton btnWenigerEinblenden = new JButton("Weniger Einblenden");
		btnWenigerEinblenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showReducedInfo();
			}
		});
		btnWenigerEinblenden.setBackground(Color.GRAY);
		panel_side_bar.add(btnWenigerEinblenden, "cell 0 5,growx");
		
		JPanel panel_low_bar = new JPanel();
		panel_low_bar.setBackground(Color.GRAY);
		panel_complete_info_set.add(panel_low_bar, "cell 0 1,grow");
		panel_low_bar.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		panel_execution = new CommandExecutionPanel(this, game);
		panel_low_bar.add(panel_execution, "cell 0 0,grow");
		
		showReducedInfo();
	}
	
	@Override
	public void receiveBoardMouseClick(MouseEvent event) {
		selectedField = game.getBoard().getFieldAtMousePosition();
		panel_execution.updateField();
		panel_execution_2.updateField();
		panel_execution.updateKosts();
		panel_execution_2.updateKosts();
	}
	
	@Override
	public void receiveConfirmAnswer(boolean confirm, int type) {
		if (confirm) {
			//game.setState(GameState.SELECT_BONUS);
			if (game.getTurnManager().getTurn() < Game.getGameVariableStorage().getGameTurns() - 1) {
				TurnGoalTurnBonusDialog dialog = game.getGameFrame().getTurnGoalTurnBonusDialog();
				dialog.showPanel(TurnGoalTurnBonusDialog.TURN_BONUS_PANEL);
				dialog.setTurnBonusSelectable(true, this);
				dialog.setVisible(true);
				dialog.requestFocus();
			}
			else {
				//no need to choose a bonus after the last turn
				game.getGameTurnBonusManager().putBackTurnBonus(game.getLocalUser());
				game.getGameFrame().getTurnGoalTurnBonusDialog().updateTurnBonuses();
				//give out the turn goal points for passing
				int passingOrder = game.getPlayers().size() - game.getPlayerOrder().getOrder().length + 1;
				game.getGameTurnGoalManager().receivePointsPassing(game.getLocalUser(), passingOrder, game.getPlayers().size());
				game.getPlayerOrder().userPassed(game.getLocalUser());
				game.setState(GameState.ACT);
				try {
					game.getPlayerOrder().nextMove();
				}
				catch (TurnOrderException toe) {
					//start next turn
					game.getTurnManager().nextTurn();
					game.setState(GameState.PLAN);
				}
				game.getTurnExecutionManager().commit();
			}
		}
	}
	
	@Override
	public void receiveTurnBonusSelection(TurnBonus selectedBonus) {
		game.getGameTurnBonusManager().chooseTurnBonus(game.getLocalUser(), selectedBonus);
		//game.setState(GameState.WAIT);
		game.getGameFrame().getTurnGoalTurnBonusDialog().setTurnBonusSelectable(false, null);
		game.getGameFrame().getTurnGoalTurnBonusDialog().updateTurnBonuses();
		//give out the turn goal points for passing
		int passingOrder = game.getPlayers().size() - game.getPlayerOrder().getOrder().length + 1;
		game.getGameTurnGoalManager().receivePointsPassing(game.getLocalUser(), passingOrder, game.getPlayers().size());
		game.getPlayerOrder().userPassed(game.getLocalUser());
		game.setState(GameState.ACT);
		try {
			game.getPlayerOrder().nextMove();
		}
		catch (TurnOrderException toe) {
			//start next turn
			game.getTurnManager().nextTurn();
			game.setState(GameState.PLAN);
		}
		game.getTurnExecutionManager().commit();
	}
	
	private void showReducedInfo() {
		CardLayout layout = (CardLayout) panel_game_type.getLayout();
		layout.show(panel_game_type, REDUCED_INFO_VIEW);
	}
	private void showExtendedInfo() {
		CardLayout layout = (CardLayout) panel_game_type.getLayout();
		layout.show(panel_game_type, COMPLETE_INFO_VIEW);
	}
	
	public void update() {
		updateBoard();
		updateResources();
		updatePlayerOrder();
		updatePointPanel();
		panel_execution.updateField();
		panel_execution_2.updateField();
		updateFieldList();
		updateFieldCommandList();
		panel_execution.updateKosts();
		panel_execution_2.updateKosts();
	}
	
	private void updateBoard() {
		boardPanel.updateBoardImage(game.getBoard().displayBoard());
		boardPanel_2.updateBoardImage(game.getBoard().displayBoard());
	}
	
	private void updateFieldList() {
		fieldAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
		}
	}
	
	private void updateFieldCommandList() {
		fieldCommandModel.removeAllElements();
		executableFieldCommandModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (field.getCommand() != null) {
				fieldCommandModel.addElement(new FieldCommand(field, field.getCommand()));
				if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser()) && field.getCommand().isExecutable()) {
					executableFieldCommandModel.addElement(new FieldCommand(field, field.getCommand()));
				}
			}
		}
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
		resourcePanel_2.updateResources(game, game.getLocalUser());
	}
	
	private void updatePlayerOrder() {
		orderPanel.updateTurnOrder(game);
		orderPanel_2.updateTurnOrder(game);
	}
	
	private void updatePointPanel() {
		pointPanel.updatePoints(game);
		pointPanel_2.updatePoints(game);
	}
	
	protected void updateFieldPanel() {
		fieldPanel.updateField(selectedField);
		fieldPanel_2.updateField(selectedField);
	}
	
	public Field getSelectedField() {
		return selectedField;
	}
	
	public void setSelectedField(Field field) {
		selectedField = field;
		panel_execution.updateField();
		panel_execution_2.updateField();
	}
	public void setFieldSelectionSucessfull(boolean fieldSelectionSucessfull) {
		panel_execution.setFieldSelectionSucessfull(fieldSelectionSucessfull);
		panel_execution_2.setFieldSelectionSucessfull(fieldSelectionSucessfull);
	}
	
	protected void showOtherBoardView() {
		boardPanel.showOtherView();
		boardPanel_2.showOtherView();
	}
}