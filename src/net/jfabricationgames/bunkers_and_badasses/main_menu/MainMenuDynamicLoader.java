package net.jfabricationgames.bunkers_and_badasses.main_menu;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.jfabricationgames.jfgserver.client.JFGClient;

public class MainMenuDynamicLoader {
	
	private JFGClient client;
	
	private MainMenuMessage dynamicContent;
	
	public MainMenuDynamicLoader(JFGClient client) {
		this.client = client;
	}
	
	public void addDynamicContent(JPanel component) {
		Thread dynamicComponentThread = new Thread(new Runnable() {
			@Override
			public void run() {
				MainMenuMessage dynamicContentRequest = new MainMenuMessage();
				dynamicContentRequest.setMessageType(MainMenuMessage.MessageType.DYNAMIC_CONTENT_REQUEST);
				client.sendMessage(dynamicContentRequest);
				try {
					while (dynamicContent == null) {
						Thread.sleep(100);
					}
					addMessageContent(dynamicContent, component);
					dynamicContent = null;
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		});
		dynamicComponentThread.start();
	}
	
	private void addMessageContent(MainMenuMessage content, JPanel component) {
		component.removeAll();
		JLabel lblDynamiccontent = new JLabel(content.getDynamicContentAnswer());
		component.add(lblDynamiccontent, "cell 0 0");
	}
	
	public void receiveDynamicContent(MainMenuMessage dynamicContent) {
		this.dynamicContent = dynamicContent;
	}
}