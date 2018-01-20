package com.pro.common.Util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


public class UserUtil {

	
	/**
	 * 获取当前登录用户名
	 * @return
	 */
	public static String getLoginUserName() {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		return username;
	}
}
