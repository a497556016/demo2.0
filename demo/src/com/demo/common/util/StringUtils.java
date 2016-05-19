package com.demo.common.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 将字符串前面补0以达到需求长度
	 * @param str
	 * @param len
	 * @return
	 */
	public static String appenderLength(String str,int len){
		if(isEmpty(str)){
			return null;
		}else if(str.length()>=len){
			return str.substring(str.length()-10, str.length());
		}else{
			int i = len - str.length();
			for(int m=0;m<i;m++){
				str = "0"+str;
			}
			return str;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(patternEmail("123123.com"));
	}

	public static boolean isEmpty(String str) {
		if(null==str||str.length()==0){
			return true;
		}
		return false;
	}

	public static String toStr(Object obj) {
		if(null!=obj){
			return obj.toString();
		}
		return "";
	}

	public static boolean contains(String str1, String str2) {
		if(null!=str1&&str1.indexOf(str2)>-1){
			return true;
		}
		return false;
	}
	
	/**
	 * 将double类型转换为字符串非科学计数法形式
	 * @param d
	 * @return
	 */
	public static String doubleToString(double d)  
    {  
        String i = DecimalFormat.getInstance().format(d);  
        String result = i.replaceAll(",", "");  
        return result;  
  
    }

	public static boolean patternEmail(String email) {
		if(isEmpty(email)){
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	} 
}
