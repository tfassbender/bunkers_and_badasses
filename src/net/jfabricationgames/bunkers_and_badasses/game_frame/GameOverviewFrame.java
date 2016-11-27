package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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

import net.jfabricationgames.bunkers_and_badasses.game.PointManager.UserPoints;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.TurnPlaningFrame.FieldBuilding;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class GameOverviewFrame extends JFrame {
	
	private static final long serialVersionUID = 7138951336417150419L;
	
	private JPanel contentPane;
	
	private ListModel<UserPoints> pointListModel = new DefaultListModel<UserPoints>();
	private ListModel<User> userListModel = new DefaultListModel<User>();
	private ListModel<User> selectPlayerListModel = new DefaultListModel<User>();
	private ListModel<User> userBorderListModel = new DefaultListModel<User>();
	private ListModel<Field> fieldAllListModel = new DefaultListModel<Field>();
	private ListModel<Field> fieldListModel = new DefaultListModel<Field>();
	private ListModel<FieldBuilding> buildingsPlayerListModel = new DefaultListModel<FieldBuilding>();
	
	private JTextField txtYourPoints;
	private JTextField txtYourPosition;
	
	private JTextField txtSpieler;
	private JTextField txtBefehl;
	private JTextField txtGebude;
	private JTextField txtTruppennormal;
	private JTextField txtTruppenbadass;
	private JTextField txtSpieler_1;
	private JTextField txtTruppenstrke;
	private JTextField txtGebude_1;
	private JTextField txtHelden;
	private JTextField txtBasis;
	private JTextField txtPunkte;
	private JTextField txtPosition;
	private JTextField txtGrenzen;
	private JTextField txtGebiete;
	
	public GameOverviewFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameOverviewFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Spiel \u00DCbersicht");
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
		
		JPanel panel_points = new JPanel();
		panel_points.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_points.setBackground(Color.GRAY);
		panel.add(panel_points, "cell 0 0 1 2,grow");
		panel_points.setLayout(new MigLayout("", "[][5px][grow]", "[][5px][grow][][]"));
		
		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_points.add(lblPunkte, "cell 0 0 3 1,alignx center");
		
		JScrollPane scrollPane_points = new JScrollPane();
		panel_points.add(scrollPane_points, "cell 0 2 3 1,grow");
		
		JList<UserPoints> list_points = new JList<UserPoints>(pointListModel);
		list_points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_points.setToolTipText("<html>\r\nDie Siegpunkte aller Spieler\r\n</html>");
		list_points.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_points.setBackground(Color.LIGHT_GRAY);
		scrollPane_points.setViewportView(list_points);
		
		JLabel lblDeinePunkte = new JLabel("Deine Punkte:");
		lblDeinePunkte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblDeinePunkte, "cell 0 3");
		
		txtYourPoints = new JTextField();
		txtYourPoints.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourPoints.setBackground(Color.LIGHT_GRAY);
		txtYourPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYourPoints.setEditable(false);
		panel_points.add(txtYourPoints, "cell 2 3,growx");
		txtYourPoints.setColumns(10);
		
		JLabel lblDeinePosition = new JLabel("Deine Position:");
		lblDeinePosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_points.add(lblDeinePosition, "cell 0 4");
		
		txtYourPosition = new JTextField();
		txtYourPosition.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourPosition.setBackground(Color.LIGHT_GRAY);
		txtYourPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYourPosition.setEditable(false);
		panel_points.add(txtYourPosition, "cell 2 4,growx");
		txtYourPosition.setColumns(10);
		
		ImagePanel panel_image_1 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/salvador_1.png"));
		panel_image_1.setAdaptSizeKeepProportion(true);
		panel_image_1.setCentered(true);
		panel_image_1.setBackground(Color.GRAY);
		panel.add(panel_image_1, "cell 1 0,grow");
		
		JPanel panel_player_info = new JPanel();
		panel_player_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_player_info.setBackground(Color.GRAY);
		panel.add(panel_player_info, "cell 2 0 2 2,grow");
		panel_player_info.setLayout(new MigLayout("", "[grow][][100px,grow][10px][][][35px:n:35px][:50px:100px,grow][grow]", "[][5px,grow][][5px][][][][][][][::5px][grow]"));
		
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
		panel_player_info.add(txtGrenzen, "cell 6 4,growx");
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
		
		JScrollPane scrollPane_borders = new JScrollPane();
		panel_player_info.add(scrollPane_borders, "cell 4 5 4 6,grow");
		
		JList<User> list_borders = new JList<User>(userBorderListModel);
		list_borders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_borders.setBackground(Color.LIGHT_GRAY);
		scrollPane_borders.setViewportView(list_borders);
		
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
		list_player.setBackground(Color.LIGHT_GRAY);
		list_player.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_player.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_player.setViewportView(list_player);
		
		JPanel panel_order = new JPanel();
		panel_order.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order.setBackground(Color.GRAY);
		panel.add(panel_order, "cell 0 2 1 2,grow");
		panel_order.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblReihenfolge = new JLabel("Reihenfolge:");
		lblReihenfolge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_order.add(lblReihenfolge, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_order = new JScrollPane();
		panel_order.add(scrollPane_order, "cell 0 2,grow");
		
		JList<User> list_order = new JList<User>(userListModel);
		list_order.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_order.setToolTipText("<html>\r\nDie Reihenfolge der Spieler <br>\r\n(F\u00FCr diese Runde)\r\n</html>");
		scrollPane_order.setViewportView(list_order);
		list_order.setBackground(Color.LIGHT_GRAY);
		list_order.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JPanel panel_resources = new JPanel();
		panel_resources.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_resources.setBackground(Color.GRAY);
		panel.add(panel_resources, "cell 4 0 2 2,grow");
		panel_resources.setLayout(new MigLayout("", "[right][grow,center][grow,center][grow,center]", "[][grow][][grow][grow][grow][grow]"));
		
		JLabel lblResourcen = new JLabel("Resourcen:");
		lblResourcen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_resources.add(lblResourcen, "cell 0 0 4 1,alignx center");
		
		JLabel lblbrigeResourcen = new JLabel("\u00DCbrig:");
		lblbrigeResourcen.setToolTipText("Im Moment vorhandene Resourcen");
		lblbrigeResourcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_resources.add(lblbrigeResourcen, "cell 1 2");
		
		JLabel lblErhaltnchsteRunde = new JLabel("Erhalt:");
		lblErhaltnchsteRunde.setToolTipText("<html>\r\nMomentaner Erhalt an Resourcen zu<br>\r\nBeginn der n\u00E4chsten Runde. (Resourcen<br>\r\nGewinnung nicht ber\u00FCcksichtigt)<br>\r\n</html>");
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
		panel.add(panel_fields, "cell 2 2 1 2,grow");
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
		panel.add(panel_field, "cell 3 2 2 2,grow");
		panel_field.setLayout(new MigLayout("", "[][grow][][grow]", "[][5px][][][][5px][][50px,grow]"));
		
		JLabel lblFeldbersicht = new JLabel("Feld \u00DCbersicht:");
		lblFeldbersicht.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_field.add(lblFeldbersicht, "cell 0 0 4 1,alignx center");
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblSpieler, "cell 0 2,alignx trailing");
		
		txtSpieler = new JTextField();
		txtSpieler.setBackground(Color.LIGHT_GRAY);
		txtSpieler.setEditable(false);
		txtSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtSpieler, "cell 1 2 3 1,growx");
		txtSpieler.setColumns(10);
		
		JLabel lblBefehl_1 = new JLabel("Befehl:");
		lblBefehl_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblBefehl_1, "cell 0 3,alignx trailing");
		
		txtBefehl = new JTextField();
		txtBefehl.setBackground(Color.LIGHT_GRAY);
		txtBefehl.setEditable(false);
		txtBefehl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtBefehl, "cell 1 3,growx");
		txtBefehl.setColumns(10);
		
		JLabel lblNormaleTruppen = new JLabel("Normale Truppen:");
		lblNormaleTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblNormaleTruppen, "cell 2 3,alignx trailing");
		
		txtTruppennormal = new JTextField();
		txtTruppennormal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppennormal.setBackground(Color.LIGHT_GRAY);
		txtTruppennormal.setEditable(false);
		txtTruppennormal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtTruppennormal, "cell 3 3,growx");
		txtTruppennormal.setColumns(10);
		
		JLabel lblGebude = new JLabel("Geb\u00E4ude:");
		lblGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblGebude, "cell 0 4,alignx trailing");
		
		txtGebude = new JTextField();
		txtGebude.setBackground(Color.LIGHT_GRAY);
		txtGebude.setEditable(false);
		txtGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtGebude, "cell 1 4,growx");
		txtGebude.setColumns(10);
		
		JLabel lblBadassTruppen = new JLabel("Badass Truppen:");
		lblBadassTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblBadassTruppen, "cell 2 4,alignx trailing");
		
		txtTruppenbadass = new JTextField();
		txtTruppenbadass.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenbadass.setBackground(Color.LIGHT_GRAY);
		txtTruppenbadass.setEditable(false);
		txtTruppenbadass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(txtTruppenbadass, "cell 3 4,growx");
		txtTruppenbadass.setColumns(10);
		
		JLabel lblNachbarn = new JLabel("Nachbarn:");
		lblNachbarn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_field.add(lblNachbarn, "cell 0 6 4 1,alignx center");
		
		JScrollPane scrollPane_neighbours = new JScrollPane();
		panel_field.add(scrollPane_neighbours, "cell 0 7 4 1,grow");
		
		JList<Field> list_neighbours = new JList<Field>(fieldListModel);
		list_neighbours.setToolTipText("<html>\r\nBenachbarte Felder\r\n</html>");
		list_neighbours.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_neighbours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_neighbours.setBackground(Color.LIGHT_GRAY);
		scrollPane_neighbours.setViewportView(list_neighbours);
		
		JPanel panel_buildings = new JPanel();
		panel.add(panel_buildings, "cell 5 2 1 2,grow");
		panel_buildings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_buildings.setBackground(Color.GRAY);
		panel_buildings.setLayout(new MigLayout("", "[grow]", "[][5px][][grow]"));
		
		JLabel lblGebude_1 = new JLabel("Geb\u00E4ude:");
		lblGebude_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_buildings.add(lblGebude_1, "cell 0 0,alignx center");
		
		JLabel lblDeineGebude = new JLabel("Deine Geb\u00E4ude:");
		lblDeineGebude.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_buildings.add(lblDeineGebude, "cell 0 2");
		
		JScrollPane scrollPane_buildings_player = new JScrollPane();
		panel_buildings.add(scrollPane_buildings_player, "cell 0 3,grow");
		
		JList<FieldBuilding> list_buildings_player = new JList<FieldBuilding>(buildingsPlayerListModel);
		list_buildings_player.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_buildings_player.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_buildings_player.setBackground(Color.LIGHT_GRAY);
		scrollPane_buildings_player.setViewportView(list_buildings_player);
		
		ImagePanel panel_image_2 = new ImagePanel(GameFrame.getImageLoader().loadImage("game_frame/roland_1.png"));
		panel_image_2.setAdaptSizeKeepProportion(true);
		panel_image_2.setCentered(true);
		panel_image_2.setBackground(Color.GRAY);
		panel.add(panel_image_2, "cell 1 3,grow");
	}
}