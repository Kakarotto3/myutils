package cn.healthmall.iot.dto;

public class GetTemplateInDTO {
	private String targetTable;
	private String targetDomainObject;
	
	private String targetPackage;
	private String targetProjectSuffix;
	
	private String targetProject;
	
	private String targetConnection;
	private String username;
	private String password;
	
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}
	public String getTargetDomainObject() {
		return targetDomainObject;
	}
	public void setTargetDomainObject(String targetDomainObject) {
		this.targetDomainObject = targetDomainObject;
	}
	public String getTargetPackage() {
		return targetPackage;
	}
	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}
	public String getTargetProjectSuffix() {
		return targetProjectSuffix;
	}
	public void setTargetProjectSuffix(String targetProjectSuffix) {
		this.targetProjectSuffix = targetProjectSuffix;
	}
	public String getTargetProject() {
		return targetProject;
	}
	public void setTargetProject(String targetProject) {
		this.targetProject = targetProject;
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
