package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.CardLayout;

public class StatisticsFrame extends JFrame {
	
	private static final long serialVersionUID = 7270679844108130046L;
	
	private JPanel contentPane;
	
	private StatisticsAnalyzer analyzer;
	
	public StatisticsFrame(StatisticsAnalyzer analyzer) {
		this.analyzer = analyzer;
		
		setTitle("Statistiken - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(StatisticsFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1199, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnStatistik = new JMenu("Statistik");
		menuBar.add(mnStatistik);
		
		JMenuItem mntmHighscores = new JMenuItem("Highscores");
		mnStatistik.add(mntmHighscores);
		
		JMenuItem mntmKartenStatistik = new JMenuItem("Karten Statistik");
		mnStatistik.add(mntmKartenStatistik);
		
		JMenuItem mntmMeineStatistik = new JMenuItem("Meine Statistik");
		mnStatistik.add(mntmMeineStatistik);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[60px:n:60px][grow]"));
		
		ImagePanel panel_headline = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("jfg/headline.png"));
		panel_headline.setBackground(Color.GRAY);
		panel.add(panel_headline, "cell 0 0,grow");
		
		JPanel panel_cards = new JPanel();
		panel_cards.setBackground(Color.GRAY);
		panel.add(panel_cards, "cell 0 1,grow");
		panel_cards.setLayout(new CardLayout(0, 0));
		
		JPanel panel_highscores = new JPanel();
		panel_highscores.setBackground(Color.GRAY);
		panel_cards.add(panel_highscores, "name_3018081567507");
		panel_highscores.setLayout(new MigLayout("", "[]", "[]"));
		
		JPanel panel_map_statistics = new JPanel();
		panel_map_statistics.setBackground(Color.GRAY);
		panel_cards.add(panel_map_statistics, "name_4641608459903");
		panel_map_statistics.setLayout(new MigLayout("", "[]", "[]"));
		
		JPanel panel_game_detail = new JPanel();
		panel_game_detail.setBackground(Color.GRAY);
		panel_cards.add(panel_game_detail, "name_4657447906652");
		panel_game_detail.setLayout(new MigLayout("", "[]", "[]"));
	}
}