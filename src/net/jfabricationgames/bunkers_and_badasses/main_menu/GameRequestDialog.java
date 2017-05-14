package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.game_frame.GameStartDialog;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameOverview;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameRequestDialog extends JFrame {
	
	private static final long serialVersionUID = -7224302886169954013L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	private MainMenuFrame mainMenu;
	
	private User invitingUser;
	private JButton okButton;
	private JButton cancelButton;
	private JTextArea txtrInvitedplayers;
	private JLabel lblMessage_1;
	private JTextField txtMap;
	private JTextField txtDateStored;
	
	private GameOverview overview;//keep the reference to know which game to load
	
	public GameRequestDialog(JFGClient client, MainMenuFrame callingFrame, User invitingUser, List<User> invitedUsers, String map, GameOverview overview) {
		setAlwaysOnTop(true);
		this.client = client;
		this.invitingUser = invitingUser;
		this.overview = overview;
		this.mainMenu = callingFrame;
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameRequestDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Spiel Einladung");
		
		setBounds(100, 100, 500, 450);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[250px][grow]", "[][10px][][10px][][grow][5px][][][5px][][][25px][]"));
		{
			JLabel lblSpielEinladung = new JLabel("Spiel Einladung");
			lblSpielEinladung.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielEinladung, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblMessage = new JLabel(invitingUser.toString() + " hat dich zu einem Spiel eingeladen");
			lblMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblMessage, "cell 0 2 2 1");
		}
		{
			JLabel lblTeilnehmer = new JLabel("Teilnehmer:");
			lblTeilnehmer.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblTeilnehmer, "cell 0 4");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 5,grow");
			{
				txtrInvitedplayers = new JTextArea();
				txtrInvitedplayers.setBackground(Color.LIGHT_GRAY);
				txtrInvitedplayers.setEditable(false);
				txtrInvitedplayers.setWrapStyleWord(true);
				txtrInvitedplayers.setLineWrap(true);
				scrollPane.setViewportView(txtrInvitedplayers);
			}
		}
		{
			ImagePanel panel = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("login/mr_torgue_1.png"));
			panel.setCentered(true);
			panel.setAdaptSizeKeepProportion(true);
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 1 5 1 8,grow");
		}
		{
			JLabel lblKarte = new JLabel("Karte:");
			lblKarte.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblKarte, "cell 0 7");
		}
		{
			txtMap = new JTextField(map);
			txtMap.setEditable(false);
			txtMap.setBackground(Color.LIGHT_GRAY);
			txtMap.setFont(new Font("Tahoma", Font.PLAIN, 12));
			contentPanel.add(txtMap, "cell 0 8,growx");
			txtMap.setColumns(10);
		}
		{
			JLabel lblZuletztGespielt = new JLabel("Zuletzt Gespielt:");
			lblZuletztGespielt.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblZuletztGespielt, "cell 0 10");
		}
		{
			String lastPlayed = null;
			if (overview != null) {
				lastPlayed = overview.getDateStored();
				lastPlayed += " (Runde " + overview.getTurn() + ")"; 
			}
			else {
				lastPlayed = "-----";
			}
			txtDateStored = new JTextField(lastPlayed);
			txtDateStored.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(txtDateStored, "cell 0 11,growx");
			txtDateStored.setColumns(10);
			
		}
		{
			lblMessage_1 = new JLabel("");
			lblMessage_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblMessage_1, "cell 0 12");
		}
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "cell 0 13 2 1,alignx center");
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new MigLayout("", "[][]", "[]"));
			{
				okButton = new JButton("Annehmen");
				okButton.setEnabled(callingFrame.isDynamicVariablesLoaded());
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sendAnswer(true);
					}
				});
				okButton.setBackground(Color.GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 0 0");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Ablehnen");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sendAnswer(false);
						dispose();
					}
				});
				cancelButton.setBackground(Color.GRAY);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 1 0");
			}
		}
		
		addInvitedPlayers(invitedUsers, invitingUser);
	}
	
	public void startGame(int boardId, int players, boolean loadedGame) {
		GameStartDialog startDialog = new GameStartDialog();
		startDialog.setVisible(true);
		mainMenu.dispose();
		startDialog.startGame(client, boardId, players, loadedGame, overview, mainMenu.getSkillProfileManager());
	}
	
	private void addInvitedPlayers(List<User> players, User invitingPlayer) {
		txtrInvitedplayers.append(invitingPlayer + "\n");
		for (User user : players) {
			txtrInvitedplayers.append(user.getUsername() + "\n");
		}
	}
	
	private void sendAnswer(boolean joining) {
		//disable the buttons
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
		//send the message
		MainMenuMessage gameCreationAnswer = new MainMenuMessage();
		gameCreationAnswer.setMessageType(MainMenuMessage.MessageType.GAME_CREATION_ANSWER);
		gameCreationAnswer.setJoining(joining);
		gameCreationAnswer.setPlayer(new User(UserManager.getUsername()));
		gameCreationAnswer.setToPlayer(invitingUser);
		client.resetOutput();
		client.sendMessage(gameCreationAnswer);
		lblMessage_1.setText("Warte auf andere Spieler");
	}
	
	public void receiveAbort(String abortMessage) {
		okButton.setEnabled(false);
		lblMessage_1.setText(abortMessage);
	}
	
	public void enableGameStart() {
		okButton.setEnabled(true);
	}
}