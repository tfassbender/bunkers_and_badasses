package net.jfabricationgames.bunkers_and_badasses.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.jfabricationgames.bunkers_and_badasses.server.ServerLogoutMessage;
import net.miginfocom.swing.MigLayout;
import java.awt.Toolkit;

public class ServerLogoutDialog extends JDialog {
	
	private static final long serialVersionUID = -6417152737213638861L;
	
	private final JPanel contentPanel = new JPanel();
	
	public ServerLogoutDialog(ServerLogoutMessage message) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServerLogoutDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setModal(true);
		setTitle("Bunkers and Badasses - Error");
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[25px][grow][25px]", "[10px][][10px][][grow][]"));
			{
				JLabel lblVerbindungsFehler = new JLabel("Verbindungs Fehler");
				lblVerbindungsFehler.setFont(new Font("Tahoma", Font.BOLD, 20));
				panel.add(lblVerbindungsFehler, "cell 0 1 3 1,alignx center");
			}
			{
				JLabel lblEsGibtSchwierigkeiten = new JLabel("<html>\r\nEs gibt schwierigkeiten mit der Verbindung zum Server.<br>\r\nDer Server hat dich ausgeloggt.<br>\r\n<br>\r\nServer Nachricht:\r\n</html>");
				lblEsGibtSchwierigkeiten.setFont(new Font("Tahoma", Font.PLAIN, 12));
				panel.add(lblEsGibtSchwierigkeiten, "cell 1 3,alignx center");
			}
			{
				JTextArea txtrServerMessage = new JTextArea(message.getMessage());
				txtrServerMessage.setEditable(false);
				txtrServerMessage.setBackground(Color.LIGHT_GRAY);
				panel.add(txtrServerMessage, "cell 1 4,grow");
			}
			{
				JButton btnBeenden = new JButton("Beenden");
				btnBeenden.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(1);
					}
				});
				btnBeenden.setBackground(Color.GRAY);
				panel.add(btnBeenden, "cell 1 5,alignx center");
			}
		}
	}	
}