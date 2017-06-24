package net.jfabricationgames.bunkers_and_badasses.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.main_menu.MainMenuFrame;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;
import java.awt.Toolkit;

public class LoginLoadingDialog extends JDialog {
	
	private static final long serialVersionUID = 3471266476453409610L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	public LoginLoadingDialog(JFGClient client) {
		setUndecorated(true);
		setTitle("Verbindungsaufbau - Bunkers And Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginLoadingDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		this.client = client;
		
		setBounds(100, 100, 550, 200);
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
			panel.setLayout(new MigLayout("", "[grow]", "[50px][grow][][grow]"));
			{
				ImagePanel panel_headline = new ImagePanel(LoginClientMain.getImageLoader().loadImage("jfg/headline.png"));
				panel_headline.setCentered(true);
				panel_headline.setAdaptSizeKeepProportion(true);
				panel_headline.setBackground(Color.GRAY);
				panel.add(panel_headline, "cell 0 0,grow");
			}
			{
				JLabel lblVerbindungZumServer = new JLabel("Verbindung zum Server wird aufgebaut...");
				lblVerbindungZumServer.setFont(new Font("Tahoma", Font.BOLD, 16));
				panel.add(lblVerbindungZumServer, "cell 0 2,alignx center");
			}
		}
	}
	
	public void startMainMenu() {
		new MainMenuFrame(client).setVisible(true);
		dispose();
	}
}