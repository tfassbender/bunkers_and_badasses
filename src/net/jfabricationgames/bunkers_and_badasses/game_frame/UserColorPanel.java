package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.jfabricationgames.bunkers_and_badasses.game.UserColor;
import net.jfabricationgames.bunkers_and_badasses.game.UserColorManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class UserColorPanel extends JPanel {
	
	private static final long serialVersionUID = 8220917126664005957L;
	
	private JPanel panel_colors_1;
	
	public UserColorPanel() {

		setBackground(Color.GRAY);
		setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblFarben = new JLabel("Farben:");
		lblFarben.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblFarben, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_colors = new JScrollPane();
		add(scrollPane_colors, "cell 0 1,grow");
		
		panel_colors_1 = new JPanel();
		panel_colors_1.setBackground(Color.GRAY);
		scrollPane_colors.setViewportView(panel_colors_1);
	}
	
	/**
	 * Display the players color in this panel.
	 */
	private class ColorPanel extends JPanel {
		
		private static final long serialVersionUID = 1744860767844725855L;
		
		private UserColor color;
		
		public ColorPanel(UserColor color) {
			this.color = color;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(color.getColor());
			g.fillOval(2, 2, getWidth()-4, getHeight()-4);//draw an oval of the users color on the panel
		}
	}
	
	public void addPlayerColors(List<User> players, UserColorManager colorManager) {
		addPlayerColors(panel_colors_1, players, colorManager);
	}
	
	private void addPlayerColors(JPanel panel_colors, List<User> players, UserColorManager colorManager) {
		String singleRow = "[20px:n:20px]"; 
		StringBuilder rows = new StringBuilder();
		for (int i = 0; i < players.size(); i++) {
			rows.append(singleRow);
		}
		Font font = new Font("Tahoma", Font.PLAIN, 12);
		for (int i = 0; i < players.size(); i++) {
			//create a label for the players name and a panel for his color
			JLabel lblName = new JLabel(players.get(i).getUsername());
			lblName.setFont(font);
			ColorPanel colorPanel = new ColorPanel(colorManager.getUserColors().get(players.get(i)));
			colorPanel.setBackground(Color.GRAY);
			
			//add the components to the panel
			panel_colors.add(lblName, "cell 0 " + i);
			panel_colors.add(colorPanel, "cell 1 " + i + ",grow");
		}
	}
}