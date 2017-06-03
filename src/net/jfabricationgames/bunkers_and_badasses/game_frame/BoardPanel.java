package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.BoardPanelListener;
import net.miginfocom.swing.MigLayout;

public class BoardPanel extends JPanel {
	
	private ImagePanel panel_scroll_board;
	private ImagePanel panel_board_overview;
	private JScrollPane scrollPane_board;
	
	private boolean fieldOverview = false;
	private final String SCROLL_BOARD = "scroll_board";
	private final String OVERVIEW_BOARD = "overview_board";
	
	private List<BoardPanelListener> boardPanelListeners = new ArrayList<BoardPanelListener>();
	
	private static final long serialVersionUID = 5816469099642038112L;
	
	public BoardPanel() {
		setBackground(Color.GRAY);
		setLayout(new CardLayout(0, 0));
		
		JPanel panel_scroll_board_capture = new JPanel();
		panel_scroll_board_capture.setBackground(Color.GRAY);
		add(panel_scroll_board_capture, SCROLL_BOARD);
		panel_scroll_board_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		scrollPane_board = new JScrollPane();
		panel_scroll_board_capture.add(scrollPane_board, "cell 0 0,grow");
		scrollPane_board.getVerticalScrollBar().setUnitIncrement(20);
		
		panel_scroll_board = new ImagePanel();
		panel_scroll_board.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				informListeners(e);
			}
		});
		panel_scroll_board.setBackground(Color.GRAY);
		scrollPane_board.setViewportView(panel_scroll_board);
		
		JPanel panel_board_overview_capture = new JPanel();
		panel_board_overview_capture.setBackground(Color.GRAY);
		add(panel_board_overview_capture, OVERVIEW_BOARD);
		panel_board_overview_capture.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		panel_board_overview = new ImagePanel();
		panel_board_overview.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				informListeners(e);
			}
		});
		panel_board_overview.setCentered(true);
		panel_board_overview.setAdaptSizeKeepProportion(true);
		panel_board_overview.setBackground(Color.GRAY);
		panel_board_overview_capture.add(panel_board_overview, "cell 0 0,grow");
	}
	
	public void updateBoardImage(BufferedImage boardImage) {
		if (boardImage != null) {
			panel_scroll_board.setImage(boardImage);
			panel_scroll_board.setPreferredSize(new Dimension(boardImage.getWidth(), boardImage.getHeight()));
			panel_board_overview.setImage(boardImage);
			repaint();
		}
	}
	
	private void informListeners(MouseEvent event) {
		for (BoardPanelListener listener : boardPanelListeners) {
			listener.receiveBoardMouseClick(event);
		}
	}
	
	public void showOtherView() {
		CardLayout layout = (CardLayout) getLayout();
		if (fieldOverview) {
			layout.show(this, SCROLL_BOARD);
		}
		else {
			layout.show(this, OVERVIEW_BOARD);
		}
		fieldOverview = !fieldOverview;
	}
	
	public void addBoardPanelListener(BoardPanelListener boardPanelListener) {
		this.boardPanelListeners.add(boardPanelListener);
	}
	public void removeBoardPanelListener(BoardPanelListener boardPanelListener) {
		this.boardPanelListeners.remove(boardPanelListener);
	}
}