package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameCreationDialog extends JDialog {
	
	private static final long serialVersionUID = 6165525170674954371L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	public GameCreationDialog(JFGClient client, MainMenuFrame callingFrame) {
		setResizable(false);
		setTitle("Bunkers and Badasses - Spiel Erstellen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameCreationDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		this.client = client;
		
		setBounds(100, 100, 325, 400);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow][200px,grow]", "[][10px][][grow][25px:n:25px][]"));
		{
			JLabel lblSpielErstellen = new JLabel("Spiel Erstellen");
			lblSpielErstellen.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielErstellen, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblSpielerAuswhlen = new JLabel("Spieler Ausw\u00E4hlen:");
			lblSpielerAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblSpielerAuswhlen, "cell 0 2");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(Color.GRAY);
			contentPanel.add(scrollPane, "cell 0 3,grow");
			{
				JList<User> list = new JList<User>();
				list.setBackground(Color.LIGHT_GRAY);
				scrollPane.setViewportView(list);
			}
		}
		{
			ImagePanel panel = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("main_menu/marcus_1.png"));
			panel.setCentered(true);
			panel.setAdaptSizeKeepProportion(true);
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 1 3,grow");
		}
		{
			JLabel lblError = new JLabel("");
			lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblError, "cell 0 4,alignx center");
		}
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "cell 0 5 2 1,alignx center");
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new MigLayout("", "[][]", "[]"));
			{
				JButton okButton = new JButton("Erstellen");
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
	
	private void sendGameRequest(List players) {
		//TODO
	}
	public void receiveClientAnswer(User user, boolean joining) {
		//TODO
	}
}