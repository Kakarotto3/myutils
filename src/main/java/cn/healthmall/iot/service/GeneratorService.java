package cn.healthmall.iot.service;

import java.io.IOException;
import java.util.List;

import cn.healthmall.iot.dto.DataSourceInDTO;
import cn.healthmall.iot.dto.DataSourceOutDTO;
import cn.healthmall.iot.dto.GetTemplateInDTO;
import cn.healthmall.iot.dto.ProjectInDTO;
import cn.healthmall.iot.dto.ProjectOutDTO;
import cn.healthmall.iot.dto.WorkspaceInDTO;
import cn.healthmall.iot.dto.WorkspaceOutDTO;

public interface GeneratorService {
	String getTemplate(GetTemplateInDTO inDTO) throws IOException;
	List<WorkspaceOutDTO> getWorkspace(String user);
	List<ProjectOutDTO> getProjects(String user);
	List<DataSourceOutDTO> getDataSource(String user);
	void login(String username);
	
	void addWorkspace(String user, WorkspaceInDTO inDTO);
	void addProject(String user, ProjectInDTO inDTO);
	void addDataSource(String user, DataSourceInDTO inDTO);
}
