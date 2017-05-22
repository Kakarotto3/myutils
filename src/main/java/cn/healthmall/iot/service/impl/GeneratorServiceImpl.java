package cn.healthmall.iot.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import cn.healthmall.iot.dao.GeneratorDao;
import cn.healthmall.iot.dto.DataSourceInDTO;
import cn.healthmall.iot.dto.DataSourceOutDTO;
import cn.healthmall.iot.dto.GetTemplateInDTO;
import cn.healthmall.iot.dto.ProjectInDTO;
import cn.healthmall.iot.dto.ProjectOutDTO;
import cn.healthmall.iot.dto.WorkspaceInDTO;
import cn.healthmall.iot.dto.WorkspaceOutDTO;
import cn.healthmall.iot.entity.ColumnOverride;
import cn.healthmall.iot.service.GeneratorService;
import cn.healthmall.iot.util.ConfigUtil;

@Service
public class GeneratorServiceImpl implements GeneratorService{
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String SELECT_ALL = "select * from {{targetTable}} limit 0";
	
	public static final String PLACEHOLDER_TARGET_PACKAGE = "{{targetPackage}}";
	public static final String PLACEHOLDER_TARGET_PROJECT = "{{targetProject}}";
	public static final String PLACEHOLDER_TARGET_PROJECT_SUFFIX = "{{targetProjectSuffix}}";
	public static final String PLACEHOLDER_TARGET_CONNECTION = "{{targetConnection}}";
	public static final String PLACEHOLDER_TARGET_CONNECTION_USER = "{{targetConnectionUser}}";
	public static final String PLACEHOLDER_TARGET_CONNECTION_PASSWORD = "{{targetConnectionPassword}}";
	public static final String PLACEHOLDER_TARGET_TABLE = "{{targetTable}}";
	public static final String PLACEHOLDER_TARGET_DOMAIN_OBJECT = "{{targetDomainObject}}";
	public static final String PLACEHOLDER_COLUMNOVERRIDES = "{{columnOverrides}}";
	//三缩进
	public static final String COLUMNOVERRIDE_TEMPLATE = "			<columnOverride column=\"{{column}}\" property=\"{{property}}\"></columnOverride>";
	@Autowired
	private GeneratorDao generatorDao;
	
	
	private String columnToProperty(String column){
		String[] segs = StringUtils.split(column, "_");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<segs.length; i++){
			sb.append(i == 0? segs[i]:StringUtils.capitalize(segs[i]));
		}
		return sb.toString();
	}
	
	protected List<ColumnOverride> getColumnOverride(SqlRowSetMetaData metaData) {
		List<ColumnOverride> columnOverrides = new ArrayList<>();
		String[] columnArr = metaData.getColumnNames();
		for(int i=0; i<columnArr.length; i++){
			ColumnOverride columnOverride = new ColumnOverride();
			columnOverride.setColumn(columnArr[i]);
			columnOverride.setProperty(columnToProperty(columnArr[i]));
			columnOverrides.add(columnOverride);
		}
		return columnOverrides;
	}
	
	@Override
	public void login(String username) {
		ConfigUtil.createUser(username);
	}
	
	public String renderingTemplate(Resource resource, GetTemplateInDTO inDTO, List<ColumnOverride> columnOverrides) throws IOException{
		InputStream input = resource.getInputStream();
		String templateStr = IOUtils.toString(input, Charset.forName("UTF-8"));
		
		StringBuilder columnOverridePart = new StringBuilder();
		for(ColumnOverride item:columnOverrides){
			columnOverridePart.append(COLUMNOVERRIDE_TEMPLATE.replace("{{column}}", item.getColumn())
					.replace("{{property}}", item.getProperty()))
					.append("\n"); 
		}
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_PACKAGE, inDTO.getTargetPackage());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_PROJECT, inDTO.getTargetProject());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_PROJECT_SUFFIX, inDTO.getTargetProjectSuffix());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_CONNECTION, inDTO.getTargetConnection());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_CONNECTION_USER, inDTO.getUsername());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_CONNECTION_PASSWORD, inDTO.getPassword());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_TABLE, inDTO.getTargetTable());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_TARGET_DOMAIN_OBJECT, inDTO.getTargetDomainObject());
		templateStr = StringUtils.replace(templateStr, PLACEHOLDER_COLUMNOVERRIDES, columnOverridePart.toString());
		
		return templateStr;
	}
	
	@Override
	public String getTemplate(GetTemplateInDTO inDTO) throws IOException {
		DataSource ds = DataSourceBuilder.create()
				.driverClassName(DRIVER_NAME)
				.url(inDTO.getTargetConnection())
				.username(inDTO.getUsername())
				.password(inDTO.getPassword())
				.build();
		
		JdbcTemplate template = new JdbcTemplate(ds);
		SqlRowSet rowSet = template.queryForRowSet(SELECT_ALL.replace(PLACEHOLDER_TARGET_TABLE, inDTO.getTargetTable()));
		SqlRowSetMetaData metaData = rowSet.getMetaData();
		List<ColumnOverride> columnOverrides = getColumnOverride(metaData);
		ClassPathResource resource = new ClassPathResource("generator-template.xml");
		return renderingTemplate(resource, inDTO, columnOverrides);
	}

	@Override
	public List<WorkspaceOutDTO> getWorkspace(String user) {
		return ConfigUtil.getWorkspaces(user);
	}

	@Override
	public List<ProjectOutDTO> getProjects(String user) {
		return ConfigUtil.getProjects(user);
	}

	@Override
	public List<DataSourceOutDTO> getDataSource(String user) {
		return ConfigUtil.getDataSources(user);
	}

	@Override
	public void addWorkspace(String user, WorkspaceInDTO inDTO) {
		ConfigUtil.createWorkspace(user, inDTO);
	}

	@Override
	public void addProject(String user, ProjectInDTO inDTO) {
		ConfigUtil.createProject(user, inDTO);		
	}

	@Override
	public void addDataSource(String user, DataSourceInDTO inDTO) {
		ConfigUtil.createDataSource(user, inDTO);
	}
	
}
