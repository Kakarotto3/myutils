package cn.healthmall.iot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.healthmall.iot.dto.DataSourceInDTO;
import cn.healthmall.iot.dto.GetTemplateInDTO;
import cn.healthmall.iot.dto.ProjectInDTO;
import cn.healthmall.iot.dto.WorkspaceInDTO;
import cn.healthmall.iot.service.GeneratorService;
import cn.healthmall.iot.util.JsonUtil;
import cn.healthmall.iot.util.RequestUtil;
import cn.healthmall.iot.util.SessionUtil;

@RestController
@RequestMapping("/generator")
public class GeneratorController {
	private static Logger logger = LoggerFactory.getLogger(GeneratorController.class);
	@Autowired
	private GeneratorService generatorService;
	
	@RequestMapping("/login")
	public Object login(String username){
		generatorService.login(username);
		SessionUtil.saveUser(username);
		return null;
	}
	
	@RequestMapping("/generator")
	public Object generator(String template) throws Exception{
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		
		ConfigurationParser cp = new ConfigurationParser(warnings);
		logger.info("============XML start============");
		logger.info(template);
		logger.info("============XML  end============");
		
		InputStream configStream = IOUtils.toInputStream(template, Charset.forName("UTF-8"));
		Configuration config = cp.parseConfiguration(configStream);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		
		return null;
	}
	
	@RequestMapping("/getTemplate")
	public Object getTemplate(GetTemplateInDTO inDTO) throws IOException{
		return generatorService.getTemplate(inDTO);
	}
	
	@RequestMapping("/getWorkspace")
	public Object getWorkspace(){
		return generatorService.getWorkspace(SessionUtil.getUsername());
	}
	
	@RequestMapping("/getProjects")
	public Object getProjects(){
		return generatorService.getProjects(SessionUtil.getUsername());
	}
	
	@RequestMapping("/getDataSource")
	public Object getDataSource(){
		return generatorService.getDataSource(SessionUtil.getUsername());
	}
	
	@RequestMapping("/addWorkspace")
	public Object addWorkspace(@Validated WorkspaceInDTO inDTO){
		generatorService.addWorkspace(SessionUtil.getUsername(), inDTO);
		return null;
	}
	
	@RequestMapping("/addProject")
	public Object addProject(@Validated ProjectInDTO inDTO){
		generatorService.addProject(SessionUtil.getUsername(), inDTO);
		return null;
	}
	
	@RequestMapping("/addDataSource")
	public Object addDataSource(@Validated DataSourceInDTO inDTO){
		generatorService.addDataSource(SessionUtil.getUsername(), inDTO);
		return null;
	}
}
