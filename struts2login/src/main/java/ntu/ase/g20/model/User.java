package ntu.ase.g20.model;

public class User {
	private String username;
	public User() {}
	public User(String name) {
		username = name;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
