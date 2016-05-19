package com.demo.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.demo.common.constant.SystemKeys;
import com.demo.common.model.Resource;
import com.demo.common.model.UserInfo;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
	private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);
	
	private String expectURL;

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpServletResponse response2 = (HttpServletResponse) response;
		List<String> expectUrls = new ArrayList<>();
		if(null!=this.expectURL){
			String[] urlStrs = this.expectURL.split("\\|");
			expectUrls.addAll(Arrays.asList(urlStrs));
		}
		
		String contextPath = request2.getContextPath();
		String servletPath = request2.getServletPath();
		String requestUrl = request2.getRequestURI().toString();
		LOGGER.info("请求的URL为："+requestUrl);
		if(expectUrls.contains(servletPath)){
			LOGGER.info("不需要验证用户登录权限。");
			chain.doFilter(request, response);
			return;
		}
		
		//验证用户登录以及权限
		Object obj = request2.getSession().getAttribute(SystemKeys.LOGIN_USER_INFO_SESSION_NAME);
		if(null==obj){
			String loginPage = contextPath;
			LOGGER.debug("用户没有登录，转到登录页面："+loginPage);
			if(requestUrl.contains("m_toMIndex.action")){
				loginPage += "/mLogin.jsp";
			}
			response2.sendRedirect(loginPage);
			return;
		}else{
			if(requestUrl.contains("/m_")){
				chain.doFilter(request, response);
				return;
			}
			UserInfo userInfo = (UserInfo) obj;
			LOGGER.debug("登录验证成功！");
			LOGGER.debug("判断用户是否拥有该链接权限。。。");
			List<Resource> resources = userInfo.getResources();
			List<String> urls = new ArrayList<>();
			for (Resource resource : resources) {
				String code = resource.getCode();
				String type = resource.getType();
				String url = "/web/";
				if("1".equals(type)){
					url += code+".action";
					urls.add(url);
				}
			}
			boolean isRight = true;
			if(servletPath.contains("/web")&&!urls.contains(servletPath)){
				isRight = false;
			}
			if(servletPath.contains("/auth")){
				String path = "/web"+servletPath.substring(0,servletPath.lastIndexOf("/auth"))+".action";
				if(!urls.contains(path)){
					isRight = false;
				}
			}
			if(!isRight&&!servletPath.equals("/web/main.action")){
				LOGGER.debug("权限验证失败！"+userInfo.getLastName()+"没有"+servletPath+"的权限！");
				return;
			}
		}
		LOGGER.debug("权限验证成功！");
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		this.expectURL = fConfig.getInitParameter("expectURL");
	}

}
