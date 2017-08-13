package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.Region;
import net.miginfocom.swing.MigLayout;

public class RegionInfoPanel extends JPanel {
	
	private static final long serialVersionUID = 3831211220201300228L;
	private JPanel panel_regions;
	
	public RegionInfoPanel() {
		setBackground(Color.GRAY);
		setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblRegionen = new JLabel("Regionen:");
		lblRegionen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblRegionen, "cell 0 0,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1,grow");
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		panel_regions = new JPanel();
		panel_regions.setLayout(new MigLayout("", "[grow][50px,center][40px]", "[30px]"));
		panel_regions.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(panel_regions);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new MigLayout("", "[grow][50px][40px]", "[]"));
		
		JLabel lblRegion = new JLabel("Region");
		lblRegion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblRegion, "cell 0 0,alignx center");
		
		JLabel lblFelder = new JLabel("Felder");
		lblFelder.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblFelder, "cell 1 0,alignx center");
		
		JLabel lblFarbe = new JLabel("Farbe");
		lblFarbe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblFarbe, "cell 2 0,alignx center");
	}
	
	public void updateRegions(Board board) {
		String layout = "";
		for (int i = 0; i < board.getRegions().size(); i++) {
			layout += "[40px]";
		}
		
		panel_regions.removeAll();
		panel_regions.setLayout(new MigLayout("", "[grow][50px,center][40px]", layout));
		
		for (int i = 0; i < board.getRegions().size(); i++) {
			JLabel lblRegionname = new JLabel(board.getRegions().get(i).getName());
			lblRegionname.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_regions.add(lblRegionname, "cell 0 " + i);
			
			JLabel lblFields = new JLabel(Integer.toString(board.getRegions().get(i).getFields().size()));
			panel_regions.add(lblFields, "cell 1 " + i);
			
			RegionColorPanel panel = new RegionColorPanel(board.getRegions().get(i));
			panel_regions.add(panel, "cell 2 " + i + ",grow");
		}
	}
	
	/**
	 * Display the players color in this panel.
	 */
	private class RegionColorPanel extends JPanel {
		
		private static final long serialVersionUID = 1744860767844725855L;
		
		private Color color;
		
		public RegionColorPanel(Region region) {
			this.color = region.getFields().get(0).getFieldColor();//just select the first color of a field
			setBackground(Color.LIGHT_GRAY);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(color);
			g.fillOval(10, 10, getWidth()-20, getHeight()-20);//draw an oval of the regions color on the panel
		}
	}
}