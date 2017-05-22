package cn.healthmall.iot.dto;

import org.hibernate.validator.constraints.NotBlank;

public class ProjectInDTO {
	@NotBlank
	private String name;
	@NotBlank
	private String targetPackage;
	@NotBlank
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
