package com.demo.system.userRoleConfig.service;

import java.util.List;

import com.demo.common.model.Role;
import com.demo.common.model.UserInfo;
import com.demo.common.response.BaseResponse;
import com.demo.common.response.PageResponse;

public interface UserRoleConfigService {

	PageResponse<UserInfo> queryUserInfos(UserInfo userInfo);

	BaseResponse<UserInfo> addUser(UserInfo userInfo);

	BaseResponse<?> deleteUser(List<Integer> ids);

	BaseResponse<?> modifyUser(UserInfo userInfo);

	PageResponse<Role> queryRoles();

}
