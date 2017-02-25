package net.jfabricationgames.bunkers_and_badasses.game_frame;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Font;

public class FieldDescriptionPanel extends JPanel {
	
	private static final long serialVersionUID = -6497983376624830922L;
	
	private DefaultListModel<Field> fieldListModel = new DefaultListModel<Field>();
	
	private JTextField txtFeld;
	private JTextField txtRegion;
	private JTextField txtSpieler;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtTruppennormal;
	private JTextField txtTruppenbadass;
	
	public FieldDescriptionPanel(String title, boolean addNeighbours) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.GRAY);
		
		String layoutY = "[][5px][][][][][]";
		if (addNeighbours) {
			layoutY += "[5px][][50px,grow]";
		}
		setLayout(new MigLayout("", "[][grow][][grow]", layoutY));
		
		JLabel lblFeldbersicht = new JLabel(title);
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblFeld, "cell 0 2,alignx trailing");
		
		txtFeld = new JTextField();
		txtFeld.setEditable(false);
		txtFeld.setBackground(Color.LIGHT_GRAY);
		txtFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtFeld, "cell 1 2 3 1,growx");
		txtFeld.setColumns(10);
		
		JLabel lblRegion = new JLabel("Region:");
		lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblRegion, "cell 0 3,alignx trailing");
		
		txtRegion = new JTextField();
		txtRegion.setEditable(false);
		txtRegion.setBackground(Color.LIGHT_GRAY);
		txtRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtRegion, "cell 1 3 3 1,growx");
		txtRegion.setColumns(10);
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblSpieler, "cell 0 4,alignx trailing");
		
		txtSpieler = new JTextField();
		txtSpieler.setBackground(Color.LIGHT_GRAY);
		txtSpieler.setEditable(false);
		txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtSpieler, "cell 1 4 3 1,growx");
		txtSpieler.setColumns(10);
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblBefehl_1, "cell 0 5,alignx trailing");
		
		txtBefehl = new JTextField();
		txtBefehl.setBackground(Color.LIGHT_GRAY);
		txtBefehl.setEditable(false);
		txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtBefehl, "cell 1 5,growx");
		txtBefehl.setColumns(10);
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblNormaleTruppen, "cell 2 5,alignx trailing");
		
		txtTruppennormal = new JTextField();
		txtTruppennormal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppennormal.setBackground(Color.LIGHT_GRAY);
		txtTruppennormal.setEditable(false);
		txtTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtTruppennormal, "cell 3 5,growx");
		txtTruppennormal.setColumns(10);
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblGebude, "cell 0 6,alignx trailing");
		
		txtGebude = new JTextField();
		txtGebude.setBackground(Color.LIGHT_GRAY);
		txtGebude.setEditable(false);
		txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtGebude, "cell 1 6,growx");
		txtGebude.setColumns(10);
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblBadassTruppen, "cell 2 6,alignx trailing");
		
		txtTruppenbadass = new JTextField();
		txtTruppenbadass.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenbadass.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass.setEditable(false);
		txtTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(txtTruppenbadass, "cell 3 6,growx");
		txtTruppenbadass.setColumns(10);
		
		if (addNeighbours) {
			JLabel lblNachbarn = new JLabel("Nachbarn:");
			lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
			add(lblNachbarn, "cell 0 8 4 1,alignx center");
			
			JScrollPane scrollPane_neighbours = new JScrollPane();
			add(scrollPane_neighbours, "cell 0 9 4 1,grow");
			
			JList<Field> list_neighbours = new JList<Field>(fieldListModel);
			list_neighbours.setToolTipText("<html>\r\nBenachbarte Felder\r\n</html>");
			list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
			list_neighbours.setBackground(Color.LIGHT_GRAY);
			scrollPane_neighbours.setViewportView(list_neighbours);
		}
	}
	
	public void updateField(Field field) {
		txtFeld.setText(field.getName());
		txtRegion.setText(field.getRegion().getName());
		if (field.getAffiliation() != null) {
			txtSpieler.setText(field.getAffiliation().getUsername());			
		}
		else {
			txtSpieler.setText("-----");
		}
		//TODO add the field command to txtBefehl
		txtTruppennormal.setText(Integer.toString(field.getNormalTroops()));
		txtTruppenbadass.setText(Integer.toString(field.getBadassTroops()));
		txtGebude.setText(field.getBuilding().getName());
		fieldListModel.removeAllElements();
		for (Field neighbour : field.getNeighbours()) {
			fieldListModel.addElement(neighbour);
		}
	}
}