package net.jfabricationgames.bunkers_and_badasses.chat;

import javax.swing.JPanel;

import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class ChatPanel extends JPanel {
	
	private static final long serialVersionUID = 2030840939432548875L;
	
	private ChatClient client;
	private JTextField textField;
	private JTextArea txtrChatpanel;
	private JButton btnSenden;
	
	public ChatPanel(ChatClient client) {
		setBackground(Color.GRAY);
		this.client = client;
		setLayout(new MigLayout("", "[grow][]", "[grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0 2 1,grow");
		
		txtrChatpanel = new JTextArea();
		txtrChatpanel.setEditable(false);
		txtrChatpanel.setWrapStyleWord(true);
		txtrChatpanel.setLineWrap(true);
		txtrChatpanel.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(txtrChatpanel);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		textField.setBackground(Color.LIGHT_GRAY);
		add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		
		btnSenden = new JButton("Senden");
		btnSenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});
		btnSenden.setBackground(Color.GRAY);
		add(btnSenden, "cell 1 1");
	}
	
	private void sendMessage() {
		String message = UserManager.getUsername() + ": " + textField.getText();
		if (textField.getText() != null && !textField.getText().equals("")) {
			client.sendMessage(message);
		}
		txtrChatpanel.append(message + "\n");
		textField.setText("");
	}
	
	public void receiveMessage(String message) {
		txtrChatpanel.append(message + "\n");
	}
}