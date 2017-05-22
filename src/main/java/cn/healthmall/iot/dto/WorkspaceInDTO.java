package cn.healthmall.iot.dto;

import org.hibernate.validator.constraints.NotBlank;

public class WorkspaceInDTO {
	@NotBlank
	private String name;
	@NotBlank
	private String targetProject;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTargetProject() {
		return targetProject;
	}
	public void setTargetProject(String targetProject) {
		this.targetProject = targetProject;
	}
}
