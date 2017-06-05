package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.PointManager.UserPoints;
import net.miginfocom.swing.MigLayout;

public class PointPanel extends JPanel {
	
	private static final long serialVersionUID = -3963501355619976917L;
	
	private JTextField txtYourPoints;
	private JTextField txtYourPosition;
	
	private DefaultListModel<UserPoints> pointListModel = new DefaultListModel<UserPoints>();
	
	public PointPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.GRAY);
		setLayout(new MigLayout("", "[][5px][grow]", "[][5px][grow][][]"));
		
		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblPunkte, "cell 0 0 3 1,alignx center");
		
		JScrollPane scrollPane_points = new JScrollPane();
		add(scrollPane_points, "cell 0 2 3 1,grow");
		
		JList<UserPoints> list_points = new JList<UserPoints>(pointListModel);
		list_points.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_points.setToolTipText("<html>\r\nDie Siegpunkte aller Spieler\r\n</html>");
		list_points.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_points.setBackground(Color.LIGHT_GRAY);
		scrollPane_points.setViewportView(list_points);
		
		JLabel lblDeinePunkte = new JLabel("Deine Punkte:");
		lblDeinePunkte.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblDeinePunkte, "cell 0 3");
		
		txtYourPoints = new JTextField();
		txtYourPoints.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourPoints.setBackground(Color.LIGHT_GRAY);
		txtYourPoints.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYourPoints.setEditable(false);
		add(txtYourPoints, "cell 2 3,growx");
		txtYourPoints.setColumns(10);
		
		JLabel lblDeinePosition = new JLabel("Deine Position:");
		lblDeinePosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblDeinePosition, "cell 0 4");
		
		txtYourPosition = new JTextField();
		txtYourPosition.setHorizontalAlignment(SwingConstants.CENTER);
		txtYourPosition.setBackground(Color.LIGHT_GRAY);
		txtYourPosition.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtYourPosition.setEditable(false);
		add(txtYourPosition, "cell 2 4,growx");
		txtYourPosition.setColumns(10);
	}
	
	public void updatePoints(Game game) {
		pointListModel.removeAllElements();
		List<UserPoints> users = game.getPointManager().getSortedPointList();
		for (int i = 0; i < users.size(); i++) {
			pointListModel.addElement(users.get(i));
		}
		txtYourPoints.setText(Integer.toString(game.getPointManager().getPoints(game.getLocalUser())));
		txtYourPosition.setText(Integer.toString(game.getPointManager().getPosition(game.getLocalUser()) + 1));
	}
}