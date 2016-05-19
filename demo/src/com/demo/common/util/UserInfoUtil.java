package com.demo.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.common.constant.SystemKeys;
import com.demo.common.model.UserInfo;

public class UserInfoUtil {
	public static UserInfo getUserInfo(HttpServletRequest request){
		UserInfo userInfo = null;
		
		try {
			request = null==request?((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest():request;
			userInfo = (UserInfo) request.getSession().getAttribute(SystemKeys.LOGIN_USER_INFO_SESSION_NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userInfo;
	}
	
	public static UserInfo getUserInfo(){
		return getUserInfo(null);
	}
	
	public static String getUserId(){
		UserInfo userInfo = getUserInfo();
		if(null!=userInfo){
			return userInfo.getPersonCode();
		}else{
			return "";
		}
	}
}
