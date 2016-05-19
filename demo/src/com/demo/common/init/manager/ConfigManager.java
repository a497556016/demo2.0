package com.demo.common.init.manager;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

	private static final Map<String, Object> map = new HashMap<>();
	
	public static void setConfigValue(String key,Object value){
		map.put(key, value);
	}
	
	public static Object get(String key){
		Object obj = map.get(key);
		return obj;
	}
	
	public static String getString(String key){
		Object obj = get(key);
		if(null==obj){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	public static Boolean getBoolean(String key){
		Object obj = get(key);
		if(null==obj){
			return null;
		}else{
			String str = obj.toString();
			if(str.equals("1")||str.equals("true")){
				return true;
			}else if(str.equals("0")||str.equals("false")){
				return false;
			}else{
				return false;
			}
		}
	}
}
