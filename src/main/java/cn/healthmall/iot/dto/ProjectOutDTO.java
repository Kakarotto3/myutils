package cn.healthmall.iot.dto;

public class ProjectOutDTO {
	private String name;
	private String targetPackage;
	private String targetProjectSuffix;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
