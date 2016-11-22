package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import java.awt.Toolkit;

public class FightExecutionFrame extends JFrame {
	
	private static final long serialVersionUID = -8826989423127239957L;
	
	private JPanel contentPane;
	
	public FightExecutionFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FightExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Kampf Ausf\u00FChrung");
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[]", "[]"));
	}
}