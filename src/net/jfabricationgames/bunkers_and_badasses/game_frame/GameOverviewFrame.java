package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class GameOverviewFrame extends JFrame {
	
	private static final long serialVersionUID = 7138951336417150419L;
	
	private JPanel contentPane;
	
	private Game game;
	
	private User selectedUser;
	
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	private PointPanel pointPanel;
	private PlayerOrderPanel orderPanel;
	
	//private DefaultListModel<User> userListModel = new DefaultListModel<User>();
	private DefaultListModel<User> selectPlayerListModel = new DefaultListModel<User>();
	private DefaultListModel<User> userBorderListModel = new DefaultListModel<User>();
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	
	private JTextField txtSpieler_1;
	private JTextField txtTruppenstrke;
	private JTextField txtGebude_1;
	private JTextField txtHelden;
	private JTextField txtBasis;
	private JTextField txtPunkte;
	private JTextField txtPosition;
	private JTextField txtGrenzen;
	private JTextField txtGebiete;
	
	public GameOverviewFrame(Game game) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		selectedUser = game.getLocalUser();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameOverviewFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Spiel Übersicht - Bunkers and Badasses");
		setBounds(100, 100, 1100, 600);
		setMinimumSize(new Dimension(1100, 600));
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[200px,grow][200px,grow][200px,grow][125px,grow][25px,grow][200px,grow]", "[150px,grow][100px,grow][100px,grow][200px,grow]"));
		
		pointPanel = new PointPanel();
		panel.add(pointPanel, "cell 0 0 1 2,grow");
		
		ImagePanel panel_image_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/salvador_1.png"));
		panel_image_1.setToolTipText("Salvadore: Doppelte Knarren = Doppelter Spa\u00DF");
		panel_image_1.setAdaptSizeKeepProportion(true);
		panel_image_1.setCentered(true);
		panel_image_1.setBackground(Color.GRAY);
		panel.add(panel_image_1, "cell 1 0,grow");
		
		JPanel panel_player_info = new JPanel();
		panel_player_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_player_info.setBackground(Color.GRAY);
		panel.add(panel_player_info, "cell 2 0 2 2,grow");
		panel_player_info.setLayout(new MigLayout("", "[grow][][100px,grow][10px][][][:50px:100px,grow][35px:n:35px][grow]", "[][5px,grow][][5px][][][][][][][::5px][grow]"));
		
		JLabel lblSpielerInfo = new JLabel("Spieler Info:");
		lblSpielerInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_player_info.add(lblSpielerInfo, "cell 0 0 9 1,alignx center");
		
		JLabel lblSpieler_1 = new JLabel("Spieler:");
		lblSpieler_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblSpieler_1, "cell 1 2,alignx trailing");
		
		txtSpieler_1 = new JTextField();
		txtSpieler_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpieler_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSpieler_1.setEditable(false);
		txtSpieler_1.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtSpieler_1, "cell 2 2,growx");
		txtSpieler_1.setColumns(10);
		
		JLabel lblBasis = new JLabel("Basis:");
		lblBasis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblBasis, "cell 4 2,alignx trailing");
		
		txtBasis = new JTextField();
		txtBasis.setHorizontalAlignment(SwingConstants.CENTER);
		txtBasis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtBasis.setEditable(false);
		txtBasis.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtBasis, "cell 5 2 3 1,growx");
		txtBasis.setColumns(10);
		
		JLabel lblTruppenstrke = new JLabel("Truppenst\u00E4rke:");
		lblTruppenstrke.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblTruppenstrke, "cell 1 4,alignx trailing");
		
		txtTruppenstrke = new JTextField();
		txtTruppenstrke.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenstrke.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtTruppenstrke.setEditable(false);
		txtTruppenstrke.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtTruppenstrke, "cell 2 4,growx");
		txtTruppenstrke.setColumns(10);
		
		JLabel lblGrenzenZu = new JLabel("Grenzen zu:");
		lblGrenzenZu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblGrenzenZu, "cell 4 4 2 1,alignx trailing");
		
		txtGrenzen = new JTextField();
		txtGrenzen.setBackground(Color.LIGHT_GRAY);
		txtGrenzen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtGrenzen.setEditable(false);
		panel_player_info.add(txtGrenzen, "cell 7 4,growx");
		txtGrenzen.setColumns(10);
		
		JLabel lblGebiete_1 = new JLabel("Gebiete:");
		lblGebiete_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblGebiete_1, "cell 1 5,alignx trailing");
		
		txtGebiete = new JTextField();
		txtGebiete.setEditable(false);
		txtGebiete.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtGebiete.setHorizontalAlignment(SwingConstants.CENTER);
		txtGebiete.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtGebiete, "cell 2 5,growx");
		txtGebiete.setColumns(10);
		
		JLabel lblGebude_2 = new JLabel("Geb\u00E4ude:");
		lblGebude_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblGebude_2, "cell 1 6,alignx trailing");
		
		txtGebude_1 = new JTextField();
		txtGebude_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtGebude_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtGebude_1.setEditable(false);
		txtGebude_1.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtGebude_1, "cell 2 6,growx");
		txtGebude_1.setColumns(10);
		
		JScrollPane scrollPane_borders_users = new JScrollPane();
		panel_player_info.add(scrollPane_borders_users, "cell 4 5 4 6,grow");
		
		JList<User> list_borders_users = new JList<User>(userBorderListModel);
		list_borders_users.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				countBordersTo(list_borders_users.getSelectedValue());
			}
		});
		list_borders_users.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_borders_users.setBackground(Color.LIGHT_GRAY);
		scrollPane_borders_users.setViewportView(list_borders_users);
		
		JLabel lblHelden = new JLabel("Helden:");
		lblHelden.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblHelden, "cell 1 7,alignx trailing");
		
		txtHelden = new JTextField();
		txtHelden.setHorizontalAlignment(SwingConstants.CENTER);
		txtHelden.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHelden.setEditable(false);
		txtHelden.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtHelden, "cell 2 7,growx");
		txtHelden.setColumns(10);
		
		JLabel lblPunkte_1 = new JLabel("Punkte:");
		lblPunkte_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblPunkte_1, "cell 1 8,alignx trailing");
		
		txtPunkte = new JTextField();
		txtPunkte.setHorizontalAlignment(SwingConstants.CENTER);
		txtPunkte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPunkte.setEditable(false);
		txtPunkte.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtPunkte, "cell 2 8,growx");
		txtPunkte.setColumns(10);
		
		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_player_info.add(lblPosition, "cell 1 9,alignx trailing");
		
		txtPosition = new JTextField();
		txtPosition.setHorizontalAlignment(SwingConstants.CENTER);
		txtPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPosition.setEditable(false);
		txtPosition.setBackground(Color.LIGHT_GRAY);
		panel_player_info.add(txtPosition, "cell 2 9,growx");
		txtPosition.setColumns(10);
		
		JPanel panel_players = new JPanel();
		panel_players.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_players.setBackground(Color.GRAY);
		panel.add(panel_players, "cell 1 1 1 2,grow");
		panel_players.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblSpielerAuswhlen = new JLabel("Spieler Ausw\u00E4hlen:");
		lblSpielerAuswhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_players.add(lblSpielerAuswhlen, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_player = new JScrollPane();
		panel_players.add(scrollPane_player, "cell 0 1,grow");
		
		JList<User> list_player = new JList<User>(selectPlayerListModel);
		list_player.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedUser = list_player.getSelectedValue();
				selectPlayer(selectedUser);
				updateResources();
			}
		});
		list_player.setBackground(Color.LIGHT_GRAY);
		list_player.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_player.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_player.setViewportView(list_player);

		orderPanel = new PlayerOrderPanel();
		panel.add(orderPanel, "cell 0 2 1 2,grow");
		
		resourcePanel = new ResourceInfoPanel();
		panel.add(resourcePanel, "cell 4 0 2 2,grow");
		
		JPanel panel_fields = new JPanel();
		panel_fields.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fields.setBackground(Color.GRAY);
		panel.add(panel_fields, "cell 2 2 1 2,grow");
		panel_fields.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblGebiete = new JLabel("Gebiete:");
		lblGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fields.add(lblGebiete, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_fields_all = new JScrollPane();
		panel_fields.add(scrollPane_fields_all, "cell 0 2,grow");
		
		JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
		list_fields_all.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectField(list_fields_all.getSelectedValue());
			}
		});
		list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_fields_all.setBackground(Color.LIGHT_GRAY);
		scrollPane_fields_all.setViewportView(list_fields_all);

		fieldPanel = new FieldDescriptionPanel("Feld Übersicht", true);
		panel.add(fieldPanel, "cell 3 2 2 2,grow");
		
		JPanel panel_buildings = new JPanel();
		panel.add(panel_buildings, "cell 5 2 1 2,grow");
		panel_buildings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_buildings.setBackground(Color.GRAY);
		panel_buildings.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblGebude_1 = new JLabel("Geb\u00E4ude:");
		lblGebude_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_buildings.add(lblGebude_1, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_buildings_player = new JScrollPane();
		panel_buildings.add(scrollPane_buildings_player, "cell 0 2,grow");
		
		JList<FieldBuilding> list_buildings_player = new JList<FieldBuilding>(buildingsPlayerListModel);
		list_buildings_player.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_buildings_player.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_buildings_player.setBackground(Color.LIGHT_GRAY);
		scrollPane_buildings_player.setViewportView(list_buildings_player);
		
		ImagePanel panel_image_2 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/roland_1.png"));
		panel_image_2.setToolTipText("Roland: Commander der Crimson Raiders");
		panel_image_2.setAdaptSizeKeepProportion(true);
		panel_image_2.setCentered(true);
		panel_image_2.setBackground(Color.GRAY);
		panel.add(panel_image_2, "cell 1 3,grow");
	}
	
	public void update() {
		updateResources();
		updatePoints();
		updateTurnOrder();
		updatePlayerBuildings();
		updatePlayers();
		updateFields();
		selectPlayer(selectedUser);
		repaint();
	}
	
	private void selectField(Field field) {
		fieldPanel.updateField(field);
	}
	
	private void selectPlayer(User player) {
		int buildings = 0;
		int troops = 0;
		int fields = 0;
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(player)) {
				fields++;
				troops += field.getNormalTroops();
				troops += field.getBadassTroops()*2;//strength of the badass troops
				if (!(field.getBuilding() instanceof EmptyBuilding)) {
					buildings++;
				}
			}
		}
		txtSpieler_1.setText(player.getUsername());
		txtTruppenstrke.setText(Integer.toString(troops));
		txtGebiete.setText(Integer.toString(fields));
		txtGebude_1.setText(Integer.toString(buildings));
		txtHelden.setText(Integer.toString(game.getHeroCardManager().getHeroCards(player).size()));
		txtPunkte.setText(Integer.toString(game.getPointManager().getPoints(player)));
		txtPosition.setText(Integer.toString(game.getPointManager().getPosition(player)));
		txtGrenzen.setText("---");
		userBorderListModel.removeAllElements();
		for (User user : game.getPlayers()) {
			if (!user.equals(player)) {
				userBorderListModel.addElement(user);
			}
		}
	}
	
	private void updateFields() {
		fieldAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
		}
	}
	
	private void updatePlayers() {
		selectPlayerListModel.removeAllElements();
		for (User user : game.getPlayers()) {
			selectPlayerListModel.addElement(user);
		}
	}
	
	private void updatePlayerBuildings() {
		buildingsPlayerListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(selectedUser) && !(field.getBuilding() instanceof EmptyBuilding)) {
				buildingsPlayerListModel.addElement(new FieldBuilding(field, field.getBuilding()));
			}
		}
	}
	
	private void countBordersTo(User player) {
		int borders = 0;
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(selectedUser)) {
				for (Field neighbour : field.getNeighbours()) {
					if (neighbour.getAffiliation().equals(player)) {
						borders++;
					}
				}
			}
		}
		txtGrenzen.setText(Integer.toString(borders));
	}
	
	private void updateTurnOrder() {
		orderPanel.updateTurnOrder(game);
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, selectedUser);
	}
	
	private void updatePoints() {
		pointPanel.updatePoints(game);
	}
}