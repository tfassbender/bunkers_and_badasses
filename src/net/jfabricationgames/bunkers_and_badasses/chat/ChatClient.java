package net.jfabricationgames.bunkers_and_badasses.chat;

import net.jfabricationgames.jfgserver.client.JFGClient;

public class ChatClient {
	
	private JFGClient client;
	
	private ChatPanel panel;
	
	public ChatClient(JFGClient client) {
		this.client = client;
	}
	
	public void sendMessage(String message) {
		ChatMessage msg = new ChatMessage(message);
		client.sendMessage(msg);
	}
	public void receiveMessage(String message) {
		if (panel != null) {
			panel.receiveMessage(message);
		}
	}
	
	public ChatPanel getPanel() {
		return panel;
	}
	public void setChatPanel(ChatPanel panel) {
		this.panel = panel;
	}
}