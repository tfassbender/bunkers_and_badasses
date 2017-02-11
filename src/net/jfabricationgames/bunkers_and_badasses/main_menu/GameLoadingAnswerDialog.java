package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameStartDialog;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameLoadingAnswerDialog extends JDialog {
	
	private static final long serialVersionUID = -1855631664399810658L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	private JTextField txtBoard;
	private JTextField txtStoringDate;
	private JTextField txtTurn;
	private JTextArea txtrAnswers;
	
	private DefaultListModel<User> playersListModel = new DefaultListModel<User>();
	private JButton btnSpielStarten;
	
	private List<User> players;
	private MainMenuFrame mainMenu;
	private Board selectedBoard;
	
	public GameLoadingAnswerDialog(GameLoadingDialog callingFrame, JFGClient client, List<User> players, MainMenuFrame mainMenu, Board selectedBoard) {
		setResizable(false);
		setTitle("Bunkers and Badasses - Spiel Laden");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameLoadingAnswerDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 500, 550);
		setMinimumSize(new Dimension(500, 450));
		setLocationRelativeTo(callingFrame);
		
		this.client = client;
		this.players = players;
		this.mainMenu = mainMenu;
		this.selectedBoard = selectedBoard;
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][5px][][][5px][][grow][]"));
		{
			JLabel lblSpielLaden = new JLabel("Spiel Laden");
			lblSpielLaden.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielLaden, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblSpielDetail = new JLabel("Spiel Detail");
			lblSpielDetail.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblSpielDetail, "cell 0 2");
		}
		{
			{
				JPanel panel_1 = new JPanel();
				contentPanel.add(panel_1, "cell 0 3");
				panel_1.setBackground(Color.GRAY);
				panel_1.setLayout(new MigLayout("", "[][150px][grow]", "[][][][][grow]"));
				{
					JLabel lblSpielfeld = new JLabel("Spielfeld:");
					panel_1.add(lblSpielfeld, "cell 0 0");
					lblSpielfeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
				}
				{
					txtBoard = new JTextField();
					panel_1.add(txtBoard, "cell 1 0,growx");
					txtBoard.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtBoard.setBackground(Color.LIGHT_GRAY);
					txtBoard.setEditable(false);
					txtBoard.setColumns(10);
				}
				{
					JLabel lblSpeicherDatum = new JLabel("Speicher Datum:");
					panel_1.add(lblSpeicherDatum, "cell 0 1");
					lblSpeicherDatum.setFont(new Font("Tahoma", Font.PLAIN, 12));
				}
				{
					txtStoringDate = new JTextField();
					panel_1.add(txtStoringDate, "cell 1 1,growx");
					txtStoringDate.setBackground(Color.LIGHT_GRAY);
					txtStoringDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
					txtStoringDate.setColumns(10);
				}
				{
					JLabel lblSpielRunde = new JLabel("Spiel Runde:");
					panel_1.add(lblSpielRunde, "cell 0 2");
					lblSpielRunde.setFont(new Font("Tahoma", Font.PLAIN, 12));
				}
				{
					txtTurn = new JTextField();
					txtTurn.setFont(new Font("Tahoma", Font.PLAIN, 12));
					panel_1.add(txtTurn, "cell 1 2,growx");
					txtTurn.setBackground(Color.LIGHT_GRAY);
					txtTurn.setEditable(false);
					txtTurn.setColumns(10);
				}
				{
					JLabel lblSpieler = new JLabel("Spieler:");
					panel_1.add(lblSpieler, "cell 0 3");
					lblSpieler.setFont(new Font("Tahoma", Font.PLAIN, 12));
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					panel_1.add(scrollPane, "cell 0 4 3 1,grow");
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
				ImagePanel panel = new ImagePanel("main_menu/angel_1.png");
				panel.setBackground(Color.GRAY);
				contentPanel.add(panel, "cell 1 1 1 4,grow");
			}
			{
				JLabel lblAntworten = new JLabel("Antworten:");
				lblAntworten.setFont(new Font("Tahoma", Font.BOLD, 16));
				contentPanel.add(lblAntworten, "cell 0 5 2 1");
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				contentPanel.add(scrollPane, "cell 0 6 2 1,grow");
				{
					txtrAnswers = new JTextArea();
					txtrAnswers.setEditable(false);
					txtrAnswers.setBackground(Color.LIGHT_GRAY);
					scrollPane.setViewportView(txtrAnswers);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 7 2 1,grow");
			panel.setLayout(new MigLayout("", "[grow][grow]", "[]"));
			{
				btnSpielStarten = new JButton("Spiel Starten");
				btnSpielStarten.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						startGame();
					}
				});
				btnSpielStarten.setEnabled(false);
				btnSpielStarten.setBackground(Color.GRAY);
				panel.add(btnSpielStarten, "cell 0 0,alignx right");
			}
			{
				JButton btnAbbrechen = new JButton("Abbrechen");
				btnAbbrechen.setBackground(Color.GRAY);
				panel.add(btnAbbrechen, "cell 1 0");
			}
		}
	}
	
	public void receiveGameLoadingAnswer(User player, boolean joining) {
		if (joining) {
			txtrAnswers.append("Zusage von: ");
		}
		else {
			txtrAnswers.append("Absage von: ");
		}
		txtrAnswers.append(player.getUsername() + "\n");
	}
	
	public void enableGameStart() {
		btnSpielStarten.setEnabled(true);
	}
	public void disableGameStart() {
		btnSpielStarten.setEnabled(false);
	}
	
	public void startGame() {
		//create a new GameStartDialog and pass on the data
		GameStartDialog startDialog = new GameStartDialog();
		startDialog.setVisible(true);
		mainMenu.dispose();// dispose all frames of the main menu
		//startDialog.startGameMaster(client, players, selectedBoard);
		//TODO load a game (don't use startGameMaster)
	}
}