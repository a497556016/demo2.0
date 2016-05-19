package com.demo.common.mapper;

import java.util.List;

import com.demo.common.model.Role;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * RoleMapper数据库操作接口类
 * 
 **/

public interface RoleMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<Role>  queryList ( Role record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( Role record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Role  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( Role record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Role record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Role record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Role record );
	
	/**
	 * 根据用户编码查询用户的角色
	 * @param personCode
	 * @return
	 */
	List<Role> queryRoleByPersonCode(String personCode);

}