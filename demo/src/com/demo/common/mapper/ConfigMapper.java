package com.demo.common.mapper;

import com.demo.common.model.Config;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * ConfigMapper数据库操作接口类
 * 
 **/

public interface ConfigMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<Config>  queryList ( Config record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( Config record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Config  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( Config record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Config record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Config record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Config record );

}