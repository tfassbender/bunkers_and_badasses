package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.miginfocom.swing.MigLayout;

public class BoardPreviewDialog extends JDialog {
	
	private static final long serialVersionUID = 2638460643285841616L;
	
	private final JPanel contentPanel = new JPanel();
	
	public BoardPreviewDialog(GameCreationDialog callingFrame, Board board) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BoardPreviewDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Karten\u00FCbersicht");
		setBounds(100, 100, 900, 600);
		setMinimumSize(new Dimension(450, 300));
		setLocationRelativeTo(callingFrame);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblMap = new JLabel(board.toString());
		lblMap.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblMap, "cell 0 0,alignx center");
		
		ImagePanel panel_board = new ImagePanel(board.getBaseImage());
		panel_board.setAdaptSizeKeepProportion(true);
		panel_board.setBackground(Color.GRAY);
		panel.add(panel_board, "cell 0 1,grow");
	}
}