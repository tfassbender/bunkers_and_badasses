package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
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
import net.jfabricationgames.bunkers_and_badasses.game.UserResource;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ArschgaulsPalace;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.CrazyEarlsBlackMarket;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MarcusGunshop;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MoxxisTavern;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.MoxxisUnderdome;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.RolandsTurret;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.ScootersCatchARide;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TannisResearchStation;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TinyTinasMine;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.TorguesBadassDome;
import net.miginfocom.swing.MigLayout;

public class ResourceInfoFrame extends JFrame {
	
	private static final long serialVersionUID = -3046544027968281699L;
	
	private final JPanel contentPanel = new JPanel();
	
	private final Building[] buildings = createBuildings();
	
	private Game game;
	
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	
	private JTextField txtCredits;
	private JTextField txtAmmo;
	//private JTextField txtFields;
	private JTextField txtCredits_1;
	private JTextField txtAmmo_1;
	private JTextField txtEridium_1;
	private JList<Field> list_fields_all;
	private JTextField txtCostscredits;
	private JTextField txtGaincredits;
	private JTextField txtCostsammo;
	private JTextField txtGainammo;
	private JTextField txtCostseridium;
	private JTextField txtGaineridium;
	private JComboBox<Building> comboBox;
	
	public ResourceInfoFrame(Game game) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setTitle("Bunkers and Badasses - Resourcen Info");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceInfoFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1050, 650);
		setMinimumSize(new Dimension(1050, 650));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[200px,grow][100px,grow][150px,grow][100px,grow][150px,grow]", "[150px,grow][200px,grow][300px,grow]"));
			
			JPanel panel_kost_overview = new JPanel();
			panel_kost_overview.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_kost_overview.setBackground(Color.GRAY);
			panel.add(panel_kost_overview, "cell 0 0,grow");
			panel_kost_overview.setLayout(new MigLayout("", "[grow][][grow][][grow]", "[][grow][][][][grow]"));
			
			JLabel lblKostenbersicht = new JLabel("Kosten \u00DCbersicht:");
			lblKostenbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_kost_overview.add(lblKostenbersicht, "cell 1 0 3 1,alignx center");
			
			JLabel lblKostenGesammt = new JLabel("Kosten Gebiet:");
			lblKostenGesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblKostenGesammt, "cell 1 2 3 1,alignx center");
			
			JLabel lblCredits = new JLabel("Credits:");
			lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblCredits, "cell 1 3,alignx trailing");
			
			txtCredits = new JTextField();
			txtCredits.setHorizontalAlignment(SwingConstants.CENTER);
			txtCredits.setBackground(Color.LIGHT_GRAY);
			txtCredits.setEditable(false);
			txtCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtCredits, "cell 2 3 2 1,growx");
			txtCredits.setColumns(10);
			
			JLabel lblMunition = new JLabel("Munition:");
			lblMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblMunition, "cell 1 4,alignx trailing");
			
			txtAmmo = new JTextField();
			txtAmmo.setHorizontalAlignment(SwingConstants.CENTER);
			txtAmmo.setBackground(Color.LIGHT_GRAY);
			txtAmmo.setEditable(false);
			txtAmmo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtAmmo, "cell 2 4 2 1,growx");
			txtAmmo.setColumns(10);
			
			JPanel panel_resource_buildings = new JPanel();
			panel_resource_buildings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_resource_buildings.setBackground(Color.GRAY);
			panel.add(panel_resource_buildings, "cell 1 0 2 2,grow");
			panel_resource_buildings.setLayout(new MigLayout("", "[grow][][50px][][50px][][50px][grow]", "[][grow][][]"));
			
			JLabel lblResourcenGebude = new JLabel("Resourcen Gewinnende Geb\u00E4ude:");
			lblResourcenGebude.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_resource_buildings.add(lblResourcenGebude, "cell 0 0 8 1,alignx center");
			
			JScrollPane scrollPane = new JScrollPane();
			panel_resource_buildings.add(scrollPane, "cell 1 1 6 1,grow");
			
			JList<FieldBuilding> list_resouce_buildings = new JList<FieldBuilding>(buildingsPlayerListModel);
			list_resouce_buildings.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_resouce_buildings.setBackground(Color.LIGHT_GRAY);
			list_resouce_buildings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(list_resouce_buildings);
			
			JLabel lblGesammtGewinn = new JLabel("Gesammt Gewinn:");
			lblGesammtGewinn.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(lblGesammtGewinn, "cell 1 2 6 1,alignx center");
			
			JLabel lblCredits_3 = new JLabel("Credits:");
			lblCredits_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(lblCredits_3, "cell 1 3,alignx trailing");
			
			txtCredits_1 = new JTextField();
			txtCredits_1.setHorizontalAlignment(SwingConstants.CENTER);
			txtCredits_1.setBackground(Color.LIGHT_GRAY);
			txtCredits_1.setEditable(false);
			txtCredits_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(txtCredits_1, "cell 2 3,growx");
			txtCredits_1.setColumns(10);
			
			JLabel lblMunition_2 = new JLabel("Munition:");
			lblMunition_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(lblMunition_2, "cell 3 3,alignx trailing");
			
			txtAmmo_1 = new JTextField();
			txtAmmo_1.setHorizontalAlignment(SwingConstants.CENTER);
			txtAmmo_1.setBackground(Color.LIGHT_GRAY);
			txtAmmo_1.setEditable(false);
			txtAmmo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(txtAmmo_1, "cell 4 3,growx");
			txtAmmo_1.setColumns(10);
			
			JLabel lblEridium_1 = new JLabel("Eridium:");
			lblEridium_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(lblEridium_1, "cell 5 3,alignx trailing");
			
			txtEridium_1 = new JTextField();
			txtEridium_1.setHorizontalAlignment(SwingConstants.CENTER);
			txtEridium_1.setBackground(Color.LIGHT_GRAY);
			txtEridium_1.setEditable(false);
			txtEridium_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(txtEridium_1, "cell 6 3,growx");
			txtEridium_1.setColumns(10);

			resourcePanel = new ResourceInfoPanel();
			panel.add(resourcePanel, "cell 3 0 2 2,grow");
			
			JPanel panel_building_costs = new JPanel();
			panel_building_costs.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_building_costs.setBackground(Color.GRAY);
			panel.add(panel_building_costs, "cell 0 1,grow");
			panel_building_costs.setLayout(new MigLayout("", "[][grow]", "[][10px][][grow]"));
			
			JLabel lblGebudeKosten = new JLabel("Geb\u00E4ude Kosten:");
			lblGebudeKosten.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_building_costs.add(lblGebudeKosten, "cell 0 0 2 1,alignx center");
			
			JLabel lblGebude = new JLabel("Geb\u00E4ude:");
			lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_building_costs.add(lblGebude, "cell 0 2,alignx trailing");
			
			DefaultComboBoxModel<Building> model = new DefaultComboBoxModel<Building>(buildings);
			comboBox = new JComboBox<Building>(model);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateBuildingCosts();
				}
			});
			panel_building_costs.add(comboBox, "cell 1 2,growx");
			
			JPanel panel_building_costs_2 = new JPanel();
			panel_building_costs_2.setBackground(Color.GRAY);
			panel_building_costs.add(panel_building_costs_2, "cell 0 3 2 1,grow");
			panel_building_costs_2.setLayout(new MigLayout("", "[][grow][grow]", "[][grow][grow][grow]"));
			
			JLabel lblKosten = new JLabel("Kosten:");
			lblKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_building_costs_2.add(lblKosten, "cell 1 0,alignx center");
			
			JLabel lblErtrge = new JLabel("Ertr\u00E4ge:");
			lblErtrge.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_building_costs_2.add(lblErtrge, "cell 2 0,alignx center");
			
			JLabel lblCredits_1 = new JLabel("Credits:");
			lblCredits_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_building_costs_2.add(lblCredits_1, "cell 0 1,alignx trailing");
			
			txtCostscredits = new JTextField();
			txtCostscredits.setHorizontalAlignment(SwingConstants.CENTER);
			txtCostscredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtCostscredits.setBackground(Color.LIGHT_GRAY);
			txtCostscredits.setEditable(false);
			panel_building_costs_2.add(txtCostscredits, "cell 1 1,alignx center");
			txtCostscredits.setColumns(3);
			
			txtGaincredits = new JTextField();
			txtGaincredits.setHorizontalAlignment(SwingConstants.CENTER);
			txtGaincredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGaincredits.setBackground(Color.LIGHT_GRAY);
			txtGaincredits.setEditable(false);
			panel_building_costs_2.add(txtGaincredits, "cell 2 1,alignx center");
			txtGaincredits.setColumns(3);
			
			JLabel lblMunition_1 = new JLabel("Munition:");
			lblMunition_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_building_costs_2.add(lblMunition_1, "cell 0 2,alignx trailing");
			
			txtCostsammo = new JTextField();
			txtCostsammo.setHorizontalAlignment(SwingConstants.CENTER);
			txtCostsammo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtCostsammo.setBackground(Color.LIGHT_GRAY);
			txtCostsammo.setEditable(false);
			panel_building_costs_2.add(txtCostsammo, "cell 1 2,alignx center");
			txtCostsammo.setColumns(3);
			
			txtGainammo = new JTextField();
			txtGainammo.setHorizontalAlignment(SwingConstants.CENTER);
			txtGainammo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGainammo.setBackground(Color.LIGHT_GRAY);
			txtGainammo.setEditable(false);
			panel_building_costs_2.add(txtGainammo, "cell 2 2,alignx center");
			txtGainammo.setColumns(3);
			
			JLabel lblEridium = new JLabel("Eridium:");
			lblEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_building_costs_2.add(lblEridium, "cell 0 3,alignx trailing");
			
			txtCostseridium = new JTextField();
			txtCostseridium.setHorizontalAlignment(SwingConstants.CENTER);
			txtCostseridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtCostseridium.setBackground(Color.LIGHT_GRAY);
			txtCostseridium.setEditable(false);
			panel_building_costs_2.add(txtCostseridium, "cell 1 3,alignx center");
			txtCostseridium.setColumns(3);
			
			txtGaineridium = new JTextField();
			txtGaineridium.setHorizontalAlignment(SwingConstants.CENTER);
			txtGaineridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGaineridium.setBackground(Color.LIGHT_GRAY);
			txtGaineridium.setEditable(false);
			panel_building_costs_2.add(txtGaineridium, "cell 2 3,alignx center");
			txtGaineridium.setColumns(3);
			
			JPanel panel_fields = new JPanel();
			panel_fields.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_fields.setBackground(Color.GRAY);
			panel.add(panel_fields, "cell 0 2 2 1,grow");
			panel_fields.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
			
			JLabel lblGebiete = new JLabel("Gebiete:");
			lblGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields.add(lblGebiete, "cell 0 0,alignx center");
			
			JScrollPane scrollPane_fields_all = new JScrollPane();
			panel_fields.add(scrollPane_fields_all, "cell 0 2,grow");
			
			list_fields_all = new JList<Field>(fieldAllListModel);
			list_fields_all.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					updateField(list_fields_all.getSelectedValue());
				}
			});
			list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_fields_all.setBackground(Color.LIGHT_GRAY);
			scrollPane_fields_all.setViewportView(list_fields_all);
			
			fieldPanel = new FieldDescriptionPanel("Feld Übersicht", true);
			panel.add(fieldPanel, "cell 2 2 2 1,grow");
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/marcus_1.png"));
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setBackground(Color.GRAY);
			panel.add(panel_image, "cell 4 2,grow");
		}
		
		update();
	}
	
	public void update() {
		updateResources();
		updateFields();
		updateCosts();
		updateBuildings();
		updateBuildingCosts();
	}
	
	private void updateCosts() {
		txtCredits.setText("");
		txtAmmo.setText("");
		Field field = list_fields_all.getSelectedValue();
		if (field != null && field.getCommand() != null) {
			txtCredits.setText(Integer.toString(UserResource.getCreditsForCommand(field.getCommand(), field)));
			txtAmmo.setText(Integer.toString(UserResource.getAmmoForCommand(field.getCommand(), field)));
		}
		//txtFields.setText(Integer.toString(game.getBoard().getUsersFields(game.getLocalUser()).size() * Game.getGameVariableStorage().getFieldCosts()));
	}
	
	private void updateBuildingCosts() {
		Building building = (Building) comboBox.getSelectedItem();
		if (building != null) {
			txtCostscredits.setText(Integer.toString(building.getBuildingPrice()[0]));
			txtCostsammo.setText(Integer.toString(building.getBuildingPrice()[1]));
			txtCostseridium.setText(Integer.toString(building.getBuildingPrice()[2]));
			txtGaincredits.setText(Integer.toString(building.getCreditMining()));
			txtGainammo.setText(Integer.toString(building.getAmmoMining()));
			txtGaineridium.setText(Integer.toString(building.getEridiumMining()));			
		}
	}
	
	private void updateField(Field field) {
		fieldPanel.updateField(field);
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
	}
	
	private void updateFields() {
		fieldAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
		}
	}
	
	private Building[] createBuildings() {
		Building[] buildings = new Building[10];
		buildings[0] = new ArschgaulsPalace();
		buildings[1] = new CrazyEarlsBlackMarket();
		buildings[2] = new MarcusGunshop();
		buildings[3] = new MoxxisTavern();
		buildings[4] = new MoxxisUnderdome();
		buildings[5] = new RolandsTurret();
		buildings[6] = new ScootersCatchARide();
		buildings[7] = new TannisResearchStation();
		buildings[8] = new TinyTinasMine();
		buildings[9] = new TorguesBadassDome();
		return buildings;
	}
	
	private void updateBuildings() {
		int credits = 0;
		int ammo = 0;
		int eridium = 0;
		buildingsPlayerListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
				credits += field.getBuilding().getCreditMining();
				ammo += field.getBuilding().getAmmoMining();
				eridium += field.getBuilding().getEridiumMining();
				buildingsPlayerListModel.addElement(new FieldBuilding(field, field.getBuilding()));
			}
		}
		txtCredits_1.setText(Integer.toString(credits));
		txtAmmo_1.setText(Integer.toString(ammo));
		txtEridium_1.setText(Integer.toString(eridium));
	}
}