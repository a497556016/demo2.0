package com.demo.common.mapper;

import com.demo.common.model.Icons;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * IconsMapper数据库操作接口类
 * 
 **/

public interface IconsMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<Icons>  queryList ( Icons record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( Icons record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Icons  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( Icons record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( Icons record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Icons record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( Icons record );

}