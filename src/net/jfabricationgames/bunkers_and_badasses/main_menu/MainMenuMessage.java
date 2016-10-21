package net.jfabricationgames.bunkers_and_badasses.main_menu;

import java.util.List;

import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.client.JFGServerMessage;

public class MainMenuMessage implements JFGClientMessage, JFGServerMessage {
	
	public enum MessageType {
		PASSWORD_UPDATE,
		USERNAME_UPDATE,
		PASSWORD_USERNAME_UPDATE,
		GAME_CREATION_REQUEST,
		GAME_CREATION_ANSWER,
		DYNAMIC_CONTENT_REQUEST,
		DYNAMIC_CONTENT_ANSWER,
		USERLIST_UPDATE;
	}
	
	private static final long serialVersionUID = 3573152800545735135L;
	
	private MessageType messageType;
	private String username;
	private String lastUsername;
	private String password;
	private Object player;
	private Object toPlayer;
	private List invitedPlayers;
	private boolean joining;
	private String dynamicContentRequest;
	private String dynamicContentAnswer;
	private List usersOnline;
	private List usersInGame;
	
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
	
	public Object getPlayer() {
		return player;
	}
	public void setPlayer(Object player) {
		this.player = player;
	}
	
	public Object getToPlayer() {
		return toPlayer;
	}

	public void setToPlayer(Object toPlayer) {
		this.toPlayer = toPlayer;
	}
	
	public List getInvitedPlayers() {
		return invitedPlayers;
	}
	public void setInvitedPlayers(List invitedPlayers) {
		this.invitedPlayers = invitedPlayers;
	}
	
	public boolean isJoining() {
		return joining;
	}
	public void setJoining(boolean joining) {
		this.joining = joining;
	}
	
	public String getDynamicContentRequest() {
		return dynamicContentRequest;
	}
	public void setDynamicContentRequest(String dynamicContentRequest) {
		this.dynamicContentRequest = dynamicContentRequest;
	}
	
	public String getDynamicContentAnswer() {
		return dynamicContentAnswer;
	}
	public void setDynamicContentAnswer(String dynamicContentAnswer) {
		this.dynamicContentAnswer = dynamicContentAnswer;
	}
	
	public List getUsersOnline() {
		return usersOnline;
	}
	public void setUsersOnline(List usersOnline) {
		this.usersOnline = usersOnline;
	}
	
	public List getUsersInGame() {
		return usersInGame;
	}
	public void setUsersInGame(List usersInGame) {
		this.usersInGame = usersInGame;
	}
}