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
import net.miginfocom.swing.MigLayout;

public class TroopInfoDialog extends JDialog {
	
	private static final long serialVersionUID = 8964222516995478087L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtGebieteMitTruppen;
	private JTextField txtGebieteOhneTruppen;
	private JTextField txtGesammteTruppenstrke;
	private JTextField txtGesamtzahlTruppen;
	private JTextField txtNormaleTruppen;
	private JTextField txtBadassTruppen;
	private JTextField txtGesammtKostenCredits;
	private JTextField txtGesammtKostenMunition;
	
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldControlledListModel = new DefaultListModel<Field>();
	private JTextField txtNormaleTruppen_1;
	private JTextField txtBadassTruppen_1;
	private JTextField txtGesammtKostenCredits_1;
	private JTextField txtGesammtKostenMunition_1;
	private JTextField txtSpieler;
	private JTextField txtBefehl;
	private JTextField txtField;
	private JTextField txtRegion;
	
	public TroopInfoDialog() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TroopInfoDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Truppen Info");
		setBounds(100, 100, 900, 600);
		setMinimumSize(new Dimension(900, 600));
		
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
			panel_troop_info.setLayout(new MigLayout("", "[grow][][50px][][grow]", "[][5px,grow][][][][][][][5px][][][grow]"));
			
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
			
			JLabel lblGesammtKosten = new JLabel("Gesammt Kosten (Truppen):");
			lblGesammtKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblGesammtKosten, "cell 1 9,alignx trailing");
			
			txtGesammtKostenCredits = new JTextField();
			txtGesammtKostenCredits.setToolTipText("<html>\r\nDie Kosten fallen nur an wenn die Gebiete <br>\r\nauch Befehle erhallten. Erhallten einige <br>\r\nGebiete keinen Befehl, sinken die Kosten\r\n</html>");
			txtGesammtKostenCredits.setEditable(false);
			txtGesammtKostenCredits.setHorizontalAlignment(SwingConstants.CENTER);
			txtGesammtKostenCredits.setBackground(Color.LIGHT_GRAY);
			txtGesammtKostenCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(txtGesammtKostenCredits, "cell 2 9,growx");
			txtGesammtKostenCredits.setColumns(10);
			
			JLabel lblCredits = new JLabel("Credits");
			lblCredits.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblCredits, "cell 3 9");
			
			txtGesammtKostenMunition = new JTextField();
			txtGesammtKostenMunition.setToolTipText("<html>\r\nDie Kosten fallen nur an wenn die Gebiete <br>\r\nauch Befehle erhallten. Erhallten einige <br>\r\nGebiete keinen Befehl, sinken die Kosten\r\n</html>");
			txtGesammtKostenMunition.setEditable(false);
			txtGesammtKostenMunition.setHorizontalAlignment(SwingConstants.CENTER);
			txtGesammtKostenMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGesammtKostenMunition.setBackground(Color.LIGHT_GRAY);
			panel_troop_info.add(txtGesammtKostenMunition, "cell 2 10,growx");
			txtGesammtKostenMunition.setColumns(10);
			
			JLabel lblMunition = new JLabel("Munition");
			lblMunition.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_troop_info.add(lblMunition, "cell 3 10");
			
			JPanel panel_resources = new JPanel();
			panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_resources.setBackground(Color.GRAY);
			panel.add(panel_resources, "cell 0 1,grow");
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
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/mr_torgue_1.png"));
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setToolTipText("Marcus: Keine R\u00FCckerstattung");
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
}