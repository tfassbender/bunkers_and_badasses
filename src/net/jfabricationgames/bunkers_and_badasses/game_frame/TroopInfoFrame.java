package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
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

import com.jfabricationgames.toolbox.graphic.ImagePanel;
import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;
import com.jfabricationgames.toolbox.properties.event.PropertiesWindowListener;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.UserResource;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class TroopInfoFrame extends JFrame {
	
	private static final long serialVersionUID = 8964222516995478087L;
	
	private Game game;
	
	private Field selectedField;
	
	private ResourceInfoPanel resourcePanel;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtGebieteMitTruppen;
	private JTextField txtGebieteOhneTruppen;
	private JTextField txtGesammteTruppenstrke;
	private JTextField txtGesamtzahlTruppen;
	private JTextField txtNormaleTruppen;
	private JTextField txtBadassTruppen;
	private JTextField txtNormaleTruppen_1;
	private JTextField txtBadassTruppen_1;
	private JTextField txtGesammtKostenCredits_1;
	private JTextField txtGesammtKostenMunition_1;
	private JTextField txtSpieler;
	private JTextField txtBefehl;
	private JTextField txtField;
	private JTextField txtRegion;
	
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldControlledListModel = new DefaultListModel<Field>();
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
	public TroopInfoFrame(Game game) {
		addWindowListener(new PropertiesWindowListener(propsFile, PropertiesWindowListener.WINDOW_CLOSING_EVENT));
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(TroopInfoFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Truppen Info - Bunkers and Badasses");
		setBounds(100, 100, 900, 550);
		setMinimumSize(new Dimension(900, 550));
		
		propsFile.alignWindow();
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[:300px:500px,grow][100px,grow][100px,grow][300px,grow]", "[300px,grow][200px,grow]"));
			
			JPanel panel_troop_info = new JPanel();
			panel_troop_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_troop_info.setBackground(Color.GRAY);
			panel.add(panel_troop_info, "cell 0 0 2 1,grow");
			panel_troop_info.setLayout(new MigLayout("", "[grow][][50px][][grow]", "[][5px,grow][][][][][][][5px,grow]"));
			
			JLabel lblTruppenInfo = new JLabel("Truppen Info:");
			lblTruppenInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_troop_info.add(lblTruppenInfo, "cell 0 0 5 1,alignx center");
			
			JLabel lblGebieteMitTruppen = new JLabel("Gebiete mit Truppen:");
			lblGebieteMitTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblGebieteMitTruppen, "cell 1 2,alignx trailing");
			
			txtGebieteMitTruppen = new JTextField();
			txtGebieteMitTruppen.setEditable(false);
			txtGebieteMitTruppen.setHorizontalAlignment(SwingConstants.CENTER);
			txtGebieteMitTruppen.setBackground(Color.LIGHT_GRAY);
			txtGebieteMitTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtGebieteMitTruppen, "cell 2 2,growx");
			txtGebieteMitTruppen.setColumns(10);
			
			JLabel lblGebieteOhneTruppen = new JLabel("Gebiete ohne Truppen:");
			lblGebieteOhneTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblGebieteOhneTruppen, "cell 1 3,alignx trailing");
			
			txtGebieteOhneTruppen = new JTextField();
			txtGebieteOhneTruppen.setEditable(false);
			txtGebieteOhneTruppen.setHorizontalAlignment(SwingConstants.CENTER);
			txtGebieteOhneTruppen.setBackground(Color.LIGHT_GRAY);
			txtGebieteOhneTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtGebieteOhneTruppen, "cell 2 3,growx");
			txtGebieteOhneTruppen.setColumns(10);
			
			JLabel lblGesammteTruppenstrke = new JLabel("Gesammte Truppenst\u00E4rke:");
			lblGesammteTruppenstrke.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblGesammteTruppenstrke, "cell 1 4,alignx trailing");
			
			txtGesammteTruppenstrke = new JTextField();
			txtGesammteTruppenstrke.setToolTipText("<html>\r\nDie gesammte Kampfst\u00E4rke aller Truppen <br>\r\nauf dem Feld (Normale Truppen: 1, Badass <br>\r\nTruppen: 2)\r\n</html>");
			txtGesammteTruppenstrke.setEditable(false);
			txtGesammteTruppenstrke.setHorizontalAlignment(SwingConstants.CENTER);
			txtGesammteTruppenstrke.setBackground(Color.LIGHT_GRAY);
			txtGesammteTruppenstrke.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtGesammteTruppenstrke, "cell 2 4,growx");
			txtGesammteTruppenstrke.setColumns(10);
			
			JLabel lblGesammtzahlAnTruppen = new JLabel("Gesammtzahl an Truppen:");
			lblGesammtzahlAnTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblGesammtzahlAnTruppen, "cell 1 5,alignx trailing");
			
			txtGesamtzahlTruppen = new JTextField();
			txtGesamtzahlTruppen.setEditable(false);
			txtGesamtzahlTruppen.setHorizontalAlignment(SwingConstants.CENTER);
			txtGesamtzahlTruppen.setBackground(Color.LIGHT_GRAY);
			txtGesamtzahlTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtGesamtzahlTruppen, "cell 2 5,growx");
			txtGesamtzahlTruppen.setColumns(10);
			
			JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
			lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblNormaleTruppen, "cell 1 6,alignx trailing");
			
			txtNormaleTruppen = new JTextField();
			txtNormaleTruppen.setEditable(false);
			txtNormaleTruppen.setHorizontalAlignment(SwingConstants.CENTER);
			txtNormaleTruppen.setBackground(Color.LIGHT_GRAY);
			txtNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtNormaleTruppen, "cell 2 6,growx");
			txtNormaleTruppen.setColumns(10);
			
			JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
			lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblBadassTruppen, "cell 1 7,alignx trailing");
			
			txtBadassTruppen = new JTextField();
			txtBadassTruppen.setEditable(false);
			txtBadassTruppen.setHorizontalAlignment(SwingConstants.CENTER);
			txtBadassTruppen.setBackground(Color.LIGHT_GRAY);
			txtBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtBadassTruppen, "cell 2 7,growx");
			txtBadassTruppen.setColumns(10);
			
			resourcePanel = new ResourceInfoPanel();
			panel.add(resourcePanel, "cell 0 1,grow");
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/hammerlock_1.png"));
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setToolTipText("Mr. Torgue: EXPLOSIONSGERÄUSCH");
			panel_image.setCentered(true);
			panel_image.setBackground(Color.GRAY);
			panel.add(panel_image, "cell 1 1 2 1,grow");
			
			JPanel panel_fields_all = new JPanel();
			panel_fields_all.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_fields_all.setBackground(Color.GRAY);
			panel.add(panel_fields_all, "cell 2 0 2 1,grow");
			panel_fields_all.setLayout(new MigLayout("", "[grow][10px][grow]", "[][5px][grow]"));
			
			JLabel lblAlleGebiete = new JLabel("Alle Gebiete:");
			lblAlleGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields_all.add(lblAlleGebiete, "cell 0 0,alignx center");
			
			JLabel lblKontrollierteGebiete = new JLabel("Kontrollierte Gebiete:");
			lblKontrollierteGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields_all.add(lblKontrollierteGebiete, "cell 2 0,alignx center");
			
			JScrollPane scrollPane_fields_all = new JScrollPane();
			panel_fields_all.add(scrollPane_fields_all, "cell 0 2,grow");
			
			JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
			list_fields_all.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					selectedField = list_fields_all.getSelectedValue();
					updateSelectedField();
				}
			});
			list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_fields_all.setBackground(Color.LIGHT_GRAY);
			scrollPane_fields_all.setViewportView(list_fields_all);
			
			JScrollPane scrollPane_fields_controlled = new JScrollPane();
			panel_fields_all.add(scrollPane_fields_controlled, "cell 2 2,grow");
			
			JList<Field> list_fields_controlled = new JList<Field>(fieldControlledListModel);
			list_fields_controlled.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_fields_controlled.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_fields_controlled.setBackground(Color.LIGHT_GRAY);
			scrollPane_fields_controlled.setViewportView(list_fields_controlled);
			
			JPanel panel_field_info = new JPanel();
			panel_field_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_field_info.setBackground(Color.GRAY);
			panel.add(panel_field_info, "cell 3 1,grow");
			panel_field_info.setLayout(new MigLayout("", "[grow][::100px,grow][][50px][][::100px,grow][grow]", "[][5px,grow][grow][][5px][][][][][grow]"));
			
			JLabel lblFeldInfo = new JLabel("Feld Info:");
			lblFeldInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_field_info.add(lblFeldInfo, "cell 1 0 5 1,alignx center");
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.GRAY);
			panel_field_info.add(panel_1, "cell 1 2 5 1,grow");
			panel_1.setLayout(new MigLayout("", "[][grow][][grow]", "[][]"));
			
			JLabel lblFeld = new JLabel("Feld:");
			lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_1.add(lblFeld, "cell 0 0,alignx trailing");
			
			txtField = new JTextField();
			txtField.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtField.setBackground(Color.LIGHT_GRAY);
			txtField.setEditable(false);
			panel_1.add(txtField, "cell 1 0,growx");
			txtField.setColumns(10);
			
			JLabel lblRegion = new JLabel("Region:");
			lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_1.add(lblRegion, "cell 2 0,alignx trailing");
			
			txtRegion = new JTextField();
			txtRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtRegion.setBackground(Color.LIGHT_GRAY);
			txtRegion.setEditable(false);
			panel_1.add(txtRegion, "cell 3 0,growx");
			txtRegion.setColumns(10);
			
			JLabel lblSpieler = new JLabel("Spieler:");
			panel_1.add(lblSpieler, "cell 0 1");
			lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			txtSpieler = new JTextField();
			panel_1.add(txtSpieler, "cell 1 1,growx");
			txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtSpieler.setEditable(false);
			txtSpieler.setBackground(Color.LIGHT_GRAY);
			txtSpieler.setColumns(10);
			
			JLabel lblBefehl = new JLabel("Befehl:");
			panel_1.add(lblBefehl, "cell 2 1");
			lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			txtBefehl = new JTextField();
			panel_1.add(txtBefehl, "cell 3 1,growx");
			txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtBefehl.setEditable(false);
			txtBefehl.setBackground(Color.LIGHT_GRAY);
			txtBefehl.setColumns(10);
			
			JLabel lblNormaleTruppen_1 = new JLabel("Normale Truppen:");
			lblNormaleTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_info.add(lblNormaleTruppen_1, "cell 2 5,alignx trailing");
			
			txtNormaleTruppen_1 = new JTextField();
			txtNormaleTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtNormaleTruppen_1.setEditable(false);
			txtNormaleTruppen_1.setBackground(Color.LIGHT_GRAY);
			panel_field_info.add(txtNormaleTruppen_1, "cell 3 5,growx");
			txtNormaleTruppen_1.setColumns(10);
			
			JLabel lblBadassTruppen_1 = new JLabel("Badass Truppen:");
			lblBadassTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_info.add(lblBadassTruppen_1, "cell 2 6,alignx trailing");
			
			txtBadassTruppen_1 = new JTextField();
			txtBadassTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtBadassTruppen_1.setEditable(false);
			txtBadassTruppen_1.setBackground(Color.LIGHT_GRAY);
			panel_field_info.add(txtBadassTruppen_1, "cell 3 6,growx");
			txtBadassTruppen_1.setColumns(10);
			
			txtGesammtKostenCredits_1 = new JTextField();
			txtGesammtKostenCredits_1.setToolTipText("<html>\r\nDie Kosten fallen nur an wenn das Gebiet <br>\r\neinen Befehle erh\u00E4llt. Ohne Befehl sind die <br>\r\nKosten f\u00FCr die Truppen auf dem Gebiet 0\r\n</html>");
			txtGesammtKostenCredits_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGesammtKostenCredits_1.setEditable(false);
			txtGesammtKostenCredits_1.setBackground(Color.LIGHT_GRAY);
			panel_field_info.add(txtGesammtKostenCredits_1, "cell 3 7,growx");
			txtGesammtKostenCredits_1.setColumns(10);
			
			JLabel lblCredits_1 = new JLabel("Credits");
			lblCredits_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_info.add(lblCredits_1, "cell 4 7");
			
			txtGesammtKostenMunition_1 = new JTextField();
			txtGesammtKostenMunition_1.setToolTipText("<html>\r\nDie Kosten fallen nur an wenn das Gebiet <br>\r\neinen Befehle erh\u00E4llt. Ohne Befehl sind die <br>\r\nKosten f\u00FCr die Truppen auf dem Gebiet 0\r\n</html>");
			txtGesammtKostenMunition_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGesammtKostenMunition_1.setEditable(false);
			txtGesammtKostenMunition_1.setBackground(Color.LIGHT_GRAY);
			panel_field_info.add(txtGesammtKostenMunition_1, "cell 3 8,growx");
			txtGesammtKostenMunition_1.setColumns(10);
			
			JLabel lblMunition_1 = new JLabel("Munition");
			lblMunition_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_info.add(lblMunition_1, "cell 4 8");
		}
	}
	
	public void update() {
		updateTroopInfo();
		updateFieldLists();
		updateSelectedField();
		updateResources();
	}
	
	private void updateTroopInfo() {
		int fieldsWithTroops = 0;
		int fieldsWithoutTroops = 0;
		int troopStrength = 0;
		int troopsAll = 0;
		int troopsNormal = 0;
		int troopsBadass = 0;
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
				if (field.getTroopStrength() > 0) {
					fieldsWithTroops++;
				}
				else {
					fieldsWithoutTroops++;
				}
				troopStrength += field.getTroopStrength();
				troopsNormal += field.getNormalTroops();
				troopsBadass += field.getBadassTroops();
			}
		}
		troopsAll = troopsNormal + troopsBadass;
		txtGebieteMitTruppen.setText(Integer.toString(fieldsWithTroops));
		txtGebieteOhneTruppen.setText(Integer.toString(fieldsWithoutTroops));
		txtGesammteTruppenstrke.setText(Integer.toString(troopStrength));
		txtGesamtzahlTruppen.setText(Integer.toString(troopsAll));
		txtNormaleTruppen.setText(Integer.toString(troopsNormal));
		txtBadassTruppen.setText(Integer.toString(troopsBadass));
		if (selectedField != null && selectedField.getCommand() != null) {
			txtGesammtKostenCredits_1.setText(Integer.toString(UserResource.getCreditsForCommand(selectedField.getCommand(), selectedField)));
			txtGesammtKostenMunition_1.setText(Integer.toString(UserResource.getAmmoForCommand(selectedField.getCommand(), selectedField)));
		}
	}
	
	private void updateFieldLists() {
		fieldAllListModel.removeAllElements();
		fieldControlledListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
			if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
				fieldControlledListModel.addElement(field);
			}
		}
	}
	
	private void updateSelectedField() {
		if (selectedField != null) {
			txtField.setText(selectedField.getName());
			txtRegion.setText(selectedField.getRegion().getName());
			User affiliation = selectedField.getAffiliation();
			if (affiliation == null) {
				txtSpieler.setText("Neutral");
			}
			else {
				txtSpieler.setText(affiliation.getUsername());
			}
			txtNormaleTruppen_1.setText(Integer.toString(selectedField.getNormalTroops()));
			txtBadassTruppen_1.setText(Integer.toString(selectedField.getBadassTroops()));
		}
		else {
			txtField.setText("");
			txtRegion.setText("");
			txtSpieler.setText("");
			txtNormaleTruppen_1.setText("");
			txtBadassTruppen_1.setText("");
		}
	}
	
	private void updateResources() {
		resourcePanel.updateResources(game, game.getLocalUser());
	}
}