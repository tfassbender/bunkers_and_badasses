package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;

public class FieldOverviewDialog extends JDialog {
	
	private static final long serialVersionUID = -7613818198003965951L;
	
	private final JPanel contentPanel = new JPanel();
	
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldControlledListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldNeighbourListModel = new DefaultListModel<Field>();
	
	private JPanel panel_board_capture;
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";
	
	private JTextField txtSpieler;
	private JTextField txtFeld;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtNormaleTruppen;
	private JTextField txtBadassTruppen;
	private JTextField txtGrenzenkontrolliert;
	private JTextField txtGrenzenneutral;
	private JTextField txtGrenzenfeindlich;
	
	public FieldOverviewDialog() {
		setTitle("Bunkers and Badasses - Gebiets \u00DCbersicht");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FieldOverviewDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1150, 500);
		setMinimumSize(new Dimension(1100, 500));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[400px,grow][:200px:300px][:200px:300px,grow][:200px:300px,grow]", "[200px,grow][:100px:100px,grow][:200px:300px,grow]"));
			
			panel_board_capture = new JPanel();
			panel_board_capture.setBackground(Color.GRAY);
			panel.add(panel_board_capture, "cell 0 0 1 2,grow");
			panel_board_capture.setLayout(new CardLayout(0, 0));
			
			JPanel panel_scroll_board_capture = new JPanel();
			panel_scroll_board_capture.setBackground(Color.GRAY);
			panel_board_capture.add(panel_scroll_board_capture, SCROLL_BOARD);
			panel_scroll_board_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
			
			JScrollPane scrollPane_board = new JScrollPane();
			panel_scroll_board_capture.add(scrollPane_board, "cell 0 0,grow");
			
			JPanel panel_scroll_board = new JPanel();
			panel_scroll_board.setBackground(Color.GRAY);
			scrollPane_board.setViewportView(panel_scroll_board);
			
			JPanel panel_board_overview_capture = new JPanel();
			panel_board_overview_capture.setBackground(Color.GRAY);
			panel_board_capture.add(panel_board_overview_capture, OVERVIEW_BOARD);
			panel_board_overview_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
			
			JPanel panel_board_overview = new JPanel();
			panel_board_overview.setBackground(Color.GRAY);
			panel_board_overview_capture.add(panel_board_overview, "cell 0 0,grow");
			
			JPanel panel_fields_all = new JPanel();
			panel_fields_all.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_fields_all.setBackground(Color.GRAY);
			panel.add(panel_fields_all, "cell 1 0 1 3,grow");
			panel_fields_all.setLayout(new MigLayout("", "[grow]", "[][5px][grow][15px][][5px][grow]"));
			
			JLabel lblAlleGebiete = new JLabel("Alle Gebiete:");
			lblAlleGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields_all.add(lblAlleGebiete, "cell 0 0,alignx center");
			
			JScrollPane scrollPane_fields_all = new JScrollPane();
			panel_fields_all.add(scrollPane_fields_all, "cell 0 2,grow");
			
			JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
			list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_fields_all.setBackground(Color.LIGHT_GRAY);
			scrollPane_fields_all.setViewportView(list_fields_all);
			
			JLabel lblKontrollierteGebiete = new JLabel("Kontrollierte Gebiete:");
			lblKontrollierteGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields_all.add(lblKontrollierteGebiete, "cell 0 4,alignx center");
			
			JScrollPane scrollPane = new JScrollPane();
			panel_fields_all.add(scrollPane, "cell 0 6,grow");
			
			JList<Field> list = new JList<Field>(fieldControlledListModel);
			list.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setBackground(Color.LIGHT_GRAY);
			scrollPane.setViewportView(list);
			
			JPanel panel_field_description = new JPanel();
			panel_field_description.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_field_description.setBackground(Color.GRAY);
			panel.add(panel_field_description, "cell 2 0 2 3,grow");
			panel_field_description.setLayout(new MigLayout("", "[grow][grow][150px,grow][10px][][50px][100px][grow]", "[][5px][][][][10px][grow]"));
			
			JLabel lblFeldDetail = new JLabel("Feld Detail:");
			lblFeldDetail.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_field_description.add(lblFeldDetail, "cell 0 0 8 1,alignx center");
			
			JLabel lblFeld = new JLabel("Feld:");
			lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblFeld, "cell 1 2,alignx trailing");
			
			txtFeld = new JTextField();
			txtFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtFeld.setEditable(false);
			txtFeld.setBackground(Color.LIGHT_GRAY);
			panel_field_description.add(txtFeld, "cell 2 2,growx");
			txtFeld.setColumns(10);
			
			JLabel lblSpieler = new JLabel("Spieler:");
			lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblSpieler, "cell 4 2,alignx trailing");
			
			txtSpieler = new JTextField();
			txtSpieler.setEditable(false);
			txtSpieler.setBackground(Color.LIGHT_GRAY);
			txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtSpieler, "cell 5 2 2 1,growx");
			txtSpieler.setColumns(10);
			
			JLabel lblBefehl = new JLabel("Befehl:");
			lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblBefehl, "cell 1 3,alignx trailing");
			
			txtBefehl = new JTextField();
			txtBefehl.setEditable(false);
			txtBefehl.setBackground(Color.LIGHT_GRAY);
			txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtBefehl, "cell 2 3,growx");
			txtBefehl.setColumns(10);
			
			JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
			lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblNormaleTruppen, "cell 4 3 2 1");
			
			txtNormaleTruppen = new JTextField();
			txtNormaleTruppen.setEditable(false);
			txtNormaleTruppen.setBackground(Color.LIGHT_GRAY);
			txtNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtNormaleTruppen, "cell 6 3,growx");
			txtNormaleTruppen.setColumns(10);
			
			JLabel lblGebude = new JLabel("Geb\u00E4ude:");
			lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblGebude, "cell 1 4,alignx trailing");
			
			txtGebude = new JTextField();
			txtGebude.setEditable(false);
			txtGebude.setBackground(Color.LIGHT_GRAY);
			txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtGebude, "cell 2 4,growx");
			txtGebude.setColumns(10);
			
			JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
			lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblBadassTruppen, "cell 4 4 2 1");
			
			txtBadassTruppen = new JTextField();
			txtBadassTruppen.setEditable(false);
			txtBadassTruppen.setBackground(Color.LIGHT_GRAY);
			txtBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtBadassTruppen, "cell 6 4,growx");
			txtBadassTruppen.setColumns(10);
			
			JPanel panel_field_1 = new JPanel();
			panel_field_1.setBackground(Color.GRAY);
			panel_field_description.add(panel_field_1, "cell 0 6 8 1,grow");
			panel_field_1.setLayout(new MigLayout("", "[][50px][5px][grow]", "[][][][10px][][grow]"));
			
			JLabel lblGrenzenZuKontrollierten = new JLabel("Grenzen zu kontrollierten Gebieten:");
			lblGrenzenZuKontrollierten.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_1.add(lblGrenzenZuKontrollierten, "cell 0 0,alignx trailing");
			
			txtGrenzenkontrolliert = new JTextField();
			txtGrenzenkontrolliert.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGrenzenkontrolliert.setBackground(Color.LIGHT_GRAY);
			txtGrenzenkontrolliert.setEditable(false);
			panel_field_1.add(txtGrenzenkontrolliert, "cell 1 0,growx");
			txtGrenzenkontrolliert.setColumns(10);
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/tannis_1.png"));
			panel_image.setToolTipText("Tannis: Genie und Wahnsinn...");
			panel_image.setCentered(true);
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setBackground(Color.GRAY);
			panel_field_1.add(panel_image, "cell 3 0 1 6,grow");
			
			JLabel lblGrenzenZuNeutralen = new JLabel("Grenzen zu neutralen Gebieten:");
			lblGrenzenZuNeutralen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_1.add(lblGrenzenZuNeutralen, "cell 0 1,alignx trailing");
			
			txtGrenzenneutral = new JTextField();
			txtGrenzenneutral.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGrenzenneutral.setBackground(Color.LIGHT_GRAY);
			txtGrenzenneutral.setEditable(false);
			panel_field_1.add(txtGrenzenneutral, "cell 1 1,growx");
			txtGrenzenneutral.setColumns(10);
			
			JLabel lblGrenzenZuFeindlichen = new JLabel("Grenzen zu feindlichen Gebieten:");
			lblGrenzenZuFeindlichen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_1.add(lblGrenzenZuFeindlichen, "cell 0 2,alignx trailing");
			
			txtGrenzenfeindlich = new JTextField();
			txtGrenzenfeindlich.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtGrenzenfeindlich.setBackground(Color.LIGHT_GRAY);
			txtGrenzenfeindlich.setEditable(false);
			panel_field_1.add(txtGrenzenfeindlich, "cell 1 2,growx");
			txtGrenzenfeindlich.setColumns(10);
			
			JLabel lblNachbarn = new JLabel("Grenzen");
			lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_1.add(lblNachbarn, "cell 0 4 2 1,alignx center");
			
			JScrollPane scrollPane_1 = new JScrollPane();
			panel_field_1.add(scrollPane_1, "cell 0 5 2 1,grow");
			
			JList<Field> list_neighbours = new JList<Field>(fieldNeighbourListModel);
			list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_neighbours.setBackground(Color.LIGHT_GRAY);
			scrollPane_1.setViewportView(list_neighbours);
			
			JPanel panel_resources = new JPanel();
			panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_resources.setBackground(Color.GRAY);
			panel.add(panel_resources, "cell 0 2,grow");
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
		}
	}
}