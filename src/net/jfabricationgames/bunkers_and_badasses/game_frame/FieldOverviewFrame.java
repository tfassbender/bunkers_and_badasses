package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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
import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;

public class FieldOverviewFrame extends JFrame implements BoardPanelListener {
	
	private static final long serialVersionUID = -7613818198003965951L;
	
	private final JPanel contentPanel = new JPanel();
	
	private Game game;
	
	private RegionInfoPanel regionPanel;
	
	private DefaultListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldControlledListModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldNeighbourListModel = new DefaultListModel<Field>();
	
	private JTextField txtSpieler;
	private JTextField txtFeld;
	private JTextField txtRegion;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtTruppennormal;
	private JTextField txtTruppenbadass;
	private JTextField txtGrenzenkontrolliert;
	private JTextField txtGrenzenneutral;
	private JTextField txtGrenzenfeindlich;
	
	public FieldOverviewFrame(Game game) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setTitle("Gebiets Übersicht - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FieldOverviewFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 1000, 500);
		setMinimumSize(new Dimension(800, 500));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[:300px:350px,grow][:200px:300px,grow][:200px:300px,grow][:200px:300px,grow]", "[200px,grow][:100px:100px,grow][:200px:300px,grow]"));
			
			JPanel panel_fields_all = new JPanel();
			panel_fields_all.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_fields_all.setBackground(Color.GRAY);
			panel.add(panel_fields_all, "cell 1 0 1 3,grow");
			panel_fields_all.setLayout(new MigLayout("", "[grow]", "[][5px][grow][10px][][5px][grow]"));
			
			JLabel lblAlleGebiete = new JLabel("Alle Gebiete:");
			lblAlleGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields_all.add(lblAlleGebiete, "cell 0 0,alignx center");
			
			JScrollPane scrollPane_fields_all = new JScrollPane();
			panel_fields_all.add(scrollPane_fields_all, "cell 0 2,grow");
			
			JList<Field> list_fields_all = new JList<Field>(fieldAllListModel);
			list_fields_all.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					selectField(list_fields_all.getSelectedValue());
				}
			});
			list_fields_all.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_fields_all.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_fields_all.setBackground(Color.LIGHT_GRAY);
			scrollPane_fields_all.setViewportView(list_fields_all);
			
			JScrollPane scrollPane = new JScrollPane();
			panel_fields_all.add(scrollPane, "cell 0 6,grow");
			
			JList<Field> list = new JList<Field>(fieldControlledListModel);
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					selectField(list.getSelectedValue());
				}
			});
			list.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setBackground(Color.LIGHT_GRAY);
			scrollPane.setViewportView(list);
			
			JLabel lblKontrollierteGebiete = new JLabel("Kontrollierte Gebiete:");
			lblKontrollierteGebiete.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_fields_all.add(lblKontrollierteGebiete, "cell 0 4,alignx center");
			
			JPanel panel_field_description = new JPanel();
			panel_field_description.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_field_description.setBackground(Color.GRAY);
			panel.add(panel_field_description, "cell 2 0 2 3,grow");
			panel_field_description.setLayout(new MigLayout("", "[grow][grow][150px,grow][10px][][50px][100px][grow]", "[][5px][][][][][10px][grow]"));
			
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
			
			JLabel lblRegion = new JLabel("Region:");
			lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblRegion, "cell 1 3,alignx trailing");
			
			txtRegion = new JTextField();
			txtRegion.setEditable(false);
			txtRegion.setBackground(Color.LIGHT_GRAY);
			txtRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtRegion, "cell 2 3,growx");
			txtRegion.setColumns(10);
			
			JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
			lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblNormaleTruppen, "cell 4 3 2 1");
			
			txtTruppennormal = new JTextField();
			txtTruppennormal.setEditable(false);
			txtTruppennormal.setBackground(Color.LIGHT_GRAY);
			txtTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtTruppennormal, "cell 6 3,growx");
			txtTruppennormal.setColumns(10);
			
			JLabel lblBefehl = new JLabel("Befehl:");
			lblBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblBefehl, "cell 1 4,alignx trailing");
			
			txtBefehl = new JTextField();
			txtBefehl.setEditable(false);
			txtBefehl.setBackground(Color.LIGHT_GRAY);
			txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtBefehl, "cell 2 4,growx");
			txtBefehl.setColumns(10);
			
			JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
			lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblBadassTruppen, "cell 4 4 2 1");
			
			txtTruppenbadass = new JTextField();
			txtTruppenbadass.setEditable(false);
			txtTruppenbadass.setBackground(Color.LIGHT_GRAY);
			txtTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtTruppenbadass, "cell 6 4,growx");
			txtTruppenbadass.setColumns(10);
			
			JLabel lblGebude = new JLabel("Geb\u00E4ude:");
			lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(lblGebude, "cell 1 5,alignx trailing");
			
			txtGebude = new JTextField();
			txtGebude.setEditable(false);
			txtGebude.setBackground(Color.LIGHT_GRAY);
			txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_field_description.add(txtGebude, "cell 2 5,growx");
			txtGebude.setColumns(10);
			
			JPanel panel_field_1 = new JPanel();
			panel_field_1.setBackground(Color.GRAY);
			panel_field_description.add(panel_field_1, "cell 0 7 8 1,grow");
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
			
			ImagePanel panel_image = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/tannis_1.png"));
			panel_field_1.add(panel_image, "cell 3 0 1 6,grow");
			panel_image.setToolTipText("Tannis: Genie und Wahnsinn...");
			panel_image.setCentered(true);
			panel_image.setAdaptSizeKeepProportion(true);
			panel_image.setBackground(Color.GRAY);
			
			regionPanel = new RegionInfoPanel();
			regionPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel.add(regionPanel, "cell 0 0 1 3,grow");
			
			addFields();
			updateRegions();
		}
	}
	
	@Override
	public void receiveBoardMouseClick(MouseEvent event) {
		Field field = game.getBoard().getFieldAtMousePosition();
		selectField(field);
	}
	
	private void addFields() {
		fieldAllListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			fieldAllListModel.addElement(field);
		}
		updateControlledFields();
	}
	
	public void update() {
		updateControlledFields();
		updateRegions();
	}
	public void updateControlledFields() {
		fieldControlledListModel.removeAllElements();
		for (Field field : game.getBoard().getFields()) {
			if (field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser())) {
				fieldControlledListModel.addElement(field);
			}
		}
	}
	public void updateRegions() {
		regionPanel.updateRegions(game.getBoard());
	}

	/**
	 * Select the field that was selected using the mouse.
	 */
	private void selectField(Field field) {
		if (field != null) {
			txtFeld.setText(field.getName());
			txtRegion.setText(field.getRegion().getName());
			if (field.getAffiliation() != null) {
				txtSpieler.setText(field.getAffiliation().getUsername());			
			}
			else {
				txtSpieler.setText("-----");
			}
			if (field.getCommand() != null) {
				txtBefehl.setText(field.getCommand().getName());				
			}
			else {
				txtBefehl.setText("-----");
			}
			txtTruppennormal.setText(Integer.toString(field.getNormalTroops()));
			txtTruppenbadass.setText(Integer.toString(field.getBadassTroops()));
			txtGebude.setText(field.getBuilding().getName());
			fieldNeighbourListModel.removeAllElements();
			int neighboursControlled = 0;
			int neighboursNeutral = 0;
			int neighboursEnemy = 0;
			for (Field neighbour : field.getNeighbours()) {
				fieldNeighbourListModel.addElement(neighbour);
				if (neighbour.getAffiliation() == null) {
					neighboursNeutral++;
				}
				else if (neighbour.getAffiliation().equals(game.getLocalUser())) {
					neighboursControlled++;
				}
				else {
					neighboursEnemy++;
				}
			}
			txtGrenzenkontrolliert.setText(Integer.toString(neighboursControlled));
			txtGrenzenneutral.setText(Integer.toString(neighboursNeutral));
			txtGrenzenfeindlich.setText(Integer.toString(neighboursEnemy));
		}
		else {
			txtFeld.setText("");
			txtRegion.setText("");
			txtSpieler.setText("");
			txtFeld.setText("");
			txtTruppennormal.setText("");
			txtTruppenbadass.setText("");
			txtGebude.setText("");
			fieldNeighbourListModel.removeAllElements();
			txtGrenzenkontrolliert.setText("");
			txtGrenzenneutral.setText("");
			txtGrenzenfeindlich.setText("");
		}
	}
}