package net.jfabricationgames.bunkers_and_badasses.login;

import net.jfabricationgames.bunkers_and_badasses.server.ServerNameRequest;
import net.jfabricationgames.bunkers_and_badasses.server.UserUpdateMessage;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgdatabaselogin.client.JFGDatabaseLoginClientInterpreter;
import net.jfabricationgames.jfgdatabaselogin.message.JFGDatabaseLoginMessage;
import net.jfabricationgames.jfgserver.client.JFGClient;
import net.jfabricationgames.jfgserver.client.JFGClientMessage;
import net.jfabricationgames.jfgserver.interpreter.JFGClientInterpreter;

public class LoginClientInterpreter implements JFGClientInterpreter {
	
	private JFGDatabaseLoginClientInterpreter loginInterpreter;
	private LoginClientMain loginMain;
	private LoginLoadingDialog loginLoadingDialog;
	
	public LoginClientInterpreter(JFGDatabaseLoginClientInterpreter loginInterpreter, LoginClientMain loginMain) {
		this.loginInterpreter = loginInterpreter;
		this.loginMain = loginMain;
	}
	
	@Override
	public void interpreteClientMessage(JFGClientMessage message, JFGClient client) {
		if (message instanceof JFGDatabaseLoginMessage) {
			loginInterpreter.interpreteClientMessage(message, client);
		}
		else if (message instanceof ServerNameRequest) {
			ServerNameRequest answer = new ServerNameRequest(loginMain.getUsername());
			client.sendMessage(answer);
		}
		else if (message instanceof UserUpdateMessage) {
			UserUpdateMessage updateMessage = (UserUpdateMessage) message;
			//the server has received the name request and has answered the user list
			UserManager.setUsers(updateMessage.getUsers());
			loginLoadingDialog.startMainMenu();
			//store the user name in the UserManager
			UserManager.setUsername(loginMain.getUsername());
		}
	}
	
	public LoginLoadingDialog getLoginLoadingDialog() {
		return loginLoadingDialog;
	}
	public void setLoginLoadingDialog(LoginLoadingDialog loginLoadingDialog) {
		this.loginLoadingDialog = loginLoadingDialog;
	}
}