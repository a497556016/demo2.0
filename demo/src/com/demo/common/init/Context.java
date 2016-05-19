package com.demo.common.init;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Context {
	
	private static Context context;
	
	private Context(){};
	
	public static Context getInstance(){
		if(null==context){
			context = new Context();
		}
		return context;
	}

	private ServletContext servletContext;
	
	public void init(ServletContext servletContext){
		this.servletContext = servletContext;
	}
	
	public Object getBean(String name){
		 // 通过servlet的ServletConfig得到spring容器对象
        ApplicationContext ctx = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);
		return ctx.getBean(name);
	}
}
