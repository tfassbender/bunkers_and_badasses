package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameDetail.Display;
import net.jfabricationgames.bunkers_and_badasses.statistic.UserStatistic.Value;
import net.miginfocom.swing.MigLayout;
import javax.swing.ListSelectionModel;

public class StatisticsFrame extends JFrame {
	
	private static final long serialVersionUID = 7270679844108130046L;
	
	private JPanel contentPane;
	
	private StatisticsAnalyzer analyzer;
	private JTable table;
	
	private DefaultTableModel model;
	private JCheckBox chckbxAbsteigend;
	private JCheckBox chckbxAbsteigend_1;
	private JCheckBox chckbxAbsteigend_2;
	private JComboBox<MapSelection> comboBox_map;
	private JButton btnSpielDetails;
	private JComboBox<UserStatistic.Value> comboBox_sort_1;
	private JComboBox<UserStatistic.Value> comboBox_sort_2;
	private JComboBox<UserStatistic.Value> comboBox_sort_3;
	private JPanel panel_cards;
	private JList<GameDetail.Display> list_detail;
	private JPanel panel_chart;
	
	private int displayedGame;
	
	private static final String PANEL_HIGHSCORES = "highscores";
	private static final String PANEL_GAME_DETAIL = "game";
	private JTextArea txtrStatistics;
	
	private class MapSelection {
		
		private int id;
		private String name;
		
		public MapSelection(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public StatisticsFrame(StatisticsAnalyzer analyzer) {
		this.analyzer = analyzer;
		
		setTitle("Statistiken - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(StatisticsFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnStatistik = new JMenu("Statistik");
		menuBar.add(mnStatistik);
		
		JMenuItem mntmHighscores = new JMenuItem("Highscores");
		mntmHighscores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel_cards.getLayout()).show(panel_cards, PANEL_HIGHSCORES);
			}
		});
		mnStatistik.add(mntmHighscores);
		
		JMenuItem mntmSpielStatistik = new JMenuItem("Spiel Statistik");
		mntmSpielStatistik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectGameStatistic();
			}
		});
		mnStatistik.add(mntmSpielStatistik);
		
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
		
		panel_cards = new JPanel();
		panel_cards.setBackground(Color.GRAY);
		panel.add(panel_cards, "cell 0 1,grow");
		panel_cards.setLayout(new CardLayout(0, 0));
		
		JPanel panel_highscores = new JPanel();
		panel_highscores.setBackground(Color.GRAY);
		panel_cards.add(panel_highscores, PANEL_HIGHSCORES);
		panel_highscores.setLayout(new MigLayout("", "[][grow][][10px][150px][25px:n:25px][][10px:n:10px][][150px][10px:n:10px][][150px][10px:n:10px][][150px]", "[10px:n][][][grow]"));
		
		JLabel lblHighscores = new JLabel("Highscores");
		lblHighscores.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_highscores.add(lblHighscores, "cell 0 0 1 3,aligny center");
		
		JLabel lblMapAll = new JLabel("Karte:");
		lblMapAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(lblMapAll, "cell 2 1,alignx trailing");
		
		comboBox_map = new JComboBox<MapSelection>();
		comboBox_map.addItem(new MapSelection(-1, "<Alle Karten>"));
		toMapSelection(analyzer.getMaps()).forEach(selection -> comboBox_map.addItem(selection));
		comboBox_map.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MapSelection map = (MapSelection) comboBox_map.getSelectedItem();
				if (map != null) {
					addTableData(model, map.getId());
				}
			}
		});
		panel_highscores.add(comboBox_map, "cell 4 1,growx");
		
		JLabel lblSortierung = new JLabel("Sortierung:");
		lblSortierung.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(lblSortierung, "cell 6 1");
		
		JLabel label_sort_1 = new JLabel("1.");
		label_sort_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(label_sort_1, "cell 8 1,alignx trailing");
		
		comboBox_sort_1 = new JComboBox<UserStatistic.Value>();
		comboBox_sort_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Value value = (Value) comboBox_sort_1.getSelectedItem();
				if (value != null) {
					UserStatistic.setSortKey(value.getIndex(), 1, chckbxAbsteigend.isSelected());
				}
				MapSelection map = (MapSelection) comboBox_map.getSelectedItem();
				if (map != null) {
					addTableData(model, map.getId());
				}
			}
		});
		UserStatistic.getValueSelections().forEach(header -> comboBox_sort_1.addItem(header));
		panel_highscores.add(comboBox_sort_1, "cell 9 1,growx");
		
		JLabel label_sort_2 = new JLabel("2.");
		label_sort_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(label_sort_2, "cell 11 1,alignx trailing");
		
		comboBox_sort_2 = new JComboBox<UserStatistic.Value>();
		comboBox_sort_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Value value = (Value) comboBox_sort_2.getSelectedItem();
				if (value != null) {
					UserStatistic.setSortKey(value.getIndex(), 2, chckbxAbsteigend.isSelected());
				}
				MapSelection map = (MapSelection) comboBox_map.getSelectedItem();
				if (map != null) {
					addTableData(model, map.getId());
				}
			}
		});
		UserStatistic.getValueSelections().forEach(header -> comboBox_sort_2.addItem(header));
		panel_highscores.add(comboBox_sort_2, "cell 12 1,growx");
		
		JLabel label_sort_3 = new JLabel("3.");
		label_sort_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(label_sort_3, "cell 14 1,alignx trailing");
		
		comboBox_sort_3 = new JComboBox<UserStatistic.Value>();
		comboBox_sort_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Value value = (Value) comboBox_sort_3.getSelectedItem();
				if (value != null) {
					UserStatistic.setSortKey(value.getIndex(), 3, chckbxAbsteigend.isSelected());
				}
				MapSelection map = (MapSelection) comboBox_map.getSelectedItem();
				if (map != null) {
					addTableData(model, map.getId());
				}
			}
		});
		UserStatistic.getValueSelections().forEach(header -> comboBox_sort_2.addItem(header));
		panel_highscores.add(comboBox_sort_3, "cell 15 1,growx");
		
		btnSpielDetails = new JButton("Spiel Details");
		btnSpielDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectGameStatistic();
			}
		});
		btnSpielDetails.setBackground(Color.GRAY);
		panel_highscores.add(btnSpielDetails, "cell 4 2");
		
		chckbxAbsteigend = new JCheckBox("Absteigend Sortieren");
		chckbxAbsteigend.setBackground(Color.GRAY);
		chckbxAbsteigend.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(chckbxAbsteigend, "cell 9 2,alignx center");
		
		chckbxAbsteigend_1 = new JCheckBox("Absteigend Sortieren");
		chckbxAbsteigend_1.setBackground(Color.GRAY);
		chckbxAbsteigend_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(chckbxAbsteigend_1, "cell 12 2,alignx center");
		
		chckbxAbsteigend_2 = new JCheckBox("Absteigend Sortieren");
		chckbxAbsteigend_2.setBackground(Color.GRAY);
		chckbxAbsteigend_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(chckbxAbsteigend_2, "cell 15 2,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_highscores.add(scrollPane, "cell 0 3 16 1,grow");
		
		model = new DefaultTableModel();
		addTableData(model, -1);
		table = new JTable(model);
		table.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(table);
		
		JPanel panel_game_detail = new JPanel();
		panel_game_detail.setBackground(Color.GRAY);
		panel_cards.add(panel_game_detail, PANEL_GAME_DETAIL);
		panel_game_detail.setLayout(new MigLayout("", "[600px,grow][200px,grow][200px]", "[][10px:n:10px][grow][grow]"));
		
		JLabel lblSpielDetails = new JLabel("Spiel Details");
		lblSpielDetails.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_game_detail.add(lblSpielDetails, "cell 0 0");
		
		JLabel lblStatistiken = new JLabel("Statistiken:");
		lblStatistiken.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_game_detail.add(lblStatistiken, "cell 2 0");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_game_detail.add(scrollPane_1, "cell 2 1 1 3,grow");
		
		list_detail = new JList<GameDetail.Display>();
		list_detail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel<GameDetail.Display> model = new DefaultListModel<GameDetail.Display>();
		for (Display display : GameDetail.Display.values()) {
			model.addElement(display);
		}
		list_detail.setModel(model);
		list_detail.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateChart();
			}
		});
		list_detail.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(list_detail);
		
		panel_chart = new JPanel();
		panel_chart.setBackground(Color.GRAY);
		panel_game_detail.add(panel_chart, "cell 0 2 1 2,grow");
		panel_chart.setLayout(new BorderLayout(0, 0));
		
		ImagePanel panel_image = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("game_frame/claptrap_4.png"));
		panel_image.setCentered(true);
		panel_image.setAdaptSizeKeepProportion(true);
		panel_image.setBackground(Color.GRAY);
		panel_game_detail.add(panel_image, "cell 1 2,grow");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_game_detail.add(scrollPane_2, "cell 1 3,grow");
		
		txtrStatistics = new JTextArea();
		txtrStatistics.setWrapStyleWord(true);
		txtrStatistics.setEditable(false);
		txtrStatistics.setLineWrap(true);
		txtrStatistics.setBackground(Color.LIGHT_GRAY);
		scrollPane_2.setViewportView(txtrStatistics);
	}
	
	private void updateChart() {
		Display display = list_detail.getSelectedValue();
		panel_chart.removeAll();
		panel_chart.add(analyzer.getGameStatistics().get(displayedGame).generateChart(display, analyzer.getLocalUserId()), BorderLayout.CENTER);
		txtrStatistics.setText(analyzer.getGameStatistics().get(displayedGame).generateText(display, analyzer.getLocalUserId()));
	}
	
	private void selectGameStatistic() {
		if (table.getSelectedRow() != -1) {
			showGameDetails(Integer.parseInt(model.getValueAt(table.getSelectedRow(), 1).toString()));
		}
	}
	
	public void showGameDetails(int gameId) {
		if (gameId != -1) {
			displayedGame = gameId;
			((CardLayout) panel_cards.getLayout()).show(panel_cards, PANEL_GAME_DETAIL);
			list_detail.setSelectedIndex(1);//points_player
		}
	}
	
	private List<MapSelection> toMapSelection(Map<Integer, String> maps) {
		List<MapSelection> selections = new ArrayList<MapSelection>(maps.size());
		maps.forEach((id, name) -> selections.add(new MapSelection(id, name)));
		return selections;
	}
	
	private void addTableData(DefaultTableModel model, int map) {
		model.setRowCount(0);//clear the table
		if (map == -1) {
			List<UserStatistic> userStatistics = analyzer.getSortedUserStatistics();
			userStatistics.forEach(stats -> model.addRow(stats.getDataVector()));
		}
		else {
			List<UserStatistic> mapStatistics = analyzer.getSortedMapStatistics(map);
			mapStatistics.forEach(stats -> model.addRow(stats.getDataVector()));
		}
	}
}