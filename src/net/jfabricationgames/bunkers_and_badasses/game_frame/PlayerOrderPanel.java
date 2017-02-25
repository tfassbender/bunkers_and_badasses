package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;

import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class PlayerOrderPanel extends JPanel {
	
	private static final long serialVersionUID = 1872784226898348856L;
	
	private DefaultListModel<User> userListModel = new DefaultListModel<User>();
	
	private JList<User> list_order;
	
	public PlayerOrderPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setBackground(Color.GRAY);
		setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblReihenfolge = new JLabel("Reihenfolge:");
		lblReihenfolge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblReihenfolge, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_order = new JScrollPane();
		add(scrollPane_order, "cell 0 2,grow");
		
		list_order = new JList<User>(userListModel);
		list_order.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_order.setToolTipText("<html>\r\nDie Reihenfolge der Spieler <br>\r\n(F\u00FCr diese Runde)\r\n</html>");
		scrollPane_order.setViewportView(list_order);
		list_order.setBackground(Color.LIGHT_GRAY);
		list_order.setFont(new Font("Tahoma", Font.PLAIN, 12));
	}
	
	public void updateTurnOrder(Game game) {
		userListModel.removeAllElements();
		User[] order = game.getPlayerOrder().getOrder();
		for (int i = 0; i < order.length; i++) {
			userListModel.addElement(order[i]);
		}
		repaint();
	}
}