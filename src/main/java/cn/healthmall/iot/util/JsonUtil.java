package cn.healthmall.iot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.healthmall.iot.dto.ProjectOutDTO;

public abstract class JsonUtil {
	public static ObjectMapper objectMapper = new ObjectMapper();
	public static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static <T> T json2Object(String jsonStr, Class<T> clazz){
		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (IOException e) {
			logger.error("{}解析失败", jsonStr, e);
			throw new RuntimeException();
		}
	}
	
	public static <T> List<T> json2List(String jsonStr, Class<T> clazz){
		try {
			if(StringUtils.isBlank(jsonStr) || "[]".equals(jsonStr)){
				return new ArrayList<T>();
			}
			JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);   
			return objectMapper.readValue(jsonStr, javaType);
		} catch (IOException e) {
			logger.error("{}解析失败", jsonStr, e);
			throw new RuntimeException();
		}
	}
	
	public static String object2Json(Object data){
		try {
			return objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			logger.error("{}解析失败", ToStringBuilder.reflectionToString(data), e);
			throw new RuntimeException();
		}
	}
	
	public static void main(String[] args) {
//		String strArr = "[{\"name\":\"设备接入系统\",\"targetPackage\":\"cn.healthmall.iot.deviceaccess\",\"targetProjectSuffix\":\"iot-device-access\"},{\"name\":\"设备接入系统-管理\",\"targetPackage\":\"cn.healthmall.gym.iot\",\"targetProjectSuffix\":\"iot-device-access-mgmt\"},{\"name\":\"设备管理系统-api\",\"targetPackage\":\"cn.healthmall.iot.device\",\"targetProjectSuffix\":\"iot-device-api\"},{\"name\":\"设备管理系统-mgmt\",\"targetPackage\":\"cn.healthmall.iot.device\",\"targetProjectSuffix\":\"iot-device-mgmt\"}]";
//		String str = "{\"name\":\"设备接入系统\",\"targetPackage\":\"cn.healthmall.iot.deviceaccess\",\"targetProjectSuffix\":\"iot-device-access\"}";
//		List<ProjectOutDTO> list = JsonUtil.json2List(strArr, ProjectOutDTO.class);
//		System.out.println(object2Json(list));
		System.out.println(json2List("", ProjectOutDTO.class));
	}
}
