package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgdatabaselogin.message.Cryptographer;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class AccountSettingsDialog extends JDialog {
	
	private static final long serialVersionUID = 2421103951610004291L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JPasswordField pwdConfirm;
	
	private JFGClient client;
	private JCheckBox chckbxPassword;
	private JCheckBox chckbxName;
	private JLabel lblError;
	private JButton okButton;
	private JButton cancelButton;
	
	public AccountSettingsDialog(JFGClient client, MainMenuFrame callingFrame) {
		this.client = client;

		setResizable(false);
		setTitle("Bunkers and Badasses - Profil Einstellungen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AccountSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 500, 250);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[50px][][225px][grow]", "[][][][][][25px,grow][]"));
		{
			JLabel lblAendern = new JLabel("Einstellungen \u00C4ndern");
			lblAendern.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblAendern, "cell 0 0 4 1,alignx center");
		}
		{
			chckbxName = new JCheckBox("Name");
			chckbxName.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if (chckbxName.isSelected()) {
						txtUsername.setEnabled(true);
					}
					else {
						txtUsername.setEnabled(false);
					}
				}
			});
			chckbxName.setBackground(Color.GRAY);
			contentPanel.add(chckbxName, "cell 1 2");
		}
		{
			txtUsername = new JTextField();
			txtUsername.setEnabled(false);
			txtUsername.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(txtUsername, "cell 2 2,growx");
			txtUsername.setColumns(10);
		}
		{
			ImagePanel panel_img_1 = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("main_menu/claptrap_3.png"));
			panel_img_1.setCentered(true);
			panel_img_1.setAdaptSizeKeepProportion(true);
			panel_img_1.setBackground(Color.GRAY);
			contentPanel.add(panel_img_1, "cell 3 1 1 6,grow");
		}
		{
			chckbxPassword = new JCheckBox("Password");
			chckbxPassword.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (chckbxPassword.isSelected()) {
						pwdPassword.setEnabled(true);
						pwdConfirm.setEnabled(true);
					}
					else {
						pwdPassword.setEnabled(false);
						pwdPassword.setEnabled(false);
					}
				}
			});
			chckbxPassword.setBackground(Color.GRAY);
			contentPanel.add(chckbxPassword, "cell 1 3");
		}
		{
			pwdPassword = new JPasswordField();
			pwdPassword.setEnabled(false);
			pwdPassword.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(pwdPassword, "cell 2 3,growx");
		}
		{
			JLabel lblWiederhohlen = new JLabel("Wiederhohlen:");
			contentPanel.add(lblWiederhohlen, "cell 1 4,alignx trailing");
		}
		{
			pwdConfirm = new JPasswordField();
			pwdConfirm.setEnabled(false);
			pwdConfirm.setBackground(Color.LIGHT_GRAY);
			contentPanel.add(pwdConfirm, "cell 2 4,growx");
		}
		{
			ImagePanel panel_img_2 = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("main_menu/skag_2.png"));
			panel_img_2.setCentered(true);
			panel_img_2.setAdaptSizeKeepProportion(true);
			panel_img_2.setBackground(Color.GRAY);
			contentPanel.add(panel_img_2, "cell 0 5 2 2,grow");
		}
		{
			lblError = new JLabel("");
			lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblError, "cell 2 5,alignx center");
		}
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "cell 2 6,alignx center");
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new MigLayout("", "[][85px]", "[23px]"));
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						lblError.setText("");
						sendUpdate();
					}
				});
				okButton.setBackground(Color.GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 0 0");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Abbrechen");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setBackground(Color.GRAY);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 1 0");
			}
		}
	}
	
	public void receiveUpdateAnswer(boolean updateSuccessfull, String username) {
		if (updateSuccessfull) {
			lblError.setText("Änderungen wurden eingetragen");
			UserManager.setUsername(username);
		}
		else {
			lblError.setText("Fehler: Änderungen fehlgeschlagen");
		}
		okButton.setEnabled(true);
		cancelButton.setEnabled(true);
	}
	
	private void sendUpdate() {
		if (chckbxName.isSelected()) {
			if (chckbxPassword.isSelected()) {
				if (new String(pwdPassword.getPassword()).equals(pwdConfirm.getPassword()) && pwdPassword.getPassword().length >= 5 && txtUsername.getText().length() >= 5) {
					updateAll(txtUsername.getText(), new String(pwdPassword.getPassword()));
				}
				else if (pwdPassword.getPassword().length < 5) {
					lblError.setText("Password zu kurz");
				}
				else if (txtUsername.getText().length() < 5) {
					lblError.setText("Name zu kurz");
				}
			}
			else if (txtUsername.getText().length() >= 5) {
				updateUsername(txtUsername.getText());
			}
			else {
				lblError.setText("Name zu kurz");
			}
		}
		else if (chckbxPassword.isSelected()) {
			if (new String(pwdPassword.getPassword()).equals(pwdConfirm.getPassword()) && pwdPassword.getPassword().length >= 5) {
				updatePassword(new String(pwdPassword.getPassword()));
			}
			else if (pwdPassword.getPassword().length < 5) {
				lblError.setText("Password zu kurz");
			}
		}
	}
	
	private void updatePassword(String password) {
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
		MainMenuMessage message = new MainMenuMessage();
		String passwdCrypt = Cryptographer.encryptText(password, "cG4d2DP1MQlyonezuv71Z03");
		message.setMessageType(MainMenuMessage.MessageType.PASSWORD_UPDATE);
		message.setLastUsername(UserManager.getUsername());
		message.setPassword(passwdCrypt);
		client.resetOutput();
		client.sendMessage(message);
	}
	private void updateUsername(String username) {
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
		MainMenuMessage message = new MainMenuMessage();
		message.setMessageType(MainMenuMessage.MessageType.USERNAME_UPDATE);
		message.setLastUsername(UserManager.getUsername());
		message.setUsername(username);
		client.resetOutput();
		client.sendMessage(message);
	}
	private void updateAll(String username, String password) {
		okButton.setEnabled(false);
		cancelButton.setEnabled(false);
		MainMenuMessage message = new MainMenuMessage();
		String passwdCrypt = Cryptographer.encryptText(password, "cG4d2DP1MQlyonezuv71Z03");
		message.setMessageType(MainMenuMessage.MessageType.PASSWORD_USERNAME_UPDATE);
		message.setLastUsername(UserManager.getUsername());
		message.setUsername(username);
		message.setPassword(passwdCrypt);
		client.resetOutput();
		client.sendMessage(message);
	}
}