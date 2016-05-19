package com.demo.common.util.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.demo.common.util.StringUtils;

public class MailTemplateResolve {
	private static final Logger LOGGER = Logger.getLogger(MailTemplateResolve.class);
	
	private String dataSourcePath = "/WEB-INF/pages/emailTemplate";
	
	private String fieldStart = "${!";
	private String fieldEnd = "}";
	
	private static final MailTemplateResolve mailTemplateResolve = new MailTemplateResolve();
	
	private MailTemplateResolve(String dataSourcePath){
		this.dataSourcePath = dataSourcePath;
	}
	
	private MailTemplateResolve(){
		
	}
	
	public static MailTemplateResolve getInstance(){
		return mailTemplateResolve;
	}
	
	public String getResolvedText(String tempName,Map<String, Object> model){
		String tempText = getTempText(tempName);
		
		Iterator<String> it = model.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Object obj = model.get(key);
			String tempField = fieldStart+key+fieldEnd;
			if(tempText.contains(tempField)){
				tempText = tempText.replace(tempField, StringUtils.toStr(obj));
				continue;
			}
			Class clz = obj.getClass();
			String clzName = clz.getName();
			if(clzName.equals("java.util.HashMap")){
				Map<Object, Object> map = (Map<Object, Object>) obj;
				Iterator<Object> it1 = map.keySet().iterator();
				while(it1.hasNext()){
					Object key1 = it1.next();
					Object value1 = map.get(key1);
					tempField = fieldStart+key+"."+StringUtils.toStr(key1)+fieldEnd;
					if(tempText.contains(tempField)){
						tempText = tempText.replace(tempField, StringUtils.toStr(value1));
					}
				}
			}else{
				Field[] fields = clz.getDeclaredFields();
				for (Field field : fields) {
					String fieldName = field.getName();
					tempField = fieldStart + key + "." + fieldName + fieldEnd;
					if(tempText.contains(tempField)){
						field.setAccessible(true);
						Object value = null;
						try {
							value = field.get(obj);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							LOGGER.error(e.getMessage(), e);
						}
						tempText = tempText.replace(tempField, StringUtils.toStr(value));
					}
				}
			}
			
		}
		
		return tempText;
	}

	private String getTempText(String tempName){
		String filePath = getFilePath(tempName);
		StringBuffer tempText = new StringBuffer();
		try {
			File file = new File(filePath+"\\"+tempName);
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fileInputStream,"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String lineText = null;
			
			while((lineText = bufferedReader.readLine())!=null){
				LOGGER.info(lineText);
				tempText.append(lineText+"\n");
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return tempText.toString();
	}
	
	private String getFilePath(String tempName){
		String basePath = MailTemplateResolve.class.getResource("/").getPath();
		if(basePath.contains("WEB-INF")){
			int i = basePath.indexOf("WEB-INF");
			basePath = basePath.substring(0,i);
		}
		String path = basePath + dataSourcePath;
		return path;
	}

	public void setDataSourcePath(String dataSourcePath) {
		this.dataSourcePath = dataSourcePath;
	}
	
	public static void main(String[] args) {
		MailTemplateResolve mailTemplateResolve = new MailTemplateResolve("");
		Map<String, Object> map = new HashMap<>();
		BB bb = new BB();
		map.put("aa", "123456789");
		map.put("bb", bb);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("dd", "mokookokokokokokoko");
		map.put("cc", map1);
		String text = mailTemplateResolve.getResolvedText("test.html",map);
		System.out.println(text);
	}
	
	static class BB{
		private String cc = "qwerBoomBoomBoom";
		public String getCc(){
			return cc;
		}
	}
}
