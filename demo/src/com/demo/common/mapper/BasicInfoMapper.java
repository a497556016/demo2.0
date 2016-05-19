package com.demo.common.mapper;

import com.demo.common.model.BasicInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * BasicInfoMapper数据库操作接口类
 * 
 **/

public interface BasicInfoMapper{


	/**
	 * 
	 * 查询列表
	 * 
	 **/
	java.util.List<BasicInfo>  queryList ( BasicInfo record );

	/**
	 * 
	 * 查询总数
	 * 
	 **/
	java.lang.Long  queryCount ( BasicInfo record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	BasicInfo  selectByPrimaryKey ( @Param("id") Integer id );

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
	int insert( BasicInfo record );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	int insertSelective( BasicInfo record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( BasicInfo record );

	/**
	 * 
	 * 修改（根据主键ID修改）
	 * 
	 **/
	int updateByPrimaryKey ( BasicInfo record );

}