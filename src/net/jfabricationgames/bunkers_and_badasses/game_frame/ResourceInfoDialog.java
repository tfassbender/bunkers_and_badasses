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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.TurnPlaningFrame.FieldBuilding;
import net.miginfocom.swing.MigLayout;

public class ResourceInfoDialog extends JDialog {
	
	private static final long serialVersionUID = -3046544027968281699L;
	
	private final JPanel contentPanel = new JPanel();
	
	private ListModel<Field> fieldListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	
	private JTextField txtSpieler;
	private JTextField txtFeld;
	private JTextField txtRegion;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtTruppennormal;
	private JTextField txtTruppenbadass;
	private JTextField txtCredits;
	private JTextField txtAmmo;
	private JTextField txtEridium;
	private JTextField txtFields;
	private JTextField txtTroops;
	private JTextField txtCommands;
	private JTextField txtCredits_1;
	private JTextField txtAmmo_1;
	private JTextField txtEridium_1;
	
	public ResourceInfoDialog() {
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
			txtCredits_1.setBackground(Color.LIGHT_GRAY);
			txtCredits_1.setEditable(false);
			txtCredits_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(txtCredits_1, "cell 2 3,growx");
			txtCredits_1.setColumns(10);
			
			JLabel lblMunition_2 = new JLabel("Munition:");
			lblMunition_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(lblMunition_2, "cell 3 3,alignx trailing");
			
			txtAmmo_1 = new JTextField();
			txtAmmo_1.setBackground(Color.LIGHT_GRAY);
			txtAmmo_1.setEditable(false);
			txtAmmo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(txtAmmo_1, "cell 4 3,growx");
			txtAmmo_1.setColumns(10);
			
			JLabel lblEridium_1 = new JLabel("Eridium:");
			lblEridium_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(lblEridium_1, "cell 5 3,alignx trailing");
			
			txtEridium_1 = new JTextField();
			txtEridium_1.setBackground(Color.LIGHT_GRAY);
			txtEridium_1.setEditable(false);
			txtEridium_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resource_buildings.add(txtEridium_1, "cell 6 3,growx");
			txtEridium_1.setColumns(10);
			
			JPanel panel_resources = new JPanel();
			panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_resources.setBackground(Color.GRAY);
			panel.add(panel_resources, "cell 3 0 2 1,grow");
			panel_resources.setLayout(new MigLayout("", "[right][grow,center][grow,center][grow,center]", "[][grow][][grow][grow][grow][grow]"));
			
			JLabel lblResourcen = new JLabel("Resourcen:");
			lblResourcen.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_resources.add(lblResourcen, "cell 0 0 4 1,alignx center");
			
			JLabel lblbrigeResourcen = new JLabel("\u00DCbrig:");
			lblbrigeResourcen.setToolTipText("Im Moment vorhandene Resourcen");
			lblbrigeResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resources.add(lblbrigeResourcen, "cell 1 2");
			
			JLabel lblErhaltnchsteRunde = new JLabel("Erhalt:");
			lblErhaltnchsteRunde.setToolTipText("<html>\r\nMomentaner Erhalt an Resourcen zu<br>\r\nBeginn der n\u00E4chsten Runde. (Resourcen<br>\r\nGewinnungsbefehle nicht ber\u00FCcksichtigt)<br>\r\n</html>");
			lblErhaltnchsteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resources.add(lblErhaltnchsteRunde, "cell 2 2");
			
			JLabel lblVerbrauchletzteRunde = new JLabel("Verbrauch:");
			lblVerbrauchletzteRunde.setToolTipText("<html>\r\nVerbrauchte Resourcen (f\u00FCr <br>\r\nBefehle) in der letzten Runde\r\n</html>");
			lblVerbrauchletzteRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resources.add(lblVerbrauchletzteRunde, "cell 3 2");
			
			JLabel labelCredits = new JLabel("Credits:");
			labelCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resources.add(labelCredits, "cell 0 3");
			
			JLabel lblLC = new JLabel("");
			panel_resources.add(lblLC, "cell 1 3");
			
			JLabel lblGC = new JLabel("");
			panel_resources.add(lblGC, "cell 2 3");
			
			JLabel lblUC = new JLabel("");
			panel_resources.add(lblUC, "cell 3 3");
			
			JLabel labelMunition = new JLabel("Munition:");
			labelMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resources.add(labelMunition, "cell 0 4");
			
			JLabel lblLA = new JLabel("");
			panel_resources.add(lblLA, "cell 1 4");
			
			JLabel lblGA = new JLabel("");
			panel_resources.add(lblGA, "cell 2 4");
			
			JLabel lblUA = new JLabel("");
			panel_resources.add(lblUA, "cell 3 4");
			
			JLabel labelEridium = new JLabel("Eridium:");
			labelEridium.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_resources.add(labelEridium, "cell 0 5");
			
			JLabel lblLE = new JLabel("");
			panel_resources.add(lblLE, "cell 1 5");
			
			JLabel lblGE = new JLabel("");
			panel_resources.add(lblGE, "cell 2 5");
			
			JLabel lblUE = new JLabel("");
			panel_resources.add(lblUE, "cell 3 5");
			
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
			list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_fields_all.setBackground(Color.LIGHT_GRAY);
			scrollPane_fields_all.setViewportView(list_fields_all);
			
			JPanel panel_field = new JPanel();
			panel_field.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_field.setBackground(Color.GRAY);
			panel.add(panel_field, "cell 2 1 2 1,grow");
			panel_field.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][][][][5px][][50px,grow]"));
			
			JLabel lblFeldbersicht = new JLabel("Feld \u00DCbersicht:");
			lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_field.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
			
			JLabel lblFeld = new JLabel("Feld:");
			lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblFeld, "cell 0 2,alignx trailing");
			
			txtFeld = new JTextField();
			txtFeld.setEditable(false);
			txtFeld.setBackground(Color.LIGHT_GRAY);
			txtFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtFeld, "cell 1 2 3 1,growx");
			txtFeld.setColumns(10);
			
			JLabel lblRegion = new JLabel("Region:");
			lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblRegion, "cell 0 3,alignx trailing");
			
			txtRegion = new JTextField();
			txtRegion.setEditable(false);
			txtRegion.setBackground(Color.LIGHT_GRAY);
			txtRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtRegion, "cell 1 3 3 1,growx");
			txtRegion.setColumns(10);
			
			JLabel lblSpieler = new JLabel("Spieler:");
			lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblSpieler, "cell 0 4,alignx trailing");
			
			txtSpieler = new JTextField();
			txtSpieler.setBackground(Color.LIGHT_GRAY);
			txtSpieler.setEditable(false);
			txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtSpieler, "cell 1 4 3 1,growx");
			txtSpieler.setColumns(10);
			
			JLabel lblBefehl_1 = new JLabel("Befehl:");
			lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblBefehl_1, "cell 0 5,alignx trailing");
			
			txtBefehl = new JTextField();
			txtBefehl.setBackground(Color.LIGHT_GRAY);
			txtBefehl.setEditable(false);
			txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtBefehl, "cell 1 5,growx");
			txtBefehl.setColumns(10);
			
			JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
			lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblNormaleTruppen, "cell 2 5,alignx trailing");
			
			txtTruppennormal = new JTextField();
			txtTruppennormal.setHorizontalAlignment(SwingConstants.CENTER);
			txtTruppennormal.setBackground(Color.LIGHT_GRAY);
			txtTruppennormal.setEditable(false);
			txtTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtTruppennormal, "cell 3 5,growx");
			txtTruppennormal.setColumns(10);
			
			JLabel lblGebude = new JLabel("Geb\u00E4ude:");
			lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblGebude, "cell 0 6,alignx trailing");
			
			txtGebude = new JTextField();
			txtGebude.setBackground(Color.LIGHT_GRAY);
			txtGebude.setEditable(false);
			txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtGebude, "cell 1 6,growx");
			txtGebude.setColumns(10);
			
			JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
			lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblBadassTruppen, "cell 2 6,alignx trailing");
			
			txtTruppenbadass = new JTextField();
			txtTruppenbadass.setHorizontalAlignment(SwingConstants.CENTER);
			txtTruppenbadass.setBackground(Color.LIGHT_GRAY);
			txtTruppenbadass.setEditable(false);
			txtTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(txtTruppenbadass, "cell 3 6,growx");
			txtTruppenbadass.setColumns(10);
			
			JLabel lblNachbarn = new JLabel("Nachbarn:");
			lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field.add(lblNachbarn, "cell 0 8 4 1,alignx center");
			
			JScrollPane scrollPane_neighbours = new JScrollPane();
			panel_field.add(scrollPane_neighbours, "cell 0 9 4 1,grow");
			
			JList<Field> list_neighbours = new JList<Field>(fieldListModel);
			list_neighbours.setToolTipText("<html>\r\nBenachbarte Felder\r\n</html>");
			list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_neighbours.setBackground(Color.LIGHT_GRAY);
			scrollPane_neighbours.setViewportView(list_neighbours);
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/marcus_1.png"));
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setBackground(Color.GRAY);
			panel.add(panel_image, "cell 4 1,grow");
		}
	}
}