package net.jfabricationgames.bunkers_and_badasses.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class ChatDialog extends JFrame {
	
	private static final long serialVersionUID = 2773101390876188240L;
	
	private final JPanel contentPanel = new JPanel();
	
	public ChatDialog(ChatClient client, Component callingFrame) {
		setTitle("Chat - Bunkers and Badasses");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChatDialog.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(450, 300));
		setLocationRelativeTo(callingFrame);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			ChatPanel panel = new ChatPanel(client);
			contentPanel.add(panel, "cell 0 0,grow");
			client.addChatPanel(panel);
		}
	}
}