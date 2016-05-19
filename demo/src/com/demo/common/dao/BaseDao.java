package com.demo.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.common.constant.BeanName;
import com.demo.common.init.Context;

public class BaseDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
	
	private static BaseDao baseDao;
	
	private SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	private BaseDao (){};
	public static BaseDao getInstance(){
		if(null==baseDao){
			baseDao = new BaseDao();
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) Context.getInstance().getBean(BeanName.SQL_SESSION_FACTORY);
			baseDao.setSqlSessionFactory(sqlSessionFactory);
		}
		return baseDao;
	}
	
	public <K,T> Map<K,T> selectMap(String sqlLabel){
		return sqlSessionFactory.openSession().selectMap(sqlLabel, sqlLabel);
	}
	
	public <E> List<E> selectOne(String sqlLabel){
		return sqlSessionFactory.openSession().selectOne(sqlLabel);
	}
	
	public <E> List<E> selectOne(String sqlLabel,Object obj){
		return sqlSessionFactory.openSession().selectOne(sqlLabel, obj);
	}
	
	public <E> List<E> select(String sqlLabel){
		return sqlSessionFactory.openSession().selectList(sqlLabel);
	}
	
	public <E> List<E> select(String sqlLabel,Object obj){
		return sqlSessionFactory.openSession().selectList(sqlLabel, obj);
	}
	
	public int insert(String sqlLabel,Object obj){
		return sqlSessionFactory.openSession().insert(sqlLabel,obj);
	}
	
	public int update(String sqlLabel,Object obj){
		return sqlSessionFactory.openSession().update(sqlLabel, obj);
	}
	
	public int delete(String sqlLabel){
		return sqlSessionFactory.openSession().delete(sqlLabel);
	}
	
	public int delete(String sqlLabel,Object obj){
		return sqlSessionFactory.openSession().delete(sqlLabel, obj);
	}
}
