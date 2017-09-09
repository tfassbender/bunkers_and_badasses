package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.miginfocom.swing.MigLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class LoginSignUpPanel2 extends JPanel implements LoginPanel {
	
	private static final long serialVersionUID = -344393319655317638L;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	private JLabel lblError;
	private JButton btnFinish;
	
	private JFGDatabaseLoginClient client;
	private LoginClientMain superFrame;
	
	private boolean signingUp = false;
	
	public LoginSignUpPanel2(LoginClientMain superFrame, JFGDatabaseLoginClient client) {
		setBackground(Color.GRAY);
		this.superFrame = superFrame;
		this.client = client;
		
		setLayout(new MigLayout("", "[50px,grow][][150px,grow][50px,grow]", "[][20px][][][grow][][40px]"));
		
		JLabel lblSignUp = new JLabel("Sign Up:");
		lblSignUp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblSignUp, "cell 1 0 2 1,alignx center");
		
		ImagePanel panel_1 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("login/lilith_1.png"));
		panel_1.setAdaptSizeKeepProportion(true);
		panel_1.setCentered(true);
		panel_1.setBackground(Color.GRAY);
		add(panel_1, "cell 0 0 1 5,grow");
		
		ImagePanel panel_2 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("login/moxxi_1.png"));
		panel_2.setAdaptSizeKeepProportion(true);
		panel_2.setCentered(true);
		panel_2.setBackground(Color.GRAY);
		add(panel_2, "cell 3 0 1 5,grow");
		
		JLabel lblPassword = new JLabel("Password:");
		add(lblPassword, "cell 1 2,alignx trailing");
		
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
				sendSignUp();
			}
		});
		add(passwordField, "cell 2 2,growx");
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		add(lblConfirmPassword, "cell 1 3,alignx trailing");
		
		passwordFieldConfirm = new JPasswordField();
		passwordFieldConfirm.setBackground(Color.LIGHT_GRAY);
		passwordFieldConfirm.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordFieldConfirm.selectAll();
			}
		});
		passwordFieldConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendSignUp();
			}
		});
		add(passwordFieldConfirm, "cell 2 3,growx");
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.GRAY);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				superFrame.setPanel(1);
			}
		});
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblError, "cell 1 4 2 1,alignx center");
		add(btnBack, "cell 0 5 2 1,alignx left,aligny bottom");
		
		btnFinish = new JButton("Finish");
		btnFinish.setBackground(Color.GRAY);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendSignUp();
			}
		});
		add(btnFinish, "cell 2 5 2 1,alignx right,aligny bottom");

		ImagePanel panel = new ImagePanel(LoginClientMain.getImageLoader().loadImage("jfg/headline.png"));
		panel.setAdaptSizeKeepProportion(true);
		panel.setCentered(true);
		panel.setBackground(Color.GRAY);
		add(panel, "cell 0 6 4 1,grow");
	}
	
	@Override
	public void requestFocusOnPanelChange() {
		passwordField.requestFocus();
	}
	
	/**
	 * Send the sign up to the server. 
	 * In a new thread to not interrupt the frames thread.
	 */
	private void sendSignUp() {
		if (!signingUp) {
			btnFinish.setEnabled(false);
			signingUp = true;
			Thread signUp = new Thread(new Runnable() {
				@Override
				public void run() {
					if (new String(passwordField.getPassword()).equals(new String(passwordFieldConfirm.getPassword()))) {
						if (passwordField.getPassword().length >= 5) {
							lblError.setText("Password okay");
							if (client.signUp(superFrame.getUsername(), new String(passwordField.getPassword()))) {
								superFrame.setPanel(0);
							}
							lblError.setText("Unknown Error. Can't sign up");
						}
						else {
							lblError.setText("Password to short");
						}
					}
					else {
						lblError.setText("Wrong confirm password");
					}
					btnFinish.setEnabled(true);
					signingUp = false;
				}
			}, "send_sign_up");
			signUp.start();			
		}
	}
}