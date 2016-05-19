package com.demo.system.userRoleConfig.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.controller.BaseController;
import com.demo.common.model.Role;
import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;
import com.demo.common.response.PageResponse;
import com.demo.system.userRoleConfig.service.UserRoleConfigService;
import com.icss.ro.framework.right.dao.UsergroupRoleSearchDAO;

@Controller
@RequestMapping("/userRoleConfig")
public class UserRoleConfigController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(UserRoleConfigController.class);

	@Autowired
	private UserRoleConfigService userRoleConfigService;
	
	@RequestMapping("/auth/queryUserInfos")
	@ResponseBody
	public PageResponse<UserInfo> queryUserInfos(){
		UserInfo userInfo = packageParam(UserInfo.class);
		
		PageResponse<UserInfo> pageResponse = userRoleConfigService.queryUserInfos(userInfo);
		
		return pageResponse;
	}
	
	@RequestMapping("/auth/addUser")
	@ResponseBody
	public BaseResponse<UserInfo> addUser(){
		UserInfo userInfo = packageParam(UserInfo.class);
		
		BaseResponse<UserInfo> baseResponse = userRoleConfigService.addUser(userInfo);
		
		return baseResponse;
	}
	
	@RequestMapping("/auth/deleteUser")
	@ResponseBody
	public BaseResponse<?> deleteUser(@RequestBody List<Integer> ids){
		
		BaseResponse<?> baseResponse = userRoleConfigService.deleteUser(ids);
		
		return baseResponse;
	}
	
	@RequestMapping("/auth/modifyUser")
	@ResponseBody
	public BaseResponse<?> modifyUser(@RequestBody UserInfo userInfo){
		
		BaseResponse<?> baseResponse = userRoleConfigService.modifyUser(userInfo);
		
		return baseResponse;
	}
	
	@RequestMapping("/auth/queryRoles")
	@ResponseBody
	public PageResponse<Role> queryRoles(){
		
		PageResponse<Role> pageResponse = userRoleConfigService.queryRoles();
		
		return pageResponse;
	}
	
}
