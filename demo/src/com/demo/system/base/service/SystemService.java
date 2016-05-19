package com.demo.system.base.service;

import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;

public interface SystemService {
	/**
	 * 用户登录
	 * @param userInfo
	 * @return
	 */
	BaseResponse<UserInfo> userLogin(UserInfo userInfo);

}
