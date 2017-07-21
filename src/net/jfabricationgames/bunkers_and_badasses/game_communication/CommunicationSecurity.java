package net.jfabricationgames.bunkers_and_badasses.game_communication;

import java.util.Map;

import net.jfabricationgames.bunkers_and_badasses.game.BunkersAndBadassesClient;

public class CommunicationSecurity {
	
	private Thread ackTimerThread;
	
	private BunkersAndBadassesClient client;
	
	private Map<Integer, BunkersAndBadassesMessage> securedMessages;
	private Map<Integer, Integer> messageTimers;
	
	public CommunicationSecurity(BunkersAndBadassesClient client) {
		this.client = client;
		ackTimerThread = createAckTimerThread();
		ackTimerThread.start();
	}
	
	public void secureMessage(BunkersAndBadassesMessage message) {
		securedMessages.put(message.getId(), message);
		messageTimers.put(message.getId(), 0);
	}
	public void receiveAcknoledgeMessage(AcknowledgeMessage ackMessage) {
		int ackId = ackMessage.getId();
		//synchronize using this object to be sure to not get a concurrent modification in the timer thread
		synchronized (this) {
			messageTimers.remove(ackId);
			securedMessages.remove(ackId);
		}
	}
	
	private void resendMessage(int messageId) {
		client.resetOutput();
		client.sendMessage(securedMessages.get(messageId));
	}
	
	private Thread createAckTimerThread() {
		Thread ackTimerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						synchronized (CommunicationSecurity.this) {
							for (int messageId : messageTimers.keySet()) {
								int timer = messageTimers.get(messageId);
								if (timer < 5) {
									messageTimers.put(messageId, timer+1);								
								}
								else {
									//re-send the message and reset the timer 
									messageTimers.put(messageId, 0);
									resendMessage(messageId);
								}
							}							
						}
						Thread.sleep(1000);
					}
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		});
		return ackTimerThread;
	}
}