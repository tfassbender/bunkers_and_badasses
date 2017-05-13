package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClient;
import net.miginfocom.swing.MigLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class LoginSignUpPanel1 extends JPanel implements LoginPanel {
	
	private static final long serialVersionUID = -1587127339817677764L;
	private JTextField textField;
	private JLabel lblMessage;
	private JButton btnNext;
	private JButton btnCheckUsername;
	
	private JFGDatabaseLoginClient client;
	
	private boolean checkingUsername = false;

	public LoginSignUpPanel1(LoginClientMain superFrame, JFGDatabaseLoginClient client) {
		setBackground(Color.GRAY);
		this.client = client;
		
		setLayout(new MigLayout("", "[50px,grow][][150px][50px,grow]", "[][20px][][][grow][][40px]"));
		
		JLabel lblSignUp = new JLabel("Sign Up:");
		lblSignUp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblSignUp, "cell 1 0 2 1,alignx center");
		
		ImagePanel panel_1 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("login/claptrap_2.png"));
		panel_1.setAdaptSizeKeepProportion(true);
		panel_1.setCentered(true);
		panel_1.setBackground(Color.GRAY);
		add(panel_1, "cell 0 0 1 5,grow");
		
		ImagePanel panel_2 = new ImagePanel(LoginClientMain.getImageLoader().loadImage("login/mr_torgue_1.png"));
		panel_2.setAdaptSizeKeepProportion(true);
		panel_2.setCentered(true);
		panel_2.setBackground(Color.GRAY);
		add(panel_2, "cell 3 0 1 5,grow");
		
		JLabel lblUsername = new JLabel("Username:");
		add(lblUsername, "cell 1 2,alignx trailing");
		
		textField = new JTextField();
		textField.setBackground(Color.LIGHT_GRAY);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.selectAll();
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_ENTER) {
					btnNext.setEnabled(false);					
				}
			}
		});
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUsername();
			}
		});
		add(textField, "cell 2 2,growx");
		textField.setColumns(10);
		
		btnCheckUsername = new JButton("Check Username");
		btnCheckUsername.setBackground(Color.GRAY);
		btnCheckUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUsername();
			}
		});
		add(btnCheckUsername, "cell 1 3 2 1,alignx center");
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.GRAY);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				superFrame.setPanel(0);
			}
		});
		
		lblMessage = new JLabel("");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblMessage, "cell 1 4 2 1,alignx center");
		add(btnBack, "cell 0 5 2 1,alignx left,aligny bottom");
		
		btnNext = new JButton("Next");
		btnNext.setBackground(Color.GRAY);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				superFrame.setUsername(textField.getText());
				superFrame.setPanel(2);
			}
		});
		btnNext.setEnabled(false);
		add(btnNext, "cell 2 5 2 1,alignx right,aligny bottom");
		
		ImagePanel panel = new ImagePanel(LoginClientMain.getImageLoader().loadImage("jfg/headline.png"));
		panel.setAdaptSizeKeepProportion(true);
		panel.setCentered(true);
		panel.setBackground(Color.GRAY);
		add(panel, "cell 0 6 4 1,grow");
	}
	
	@Override
	public void requestFocusOnPanelChange() {
		textField.requestFocus();
	}
	
	/**
	 * Check the name the user chose in a new thread to not interrupt the frames thread.
	 */
	private void checkUsername() {
		if (!checkingUsername) {
			btnCheckUsername.setEnabled(false);
			checkingUsername = true;
			Thread checkUsername = new Thread(new Runnable() {
				@Override
				public void run() {
					if (textField.getText() != null) {
						if (textField.getText().length() > 5) {
							if (client.isNameAvailable(textField.getText())) {
								btnNext.setEnabled(true);
								lblMessage.setText("Username is valid");
							}
							else {
								lblMessage.setText("Username is already taken");
							}
						}
						else {
							lblMessage.setText("Username to short");
						}
					}
					else {
						lblMessage.setText("Choose a username");
					}
					btnCheckUsername.setEnabled(true);
					checkingUsername = false;
				}
			});
			checkUsername.start();			
		}
	}
}