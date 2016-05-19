package com.demo.system.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.constant.SystemKeys;
import com.demo.common.controller.BaseController;
import com.demo.common.model.Resource;
import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;
import com.demo.common.util.UserInfoUtil;
import com.demo.system.base.service.SystemService;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(SystemController.class);
	
	@Autowired
	private SystemService systemService;

	@RequestMapping("/login")
	@ResponseBody
	public BaseResponse<UserInfo> login(){
		UserInfo userInfo = packageParam(UserInfo.class);
		BaseResponse<UserInfo> baseResponse = systemService.userLogin(userInfo);
		userInfo = baseResponse.getResultInfo();
		request.getSession().setMaxInactiveInterval(60*60*2);
		request.getSession().setAttribute(SystemKeys.LOGIN_USER_INFO_SESSION_NAME, userInfo);
		return baseResponse;
	}
	
	@RequestMapping("/logout")
	public void logout(){
		request.getSession().setMaxInactiveInterval(0);
		request.getSession().removeAttribute(SystemKeys.LOGIN_USER_INFO_SESSION_NAME);
		try {
			response.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getUserMenu")
	@ResponseBody
	public Resource getUserMenu(){
		
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(SystemKeys.LOGIN_USER_INFO_SESSION_NAME);
		List<Resource> resources = userInfo.getResources();
		Resource resource = new Resource();
		resource.setExpanded(true);
		resource.setCode("");
		
		this.loadChildrenResource(resource,resources);
		
		return resource;
	}

	private void loadChildrenResource(Resource resource,
			List<Resource> resources) {
		List<Resource> children = new ArrayList<>();
		for (Resource resource2 : resources) {
			if(resource.getCode().equals(resource2.getpCode())){
				children.add(resource2);
				if(!resource2.getLeaf()){
					loadChildrenResource(resource2, resources);
				}
			}
		}
		resource.setChildren(children);
	}
}
