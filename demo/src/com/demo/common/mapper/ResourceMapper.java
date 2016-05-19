package com.demo.common.mapper;

import java.util.List;

import com.demo.common.model.Resource;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ResourceMapper数据库操作接口类
 * 
 **/

public interface ResourceMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<Resource>  queryList ( Resource record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( Resource record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Resource  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( Resource record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Resource record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Resource record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Resource record );

	/**
	 * 查询用户的权限
	 * @param personCode
	 * @return
	 */
	List<Resource> queryResourcesByPersonCode(String personCode);
}