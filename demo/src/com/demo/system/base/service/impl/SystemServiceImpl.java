package com.demo.system.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.common.mapper.BasicInfoMapper;
import com.demo.common.mapper.ConfigMapper;
import com.demo.common.mapper.IconsMapper;
import com.demo.common.mapper.ResourceMapper;
import com.demo.common.mapper.RoleMapper;
import com.demo.common.mapper.UserInfoMapper;
import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;
import com.demo.common.util.Md5Util;
import com.demo.system.base.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {
	private static final Logger LOGGER = Logger.getLogger(SystemServiceImpl.class);
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private IconsMapper iconsMapper;
	@Autowired
	private BasicInfoMapper basicInfoMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public BaseResponse<UserInfo> userLogin(UserInfo userInfo) {
		BaseResponse<UserInfo> baseResponse = new BaseResponse<>();
		
		String password = userInfo.getPassword();
		password = Md5Util.encrypt(password);
		userInfo.setPassword(password);
		userInfo.setLimit(1);
		
		List<UserInfo> userInfos = userInfoMapper.queryLoginUserInfo(userInfo);
		if(null!=userInfos&&userInfos.size()==1){
			baseResponse.setSuccess(true);
			baseResponse.setResultInfo(userInfos.get(0));
		}
		
		return baseResponse;
	}
	
	
	
}
