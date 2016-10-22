package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.jfgserver.client.JFGClient;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import java.awt.Toolkit;

public class GameCreationDialog extends JDialog {
	
	private static final long serialVersionUID = 6165525170674954371L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	public GameCreationDialog(JFGClient client) {
		setTitle("Bunkers and Badasses - Spiel Erstellen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameCreationDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		this.client = client;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[]", "[]"));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(Color.GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBackground(Color.GRAY);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void sendGameRequest(List players) {
		//TODO
	}
	public void receiveClientAnswer(Object user, boolean joining) {
		//TODO
	}
}