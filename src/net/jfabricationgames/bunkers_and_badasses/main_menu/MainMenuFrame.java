package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.jfgserver.client.JFGClient;

public class MainMenuFrame extends JFrame {
	
	private static final long serialVersionUID = 8543413745165916921L;
	
	private MainMenuDynamicLoader dynamicLoader;
	private JFGClient client;
	private List playersOnline;
	private List playersInGame;
	
	private static ImageLoader imageLoader;
	
	private JPanel contentPane;
	
	public MainMenuFrame() {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	public JFGClient getClient() {
		return client;
	}
	
	private void startGameCreationDialog() {
		//TODO
	}
	private void showAccountSettings() {
		//TODO
	}
	private void showGameRequest(Object startingPlayer, List invitedPlayers) {
		//TODO
	}
}