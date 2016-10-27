package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class MainMenuMessage implements JFGClientMessage, JFGServerMessage {
	
	public enum MessageType {
		PASSWORD_UPDATE,
		USERNAME_UPDATE,
		PASSWORD_USERNAME_UPDATE,
		GAME_CREATION_REQUEST,
		GAME_CREATION_ANSWER,
		GAME_CREATEION_ABORT,
		DYNAMIC_CONTENT_REQUEST,
		DYNAMIC_CONTENT_ANSWER;
	}
	
	private static final long serialVersionUID = 3573152800545735135L;
	
	private MessageType messageType;
	private String username;
	private String lastUsername;
	private String password;
	private User player;
	private User toPlayer;
	private List<User> invitedPlayers;
	private boolean joining;
	//private String dynamicContentRequest;
	private String dynamicContentAnswer;
	private String abortCause;
	private List<User> users;
	
	public MainMenuMessage() {
		
	}

	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLastUsername() {
		return lastUsername;
	}
	public void setLastUsername(String lastUsername) {
		this.lastUsername = lastUsername;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User getPlayer() {
		return player;
	}
	public void setPlayer(User player) {
		this.player = player;
	}
	
	public User getToPlayer() {
		return toPlayer;
	}

	public void setToPlayer(User toPlayer) {
		this.toPlayer = toPlayer;
	}
	
	public List<User> getInvitedPlayers() {
		return invitedPlayers;
	}
	public void setInvitedPlayers(List<User> invitedPlayers) {
		this.invitedPlayers = invitedPlayers;
	}
	
	public boolean isJoining() {
		return joining;
	}
	public void setJoining(boolean joining) {
		this.joining = joining;
	}
	
	/*public String getDynamicContentRequest() {
		return dynamicContentRequest;
	}
	public void setDynamicContentRequest(String dynamicContentRequest) {
		this.dynamicContentRequest = dynamicContentRequest;
	}*/
	
	public String getDynamicContentAnswer() {
		return dynamicContentAnswer;
	}
	public void setDynamicContentAnswer(String dynamicContentAnswer) {
		this.dynamicContentAnswer = dynamicContentAnswer;
	}
	
	public String getAbortCause() {
		return abortCause;
	}
	public void setAbortCause(String abortCause) {
		this.abortCause = abortCause;
	}
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}