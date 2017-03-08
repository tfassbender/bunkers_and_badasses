package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class ResourceInfoDialog extends JDialog {
	
	private static final long serialVersionUID = -3046544027968281699L;
	
	private final JPanel contentPanel = new JPanel();
	
	private Game game;
	
	private ResourceInfoPanel resourcePanel;
	private FieldDescriptionPanel fieldPanel;
	
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	
	private JTextField txtCredits;
	private JTextField txtAmmo;
	private JTextField txtEridium;
	private JTextField txtFields;
	private JTextField txtTroops;
	private JTextField txtCommands;
	private JTextField txtCredits_1;
	private JTextField txtAmmo_1;
	private JTextField txtEridium_1;
	
	public ResourceInfoDialog(Game game) {
		this.game = game;
		
		setTitle("Bunkers and Badasses - Resourcen Info");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResourceInfoDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1000, 600);
		setMinimumSize(new Dimension(1000, 600));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[200px,grow][100px,grow][150px,grow][100px,grow][150px,grow]", "[300px,grow][300px,grow]"));
			
			JPanel panel_kost_overview = new JPanel();
			panel_kost_overview.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_kost_overview.setBackground(Color.GRAY);
			panel.add(panel_kost_overview, "cell 0 0,grow");
			panel_kost_overview.setLayout(new MigLayout("", "[grow][][grow][][grow]", "[][10px][][][][][10px][][][][][grow]"));
			
			JLabel lblKostenbersicht = new JLabel("Kosten \u00DCbersicht:");
			lblKostenbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_kost_overview.add(lblKostenbersicht, "cell 1 0 3 1,alignx center");
			
			JLabel lblKostenGesammt = new JLabel("Kosten Gesammt:");
			lblKostenGesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblKostenGesammt, "cell 1 2 3 1,alignx center");
			
			JLabel lblCredits = new JLabel("Credits:");
			lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblCredits, "cell 1 3,alignx trailing");
			
			txtCredits = new JTextField();
			txtCredits.setBackground(Color.LIGHT_GRAY);
			txtCredits.setEditable(false);
			txtCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtCredits, "cell 2 3 2 1,growx");
			txtCredits.setColumns(10);
			
			JLabel lblMunition = new JLabel("Munition:");
			lblMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblMunition, "cell 1 4,alignx trailing");
			
			txtAmmo = new JTextField();
			txtAmmo.setBackground(Color.LIGHT_GRAY);
			txtAmmo.setEditable(false);
			txtAmmo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtAmmo, "cell 2 4 2 1,growx");
			txtAmmo.setColumns(10);
			
			JLabel lblEridium = new JLabel("Eridium:");
			lblEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblEridium, "cell 1 5,alignx trailing");
			
			txtEridium = new JTextField();
			txtEridium.setBackground(Color.LIGHT_GRAY);
			txtEridium.setEditable(false);
			txtEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtEridium, "cell 2 5 2 1,growx");
			txtEridium.setColumns(10);
			
			JLabel lblKostenDetail = new JLabel("Kosten Detail:");
			lblKostenDetail.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(lblKostenDetail, "cell 1 7 3 1,alignx center");
			
			JLabel lblGebiete_1 = new JLabel("Gebiete:");
			panel_kost_overview.add(lblGebiete_1, "cell 1 8,alignx trailing");
			
			txtFields = new JTextField();
			txtFields.setBackground(Color.LIGHT_GRAY);
			txtFields.setEditable(false);
			txtFields.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtFields, "cell 2 8,growx");
			txtFields.setColumns(10);
			
			JLabel lblCredits_1 = new JLabel("Credits");
			panel_kost_overview.add(lblCredits_1, "cell 3 8");
			
			JLabel lblTruppen = new JLabel("Truppen:");
			panel_kost_overview.add(lblTruppen, "cell 1 9,alignx trailing");
			
			txtTroops = new JTextField();
			txtTroops.setBackground(Color.LIGHT_GRAY);
			txtTroops.setEditable(false);
			txtTroops.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtTroops, "cell 2 9,growx");
			txtTroops.setColumns(10);
			
			JLabel lblCredits_2 = new JLabel("Credits");
			panel_kost_overview.add(lblCredits_2, "cell 3 9");
			
			JLabel lblBefehle = new JLabel("Befehle:");
			panel_kost_overview.add(lblBefehle, "cell 1 10,alignx trailing");
			
			txtCommands = new JTextField();
			txtCommands.setBackground(Color.LIGHT_GRAY);
			txtCommands.setEditable(false);
			txtCommands.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_kost_overview.add(txtCommands, "cell 2 10,growx");
			txtCommands.setColumns(10);
			
			JLabel lblMunition_1 = new JLabel("Munition");
			panel_kost_overview.add(lblMunition_1, "cell 3 10");
			
			JPanel panel_resource_buildings = new JPanel();
			panel_resource_buildings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_resource_buildings.setBackground(Color.GRAY);
			panel.add(panel_resource_buildings, "cell 1 0 2 1,grow");
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
			panel.add(resourcePanel, "cell 3 0 2 1,grow");
			
			JPanel panel_fields = new JPanel();
			panel_fields.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_fields.setBackground(Color.GRAY);
			panel.add(panel_fields, "cell 0 1 2 1,grow");
			panel_fields.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
			
			JLabel lblGebiete = new JLabel("Gebiete:");
			lblGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields.add(lblGebiete, "cell 0 0,alignx center");
			
			JScrollPane scrollPane_fields_all = new JScrollPane();
			panel_fields.add(scrollPane_fields_all, "cell 0 2,grow");
			
			JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
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
			panel.add(fieldPanel, "cell 2 1 2 1,grow");
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/marcus_1.png"));
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setBackground(Color.GRAY);
			panel.add(panel_image, "cell 4 1,grow");
		}
		
		update();
	}
	
	public void update() {
		updateResources();
		updateFields();
		updateCosts();
		updateBuildings();
	}
	
	private void updateCosts() {
		//TODO count the costs for fields, commands, hero cards, ...
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
	
	private void updateBuildings() {
		int credits = 0;
		int ammo = 0;
		int eridium = 0;
		buildingsPlayerListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation().equals(game.getLocalUser())) {
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