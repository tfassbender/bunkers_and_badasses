package net.jfabricationgames.bunkers_and_badasses.chat;

import java.util.ArrayList;
import java.util.List;

import net.jfabricationgames.jfgserver.client.JFGClient;

public class ChatClient {
	
	private JFGClient client;
	
	private List<ChatPanel> panels;
	
	public ChatClient(JFGClient client) {
		this.client = client;
		panels = new ArrayList<ChatPanel>();
	}
	
	public void sendMessage(String message) {
		ChatMessage msg = new ChatMessage(message);
		client.sendMessage(msg);
		//show the message in all other client panels
		for (ChatPanel p : panels) {
			p.receiveMessage(message);
		}
	}
	public void receiveMessage(String message) {
		if (panels != null && !panels.isEmpty()) {
			for (int i = 0; i < panels.size(); i++) {
				panels.get(i).receiveMessage(message);
			}
		}
	}
	
	public List<ChatPanel> getPanels() {
		return panels;
	}
	public void addChatPanel(ChatPanel panel) {
		panels.add(panel);
	}
}