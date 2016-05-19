package com.demo.common.mapper;

import com.demo.common.model.RoleResRelation;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * RoleResRelationMapper数据库操作接口类
 * 
 **/

public interface RoleResRelationMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<RoleResRelation>  queryList ( RoleResRelation record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( RoleResRelation record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	RoleResRelation  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( RoleResRelation record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( RoleResRelation record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( RoleResRelation record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( RoleResRelation record );

}