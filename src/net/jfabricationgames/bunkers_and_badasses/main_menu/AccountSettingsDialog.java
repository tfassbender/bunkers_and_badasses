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

import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AccountSettingsDialog extends JDialog {
	
	private static final long serialVersionUID = 2421103951610004291L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JPasswordField pwdConfirm;
	
	public AccountSettingsDialog(JFGClient client, MainMenuFrame callingFrame) {
		setResizable(false);
		setTitle("Bunkers and Badasses - Profil Einstellungen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AccountSettingsDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		this.client = client;
		
		setBounds(100, 100, 450, 250);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[50px][][150px][grow]", "[][][][][][grow][]"));
		{
			JLabel lblAendern = new JLabel("Einstellungen \u00C4ndern");
			lblAendern.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblAendern, "cell 0 0 4 1,alignx center");
		}
		{
			JCheckBox chckbxName = new JCheckBox("Name");
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
			JCheckBox chckbxPassword = new JCheckBox("Password");
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
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "cell 2 6");
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new MigLayout("", "[][85px]", "[23px]"));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				okButton.setBackground(Color.GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 0 0");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Abbrechen");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setBackground(Color.GRAY);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 1 0");
			}
		}
	}
	
	private void updatePassword(String password) {
		//TODO
	}
	private void updateUsername(String username) {
		//TODO
	}
	private void updateAll(String username, String password) {
		//TODO
	}
}