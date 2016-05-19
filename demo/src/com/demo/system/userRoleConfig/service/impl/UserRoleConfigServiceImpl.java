package com.demo.system.userRoleConfig.service.impl;

import java.util.Collections;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.security.provider.MD5;

import com.demo.common.mapper.UserInfoMapper;
import com.demo.common.model.Role;
import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;
import com.demo.common.response.PageResponse;
import com.demo.common.util.Md5Util;
import com.demo.system.userRoleConfig.service.UserRoleConfigService;

@Service
public class UserRoleConfigServiceImpl implements UserRoleConfigService {
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public PageResponse<UserInfo> queryUserInfos(UserInfo userInfo) {
		PageResponse<UserInfo> pageResponse = new PageResponse<>();
		
		List<UserInfo> userInfos = userInfoMapper.queryList(userInfo);
		for (UserInfo userInfo2 : userInfos) {
			userInfo2.setPassword(null);
		}
		Long count = userInfoMapper.queryCount(userInfo);
		pageResponse.setRows(userInfos);
		pageResponse.setTotal(count);
		
		return pageResponse;
	}

	@Override
	public BaseResponse<UserInfo> addUser(UserInfo userInfo) {
		BaseResponse<UserInfo> baseResponse = new BaseResponse<>();
		
		String password = userInfo.getPassword();
		password = Md5Util.encrypt(password);
		userInfo.setPassword(password);
		int i = userInfoMapper.insertSelective(userInfo);
		User user = identityService.newUser(userInfo.getPersonCode());
		user.setPassword(userInfo.getPassword());
		user.setEmail(userInfo.getEmail());
		user.setLastName(userInfo.getLastName());
		identityService.saveUser(user);
		
		baseResponse.setResultInfo(userInfo);
		baseResponse.setSuccess(i>0);
		return baseResponse;
	}

	@Override
	public BaseResponse<?> deleteUser(List<Integer> ids) {
		BaseResponse<?> baseResponse = new BaseResponse<>();
		int count = 0;
		for (Integer id : ids) {
			UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
			identityService.deleteUser(userInfo.getPersonCode());
			count += userInfoMapper.deleteByPrimaryKey(id);
		}
		baseResponse.setSuccess(count==ids.size());
		return baseResponse;
	}

	@Override
	public BaseResponse<?> modifyUser(UserInfo userInfo) {
		BaseResponse<?> baseResponse = new BaseResponse<>();
		String password = userInfo.getPassword();
		if(null!=password){
			password = Md5Util.encrypt(password);
			userInfo.setPassword(password);
		}
		int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
		String userId = userInfo.getPersonCode();
		String lastName = userInfo.getLastName();
		String email = userInfo.getEmail();
		User user = identityService.newUser(userId);
		if(!StringUtils.isEmpty(password)){
			user.setPassword(password);
		}
		if(!StringUtils.isEmpty(lastName)){
			user.setLastName(lastName);
		}
		if(!StringUtils.isEmpty(email)){
			user.setEmail(email);
		}
		identityService.deleteUser(userId);
		identityService.saveUser(user);
		baseResponse.setSuccess(i>0);
		return baseResponse;
	}

	@Override
	public PageResponse<Role> queryRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}
