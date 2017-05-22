package cn.healthmall.iot.dto;

import org.hibernate.validator.constraints.NotBlank;

public class DataSourceInDTO {
	@NotBlank
	private String name;
	@NotBlank
	private String targetConnection;
	@NotBlank
	private String username;
	@NotBlank
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
