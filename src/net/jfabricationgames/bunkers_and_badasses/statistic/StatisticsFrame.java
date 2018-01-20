package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameDetail.Display;
import net.jfabricationgames.bunkers_and_badasses.statistic.UserStatistic.Value;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.miginfocom.swing.MigLayout;

public class StatisticsFrame extends JFrame {
	
	private static final long serialVersionUID = 7270679844108130046L;
	
	private JPanel contentPane;
	
	private StatisticsAnalyzer analyzer;
	private JTable table_highscores;
	private JTable table_games;
	
	private DefaultTableModel model;
	private DefaultTableModel modelGames;
	private DefaultComboBoxModel<User> userModel;
	
	private JCheckBox chckbxAbsteigend;
	private JCheckBox chckbxAbsteigend_1;
	private JCheckBox chckbxAbsteigend_2;
	private JComboBox<MapSelection> comboBox_map;
	private JComboBox<User> comboBox_user;
	private JComboBox<UserStatistic.Value> comboBox_sort_1;
	private JComboBox<UserStatistic.Value> comboBox_sort_2;
	private JComboBox<UserStatistic.Value> comboBox_sort_3;
	private JList<GameDetail.Display> list_detail;
	private JPanel panel_chart;
	private JPanel panel_cards;
	private JTextArea txtrStatistics;
	
	private int displayedGame;
	
	private static final String PANEL_HIGHSCORES = "highscores";
	private static final String PANEL_GAME_DETAIL = "game";
	private static final String PANEL_GAMES = "all_games";
	
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		setMinimumSize(new Dimension(1200, 750));
		setLocationRelativeTo(null);
		
		
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
		
		JMenuItem mntmAlleSpiele = new JMenuItem("Alle Spiele");
		mntmAlleSpiele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panel_cards.getLayout()).show(panel_cards, PANEL_GAMES);
			}
		});
		mnStatistik.add(mntmAlleSpiele);
		mnStatistik.add(mntmSpielStatistik);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[70px:n:70px][grow]"));
		
		ImagePanel panel_headline = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("jfg/headline.png"));
		panel_headline.setCentered(true);
		panel_headline.setAdaptSizeKeepProportion(true);
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
				updateSortKeys();
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
				updateSortKeys();
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
				updateSortKeys();
			}
		});
		UserStatistic.getValueSelections().forEach(header -> comboBox_sort_3.addItem(header));
		panel_highscores.add(comboBox_sort_3, "cell 15 1,growx");
		
		chckbxAbsteigend = new JCheckBox("Absteigend Sortieren");
		chckbxAbsteigend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateSortKeys();
			}
		});
		chckbxAbsteigend.setBackground(Color.GRAY);
		chckbxAbsteigend.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(chckbxAbsteigend, "cell 9 2,alignx center");
		
		chckbxAbsteigend_1 = new JCheckBox("Absteigend Sortieren");
		chckbxAbsteigend_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSortKeys();
			}
		});
		chckbxAbsteigend_1.setBackground(Color.GRAY);
		chckbxAbsteigend_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(chckbxAbsteigend_1, "cell 12 2,alignx center");
		
		chckbxAbsteigend_2 = new JCheckBox("Absteigend Sortieren");
		chckbxAbsteigend_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSortKeys();
			}
		});
		chckbxAbsteigend_2.setBackground(Color.GRAY);
		chckbxAbsteigend_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_highscores.add(chckbxAbsteigend_2, "cell 15 2,alignx center");
		
		JScrollPane scrollPane = new JScrollPane();
		panel_highscores.add(scrollPane, "cell 0 3 16 1,grow");
		
		model = new BunkersAndBadassesTableModel(UserStatistic.getColumnVector());
		addTableData(model, -1);
		table_highscores = new JTable(model);
		table_highscores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_highscores.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(table_highscores);
		
		JPanel panel_game_detail = new JPanel();
		panel_game_detail.setBackground(Color.GRAY);
		panel_cards.add(panel_game_detail, PANEL_GAME_DETAIL);
		panel_game_detail.setLayout(new MigLayout("", "[600px,grow][50px][:100px:200px,grow][200px]", "[][10px:n:10px][grow][][grow]"));
		
		JLabel lblSpielDetails = new JLabel("Spiel Details");
		lblSpielDetails.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_game_detail.add(lblSpielDetails, "cell 0 0");
		
		JLabel lblStatistiken = new JLabel("Statistiken:");
		lblStatistiken.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_game_detail.add(lblStatistiken, "cell 3 0");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_game_detail.add(scrollPane_1, "cell 3 1 1 4,grow");
		
		list_detail = new JList<GameDetail.Display>();
		list_detail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultListModel<GameDetail.Display> listModel = new DefaultListModel<GameDetail.Display>();
		for (Display display : GameDetail.Display.values()) {
			listModel.addElement(display);
		}
		list_detail.setModel(listModel);
		list_detail.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateChart();
			}
		});
		list_detail.setBackground(Color.LIGHT_GRAY);
		scrollPane_1.setViewportView(list_detail);
		
		panel_chart = new JPanel();
		panel_chart.setBackground(Color.GRAY);
		panel_game_detail.add(panel_chart, "cell 0 2 1 3,grow");
		panel_chart.setLayout(new BorderLayout(0, 0));
		
		ImagePanel panel_image = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("game_frame/claptrap_4.png"));
		panel_image.setToolTipText("<html>CL4P-TP. Aber seine Freunde nennen ihn Claptrap <br>\r\n(also das w\u00FCrden Sie, wenn sie nicht tot w\u00E4hren, ... oder je existiert h\u00E4tten...)</html>");
		panel_image.setCentered(true);
		panel_image.setAdaptSizeKeepProportion(true);
		panel_image.setBackground(Color.GRAY);
		panel_game_detail.add(panel_image, "cell 1 2 2 1,grow");
		
		JLabel lblSpieler = new JLabel("Spieler:");
		lblSpieler.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_game_detail.add(lblSpieler, "cell 1 3,alignx trailing");
		
		userModel = new DefaultComboBoxModel<User>();
		comboBox_user = new JComboBox<User>(userModel);
		comboBox_user.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateChart();
			}
		});
		panel_game_detail.add(comboBox_user, "cell 2 3,growx");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_game_detail.add(scrollPane_2, "cell 1 4 2 1,grow");
		
		txtrStatistics = new JTextArea();
		txtrStatistics.setWrapStyleWord(true);
		txtrStatistics.setEditable(false);
		txtrStatistics.setLineWrap(true);
		txtrStatistics.setBackground(Color.LIGHT_GRAY);
		scrollPane_2.setViewportView(txtrStatistics);
		
		JPanel panel_games = new JPanel();
		panel_games.setBackground(Color.GRAY);
		panel_cards.add(panel_games, PANEL_GAMES);
		panel_games.setLayout(new MigLayout("", "[grow][]", "[][grow]"));
		
		JLabel lblSpiele = new JLabel("Spiele:");
		lblSpiele.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_games.add(lblSpiele, "cell 0 0");
		
		JButton button = new JButton("Spiel Details");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectGameStatistic();
			}
		});
		button.setBackground(Color.GRAY);
		panel_games.add(button, "cell 1 0,aligny center");
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_games.add(scrollPane_3, "cell 0 1 2 1,grow");
		

		modelGames = new BunkersAndBadassesTableModel(GameStatistic.getColumnVector());
		table_games = new JTable(modelGames);
		table_games.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_games.setBackground(Color.LIGHT_GRAY);
		scrollPane_3.setViewportView(table_games);
		addGamesTable(modelGames);
	}
	
	private class BunkersAndBadassesTableModel extends DefaultTableModel {
		
		private static final long serialVersionUID = -5944255298397665195L;
		
		public BunkersAndBadassesTableModel(Vector<String> cols) {
			super(cols, 0);
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
	
	private void updateSortKeys() {
		if (comboBox_sort_1 != null) {
			Value value = (Value) comboBox_sort_1.getSelectedItem();
			if (value != null && chckbxAbsteigend != null) {
				UserStatistic.setSortKey(value.getIndex(), 1, chckbxAbsteigend.isSelected());
			}
			MapSelection map = (MapSelection) comboBox_map.getSelectedItem();
			if (map != null) {
				addTableData(model, map.getId());
			}			
		}
		
		if (comboBox_sort_2 != null) {
			Value value2 = (Value) comboBox_sort_2.getSelectedItem();
			if (value2 != null && chckbxAbsteigend_1 != null) {
				UserStatistic.setSortKey(value2.getIndex(), 2, chckbxAbsteigend_1.isSelected());
			}
			MapSelection map2 = (MapSelection) comboBox_map.getSelectedItem();
			if (map2 != null) {
				addTableData(model, map2.getId());
			}
		}
		
		if (comboBox_sort_3 != null) {
			Value value3 = (Value) comboBox_sort_3.getSelectedItem();
			if (value3 != null && chckbxAbsteigend_2 != null) {
				UserStatistic.setSortKey(value3.getIndex(), 3, chckbxAbsteigend_2.isSelected());
			}
			MapSelection map3 = (MapSelection) comboBox_map.getSelectedItem();
			if (map3 != null) {
				addTableData(model, map3.getId());
			}
		}
	}
	
	private void updateChart() {
		Display display = list_detail.getSelectedValue();
		GameDetail game = analyzer.getGameStatistics().get(displayedGame);
		User selectedUser = (User) comboBox_user.getSelectedItem();
		if (selectedUser != null && display != null) {
			int userId = game.userNameMap().get(selectedUser);
			panel_chart.removeAll();
			panel_chart.add(analyzer.getGameStatistics().get(displayedGame).generateChart(display, userId), BorderLayout.CENTER);
			txtrStatistics.setText(analyzer.getGameStatistics().get(displayedGame).generateText(display, userId));
			revalidate();
			repaint();
		}
	}
	
	private void selectGameStatistic() {
		if (table_games.getSelectedRow() != -1) {
			//load the game id from the table and show the game details
			showGameDetails(Integer.parseInt(modelGames.getValueAt(table_games.getSelectedRow(), 0).toString()));
		}
	}
	
	private void showGameDetails(int gameId) {
		if (gameId != -1) {
			displayedGame = gameId;
			updateUserModel();
			((CardLayout) panel_cards.getLayout()).show(panel_cards, PANEL_GAME_DETAIL);
			list_detail.setSelectedIndex(1);//points_player
		}
	}
	
	private void updateUserModel() {
		userModel.removeAllElements();
		GameDetail detail = analyzer.getGameStatistics().get(displayedGame);
		if (detail != null) {
			detail.userNameMap().forEach((user, id) -> userModel.addElement(user));
			comboBox_user.setSelectedIndex(0);
		}
	}
	
	private List<MapSelection> toMapSelection(Map<Integer, String> maps) {
		List<MapSelection> selections = new ArrayList<MapSelection>(maps.size());
		maps.forEach((id, name) -> selections.add(new MapSelection(id, name)));
		return selections;
	}
	
	private void addTableData(DefaultTableModel model, int map) {
		if (model != null) {
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
	
	private void addGamesTable(DefaultTableModel model) {
		model.setRowCount(0);//clear the table
		List<GameStatistic> stats = analyzer.getStatistics();//sorted when the analyzer is initialized
		stats.forEach(game -> model.addRow(game.getDataVector()));
	}
}