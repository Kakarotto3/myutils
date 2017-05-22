//LifeHallGenerator.java
//Create date 2016年8月29日
package cn.healthmall.iot.mybatisgenrator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * LifeHallGenerator
 * @author leo.lei
 *
 */
public class LifeHallGenerator {  

	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		
		InputStream configStream = LifeHallGenerator.class.getClassLoader()
				/**修改为 项目名/generatorConfig.xml*/
				.getResourceAsStream("iot-device-mgmt/generatorConfig.xml");
		
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configStream);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
	
}
