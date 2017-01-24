package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.game_communication.GameOverviewRequestMessage;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Toolkit;

public class GameLoadingDialog extends JDialog {
	
	private static final long serialVersionUID = 5567858686487512818L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtBoard;
	private JTextField txtStoringDate;
	private JTextField txtTurn;
	
	private DefaultListModel<User> playersListModel = new DefaultListModel<User>();
	private DefaultListModel<GameOverview> gamesListModel = new DefaultListModel<GameOverview>();
	private JLabel lblLade;
	
	public GameLoadingDialog(JFGClient client, MainMenuFrame callingFrame) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameLoadingDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				callingFrame.disposeGameLoadingDialog();
			}
		});
		
		setResizable(false);
		setTitle("Bunkers and Badasses - Spiel Laden");
		setBounds(100, 100, 400, 550);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[300px,grow][300px,grow]", "[][10px][][250px,grow][10px][][grow][]"));
		{
			JLabel lblSpielLaden = new JLabel("Spiel Laden");
			lblSpielLaden.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielLaden, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblGespeicherteSpiele = new JLabel("Gespeicherte Spiele:");
			lblGespeicherteSpiele.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblGespeicherteSpiele, "cell 0 2");
		}
		{
			lblLade = new JLabel("Laden...");
			lblLade.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPanel.add(lblLade, "cell 1 2");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 3 2 1,grow");
			{
				JList<GameOverview> list_games = new JList<GameOverview>(gamesListModel);
				list_games.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						GameOverview overview = list_games.getSelectedValue();
						showGameOverview(overview);
					}
				});
				list_games.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list_games.setBackground(Color.LIGHT_GRAY);
				list_games.setFont(new Font("Tahoma", Font.PLAIN, 12));
				scrollPane.setViewportView(list_games);
			}
		}
		{
			JLabel lblSpielDetails = new JLabel("Spiel Details:");
			lblSpielDetails.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblSpielDetails, "cell 0 5 2 1");
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 6 2 1,grow");
			panel.setLayout(new MigLayout("", "[][150px][grow]", "[][][][][grow]"));
			{
				JLabel lblSpielfeld = new JLabel("Spielfeld:");
				panel.add(lblSpielfeld, "cell 0 0");
				lblSpielfeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				txtBoard = new JTextField();
				panel.add(txtBoard, "cell 1 0,growx");
				txtBoard.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtBoard.setBackground(Color.LIGHT_GRAY);
				txtBoard.setEditable(false);
				txtBoard.setColumns(10);
			}
			{
				JLabel lblSpeicherDatum = new JLabel("Speicher Datum:");
				panel.add(lblSpeicherDatum, "cell 0 1");
				lblSpeicherDatum.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				txtStoringDate = new JTextField();
				panel.add(txtStoringDate, "cell 1 1,growx");
				txtStoringDate.setBackground(Color.LIGHT_GRAY);
				txtStoringDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
				txtStoringDate.setColumns(10);
			}
			{
				JLabel lblSpielRunde = new JLabel("Spiel Runde:");
				panel.add(lblSpielRunde, "cell 0 2");
				lblSpielRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				txtTurn = new JTextField();
				txtTurn.setFont(new Font("Tahoma", Font.PLAIN, 12));
				panel.add(txtTurn, "cell 1 2,growx");
				txtTurn.setBackground(Color.LIGHT_GRAY);
				txtTurn.setEditable(false);
				txtTurn.setColumns(10);
			}
			{
				JLabel lblSpieler = new JLabel("Spieler:");
				panel.add(lblSpieler, "cell 0 3");
				lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, "cell 0 4 3 1,grow");
				{
					JList<User> list_players = new JList<User>(playersListModel);
					list_players.setFont(new Font("Tahoma", Font.PLAIN, 12));
					list_players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list_players.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(list_players);
				}
			}
		}
		{
			JButton btnSpielLaden = new JButton("Spiel Laden");
			btnSpielLaden.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startGame();
				}
			});
			btnSpielLaden.setBackground(Color.GRAY);
			contentPanel.add(btnSpielLaden, "cell 0 7,alignx right");
		}
		{
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAbbrechen.setBackground(Color.GRAY);
			contentPanel.add(btnAbbrechen, "cell 1 7");
		}
		
		GameOverviewRequestMessage message = new GameOverviewRequestMessage();
		client.sendMessage(message);
	}
	
	private void startGame() {
		//TODO check whether all needed users are online and if so send a game request
	}
	
	private void showGameOverview(GameOverview overview) {
		//add general info
		txtBoard.setText(overview.getBoardName());
		txtStoringDate.setText(overview.getDateStored());
		txtTurn.setText(Integer.toString(overview.getTurn()));
		//add the players
		playersListModel.clear();
		for (User user : overview.getPlayers()) {
			playersListModel.addElement(user);
		}
	}
	
	public void receiveGameOverviews(List<GameOverview> gameOverviews) {
		lblLade.setText("");
		//sort the game overviews by date
		Collections.sort(gameOverviews);
		//add the game overviews to the list
		gamesListModel.clear();
		for (GameOverview overview : gameOverviews) {
			gamesListModel.addElement(overview);
		}
	}
}