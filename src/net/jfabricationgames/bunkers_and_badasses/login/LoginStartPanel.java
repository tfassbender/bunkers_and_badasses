package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.miginfocom.swing.MigLayout;

public class LoginStartPanel extends JPanel implements LoginPanel {
	
	private static final long serialVersionUID = 8609984790780328814L;
	
	private JTextField txtTest;
	private JPasswordField passwordField;
	private JLabel lblError;
	
	private LoginClientMain loginClientMain;
	
	private JFGDatabaseLoginClient client;
	
	private boolean loggingIn = false;
	
	public LoginStartPanel(LoginClientMain superFrame, JFGDatabaseLoginClient client, String lastUser) {
		setBackground(Color.GRAY);
		this.client = client;
		this.loginClientMain = superFrame;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new MigLayout("", "[50px,grow][][150px][50px,grow]", "[][20px][][][][grow][40px]"));
		
		JLabel lblJfgDatabaseLogin = new JLabel("Bunkers And Badasses");
		lblJfgDatabaseLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblJfgDatabaseLogin, "cell 1 0 2 1,alignx center");
		
		ImagePanel panel_2 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("login/arschgaul_2.png"));
		panel_2.setAdaptSizeKeepProportion(true);
		panel_2.setCentered(true);
		panel_2.setBackground(Color.GRAY);
		add(panel_2, "cell 0 0 1 6,grow");
		
		ImagePanel panel_3 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("login/tina_1.png"));
		panel_3.setAdaptSizeKeepProportion(true);
		panel_3.setCentered(true);
		panel_3.setBackground(Color.GRAY);
		add(panel_3, "cell 3 0 1 6,grow");
		
		JLabel lblUsername = new JLabel("Username:");
		add(lblUsername, "cell 1 2,alignx trailing");
		
		txtTest = new JTextField();
		txtTest.setText(lastUser);
		txtTest.setBackground(Color.LIGHT_GRAY);
		txtTest.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtTest.selectAll();
			}
		});
		txtTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		add(txtTest, "cell 2 2,growx");
		txtTest.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		add(lblPassword, "cell 1 3,alignx trailing");
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.LIGHT_GRAY);
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.selectAll();
			}
		});
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		add(passwordField, "cell 2 3,growx");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		add(panel, "cell 1 4 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow,trailing][grow]", "[]"));
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(Color.GRAY);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		panel.add(btnLogin, "cell 0 0");
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBackground(Color.GRAY);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				superFrame.setPanel(1);
			}
		});
		panel.add(btnSignUp, "cell 1 0");
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblError, "cell 1 5 2 1,alignx center");
		
		ImagePanel panel_1 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("jfg/headline.png"));
		panel_1.setAdaptSizeKeepProportion(true);
		panel_1.setCentered(true);
		panel_1.setBackground(Color.GRAY);
		add(panel_1, "cell 0 6 4 1,grow");
	}
	
	@Override
	public void requestFocusOnPanelChange() {
		txtTest.requestFocus();
	}

	/**
	 * Send the login to the server. 
	 * In a new thread to not interrupt the frames thread.
	 */
	private void login() {
		if (!loggingIn) {
			loginClientMain.storeUserName(txtTest.getText());
			lblError.setText("Logging in...");
			loggingIn = true;
			Thread login = new Thread(new Runnable() {
				@Override
				public void run() {
					if (txtTest.getText() != null && passwordField.getPassword().length > 0) {
						loginClientMain.setUsername(txtTest.getText());
						if (client.login(txtTest.getText(), new String(passwordField.getPassword()))) {
							lblError.setText("Login Successfull");
							loginClientMain.startLoginLoadingDialog();
						}
						else {
							lblError.setText("Wrong Username or Password");
						}
					}
					else {
						lblError.setText("Wrong Username or Password");
					}
					loggingIn = false;
				}
			}, "login");
			login.start();
		}
	}
	
	public void setErrorMessage(String message) {
		lblError.setText(message);
	}
}