package object;

public class UserObject {
	String username;
	String password;
	String hashuuid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHashuuid() {
		return hashuuid;
	}
	public void setHashuuid(String hashuuid) {
		this.hashuuid = hashuuid;
	}
	public UserObject(String username, String password, String hashuuid) {
		super();
		this.username = username;
		this.password = password;
		this.hashuuid = hashuuid;
	}
	public UserObject(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	
}
