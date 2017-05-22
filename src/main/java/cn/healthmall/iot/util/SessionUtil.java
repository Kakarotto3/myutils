package cn.healthmall.iot.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.healthmall.iot.entity.User;

public abstract class SessionUtil {
	public static String MYUTILS_USER_LOGIN_ATTR = "_MYUTILS_USER_LOGIN_ATTR_";
	
	public static HttpSession getHttpSession(){
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		return request.getSession();
	}
	
	public static HttpSession getHttpSession(boolean create){
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		return request.getSession(create);
	}
	
	public static void saveUser(String username){
		User user = new User();
		user.setUsername(username);
		saveUser(user); 
	}

	public static void saveUser(User user){
		getHttpSession(true).setAttribute(MYUTILS_USER_LOGIN_ATTR, user);
	}
	
	public static User getUser(){
		return (User)getHttpSession(true).getAttribute(MYUTILS_USER_LOGIN_ATTR);
	}
	
	public static String getUsername(){
		return getUser() == null ? null : getUser().getUsername();
	}
}
