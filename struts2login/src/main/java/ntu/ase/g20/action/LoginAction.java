package ntu.ase.g20.action;

import ntu.ase.g20.model.User;
import ntu.ase.g20.service.AuthenticationService;

public class LoginAction {
	AuthenticationService auth;
	private String username;
	private String password;
	
	public LoginAction() {
		auth = AuthenticationService.getIntance();
	}
	// handle requests that are mapped to LoginAction
	public String execute() {
		System.out.println("execute() executed!");
		System.out.println(username);
		System.out.println(password);
		if (username != null && password != null) {
			// delegate the authentication task to AuthenticationService
			User user = auth.authenticate(username, password);
			if (user != null) return "success";
		}
		return "login";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
