package com.demo.common.mapper;

import com.demo.common.model.UserRoleRelation;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * UserRoleRelationMapper数据库操作接口类
 * 
 **/

public interface UserRoleRelationMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<UserRoleRelation>  queryList ( UserRoleRelation record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( UserRoleRelation record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	UserRoleRelation  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( UserRoleRelation record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( UserRoleRelation record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( UserRoleRelation record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( UserRoleRelation record );

}