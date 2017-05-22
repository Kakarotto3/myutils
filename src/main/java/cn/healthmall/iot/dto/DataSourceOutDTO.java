package cn.healthmall.iot.dto;

public class DataSourceOutDTO {
	private String name; 
	private String targetConnection;
	private String username;
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTargetConnection() {
		return targetConnection;
	}
	public void setTargetConnection(String targetConnection) {
		this.targetConnection = targetConnection;
	}
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
}
