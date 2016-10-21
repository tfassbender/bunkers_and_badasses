package net.jfabricationgames.bunkers_and_badasses.chat;

import javax.swing.JPanel;

public class ChatPanel extends JPanel {
	
	private static final long serialVersionUID = 2030840939432548875L;
	
	private ChatClient client;
	
	public ChatPanel(ChatClient client) {
		this.client = client;
	}
}