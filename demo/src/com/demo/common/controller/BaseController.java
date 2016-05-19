package com.demo.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.demo.common.util.ArraysUtil;
import com.demo.common.util.DateUtils;
import com.google.gson.Gson;

@Controller
public class BaseController {
	private static final Logger LOGGER = Logger.getLogger(BaseController.class);
	
	protected static Gson gson = new Gson();
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@ModelAttribute
	private void baseRe(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	protected void print(Object obj){
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
		String json = gson.toJson(obj);
		writer.write(json);
	}
	
	protected <T> T packageParam(Class<T> clz){
		T t = null;
		try {
			t = (T) clz.newInstance();
			Field[] fields = clz.getDeclaredFields();
			Field[] fields1 = clz.getSuperclass().getDeclaredFields();
			List<Field> list = ArraysUtil.asList(fields);
			List<Field> list1 = ArraysUtil.asList(fields1);
			list.addAll(list1);
			for (Field field : list) {
				field.setAccessible(true);
				String param = this.request.getParameter(field.getName());
				Type type = field.getGenericType();
				Object value = null;
				String typeStr = type.toString();
				if(null!=param){
					try {
						if(typeStr.equals(int.class.toString())||
								typeStr.equals(Integer.class.toString())){
							value = Integer.parseInt(param);
						}else if(typeStr.equals(double.class.toString())||
								typeStr.equals(Double.class.toString())){
							value = Double.parseDouble(param);
						}else if(typeStr.equals(float.class.toString())||
								typeStr.equals(Float.class.toString())){
							value = Float.parseFloat(param);
						}else if(typeStr.equals(boolean.class.toString())||
								typeStr.equals(Boolean.class.toString())){
							value = Boolean.parseBoolean(param);
						}else if(typeStr.equals(long.class.toString())||
								typeStr.equals(Long.class.toString())){
							value = Long.parseLong(param);
						}else if(typeStr.equals(String.class.toString())){
							value = param;
						}else if(typeStr.equals(Date.class.toString())){
							value = DateUtils.parseDate(param, "yyyy-MM-dd HH:mm:ss");
						}else{
							LOGGER.error("该字符串"+param+"的数据类型无法转换成目标类型："+typeStr);
						}
					} catch (NumberFormatException e) {
						LOGGER.error(field+":参数\""+param+"\"无法转换类型："+typeStr);
					}
				}
				if(null!=value){
					try {
						field.set(t, value);
					} catch (IllegalArgumentException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return t;
	}
	
	protected Map<String, Object> packageParam(){
		Map<String, Object> map = new HashMap<>();
		
		Enumeration<String> enumeration = this.request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String name = enumeration.nextElement();
			String value = this.request.getParameter(name);
			map.put(name, value);
		}
		return map;
	}

	/**
	 * 将一个页面的请求参数传递到另一个页面
	 * @param request
	 */
	protected void setAttributes() {
		Enumeration<String> enumeration = this.request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String name = enumeration.nextElement();
			String value = this.request.getParameter(name);
			this.request.setAttribute(name, value);
		}
	}
}
