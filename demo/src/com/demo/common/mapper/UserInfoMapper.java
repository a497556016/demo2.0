package com.demo.common.mapper;

import java.util.List;

import com.demo.common.model.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * UserInfoMapper数据库操作接口类
 * 
 **/

public interface UserInfoMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<UserInfo>  queryList ( UserInfo record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( UserInfo record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	UserInfo  selectByPrimaryKey ( @Param("id") Integer id );

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey ( @Param("id") Integer id );

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert( UserInfo record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( UserInfo record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( UserInfo record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( UserInfo record );

	/**
	 * 查询用户登录信息
	 * @param userInfo
	 * @return
	 */
	List<UserInfo> queryLoginUserInfo(UserInfo userInfo);

	UserInfo getUserInfoById(String userId);

}