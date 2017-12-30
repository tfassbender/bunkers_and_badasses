package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jfabricationgames.toolbox.graphic.ImageLoader;
import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;

import net.jfabricationgames.bunkers_and_badasses.server.ServerMain;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClientInterpreter;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGConnectException;

public class LoginClientMain extends JFrame {
	
	private static final long serialVersionUID = 7230985013444319496L;
	
	private LoginPanel[] panels;
	private JPanel contentPane;
	
	private String username;
	
	private static ImageLoader imageLoader;
	
	private JFGDatabaseLoginClient client;
	
	private LoginLoadingDialog loginLoadingDialog;
	
	private static final String LAST_USER = "last_user";
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
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
		
		//let the program end when escape is pressed
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT");
		getRootPane().getActionMap().put("EXIT", new AbstractAction() {
			private static final long serialVersionUID = -3655815721132450273L;

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
	    });
		
		//set the icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginClientMain.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		
		//create a new client to login and change the interpreter to a bunkers and badasses login interpreter
		boolean connectionError = false;
		JFGClient.setExceptionOnConnectionRefuse(true);
		try {
			client = new JFGDatabaseLoginClient(ServerMain.SERVER_URL, ServerMain.SERVER_PORT, true);//use the secured messaging client
			JFGDatabaseLoginClientInterpreter loginInterpreter = (JFGDatabaseLoginClientInterpreter) client.getClient().getClientInterpreter();
			LoginClientInterpreter interpreter = new LoginClientInterpreter(loginInterpreter, this);
			client.getClient().setClientInterpreter(interpreter);			
		}
		catch (JFGConnectException ce) {
			connectionError = true;
		}
		
		//set the JFGClient to automatically reset the output before every message sent
		//JFGClient.setResetBeforeSending(true);
		
		//print the build information
		try (BufferedReader buildInfoReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/build_info")))) {
			StringBuilder buildInfo = new StringBuilder();
			String line;
			buildInfo.append("Bunkers and Badasses - Client Started\n\n");
			while ((line = buildInfoReader.readLine()) != null) {
				buildInfo.append(line);
				buildInfo.append('\n');
			}
			System.out.println(buildInfo.toString());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		catch (NullPointerException npe) {
			//the NullPointerException occurs when the client is started from eclipse and not from the built jar (can be ignored)
			System.err.println("No build information found");
		}
		
		//create a new ImageLoader for the login panels
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
		
		//create the panels for logging in and signing up
		String lastUser = propsFile.getProperty(LAST_USER);
		panels = new LoginPanel[] {new LoginStartPanel(this, client, lastUser), new LoginSignUpPanel1(this, client), new LoginSignUpPanel2(this, client)};
		
		if (connectionError) {
			((LoginStartPanel) panels[0]).setErrorMessage("<html>Error: Verbindung zum Server<br>kann nicht aufgebaut werden.</html>");
		}
		
		//build the frame
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.add((JPanel) panels[0], BorderLayout.CENTER);
		
		setContentPane(contentPane);
		
		setResizable(false);
		setTitle("Login - Bunkers And Badasses");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 525, 275);
		setLocationRelativeTo(null);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Store the last user name that logged in.
	 * 
	 * @param lastUser
	 * 		The last user's name
	 */
	public void storeUserName(String lastUser) {
		propsFile.setProperty(LAST_USER, lastUser);
		try {
			propsFile.store("");
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
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