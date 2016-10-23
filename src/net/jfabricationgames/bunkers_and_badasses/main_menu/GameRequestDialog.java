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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.miginfocom.swing.MigLayout;

public class GameRequestDialog extends JDialog {
	
	private static final long serialVersionUID = -7224302886169954013L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JFGClient client;
	
	public GameRequestDialog(JFGClient client, MainMenuFrame callingFrame, User invitingUser, List<User> invitedUsers) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GameRequestDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Spiel Einladung");
		this.client = client;
		
		setBounds(100, 100, 450, 400);
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[250px][grow]", "[][10px][][10px][][grow][]"));
		{
			JLabel lblSpielEinladung = new JLabel("Spiel Einladung");
			lblSpielEinladung.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblSpielEinladung, "cell 0 0 2 1,alignx center");
		}
		{
			JLabel lblMessage = new JLabel(invitingUser.toString() + " hat dich zu einem Spiel eingeladen");
			lblMessage.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblMessage, "cell 0 2 2 1");
		}
		{
			JLabel lblTeilnehmer = new JLabel("Teilnehmer:");
			lblTeilnehmer.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblTeilnehmer, "cell 0 4");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 5,grow");
			{
				JPanel panel = new JPanel();
				panel.setBackground(Color.LIGHT_GRAY);
				scrollPane.setViewportView(panel);
				panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
				{
					JLabel lblInvitedUsers = new JLabel("");
					panel.add(lblInvitedUsers, "cell 0 0");
				}
			}
		}
		{
			ImagePanel panel = new ImagePanel(MainMenuFrame.getImageLoader().loadImage("login/mr_torgue_1.png"));
			panel.setCentered(true);
			panel.setAdaptSizeKeepProportion(true);
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 1 5,grow");
		}
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, "cell 0 6 2 1,alignx center");
			buttonPane.setBackground(Color.GRAY);
			buttonPane.setLayout(new MigLayout("", "[][]", "[]"));
			{
				JButton okButton = new JButton("Annehmen");
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
				JButton cancelButton = new JButton("Ablehnen");
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
	
	private void sendAnswer(boolean joining) {
		//TODO
	}
}