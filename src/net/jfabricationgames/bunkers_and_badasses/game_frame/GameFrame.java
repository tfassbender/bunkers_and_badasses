package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 2516173588531625786L;
	
	private JPanel contentPane;
	
	public GameFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 750);
		setMinimumSize(new Dimension(1250, 750));
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[900px:900px][grow]", "[500px:500px][grow]"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, "cell 0 0,grow");
	}
}