package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.server.ServerMain;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClientInterpreter;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class LoginClientMain extends JFrame {
	
	private static final long serialVersionUID = 7230985013444319496L;
	
	private LoginPanel[] panels;
	private JPanel contentPane;
	
	private String username;
	
	private static ImageLoader imageLoader;
	
	private JFGDatabaseLoginClient client;
	
	private LoginLoadingDialog loginLoadingDialog;
	
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
		//set default look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	       //e.printStackTrace();
	    }
		
		//set the icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginClientMain.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		
		//create a new client to login and change the interpreter to a bunkers and badasses login interpreter
		//TODO maybe catch ConnectException ?
		client = new JFGDatabaseLoginClient(ServerMain.SERVER_URL, ServerMain.SERVER_PORT);
		JFGDatabaseLoginClientInterpreter loginInterpreter = (JFGDatabaseLoginClientInterpreter) client.getClient().getClientInterpreter();
		LoginClientInterpreter interpreter = new LoginClientInterpreter(loginInterpreter, this);
		client.getClient().setClientInterpreter(interpreter);
		
		//set the JFGClient to automatically reset the output before every message sent
		//JFGClient.setResetBeforeSending(true);
		
		//create a new ImageLoader for the login panels
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		//create the panels for logging in and signing up
		panels = new LoginPanel[] {new LoginStartPanel(this, client), new LoginSignUpPanel1(this, client), new LoginSignUpPanel2(this, client)};
		
		//build the frame
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add((JPanel) panels[0], BorderLayout.CENTER);
		
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
		contentPane.add((JPanel) panels[panel], BorderLayout.CENTER);
		panels[panel].requestFocusOnPanelChange();
		revalidate();
		repaint();
	}
	
	public void startLoginLoadingDialog() {
		loginLoadingDialog = new LoginLoadingDialog(client.getClient());
		((LoginClientInterpreter) client.getClient().getClientInterpreter()).setLoginLoadingDialog(loginLoadingDialog);
		loginLoadingDialog.setVisible(true);
		dispose();
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
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
}