package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.miginfocom.swing.MigLayout;

public class BoardOverviewFrame extends JFrame {
	
	private static final long serialVersionUID = 7049133930943819848L;
	
	private JPanel contentPane;
	private BoardPanel boardPanel;
	
	private Board board;
	
	public BoardOverviewFrame(Board board) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		setTitle("Bunkers and Badasses - Spielfeld Übersicht");
		setBounds(100, 100, 1200, 700);
		setMinimumSize(new Dimension(700, 400));
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		boardPanel = new BoardPanel();
		panel.add(boardPanel, "cell 0 0,grow");
		
		JButton btnbersicht = new JButton("Übersicht Anzeigen");
		btnbersicht.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boardPanel.showOtherView();
			}
		});
		btnbersicht.setBackground(Color.GRAY);
		panel.add(btnbersicht, "cell 0 1");
	}
	
	public void update() {
		boardPanel.updateBoardImage(board.displayBoard());
	}
	public void updateBoardImage(BufferedImage image) {
		boardPanel.updateBoardImage(image);
	}
}