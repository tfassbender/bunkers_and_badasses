package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import java.awt.Toolkit;

public class LoginClientMain extends JFrame {
	
	private static final long serialVersionUID = 7230985013444319496L;
	
	private JPanel[] panels;
	
	private JPanel contentPane;
	
	private String username;
	
	public static ImageLoader imageLoader;
	
	private JFGDatabaseLoginClient client;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginClientMain frame = new LoginClientMain();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public LoginClientMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginClientMain.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		//create a new client to login
		client = new JFGDatabaseLoginClient(LoginServerMain.SERVER_URL, LoginServerMain.SERVER_PORT);
		
		//create a new ImageLoader for the login panels
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		//create the panels for logging in and signing up
		panels = new JPanel[] {new LoginStartPanel(this, client), new LoginSignUpPanel1(this, client), new LoginSignUpPanel2(this, client)};
		
		//build the frame
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(panels[0], BorderLayout.CENTER);
		
		setContentPane(contentPane);
		
		setResizable(false);
		setTitle("Bunkers And Badasses - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 525, 275);
		setLocationRelativeTo(null);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Set the panel to the sign up or login panel
	 */
	public void setPanel(int panel) {
		contentPane.removeAll();
		contentPane.add(panels[panel], BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public JFGDatabaseLoginClient getClient() {
		return client;
	}
}