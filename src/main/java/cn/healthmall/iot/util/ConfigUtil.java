package cn.healthmall.iot.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import cn.healthmall.iot.dto.DataSourceInDTO;
import cn.healthmall.iot.dto.DataSourceOutDTO;
import cn.healthmall.iot.dto.ProjectInDTO;
import cn.healthmall.iot.dto.ProjectOutDTO;
import cn.healthmall.iot.dto.WorkspaceInDTO;
import cn.healthmall.iot.dto.WorkspaceOutDTO;

public abstract class ConfigUtil {
	public static final File SYSTEM_USER_HOME = FileUtils.getUserDirectory();
	public static final File CONFIG_HOME = FileUtils.getFile(SYSTEM_USER_HOME, "myutils");
	public static final File MYUTILS_CONFIG = FileUtils.getFile(CONFIG_HOME, "myutils.json");
	
	public static final String WORKSPACE_CONFIG_NAME ="workspace.json";
	public static final String PROJECT_CONFIG_NAME = "project.json";
	public static final String DATASOURCE_CONFIG_NAME = "dataSource.json";
	
	public static final Charset UNIFY_CHARSET = Charset.forName("UTF-8");
	
	static {
		if(!CONFIG_HOME.exists()){
			CONFIG_HOME.mkdir();
		}
	}
	
	public static void createUser(String user){
		File userHome =  FileUtils.getFile(CONFIG_HOME, user);
		if(!userHome.exists()){
			userHome.mkdir();
		}
	}
	
	public static void createWorkspace(String user, WorkspaceInDTO inDTO){
		createUser(user);
		File configFile = FileUtils.getFile(CONFIG_HOME, user, WORKSPACE_CONFIG_NAME);
		List<WorkspaceInDTO> inDTOList = JsonUtil.json2List(getJsonStr(configFile), WorkspaceInDTO.class);
		inDTOList.add(inDTO);
		persistToDisk(configFile, JsonUtil.object2Json(inDTOList));
	}
	
	public static void createProject(String user, ProjectInDTO inDTO){
		createUser(user);
		File configFile = FileUtils.getFile(CONFIG_HOME, user, PROJECT_CONFIG_NAME);
		List<ProjectInDTO> inDTOList = JsonUtil.json2List(getJsonStr(configFile), ProjectInDTO.class);
		inDTOList.add(inDTO);
		persistToDisk(configFile, JsonUtil.object2Json(inDTOList));
	}
	
	public static void createDataSource(String user, DataSourceInDTO inDTO){
		createUser(user);
		File configFile = FileUtils.getFile(CONFIG_HOME, user, DATASOURCE_CONFIG_NAME);
		List<DataSourceInDTO> inDTOList = JsonUtil.json2List(getJsonStr(configFile), DataSourceInDTO.class);
		inDTOList.add(inDTO);
		persistToDisk(configFile, JsonUtil.object2Json(inDTOList));
	}
	
	private static String getJsonStr(File configFile){
		String jsonStr = null;
		if(!configFile.exists()){
			try {
				configFile.createNewFile();
				jsonStr = "[]";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (InputStream input = FileUtils.openInputStream(configFile)){
			jsonStr = IOUtils.toString(input, UNIFY_CHARSET);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonStr;
	}
	
	private static void persistToDisk(File file, String data){
		try(OutputStream output = FileUtils.openOutputStream(file)){
			IOUtils.write(data, output, UNIFY_CHARSET);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static List<WorkspaceOutDTO> getWorkspaces(String user){
		List<WorkspaceOutDTO> result = null;
		File configFile = FileUtils.getFile(CONFIG_HOME, user, WORKSPACE_CONFIG_NAME);
		if(!configFile.exists()){
			return result;
		}
		return JsonUtil.json2List(getJsonStr(configFile), WorkspaceOutDTO.class);
	}
	
	public static List<ProjectOutDTO> getProjects(String user){
		List<ProjectOutDTO> result = null;
		File configFile = FileUtils.getFile(CONFIG_HOME, user, PROJECT_CONFIG_NAME);
		if(!configFile.exists()){
			return result;
		}
		return JsonUtil.json2List(getJsonStr(configFile), ProjectOutDTO.class);
	}
	
	public static List<DataSourceOutDTO> getDataSources(String user){
		List<DataSourceOutDTO> result = null;
		File configFile = FileUtils.getFile(CONFIG_HOME, user, DATASOURCE_CONFIG_NAME);
		if(!configFile.exists()){
			return result;
		}
		return JsonUtil.json2List(getJsonStr(configFile), DataSourceOutDTO.class);
	}
	
	public static void main(String[] args) {
		createUser("cjk");
		WorkspaceInDTO inDTO = new WorkspaceInDTO();
		inDTO.setName("工作空间2");
		inDTO.setTargetProject("F://test");
		createWorkspace("cjk", inDTO);
	}
}
